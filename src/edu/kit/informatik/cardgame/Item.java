package edu.kit.informatik.cardgame;

import java.util.Optional;

/*
In the beginning I had implemented the current attributes as methods
(e. g. getDiceSize(), getBonus(), etc.) with a "switch (this)"-orgy.
But this was procedural nonsense and it would be like saying:
if I am card #1, then return value "Ace", if I am card #2, then return value "King",
and so on. The card has a state and just needs to return it. So the state is moved by
switch in the code - even though the state is fixed. Every time the state is to be extended,
the code would have to be adjusted in several places.
If a new value is to be added, changes would have to be made in a number of places.
1. Above at enum.
2. In getCategory.
3. In diceSize.
4. In minimumRoll.
5. In requiredAmount.
This is not the case with the constructor arguments. Hence, the current solution is better.


If the question should arise why I did not use constants for the integers in the enum-values:
a constant is already defined with the enum, which consists of the constant
values specified in the constructor. If the numbers were now represented as constants,
it would be like introducing a constant THREE_POINT_FOURTEEN = 3.14 to define PI = THREE_POINT_FOURTEEN.
 */

/**
 * Represents an item in the {@link CardGame}.
 *
 * @author Lukas Probst
 * @version 1.0
 */
public enum Item {
    /**
     * Axe of the tools category.
     * Gives combat bonus.
     */
    AXE(ItemCategory.TOOLS, false, null, 2, PlayingCard.METAL, PlayingCard.METAL,
            PlayingCard.METAL),
    /**
     * Club of the tools category.
     * Only gives a combat bonus if the player does not own an {@link Item#AXE axe}.
     */
    CLUB(ItemCategory.TOOLS, false, null, 1, PlayingCard.WOOD, PlayingCard.WOOD,
            PlayingCard.WOOD),
    /**
     * Shack of the buildings category.
     * Brings five of the player's {@link CardCategory#RESOURCES resources} to safety.
     */
    SHACK(ItemCategory.BUILDINGS, false, null, 0, PlayingCard.WOOD, PlayingCard.WOOD,
            PlayingCard.METAL, PlayingCard.PLASTIC, PlayingCard.PLASTIC),
    /**
     * Fireplace of the buildings category.
     * Allows the player to build a steamboat and a balloon.
     */
    FIREPLACE(ItemCategory.BUILDINGS, false, null, 0, PlayingCard.WOOD,
            PlayingCard.WOOD, PlayingCard.WOOD, PlayingCard.METAL),
    /**
     * Sailing raft of the rescues category. Guarantees the player a rescue
     * if a number greater than or equal to 4 is rolled with the 6-sided dice after building.
     */
    SAILINGRAFT(ItemCategory.RESCUES, false, 6, 0, PlayingCard.WOOD, PlayingCard.WOOD,
            PlayingCard.WOOD, PlayingCard.WOOD, PlayingCard.METAL,
            PlayingCard.METAL, PlayingCard.PLASTIC, PlayingCard.PLASTIC),
    /**
     * Hang glider of the rescues category. Guarantees the player a rescue
     * if a number greater than or equal to 4 is rolled with the 6-sided dice after building.
     */
    HANGGLIDER(ItemCategory.RESCUES, false, 6, 0, PlayingCard.WOOD, PlayingCard.WOOD,
            PlayingCard.METAL, PlayingCard.METAL, PlayingCard.PLASTIC, PlayingCard.PLASTIC, PlayingCard.PLASTIC,
            PlayingCard.PLASTIC),
    /**
     * Steamboat of the category rescues.
     * Can only be built if the player has a {@link Item#FIREPLACE fireplace}.
     */
    STEAMBOAT(ItemCategory.RESCUES, true, null, 0, PlayingCard.METAL, PlayingCard.METAL,
            PlayingCard.METAL, PlayingCard.METAL, PlayingCard.METAL, PlayingCard.METAL, PlayingCard.PLASTIC),
    /**
     * Balloon of the category rescues.
     * Can only be built if the player has a {@link Item#FIREPLACE fireplace}.
     */
    BALLON(ItemCategory.RESCUES, true, null, 0, PlayingCard.WOOD, PlayingCard.PLASTIC,
            PlayingCard.PLASTIC, PlayingCard.PLASTIC, PlayingCard.PLASTIC, PlayingCard.PLASTIC, PlayingCard.PLASTIC);

    private final ItemCategory category;
    /**
     * Required cards to build the item.
     */
    private final PlayingCard[] requiredCards;
    /**
     * Determines whether a fireplace is required to build the item or not.
     */
    private final boolean fireplaceRequired;
    /**
     * Required dice size of the item.
     */
    private final Integer diceSize;
    /**
     * Certain items provide a bonus which gets added to the diced number.
     */
    private final int bonus;

    /**
     * Creates a new item with the given {@code category}.
     *
     * @param category of the item to be added
     * @param fireplaceRequired determines whether the item requires a fireplace to be built
     * @param diceSize of the item
     * @param bonus of the item
     * @param requiredCards that are required to build the item
     */
    Item(ItemCategory category, boolean fireplaceRequired, Integer diceSize, int bonus, PlayingCard... requiredCards) {
        this.category = category;
        this.fireplaceRequired = fireplaceRequired;
        this.diceSize = diceSize;
        this.bonus = bonus;
        this.requiredCards = requiredCards;
    }

    /**
     * Gets the category of the item.
     *
     * @return the category of the item.
     */
    public ItemCategory getCategory() {
        return category;
    }

    /**
     * Gets the required cards to build the item.
     *
     * @return the required cards to build the item
     */
    public PlayingCard[] getRequiredCards() {
        return requiredCards;
    }

    /**
     * Returns <code>true</code> if the item requires a fireplace to be built.
     *
     * @return <code>true</code> if the item requires a fireplace to be built
     */
    public boolean isFireplaceRequired() {
        return fireplaceRequired;
    }

    /**
     * Gets the required dice size of the item.
     *
     * @return the required dice size wrapped in an {@link Optional}
     */
    public Optional<Integer> getDiceSize() {
        return Optional.ofNullable(diceSize);
    }

    /**
     * Gets the bonus of the item.
     *
     * @return the bonus of the item
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * Returns <code>true</code> if the item requires a certain dice size.
     *
     * @return <code>true</code> if the item requires a certain dice size
     */
    public boolean requiresDice() {
        return getDiceSize().isPresent();
    }

    /**
     * Parses an item in String format to an actual item.
     *
     * @param itemString to be parsed
     * @return the item or null if the String format does not represent an item
     */
    public static Item parse(String itemString) {
        for (final Item item : values()) {
            if (item.name().toLowerCase().equals(itemString)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
