package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.exception.InvalidInputException;
import edu.kit.informatik.userinterface.Session;

/**
 * Command to terminate the session and thus quit the {@link edu.kit.informatik.cardgame.CardGame card game}.
 *
 * @author Lukas Probst
 * @version 1.0
 */
class Quit extends Command {
    /**
     * Name of the command.
     */
    private static final String NAME = "quit";
    private Session session;

    /**
     * Package private to avoid direct initialisation without using the {@link CommandFactory}.
     */
    Quit() {
    }

    /**
     * Gets the name of the command.
     *
     * @return a name which can be used to decide if this is the right command for a given
     *  user input
     */
    static String getName() {
        return NAME;
    }

    @Override
    public void execute() {
        assert (session != null);
        session.terminate();
    }

    @Override
    void parse(String input) throws InvalidInputException {
        validateNoArguments(input, NAME);
    }

    // In this case, the command really needs the session
    @Override
    void setSession(final Session session) {
        this.session = session;
    }
}