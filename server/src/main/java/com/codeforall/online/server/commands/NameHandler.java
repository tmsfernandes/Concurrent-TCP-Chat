package com.codeforall.online.server.commands;

import com.codeforall.online.server.ClientConnection;
import com.codeforall.online.server.Messages;
import com.codeforall.online.server.Server;

/**
 * Handles the command to change a user's name.
 */
public class NameHandler implements CommandHandler {

    /**
     * Handles the name command by changing the user's name and broadcasting the renaming message.
     * @param server  the server instance
     * @param sender  the client connection that sent the command
     * @param message the message containing the command
     */
    @Override
    public void handle(Server server, ClientConnection sender, String message) {

        if (!isValid(message)) {
            Command.INVALID.getHandler().handle(server, sender, Messages.NAME_USAGE);
            return;
        }

        String newName = message.split(" ")[1];

        if (server.getClientByName(newName) != null) {
            Command.INVALID.getHandler().handle(server, sender, Messages.NAME_IN_USE);
            return;
        }

        server.broadcast(sender.getName() + " " + Messages.RENAME + " " + newName);
        sender.setName(newName);
    }

    /**
     * Checks if the message is valid.
     * @param message the message to check
     * @return true if the message is valid, false otherwise
     */
    private boolean isValid(String message) {
        return message.split(" ").length == 2;
    }
}
