package com.codeforall.online.server.commands;

import com.codeforall.online.server.ClientConnection;
import com.codeforall.online.server.Messages;
import com.codeforall.online.server.Server;

/**
 * Handles the command to set a new user's name.
 */
public class NewUserHandler implements CommandHandler {

    /**
     * Handles the new user command by setting the user's name and broadcasting the join message.
     * @param server  the server instance
     * @param sender  the client connection that sent the command
     * @param name    the new user's name
     */
    @Override
    public void handle(Server server, ClientConnection sender, String name) {

        if(!isValid(name)) {
            Command.INVALID.getHandler().handle(server, sender, Messages.INVALID_USERNAME);
            return;
        }

        if(server.getClientByName(name) != null) {
            Command.INVALID.getHandler().handle(server, sender, Messages.NAME_IN_USE);
            return;
        }

        sender.setName(name);
        server.broadcast(sender.getName() + " " + Messages.JOIN_ALERT);
    }

    /**
     * Checks if the name is valid.
     * @param name the name to check
     * @return true if the name is valid, false otherwise
     */
    private boolean isValid(String name) {
        return !(name.split(" ").length > 2) || !(name.length() > 12);
    }
}
