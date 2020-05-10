package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.exception.InvalidInputException;
import edu.kit.informatik.userinterface.InOutput;
import edu.kit.informatik.userinterface.Session;

import java.util.TreeMap;
import java.util.function.Supplier;

/**
 * A command factory in the spirit of the factory pattern. It provides subclasses of
 * {@link Command} depending on user input. This class deals with user input
 * and should avoid accidentally creating invalid commands in the code outside of this package.
 *
 * @author Lukas Probst
 * @version 1.0
 */
public class CommandFactory {
    /**
     * Dictionary that stores the command package.
     * The key is a String and the value a Supplier of the type Command.
     */
    private static final TreeMap<String, Supplier<Command>> COMMAND_PACKAGE = new TreeMap<>();
    private final Session session;

    /* Static initialisation block that runs before the main() method
    and will execute only one time in the entire program.
    With reflection or annotation processing it would be possible to
    automatically add all subclasses of Command here.*/
    static {
        COMMAND_PACKAGE.put(Start.getName(), Start::new);
        COMMAND_PACKAGE.put(Draw.getName(), Draw::new);
        COMMAND_PACKAGE.put(ListResources.getName(), ListResources::new);
        COMMAND_PACKAGE.put(Build.getName(), Build::new);
        COMMAND_PACKAGE.put(ListBuildings.getName(), ListBuildings::new);
        COMMAND_PACKAGE.put(BuildRequest.getName(), BuildRequest::new);
        COMMAND_PACKAGE.put(RollDx.getName(), RollDx::new);
        COMMAND_PACKAGE.put(Reset.getName(), Reset::new);
        COMMAND_PACKAGE.put(Quit.getName(), Quit::new);
    }

    /**
     * Constructs a {@link CommandFactory} for one specific {@link Session}.
     *
     * @param session that uses this command factory
     */
    public CommandFactory(final Session session) {
        this.session = session;
    }

    /**
     * Gets a command from the command package.
     *
     * @param input of the user
     * @return the command that matches the supplied name or null if no command matches
     */
    private static Supplier<Command> getFromPackage(final String input) {
        /* This stream checks longer command names that start with the input in the tree map first
        and returns it. If none is found, it returns null.*/
        return COMMAND_PACKAGE.descendingKeySet()
                .stream()
                .filter(input::startsWith)
                .findFirst()
                .map(COMMAND_PACKAGE::get)
                .orElse(null);
    }

    /**
     * Gets a subclass of {@link Command} matching the {@code input}.
     *
     * @param input of the user
     * @return a reference to the corresponding command
     * @throws InvalidInputException if there is no matching command
     */
    public Command getCommand(final String input) throws InvalidInputException {
        final Command command;
        final Supplier<Command> model = getFromPackage(input);
        if (model == null) {
            throw new InvalidInputException(InOutput.UNKNOWN_COMMAND.toString());
        }
        command = model.get();
        command.parse(input);
        command.setSession(session);
        return command;
    }
}
