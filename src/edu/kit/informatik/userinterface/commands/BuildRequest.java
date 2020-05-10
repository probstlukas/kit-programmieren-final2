package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.cardgame.Item;
import edu.kit.informatik.exception.InvalidInputException;
import edu.kit.informatik.exception.LogicException;

import java.util.List;

/**
 * Command to list all possible buildable items that the player can build with his drawn resources.
 *
 * @author Lukas Probst
 * @version 1.0
 */
class BuildRequest extends Command {
    /**
     * Name of the command.
     */
    private static final String NAME = "build?";

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    BuildRequest() {
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
        final List<Item> buildableItems = game.buildableItems();
        if (!buildableItems.isEmpty()) {
            buildableItems.stream().map(Enum::toString).map(String::toLowerCase).forEach(Terminal::printLine);
        } else {
            Terminal.printLine("EMPTY");
        }
    }

    @Override
    void parse(String input) throws InvalidInputException {
        validateNoArguments(input, NAME);
    }
}
