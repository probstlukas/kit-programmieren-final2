package edu.kit.informatik.exception;

/**
 * This exception is thrown when a method cannot be executed with the provided arguments at this time.
 *
 * @author Lukas Probst
 * @version 1.0
 */
public class LogicException extends Exception {

    /**
     * Creates a new LogicException with the given detailed message.
     *
     * @param message some detailed error message (null is not allowed)
     */
    public LogicException(final String message) {
        super(message);
    }
}