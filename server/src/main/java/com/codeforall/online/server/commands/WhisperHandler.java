package com.codeforall.online.server.commands;

import com.codeforall.online.server.ClientConnection;
import com.codeforall.online.server.Messages;
import com.codeforall.online.server.Server;

/**
 * Handles the command to send a private message to a specific user.
 */
public class WhisperHandler implements CommandHandler {

    /**
     * Handles the whisper command by sending the message to the specified user.
     * @param server  the server instance
     * @param sender  the client connection that sent the command
     * @param message the message containing the command
     */
    @Override
    public void handle(Server server, ClientConnection sender, String message) {

        if (!isValid(message)) {
            Command.INVALID.getHandler().handle(server, sender, Messages.WHISPER_USAGE);
            return;
        }

        ClientConnection receiver = server.getClientByName(message.split(" ")[1]);

        if (receiver == null) {
            Command.INVALID.getHandler().handle(server, sender, Messages.INVALID_USERNAME);
            return;
        }

        message = removeWords(message, 2);
        receiver.send(sender.getName() + Messages.WHISPER + ": " + message);
    }

    /**
     * Checks if the message is valid.
     * @param message the message to check
     * @return true if the message is valid, false otherwise
     */
    private boolean isValid(String message) {
        return message.split(" ").length > 2;
    }

    /**
     * Removes the specified number of words from the start of the phrase.
     * @param phrase the phrase to modify
     * @param wordsToRemove the number of words to remove
     * @return the modified phrase
     */
    private String removeWords(String phrase, int wordsToRemove) {
        while (wordsToRemove > 0) {
            phrase = phrase.substring(phrase.indexOf(' ') + 1);
            wordsToRemove--;
        }

        return phrase;
    }
}
