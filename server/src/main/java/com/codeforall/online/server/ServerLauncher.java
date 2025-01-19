package com.codeforall.online.server;

import java.io.IOException;

/**
 * Entry point for the server application.
 * Initializes and starts the server.
 * @see Server
 */
public class ServerLauncher {

    private static final int DEFAULT_PORT = 8090;
    
    /**
     * The main method of the server application.
     * @param args the command line arguments; expects an optional port number
     */
    public static void main(String[] args) {

        try {
            Server server = new Server(args.length > 0 ? Integer.valueOf(args[0]) : DEFAULT_PORT);
            server.start();

        } catch (IOException e) {
            System.err.println("Error opening server socket: " + e.getMessage());

        } catch (NumberFormatException e) {
            System.err.println("Error port must be a valid number: " + args[0]);
        }
    }
}
