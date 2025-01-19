package com.codeforall.online.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents a client that connects to a server and handles communication.
 */
public class Client {

    private final Socket socket;
    private final ExecutorService service;

    /**
     * Constructs a new Client and establishes a connection to the specified host and port.
     * @param host the server host
     * @param port the server port
     * @throws IOException if an I/O error occurs when creating the socket
     */
    public Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.service = Executors.newSingleThreadExecutor();
    }

    /**
     * Starts the client and listens for messages from the Server
     */
    public void start() {

        service.submit(new KeyboardHandler(socket));

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (!socket.isClosed()) {
                waitMessage(reader);
            }

        } catch (IOException e) {
            System.err.println("Error handling socket connection: " + e.getMessage());
        }
    }
    /**
     * Waits for and processes messages from the server.
     * @param reader the BufferedReader to read messages from
     * @throws IOException if an I/O error occurs when reading from the socket
     */
    private void waitMessage(BufferedReader reader) throws IOException {
        String message = reader.readLine();

        if (message == null) {
            System.out.println("Connection closed from server side");
            System.exit(0);
        }

        System.out.println(message);
    }
}
