package com.codeforall.online.server.commands;

import com.codeforall.online.server.ClientConnection;
import com.codeforall.online.server.Messages;
import com.codeforall.online.server.Server;

/**
 * Handles the command to quit the server.
 */
public class QuitHandler implements CommandHandler {

    /**
     * Handles the quit command by removing the sender from the server and broadcasting the leave message.
     * @param server  the server instance
     * @param sender  the client connection that sent the command
     * @param message the message containing the command
     */
    @Override
    public void handle(Server server, ClientConnection sender, String message) {
        server.remove(sender);
        server.broadcast(sender.getName() + " " + Messages.LEAVE);
        sender.close();
    }
}
