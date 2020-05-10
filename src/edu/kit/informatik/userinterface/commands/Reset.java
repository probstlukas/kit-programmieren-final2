package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.cardgame.CardGame;
import edu.kit.informatik.exception.InvalidInputException;
import edu.kit.informatik.exception.LogicException;
import edu.kit.informatik.userinterface.InOutput;

/**
 * Command to reset the {@link CardGame}.
 *
 * @author Lukas Probst
 * @version 1.0
 */
class Reset extends Command {
    /**
     * Name of the command.
     */
    private static final String NAME = "reset";

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    Reset() {
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
        game.resetGame();
        Terminal.printLine(InOutput.OK_MESSAGE.toString());
    }

    @Override
    void parse(String input) throws InvalidInputException {
        validateNoArguments(input, NAME);
    }
}
