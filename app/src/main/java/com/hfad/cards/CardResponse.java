package com.hfad.cards;

import java.util.List;

public class CardResponse {
    private boolean success;
    private List<Card> cards;
    private String deck_id;
    private int remaining;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getDeck_id() {
        return deck_id;
    }

    public void setDeck_id(String deck_id) {
        this.deck_id = deck_id;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
}
