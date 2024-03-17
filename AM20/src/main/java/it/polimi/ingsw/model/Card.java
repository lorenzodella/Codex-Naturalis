package it.polimi.ingsw.model;

import java.util.Objects;

public abstract class Card {
    private String ID;

    public Card(String ID) {
        this.ID = ID;
    }

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
}
