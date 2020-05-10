package edu.kit.informatik.exception;

/**
 * This exception is used to indicate invalid input during command line parsing.
 *
 * @author Lukas Probst
 * @version 1.0
 */
public class InvalidInputException extends Exception {

    /**
     * Creates a new InvalidInputException with the given detailed message.
     *
     * @param message some detailed error message (null is not allowed)
     */
    public InvalidInputException(final String message) {
        super(message);
    }
}