package com.codeforall.online.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Manages client connections and handles server-side operations.
 */
public class Server {

    private static final int MAXIMUM_CLIENTS = 306;
    private final List<ClientConnection> clients;
    private final ServerSocket socket;
    private final ExecutorService executorService;


    /**
     * Constructs a new Server and binds it to the specified port.
     * @param port the port to bind the server to
     * @throws IOException if an I/O error occurs when opening the server socket
     */
    public Server(int port) throws IOException {
        socket = new ServerSocket(port);
        clients = Collections.synchronizedList(new LinkedList<>());
        executorService = Executors.newCachedThreadPool();
    }

    /**
     * Starts the server
     */
    public void start() {

        while (true) {
            waitConnection();
        }
    }

    /**
     * Waits for a client connection and handles it.
     */
    private void waitConnection() {
        try {
            Socket clientSocket = socket.accept();

            System.out.println(Messages.NEW_CONNECTION);

            ClientConnection connection = new ClientConnection(clientSocket, this);

            executorService.submit(connection);

        } catch (IOException e) {
            System.err.println("Error establishing connection: " + e.getMessage());
        }
    }

    /**
     * Adds a client to the server.
     * @param client the client to add
     * @return true if the client was added, false if the server is full
     */
    public boolean addClient(ClientConnection client) {
        synchronized (clients) {

            if (clients.size() >= MAXIMUM_CLIENTS) {
                return false;
            }

            clients.add(client);
            return true;
        }
    }

    /**
     * Broadcasts a message to all connected clients.
     * @param message the message to broadcast
     */
    public void broadcast(String message) {
        synchronized (clients) {
            for (ClientConnection client : clients) {
                client.send(message);
            }
        }
    }

    /**
     * Removes a client from the server.
     * @param client the client to remove
     */
    public void remove(ClientConnection client) {
        clients.remove(client);
    }

    /**
     * Lists all connected clients.
     * @return a string listing all connected clients
     */
    public String listClients() {
        StringBuilder list = new StringBuilder("\nConnected Clients:\n");

        synchronized (clients) {
            for (ClientConnection client : clients) {
                list.append(client.getName()).append("\n");
            }
        }

        return list.toString();
    }

    /**
     * Gets a client by their username.
     * @param name the username to search for
     * @return the client with the specified username, or null if not found
     */
    public ClientConnection getClientByName(String name) {
        synchronized (clients) {
            for (ClientConnection client : clients) {
                if (client.getName().equals(name)) {
                    return client;
                }
            }
        }

        return null;
    }
}
