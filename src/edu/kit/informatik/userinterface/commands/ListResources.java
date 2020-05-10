package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.cardgame.PlayingCard;
import edu.kit.informatik.exception.InvalidInputException;
import edu.kit.informatik.exception.LogicException;

import java.util.Deque;

/**
 * Command to list all resources of the player.
 *
 * @author Lukas Probst
 * @version 1.0
 */
class ListResources extends Command {
    /**
     * Name of the command.
     */
    private static final String NAME = "list-resources";

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    ListResources() {
    }

    /**
     * Gets the name of the command.
     *
     * @return the name of the command
     */
    static String getName() {
        return NAME;
    }

    @Override
    public void execute() throws LogicException {
        final Deque<PlayingCard> resources = game.listResources();
        if (!resources.isEmpty()) {
            resources.forEach(Terminal::printLine);
        } else {
            Terminal.printLine("EMPTY");
        }
    }

    @Override
    void parse(String input) throws InvalidInputException {
        validateNoArguments(input, NAME);
    }
}
