package edu.kit.informatik.userinterface;

import edu.kit.informatik.cardgame.PlayingCard;

/**
 * String constants for the command line interface. The user output should not be
 * obtained via the {@link Enum#name()} method but via the {@link this#toString()} method.
 * This class contains regular expressions, input patterns and user output.
 *
 * @author Lukas Probst
 * @version 1.0
 */
public enum InOutput {
    /**
     * Regular expression for all cards from {@link PlayingCard}.
     */
    CARDS_PATTERN("((wood|metal|plastic|spider|snake|tiger|thunderstorm),){63}"
            + "(wood|metal|plastic|spider|snake|tiger|thunderstorm)"),
    /**
     * Regular expression for all items from {@link edu.kit.informatik.cardgame.Item Item}.
     */
    ITEM_TYPE_PATTERN("(axe|club|shack|fireplace|sailingraft|hangglider|steamboat|ballon)"),
    /**
     * Regular expression for a natural number excluding zero.
     */
    NUMBER_PATTERN("[+]?[0-9]*[1-9][0-9]*"),
    /**
     * Separates the command from its potential arguments.
     */
    COMMAND_SEPARATOR(" "),
    /**
     * Separates the cards that are initialised by the start command.
     */
    CARD_SEPARATOR(","),
    /**
     * The output to use for communicating successful execution
     */
    OK_MESSAGE("OK"),
    /**
     * To indicate that the player survived a fight with an animal.
     */
    SURVIVED_MESSAGE("survived"),
    /**
     * To indicate that the game is won.
     */
    WIN_MESSAGE("win"),
    /**
     * To indicate with the rollDx command that the diced number is smaller than the respective minimum roll value.
     */
    LOSE_MESSAGE("lose"),
    /**
     * To indicate that the game is lost.
     */
    LOST_MESSAGE("lost"),
    /**
     * To indicate that there is no such command.
     */
    UNKNOWN_COMMAND("unknown command"),
    /**
     * To indicate that the command did not expect any arguments.
     */
    NO_ARGUMENTS_EXPECTED("no arguments expected"),
    /**
     * To indicate that invalid arguments were passed.
     */
    INVALID_ARGUMENTS("invalid arguments"),
    /**
     * To indicate that the dice has to be a 32-bit integer.
     */
    INVALID_DICE_INTEGER("dice must be a 32-bit integer"),
    /**
     * To indicate that the diced number has to be a 32-bit integer.
     */
    INVALID_DICED_INTEGER("diced number must be a 32-bit integer"),
    /**
     * To indicate that the card deck is invalid.
     */
    INVALID_CARD_DECK("invalid card deck. The amount of certain cards is incorrect");

    private final String text;

    /**
     * @param text of the in- or output
     */
    InOutput(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
