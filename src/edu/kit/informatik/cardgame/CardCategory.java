package edu.kit.informatik.cardgame;

/**
 * Represents the category of a {@link PlayingCard}.
 *
 * @author Lukas Probst
 * @version 1.0
 */
public enum CardCategory {
    /**
     * Resources category. With a certain combination of resources
     * it is possible to build tools, structures and initiate rescue operations.
     */
    RESOURCES,
    /**
     * Animals category. If the player draws a card from this category, he must roll the dice
     * and may lose all his resources.
     */
    ANIMALS,
    /**
     * Catastrophe category. If the player draws the card from the this category,
     * he loses his {@link Item#FIREPLACE fireplace} and all his collected resources
     * except those stored in his {@link Item#SHACK shack}.
     */
    CATASTROPHE
}