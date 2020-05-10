package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.cardgame.CardGame;
import edu.kit.informatik.cardgame.PlayingCard;
import edu.kit.informatik.exception.InvalidInputException;
import edu.kit.informatik.exception.LogicException;
import edu.kit.informatik.userinterface.InOutput;

import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command to allow the user to start a new {@link CardGame}.
 *
 * @author Lukas Probst
 * @version 1.0
 */
class Start extends Command {
    /**
     * Name of the command
     */
    private static final String NAME = "start";
    /**
     * The syntax of the command's arguments as a regular expression.
     */
    private static final Pattern PATTERN = Pattern.compile(String.format("%s(?<cards>%s)",
            InOutput.COMMAND_SEPARATOR,
            InOutput.CARDS_PATTERN));
    private Deque<PlayingCard> cards;

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    Start() {
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
        game.setCardDeck(cards);
        Terminal.printLine(InOutput.OK_MESSAGE.toString());
    }

    @Override
    public void parse(final String input) throws InvalidInputException {
        final Matcher matcher = checkPattern(input, PATTERN, NAME);
        cards = parseCards(matcher.group("cards"));
    }
}
