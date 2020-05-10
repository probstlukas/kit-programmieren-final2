package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.cardgame.Item;
import edu.kit.informatik.exception.InvalidInputException;
import edu.kit.informatik.exception.LogicException;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Command to list the built items that are currently still in the player's possession.
 *
 * @author Lukas Probst
 * @version 1.0
 */
class ListBuildings extends Command {
    /**
     * Name of the command.
     */
    private static final String NAME = "list-buildings";

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    ListBuildings() {
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
        final List<Item> buildings = game.listBuildings();
        if (!buildings.isEmpty()) {
            final int size = buildings.size();
            IntStream.range(0, size)
                    .map(i -> size - i - 1)
                    .mapToObj(buildings::get)
                    .forEach(Terminal::printLine);
        } else {
            Terminal.printLine("EMPTY");
        }
    }

    @Override
    void parse(String input) throws InvalidInputException {
        validateNoArguments(input, NAME);
    }
}
