package com.codeforall.online.server.commands;

import com.codeforall.online.server.ClientConnection;
import com.codeforall.online.server.Server;

/**
 * Handles the command to list all connected clients.
 */
public class ListHandler implements CommandHandler {

    /**
     * Handles the list command by sending the list of connected clients to the sender.
     * @param server  the server instance
     * @param sender  the client connection that sent the command
     * @param message the message containing the command
     */
    @Override
    public void handle(Server server, ClientConnection sender, String message) {
        sender.send(server.listClients());
    }
}
