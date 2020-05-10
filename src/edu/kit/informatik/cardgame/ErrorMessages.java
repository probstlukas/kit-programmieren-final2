package edu.kit.informatik.cardgame;

/*
One could argue that these Strings do not belong in the game logic, but an alternative solution
would be way too much for this small program and it is still easy to replace these Strings in the ui.
*/

/**
 * String constants for the command line interface. The user output should not be
 * obtained via the {@link Enum#name()} method but via the {@link this#toString()} method.
 * This class contains all error messages that could be issued to the user.
 *
 * @author Lukas Probst
 * @version 1.0
 */
public enum ErrorMessages {
    /**
     * To indicate that there is no active game at the moment.
     */
    NO_ACTIVE_GAME("there is no active game at the moment"),
    /**
     * To indicated that there is already a game running.
     */
    ACTIVE_GAME("there is already an active game"),
    /**
     * To indicate that the game has not started yet.
     */
    GAME_NOT_STARTED("the game has not started yet"),
    /**
     * To indicate that there are no more cards left in the card deck.
     */
    NO_MORE_CARDS("no more cards left"),
    /**
     * To indicate that the item is already in the game.
     */
    ITEM_EXISTS("each item may only exist a maximum of once in a game"),
    /**
     * To indicate that the player does not have enough resources to build the item.
     */
    NOT_ENOUGH_RESOURCES("you do not have enough resources to build this item"),
    /**
     * To indicate that the stage of the game has to be scavenge.
     */
    SCAVENGE_STATE_REQUIRED("the stage of the game has to be scavenge"),
    /**
     * To indicate an invalid dice number for the given dice size.
     */
    INVALID_DICE_NUMBER("invalid dice number for this dice size"),
    /**
     * To indicate that an incorrect dice was used.
     */
    WRONG_DICE("wrong dice"),
    /**
     * To indicate that the current stage of the game  is incorrect.
     */
    WRONG_STAGE("wrong stage of the game"),
    /**
     * To indicate that no valid next state can be reached in the state machine.
     */
    NO_VALID_NEXT_STAGE("no valid next state can be reached");

    private final String text;

    /**
     * @param text of the error message
     */
    ErrorMessages(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
