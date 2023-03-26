package session;

import card.Card;
import exception.InvalidMoveException;

import java.util.*;

public class Session {


    private final List<SessionMove> sessionMoves;





    public Session() {
        this.sessionMoves = new ArrayList<>();
    }

    public void checkMoves() throws InvalidMoveException{
        this.sessionMoves.forEach(this::checkIfMoveIsValid);
    }

    private void checkIfMoveIsValid(SessionMove move) throws InvalidMoveException{
        checkIfCardsAreValid(move);

        switch (move.getMove().toLowerCase()) {

            case "hit":
                if(move.getPlayerCardsValue() > 21) {
                    throw new InvalidMoveException("Cannot hit when bust", move.getSessionDataString());
                }
                if(move.getPlayerCardsValue() > 19 && move.getActor() == 'P') { //
                    throw new InvalidMoveException("Hitting will definitely make player go bust", move.getSessionDataString());
                }
                if (move.getDealerCardsValue() > 17) {
                    throw new InvalidMoveException("Dealer should not have hit if their hand exceeds 17", move.getSessionDataString());
                }
                break;

            case "stand":
                if(move.getPlayerCardsValue() > 21) {
                    throw new InvalidMoveException("Player is already bust", move.getSessionDataString());
                }

                break;
            case "win":
                if(move.getActor() == 'P' && move.getDealerCardsValue() < 17) {
                    throw new InvalidMoveException("Dealer should have hit a new card", move.getSessionDataString());
                }

                if( move.getDealerCardsValue() > move.getPlayerCardsValue() || move.getPlayerCardsValue() > 21) {
                    throw new InvalidMoveException("Player should have lost", move.getSessionDataString());
                }
                break;

            case "lose":
                if(move.getPlayerCardsValue() >= move.getDealerCardsValue() && move.getPlayerCardsValue() <= 21 ||
                        move.getDealerCardsValue() > 21 && move.getPlayerCardsValue() <= 21){
                    throw new InvalidMoveException("Player should have won", move.getSessionDataString());
                }
                break;
        }

    }

    private void checkIfCardsAreValid(SessionMove move) {
        List<Card> usedCardsList = new ArrayList<>();
        usedCardsList.addAll(move.getPlayerCards());
        usedCardsList.addAll(move.getDealerCards());

        usedCardsList.forEach(card -> {
            if(card.getValue() < 0) {
                throw new InvalidMoveException("Move contains an invalid card", move.getSessionDataString());
            }
        });

        Set<Card> usedCardsSet = new HashSet<>(usedCardsList); //set does not allow duplicate cards
        if(usedCardsSet.size() != usedCardsList.size()){
            throw new InvalidMoveException("Move contains a duplicate card", move.getSessionDataString());
        }
    }






    public void addMove(SessionMove move) {
        this.sessionMoves.add(move);
    }

    public List<SessionMove> getSessionMoves() {
        return this.sessionMoves;
    }

}
