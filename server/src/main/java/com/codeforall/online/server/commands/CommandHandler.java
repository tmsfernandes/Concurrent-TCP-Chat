package com.codeforall.online.server.commands;

import com.codeforall.online.server.ClientConnection;
import com.codeforall.online.server.Server;

/**
 * Handler for commands sent by clients
 */
public interface CommandHandler {

    /**
     * Handles the command sent by a client.
     * @param server  the server instance
     * @param sender  the client connection that sent the command
     * @param message the message containing the command
     */
    void handle(Server server, ClientConnection sender, String message);
}
