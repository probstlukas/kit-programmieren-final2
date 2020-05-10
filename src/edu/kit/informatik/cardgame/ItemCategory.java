package edu.kit.informatik.cardgame;

/**
 * Represents the category of an {@link Item}.
 *
 * @author Lukas Probst
 * @version 1.0
 */
public enum ItemCategory {
    /**
     * Tools category.
     */
    TOOLS,
    /**
     * Buildings category.
     */
    BUILDINGS,
    /**
     * Rescues category. If an item from this category was built, the player
     * usually has to roll the dice to decide whether a rescue will take place (Endeavor).
     */
    RESCUES
}