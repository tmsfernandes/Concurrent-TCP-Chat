package com.codeforall.online.server.commands;

import com.codeforall.online.server.ClientConnection;
import com.codeforall.online.server.Server;

/**
 * Handles the command to display help information.
 */
public class HelpHandler implements CommandHandler {

    /**
     * Handles the help command by sending the list of available commands to the sender.
     * @param server  the server instance
     * @param sender  the client connection that sent the command
     * @param message the message containing the command
     */
    @Override
    public void handle(Server server, ClientConnection sender, String message) {
        sender.send(Command.commandsList());
    }
}
