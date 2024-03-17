package it.polimi.ingsw.model;

public abstract class Card {
    private String ID;

    public Card(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Card{" +
                "ID='" + ID + '\'' +
                '}';
    }
}
