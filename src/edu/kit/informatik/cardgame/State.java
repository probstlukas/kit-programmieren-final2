package edu.kit.informatik.cardgame;

import edu.kit.informatik.exception.LogicException;

/**
 * Saves the state of the {@link CardGame}, which consists of the state and the expected next action.
 * This enum-based state machine describes rules of the game
 * and provides methods to proceed to the next point of the game.
 *
 * @author Lukas Probst
 * @version 1.0
 */
enum State {
    /**
     * At the beginning of the {@link CardGame} the player has the opportunity to draw cards and build items.
     */
    SCAVENGE {
        @Override
        public State next(PlayingCard card) throws LogicException {
            switch (card.getCategory()) {
                case ANIMALS:
                    return State.ENCOUNTER;
                case RESOURCES:
                case CATASTROPHE:
                    return this;
                default:
                    throw new LogicException(ErrorMessages.NO_VALID_NEXT_STAGE.toString());
            }
        }

        @Override
        public State next(Item item) {
            switch (item) {
                case BALLON:
                case STEAMBOAT:
                    return State.WIN;
                case HANGGLIDER:
                case SAILINGRAFT:
                    return State.ENDEAVOR;
                default:
                    return this;
            }
        }
    },
    /**
     * If an {@link Item} has been built of the {@link ItemCategory#RESCUES} category,
     * the dice must usually be thrown to decide whether a rescue takes place.
     */
    ENDEAVOR {
        /*
        This was implemented in this way, because it is a rule of the game.
         */
        @Override
        public State next(int size, int diced) throws LogicException {
            if (size == 6) {
                return diced < 4 ? State.SCAVENGE : State.WIN;
            }
            throw new LogicException(ErrorMessages.NO_VALID_NEXT_STAGE.toString());
        }
    },
    /**
     * When a card of the category {@link CardCategory#ANIMALS} is drawn, the player must fight it.
     */
    ENCOUNTER {
        @Override
        public State next(int size, int diced) throws LogicException {
            if (size == 4 || size == 6 || size == 8) {
                return State.SCAVENGE;
            }
            throw new LogicException(ErrorMessages.NO_VALID_NEXT_STAGE.toString());
        }
    },
    /**
     * The game is lost and the player cannot continue.
     */
    LOST,
    /**
     * The player has successfully escaped from the desert island
     */
    WIN;

    /**
     * This method is invoked in order to go further in the {@link CardGame}.
     *
     * @param card last drawn card
     * @return the next state
     * @throws LogicException if no valid state can be reached
     */
    public State next(PlayingCard card) throws LogicException {
        throw new LogicException(ErrorMessages.NO_VALID_NEXT_STAGE.toString());
    }

    /**
     * This method is invoked in order to go further in the {@link CardGame}.
     *
     * @param item that was built
     * @return the next state
     * @throws LogicException if no valid state can be reached
     */
    public State next(Item item) throws LogicException {
        throw new LogicException(ErrorMessages.NO_VALID_NEXT_STAGE.toString());
    }

    /**
     * This method is invoked in order to go further in the {@link CardGame}.
     *
     * @param size of the dice
     * @param diced number
     * @return the next state
     * @throws LogicException if no valid state can be reached
     */
    public State next(int size, int diced) throws LogicException {
        throw new LogicException(ErrorMessages.NO_VALID_NEXT_STAGE.toString());
    }
}