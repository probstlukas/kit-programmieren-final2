package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.exception.InvalidInputException;
import edu.kit.informatik.exception.LogicException;
import edu.kit.informatik.userinterface.InOutput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command to select and roll either a 4-, 6- or 8-sided dice.
 *
 * @author Lukas Probst
 * @version 1.0
 */
class RollDx extends Command {
    /**
     * Name of the command.
     */
    private static final String NAME = "rollD";
    /**
     * The syntax of the command's arguments as a regular expression.
     */
    private static final Pattern PATTERN = Pattern.compile(String.format("(?<dice>%s)%s(?<diced>%s)",
            InOutput.NUMBER_PATTERN,
            InOutput.COMMAND_SEPARATOR,
            InOutput.NUMBER_PATTERN));
    private int size;
    private int diced;

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    RollDx() {
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
        Terminal.printLine(game.setDiced(size, diced));
    }

    @Override
    void parse(String input) throws InvalidInputException {
        final Matcher matcher = checkPattern(input, PATTERN, NAME);
        try {
            size = Integer.parseInt(matcher.group("dice"));
        } catch (final NumberFormatException e) {
            throw new InvalidInputException(InOutput.INVALID_DICE_INTEGER.toString());
        }
        try {
            diced = Integer.parseInt(matcher.group("diced"));
        } catch (final NumberFormatException e) {
            throw new InvalidInputException(InOutput.INVALID_DICED_INTEGER.toString());
        }
    }
}
