package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.cardgame.CardGame;
import edu.kit.informatik.cardgame.PlayingCard;
import edu.kit.informatik.exception.InvalidInputException;
import edu.kit.informatik.exception.LogicException;
import edu.kit.informatik.userinterface.InOutput;
import edu.kit.informatik.userinterface.Session;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* There are a few reasons that this is an abstract class rather than an interface: one being
 style reasons. I do not like the default methods in interfaces. Also validateNoArguments(...) and checkPattern(...)
 be questionable as static methods of an interface. Another reason is that an
 interface does not allow package-private methods and public methods would contradict the idea of this package.*/

/**
 * Base class for all commands.
 * A command task is to perform an operation on the game, depending on the command and its arguments.
 *
 * @author Lukas Probst
 * @version 1.0
 */
public abstract class Command {
    /**
     * This is the reference to the logic which allows commands to alter or query a {@link CardGame}.
     */
    protected CardGame game;

    /**
     * Runs the command.
     *
     * @throws LogicException if there is an error in the logic of the game
     */
    public abstract void execute() throws LogicException;

    /**
     * Checks its pattern and passes valid arguments to the command.
     *
     * @param input of the user
     * @throws InvalidInputException if the parsed user input is invalid
     */
    abstract void parse(String input) throws InvalidInputException;

    /**
     * This allows the a command to manipulate a {@link Session}. For instance, {@link Session#terminate()}
     * can be called on it or most commonly it will be used to get the current
     * {@link edu.kit.informatik.cardgame.CardGame card game}.
     * For commands that do not have to interact with the session this will be ignored.
     *
     * @param session the creating factory was initiated with
     */
    void setSession(Session session) {
        this.game = session.getGame();
    }

    /**
     * Validates that there are no arguments by comparing the {@code input} length and the {@code name} length.
     *
     * @param input of the user
     * @param name of the command
     * @throws InvalidInputException if the command does not expect any arguments
     */
    void validateNoArguments(String input, String name) throws InvalidInputException {
        if (input.length() != name.length()) {
            throw new InvalidInputException(InOutput.NO_ARGUMENTS_EXPECTED.toString());
        }
    }

    /**
     * Checks the pattern of the command.
     *
     * @param input to be checked
     * @param pattern of the command
     * @param name of the command
     * @return the matcher of the pattern
     * @throws InvalidInputException if the arguments are invalid
     */
    Matcher checkPattern(String input, Pattern pattern, String name) throws InvalidInputException {
        final Matcher matcher = pattern.matcher(input.substring(name.length()));
        if (!matcher.matches()) {
            throw new InvalidInputException(InOutput.INVALID_ARGUMENTS.toString());
        }
        return matcher;
    }

    /**
     * Parses a card deck which has 64 {@link PlayingCard playing cards} and it starts with the top card.
     * The playing card identifiers (wood, metal, plastic, spider, snake, tiger, thunderstorm)
     * are separated by exactly one comma.
     *
     * @param input to be parsed
     * @return a deque of playing cards
     * @throws InvalidInputException if the parsed card deck is incorrect
     */
    Deque<PlayingCard> parseCards(String input) throws InvalidInputException {
        final String[] stringList = input.split(InOutput.CARD_SEPARATOR.toString());
        final Deque<PlayingCard> playingCards = Arrays.stream(stringList)
                .map(PlayingCard::parse)
                .collect(Collectors.toCollection(ArrayDeque::new));
        if (playingCards.stream()
                .allMatch(cardType -> Collections.frequency(playingCards, cardType) == cardType.getRequiredAmount())) {
            return playingCards;
        } else {
            throw new InvalidInputException(InOutput.INVALID_CARD_DECK.toString());
        }
    }
}
