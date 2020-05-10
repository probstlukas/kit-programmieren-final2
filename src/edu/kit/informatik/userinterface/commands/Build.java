package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.cardgame.Item;
import edu.kit.informatik.exception.InvalidInputException;
import edu.kit.informatik.exception.LogicException;
import edu.kit.informatik.userinterface.InOutput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command to build an {@link Item}.
 *
 * @author Lukas Probst
 * @version 1.0
 */
class Build extends Command {
    /**
     * Name of the command.
     */
    private static final String NAME = "build";
    /**
     * The syntax of the command's arguments as a regular expression.
     */
    private static final Pattern PATTERN = Pattern.compile(String.format("%s(?<buildType>%s)",
            InOutput.COMMAND_SEPARATOR,
            InOutput.ITEM_TYPE_PATTERN));
    private Item item;

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    Build() {
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
        Terminal.printLine(game.build(item));
    }

    @Override
    public void parse(String input) throws InvalidInputException {
        final Matcher matcher = checkPattern(input, PATTERN, NAME);
        item = Item.parse(matcher.group("buildType"));
    }
}
