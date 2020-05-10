package edu.kit.informatik.cardgame;

import edu.kit.informatik.exception.LogicException;
import edu.kit.informatik.userinterface.InOutput;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a card game. The methods of this class are used to control the game.
 * If a method is called at a point or with arguments that would violate the rules of the game,
 * methods of this class will throw a {@link LogicException}.
 *
 * @author Lukas Probst
 * @version 1.0
 */
public class CardGame {
    /**
     * Minimum dice value of the dice.
     */
    private static final int MIN_DICE_VALUE = 1;
    /* I will comment on the fact that I chose Deque over Stack.
    Deque exposes a set of operations which is all about being able to fetch/add/remove items from the start
    or end of a collection, iterate etc. There is deliberately no way to access an element by position,
    which Stack exposes because it's a subclass of Vector.
    In addition, Stack has no interface, so you end up committing to a specific concrete class,
    which isn't usually a good idea. Last but not least, Deque is likely to be faster than Stack
    when used as a stack, and faster than LinkedList when used as a queue.*/
    /**
     * Initial card deck of the game.
     */
    private Deque<PlayingCard> originalCardDeck;
    /**
     * Current card deck that can be altered
     */
    private Deque<PlayingCard> currentCardDeck;
    /**
     * Top card of the current card deck.
     */
    private PlayingCard card;
    /**
     * Deck of the player.
     */
    private final Deque<PlayingCard> resources;
    /**
     * Stores the player's items in his inventory.
     */
    private final List<Item> playerInventory;
    /**
     * Current state or rather stage of the game.
     */
    private State currentState;

    /**
     * Creates a new card game that initialises the collections and sets the current state to null.
     */
    public CardGame() {
        this.originalCardDeck = new ArrayDeque<>();
        this.resources = new ArrayDeque<>();
        this.playerInventory = new ArrayList<>();
        this.currentState = null;
    }

    /**
     * Returns <code>true</code> if the game is active.
     *
     * @return <code>true</code> if the game is active
     */
    private boolean isActive() {
        return currentState != null && currentState != State.WIN && currentState != State.LOST;
    }

    /**
     * Checks whether the game is active.
     *
     * @throws LogicException if there is no active game at the moment
     */
    private void checkActiveGame() throws LogicException {
        if (!isActive()) {
            throw new LogicException(ErrorMessages.NO_ACTIVE_GAME.toString());
        }
    }

    /**
     * Checks whether the game has already started.
     *
     * @throws LogicException if the game has not started yet
     */
    private void checkGameStarted() throws LogicException {
        if (currentState == null) {
            throw new LogicException(ErrorMessages.GAME_NOT_STARTED.toString());
        }
    }

    /**
     * Sets the card deck to the given {@code cardDeck} and copies it to {@link CardGame#currentCardDeck}
     *
     * @param cardDeck to be set
     * @throws LogicException if there is already an active game
     */
    public void setCardDeck(Deque<PlayingCard> cardDeck) throws LogicException {
        if (isActive()) {
            throw new LogicException(ErrorMessages.ACTIVE_GAME.toString());
        }
        this.originalCardDeck = cardDeck;
        reset();
    }

    /**
     * Draws the top card of the {@link CardGame#currentCardDeck}.
     *
     * @return the drawn card
     * @throws LogicException if it is not possible to draw a card from the card deck at the moment
     */
    public PlayingCard draw() throws LogicException {
        checkActiveGame();
        checkScavengeState();
        checkCardDeck();
        final PlayingCard drawnCard = currentCardDeck.getFirst();
        currentCardDeck.removeFirst();
        if (drawnCard.getCategory() == CardCategory.RESOURCES) {
            resources.addLast(drawnCard);
        } else if (drawnCard.getCategory() == CardCategory.CATASTROPHE) {
            // Player loses his fireplace
            playerInventory.remove(Item.FIREPLACE);
            removeResources();
        }
        currentState = currentState.next(drawnCard);
        checkIsLost();
        card = drawnCard;
        return drawnCard;
    }

    /**
     * Checks whether the {@link #currentCardDeck} has at least one card left.
     *
     * @throws LogicException if the card deck is empty
     */
    private void checkCardDeck() throws LogicException {
        if (currentCardDeck.isEmpty()) {
            throw new LogicException(ErrorMessages.NO_MORE_CARDS.toString());
        }
    }

    /**
     * Removes all resources from the player's inventory except those stored in the {@link Item#SHACK}.
     */
    private void removeResources() {
        if (playerInventory.contains(Item.SHACK)) {
            while (resources.size() > 5) {
                resources.removeFirst();
            }
        } else {
            resources.clear();
        }
    }

    /**
     * Returns all already drawn resources.
     *
     * @return all resources of the player
     * @throws LogicException if there is no active game
     */
    public Deque<PlayingCard> listResources() throws LogicException {
        checkGameStarted();
        return resources;
    }

    /**
     * Returns all owned items by the player.
     *
     * @return all owned items by the player
     * @throws LogicException if there is no active game
     */
    public List<Item> listBuildings() throws LogicException {
        checkGameStarted();
        return Collections.unmodifiableList(playerInventory);
    }

    /**
     * Builds the given {@code item}.
     *
     * @param item to be built
     * @return the result of the game
     * @throws LogicException if there is a problem with building the given item
     */
    public String build(Item item) throws LogicException {
        checkActiveGame();
        checkScavengeState();
        checkItemExistence(item);
        if (canBuild(item)) {
            playerInventory.add(item);
            Arrays.stream(item.getRequiredCards()).forEach(resources::removeLastOccurrence);
            currentState = currentState.next(item);
            if (item.getCategory() != ItemCategory.RESCUES) {
                checkIsLost();
            } else {
                if (!item.requiresDice()) {
                    gameOver();
                    return InOutput.WIN_MESSAGE.toString();
                } else {
                    return InOutput.OK_MESSAGE.toString();
                }
            }
            return InOutput.OK_MESSAGE.toString();
        }
        throw new LogicException(ErrorMessages.NOT_ENOUGH_RESOURCES.toString());
    }

    /**
     * Checks whether the given {@code item} already exists in the game.
     *
     * @param item to be checked
     * @throws LogicException if the item already exists
     */
    private void checkItemExistence(Item item) throws LogicException {
        if (playerInventory.contains(item)) {
            throw new LogicException(ErrorMessages.ITEM_EXISTS.toString());
        }
    }

    /**
     * Returns <code>true</code> if the item is buildable.
     *
     * @param item to be checked
     * @return <code>true</code> if the item is buildable
     */
    private boolean canBuild(Item item) {
        if (!isBuildable(item)) {
            return false;
        }
        final List<PlayingCard> required = new ArrayList<>(Arrays.asList(item.getRequiredCards()));
        resources.forEach(required::remove);
        return required.isEmpty();
    }

    /**
     * Returns <code>true</code> if the item is buildable
     * For an item to be buildable, the player must not already own it.
     * Certain items also need a fireplace to be buildable.
     *
     * @param item to be built
     * @return <code>true</code> the item is buildable
     */
    private boolean isBuildable(Item item) {
        return !playerInventory.contains(item)
                && (playerInventory.contains(Item.FIREPLACE) || !item.isFireplaceRequired());
    }

    /**
     * Returns a list of all buildable items.
     *
     * @return a list of all buildable items
     * @throws LogicException if the stage of the game is incorrect
     */
    public List<Item> buildableItems() throws LogicException {
        checkScavengeState();
        return Stream.of(Item.values())
                .filter(this::canBuild)
                .sorted(Comparator.comparing(Object::toString))
                .collect(Collectors.toList());
    }

    /**
     * Checks whether the {@link CardGame#currentState} is {@link State#SCAVENGE scavenge}.
     *
     * @throws LogicException if the current state is not scavenge
     */
    private void checkScavengeState() throws LogicException {
        if (currentState != State.SCAVENGE) {
            throw new LogicException(ErrorMessages.SCAVENGE_STATE_REQUIRED.toString());
        }
    }

    /**
     * Rolls the dice.
     *
     * @param size of the dice
     * @param diced number
     * @return result of the game
     * @throws LogicException if the dice value is incorrect; if the size of the dice is incorrect;
     *  if the current state of the game is incorrect
     */
    public String setDiced(int size, int diced) throws LogicException {
        checkActiveGame();
        checkDiceNumber(size, diced);
        if (currentState == State.ENCOUNTER) {
            checkCardDiceSize(size);
            final int dicedBonus = diced + playerInventory.stream().mapToInt(Item::getBonus).max().orElse(0);
            currentState = currentState.next(size, dicedBonus);
            if (dicedBonus > card.getMinRoll().get()) {
                return InOutput.SURVIVED_MESSAGE.toString();
            }
            removeResources();
            checkIsLost();
            return InOutput.LOSE_MESSAGE.toString();
        } else if (currentState == State.ENDEAVOR) {
            checkItemDiceSize(size);
            currentState = currentState.next(size, diced);
            checkIsLost();
            if (currentState == State.WIN) {
                gameOver();
                return InOutput.WIN_MESSAGE.toString();
            } else {
                return InOutput.LOSE_MESSAGE.toString();
            }
        } else {
            throw new LogicException(ErrorMessages.WRONG_STAGE.toString());
        }
    }

    /**
     * Checks whether the {@code size} of the dice matches the required dice size of the last built item.
     *
     * @param size of the dice
     * @throws LogicException if it is the wrong dice
     */
    private void checkItemDiceSize(int size) throws LogicException {
        if (!playerInventory.isEmpty()
                && size != playerInventory.get(playerInventory.size() - 1).getDiceSize().orElse(size)) {
            throw new LogicException(ErrorMessages.WRONG_DICE.toString());
        }
    }

    /**
     * Checks whether the {@code size} of the dice matches the required dice size of the last drawn card.
     *
     * @param size of the dice
     * @throws LogicException if it is the wrong dice
     */
    private void checkCardDiceSize(int size) throws LogicException {
        if (size != card.getDiceSize().orElse(size) ) {
            throw new LogicException(ErrorMessages.WRONG_DICE.toString());
        }
    }

    /**
     * Checks whether the {@code diced} number is valid or not.
     *
     * @param size of the dice
     * @param diced number
     * @throws LogicException if it is an invalid diced number
     */
    private void checkDiceNumber(int size, int diced) throws LogicException {
        if (diced > size || diced < MIN_DICE_VALUE) {
            throw new LogicException(ErrorMessages.INVALID_DICE_NUMBER.toString());
        }
    }

    /**
     * Returns <code>true</code> if the game is lost. This is the case if the {@link CardGame#currentState} is
     * {@link State#LOST lost} and the {@link CardGame#currentCardDeck} is empty.
     *
     * @return <code>true</code> if the game is lost
     */
    public boolean isLost() {
        return currentState == State.LOST;
    }

    /**
     * Checks whether the game is lost, and if so, sets the {@link CardGame#currentState} to {@link State#LOST lost}.
     * The game is lost if the player cannot draw cards anymore, cannot roll the dice and cannot build any more items.
     */
    private void checkIsLost() {
        if (currentState != State.LOST
                // Cannot draw cards anymore
                && currentCardDeck != null && currentCardDeck.isEmpty()
                // Cannot roll the dice
                && currentState != State.ENCOUNTER && currentState != State.ENDEAVOR
                // Cannot build the item
                && Stream.of(Item.values()).noneMatch(this::canBuild)) {
            gameOver();
            currentState = State.LOST;
        }
    }

    /**
     * This method sets the {@link CardGame#currentCardDeck} to null for the sake of beauty.
     * Otherwise there would be a half used stack in the object.
     * If the user were to access it somehow, a bug would occur.
     * In this way the user learns about it directly, because a NPE is thrown.
     */
    private void gameOver() {
        this.currentCardDeck = null;
    }

    /**
     * Resets the game if the game has started.
     *
     * @throws LogicException if there is no active game
     */
    public void resetGame() throws LogicException {
        checkGameStarted();
        reset();
    }

    /**
     * Resets the game by resetting the {@link CardGame#currentCardDeck} to
     * its {@link CardGame#originalCardDeck}, clearing the {@link CardGame#resources}, {@link CardGame#playerInventory}
     * and setting the {@link CardGame#currentState} to {@link State#SCAVENGE}.
     */
    private void reset() {
        this.currentCardDeck = new ArrayDeque<>(originalCardDeck);
        this.resources.clear();
        this.playerInventory.clear();
        this.currentState = State.SCAVENGE;
    }
}
