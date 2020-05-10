package edu.kit.informatik.userinterface;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.cardgame.CardGame;
import edu.kit.informatik.exception.InvalidInputException;
import edu.kit.informatik.exception.LogicException;
import edu.kit.informatik.userinterface.commands.Command;
import edu.kit.informatik.userinterface.commands.CommandFactory;

/**
 * User input and output are handled here. Exception handling for invalid input also takes place here.
 * A session can be started and stopped.
 *
 * @author Lukas Probst
 * @version 1.0
 */
public class Session {
    private boolean running = true;
    private CardGame game;

    /**
     * After the session is started, this method remains in a loop until the {@link this#terminate()} method is called.
     */
    void run() {
        game = new CardGame();
        final CommandFactory factory = new CommandFactory(this);
        boolean lost = false;
        while (running) {
            final String input = Terminal.readLine();
            try {
                final Command command = factory.getCommand(input);
                command.execute();
                if (lost != game.isLost()) {
                    lost = game.isLost();
                    if (lost) {
                        Terminal.printLine(InOutput.LOST_MESSAGE.toString());
                    }
                }
            } catch (final InvalidInputException | LogicException e) {
                Terminal.printError(e.getMessage());
            }
        }
    }

    /**
     * Terminates the active session.
     */
    public void terminate() {
        running = false;
    }

    /**
     * Gets the current game.
     *
     * @return the current game that the user "plays with".
     */
    public CardGame getGame() {
        return game;
    }
}
