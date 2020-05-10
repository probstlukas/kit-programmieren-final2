package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.cardgame.PlayingCard;
import edu.kit.informatik.exception.InvalidInputException;
import edu.kit.informatik.exception.LogicException;

/**
 * Command to allow the player to draw the top card from the card deck.
 *
 * @author Lukas Probst
 * @version 1.0
 */
class Draw extends Command {
    /**
     * Name of the command.
     */
    private static final String NAME = "draw";

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    Draw() {
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
        final PlayingCard drawnCard = game.draw();
        Terminal.printLine(drawnCard);
    }

    @Override
    void parse(String input) throws InvalidInputException {
        validateNoArguments(input, NAME);
    }
}
