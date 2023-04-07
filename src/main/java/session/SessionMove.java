package session;

import card.Card;
import exception.InvalidMoveException;

import java.util.*;

public class SessionMove implements Comparable<SessionMove> {
    private final String sessionDataString;
    private final int timeStamp;
    private final int sessionId;
    private final int playerId;
    private final char actor;
    private final String move;
    private final List<Card> dealerCards = new ArrayList<>();
    private final List<Card> playerCards = new ArrayList<>();


    public SessionMove(String dataString) throws IllegalArgumentException{
        this.sessionDataString = dataString;
        String[] data = dataString.split(",");
        this.timeStamp = Integer.parseInt(data[0]);
        this.sessionId = Integer.parseInt(data[1]);
        this.playerId = Integer.parseInt(data[2]);
        this.actor = data[3].toUpperCase().charAt(0);
        this.move = data[3].substring(2);
        Arrays.asList(data[4].split("-")).forEach(card -> dealerCards.add(new Card(card)));
        Arrays.asList(data[5].split("-")).forEach(card -> playerCards.add(new Card(card)));
    }



    public int getTimeStamp() {
        return timeStamp;
    }

    public int getSessionId() {
        return sessionId;
    }

    public char getActor() {
        return actor;
    }

    public String getMove() {
        return move;
    }


    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public int getPlayerCardsValue(){
        int value = 0;
        for(Card card: this.playerCards){
            value += card.getValue();
        }
        return value;
    }

    public int getDealerCardsValue(){
        int value = 0;
        for(Card card: this.dealerCards){
            value += card.getValue();
        }
        return value;
    }

    public String getSessionDataString() {
        return sessionDataString;
    }

    @Override
    public int compareTo(SessionMove o) {
        return Integer.compare(timeStamp, o.getTimeStamp());
    }

}
