package com.codeforall.online.server;

import com.codeforall.online.server.commands.Command;
import com.codeforall.online.server.commands.CommandHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles communication with a connected client.
 */
public class ClientConnection implements Runnable {

    private final Socket socket;
    private final Server server;
    private String name;
    private PrintWriter out;

    /**
     * Constructs a new ClientConnection with the specified socket and server.
     * @param socket the client's socket
     * @param server the server instance
     */
    public ClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            BufferedReader in = openStreams();

            chooseUsername(in);

            send(Messages.WELCOME);
            send(Messages.HELP);

            if (!server.addClient(this)) {
                send(Messages.SERVER_FULL);
                close();
            }

            while (!socket.isClosed()) {
                listen(in);
            }

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
            server.remove(this);
        }
    }

    /**
     * Listens for messages from the client.
     * @param in the BufferedReader to read messages from
     * @throws IOException if an I/O error occurs when reading from the socket
     */
    private void listen(BufferedReader in) throws IOException {
        String message = in.readLine();
        CommandHandler clientInputHandler = Command.getFromString(message).getHandler();
        clientInputHandler.handle(server, this, message);
    }

    /**
     * Opens the input and output streams for the client socket.
     * @return a BufferedReader for reading client input
     * @throws IOException if an I/O error occurs when opening the streams
     */
    private BufferedReader openStreams() throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Prompts the client to choose a username.
     * @param in the BufferedReader to read client input from
     * @throws IOException if an I/O error occurs when reading from the socket
     */
    private void chooseUsername(BufferedReader in) throws IOException {
        while (this.name == null) {
            send(Messages.PICK_NAME);
            String name = in.readLine();
            CommandHandler newUserHandler = Command.NEW_USER.getHandler();
            newUserHandler.handle(server, this, name);
        }
    }

    /**
     * Closes the client connection.
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
    }

    /**
     * Sends a message to the client.
     * @param message the message to send
     */
    public void send(String message) {
        out.println(message);
    }


    /**
     * Gets the client's username.
     * @return the client's username
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the client's username.
     * @param name the new username
     */
    public void setName(String name) {
        this.name = name;
    }
}
