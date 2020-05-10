package edu.kit.informatik.cardgame;

import java.util.Optional;

/**
 * Represents a playing card in the {@link CardGame}.
 *
 * @author Lukas Probst
 * @version 1.0
 */
public enum PlayingCard {
    /**
     * Playing card wood of the card category resources.
     */
    WOOD(CardCategory.RESOURCES, null, null, 16),
    /***
     * Playing card metal of the card category resources.
     */
    METAL(CardCategory.RESOURCES, null, null, 16),
    /**
     * Playing card plastic of the card category resources.
     */
    PLASTIC(CardCategory.RESOURCES, null, null, 16),
    /**
     * Playing card spider of the card category animals.
     */
    SPIDER(CardCategory.ANIMALS, 4, 2, 5),
    /**
     * Playing card snake of the card category animals.
     */
    SNAKE(CardCategory.ANIMALS, 6, 3, 5),
    /**
     * Playing card tiger of the card category animals.
     */
    TIGER(CardCategory.ANIMALS, 8, 4, 5),
    /**
     * Playing card thunderstorm of the card category catastrophe.
     */
    THUNDERSTORM(CardCategory.CATASTROPHE, null, null, 1);

    private final CardCategory category;
    /**
     * Required dice size of the card.
     */
    private final Integer diceSize;
    /**
     * Minimum diced number to survive.
     */
    private final Integer minRoll;
    /**
     * Required amount of the playing card in the card deck.
     */
    private final int requiredAmount;

    /**
     * Creates a new playing card with the given {@code category}.
     *
     * @param category of the playing card
     * @param diceSize of the playing card
     * @param minRoll minimum number that must be rolled to survive
     * @param requiredAmount of the playing card in the card deck
     */
    PlayingCard(CardCategory category, Integer diceSize, Integer minRoll, int requiredAmount) {
        this.category = category;
        this.diceSize = diceSize;
        this.minRoll = minRoll;
        this.requiredAmount = requiredAmount;
    }

    /**
     * Gets the category of the playing card.
     *
     * @return the category of the playing card
     */
    public CardCategory getCategory() {
        return category;
    }

    /**
     * Gets the dice size of the playing card.
     *
     * @return the dice size of the playing card wrapped in an {@link Optional}
     */
    public Optional<Integer> getDiceSize() {
        return Optional.ofNullable(diceSize);
    }

    /**
     * Gets the minimum number that must be rolled to survive.
     *
     * @return the minimum number that must be rolled wrapped in an {@link Optional}
     */
    public Optional<Integer> getMinRoll() {
        return Optional.ofNullable(minRoll);
    }

    /**
     * Gets the required amount of the playing card in the card deck.
     *
     * @return the required amount of the playing card in the card deck
     */
    public int getRequiredAmount() {
        return requiredAmount;
    }

    /**
     * Parses a playing card in String format to an actual playing card.
     *
     * @param cardString to be parsed
     * @return the playing card or null if the String format does not represent a playing card
     */
    public static PlayingCard parse(String cardString)  {
        for (final PlayingCard card : values()) {
            if (card.name().toLowerCase().equals(cardString)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}