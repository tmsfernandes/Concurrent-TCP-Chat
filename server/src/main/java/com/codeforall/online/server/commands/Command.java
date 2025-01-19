package com.codeforall.online.server.commands;

import com.codeforall.online.server.Messages;

/**
 * Defines the available commands and their handlers.
 */
public enum Command {
    INVALID("", new InvalidHandler()),
    MESSAGE("", new MessageHandler()),
    HELP("help", new HelpHandler()),
    NAME("name", new NameHandler()),
    LIST("list", new ListHandler()),
    WHISPER("whisper", new WhisperHandler()),
    NEW_USER("", new NewUserHandler()),
    QUIT("quit", new QuitHandler());

    private final String commandMessage;
    private final CommandHandler handler;

    /**
     * Constructs a Command with the specified command message and handler.
     * @param commandMessage the command message
     * @param handler        the handler for the command
     */
    Command(String commandMessage, CommandHandler handler) {
        this.commandMessage = commandMessage;
        this.handler = handler;
    }

    /**
     * Lists all available commands.
     *
     * @return a string listing all available commands
     */
    public static String commandsList() {

        StringBuilder builder = new StringBuilder("\nCommands List:\n");

        for (Command command : values()) {
            if (!command.commandMessage.isEmpty()) {
                builder.append(Messages.COMMAND_IDENTIFIER)
                        .append(command.commandMessage)
                        .append("\n");
            }
        }

        return builder.toString();
    }

    /**
     * Gets the targeted command
     * @param message the message to parse
     * @return the corresponding command
     */
    public static Command getFromString(String message) {

        if (message == null) {
            return QUIT;
        }

        if (!message.startsWith(Messages.COMMAND_IDENTIFIER)) {
            return MESSAGE;
        }

        String userCommand = message.split(" ")[0];

        for (Command command : values()) {
            if (userCommand.equals(Messages.COMMAND_IDENTIFIER + command.commandMessage)) {
                return command;
            }
        }

        return INVALID;
    }

    /**
     * Gets the handler for the command
     * @return the handler for the command
     */
    public CommandHandler getHandler() {
        return handler;
    }
}