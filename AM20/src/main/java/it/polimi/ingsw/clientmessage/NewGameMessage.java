package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.server.Connection;

public class NewGameMessage extends ClientMessage {
    /**
     * Nickname of the player that wants to create a new game
     */
    String client;
    /**
     * number of players that the player that just created the new game wants to play with
     */
    int numPlayers;
    /**
     * specific color of the pawn of the player
     */
    PawnColor color;


    public NewGameMessage(String client, PawnColor color, int numPlayers) {
        this.client = client;
        this.numPlayers = numPlayers;
        this.color = color;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public String getAction(){
        return NewGameMessage.NEWGAME;
    }

    public PawnColor getColor() {
        return color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }
}
