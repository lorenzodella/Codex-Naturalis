package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cards.playable.PlayableCard;

import java.io.Serializable;
import java.util.Objects;

public abstract class Card implements Serializable {
    /**
     * this attribute stands for the id of this specific card and allows to uniquely identify it
     */
    private String ID;

    public Card(String ID) {
        this.ID = ID;
    }

    public String getID(){return this.ID;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(ID, card.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return "ID='" + ID + "', ";
    }

    public abstract int getSide();

    /**
     * This method changes the displayed side of the card
     */
    public abstract void flip();
}
