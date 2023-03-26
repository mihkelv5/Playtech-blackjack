package card;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Card {
    private final String symbol;
    private final String suit;
    private int value;
    List<String> validCards = Arrays.asList("A,2,3,4,5,6,7,8,9,10,J,Q,K".split(","));
    List<String> validSuits = Arrays.asList("S,H,C,D".split(","));

    public Card(String card) {

        if(card.equals("?")) {
            this.symbol = "?";
            this.suit = "";
            this.value = 0;

        } else {

            this.symbol = card.substring(0, card.length() - 1).toUpperCase();
            this.suit = card.substring(card.length() - 1).toUpperCase();

            //sets invalid value to an invalid card for it to be later found.
            if(!this.validCards.contains(this.symbol) || !this.validSuits.contains(this.suit)) {

                this.value = -999;
                return;
            }

            try { //if symbol has numeric value it will be parsed to an integer, otherwise face cards and ace will have their specific value assigned
                this.value = Integer.parseInt(this.symbol);
            } catch (NumberFormatException e) {
                if (this.symbol.equals("A")) {
                    this.value = 11;
                } else {
                    this.value = 10;
                }
            }
        }

    }
    public int getValue() {
        return value;
    }



    @Override
    public String toString() {
        return this.symbol +  this.suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return symbol.equals(card.symbol) && suit.equals(card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, suit, value, validCards, validSuits);
    }
}
