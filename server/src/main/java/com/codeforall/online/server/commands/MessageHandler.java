package com.codeforall.online.server.commands;

import com.codeforall.online.server.ClientConnection;
import com.codeforall.online.server.Server;

/**
 * Handles the command to send a message to all clients.
 */
public class MessageHandler implements CommandHandler {

    /**
     * Handles the message command by broadcasting the message to all clients.
     * @param server  the server instance
     * @param sender  the client connection that sent the command
     * @param message the message containing the command
     */
    @Override
    public void handle(Server server, ClientConnection sender, String message) {

        if (!isValid(message)) {
            return;
        }

        server.broadcast(sender.getName() + ": " + message);
    }

    /**
     * Checks if the message is valid.
     * @param message the message to check
     * @return true if the message is valid, false otherwise
     */
    private boolean isValid(String message) {
        return !message.trim().isEmpty();
    }
}