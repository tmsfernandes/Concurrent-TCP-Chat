package com.codeforall.online.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Handles user input from the keyboard and sends it to the server.
 */
public class KeyboardHandler implements Runnable {

    private static final String EXIT = "/quit";
    private final Socket socket;

    /**
     * Constructs a new KeyboardHandler with the specified socket.
     * @param socket the socket connected to the server, to which user input will be sent
     */
    public KeyboardHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            while (!socket.isClosed()) {
                String input = scanner.nextLine();

                if (input.equals(EXIT)) {
                    System.exit(0);
                }

                writer.println(input);
            }

        } catch (IOException e) {
            System.err.println("Error handling socket connection: " + e.getMessage());
        }
    }
}
