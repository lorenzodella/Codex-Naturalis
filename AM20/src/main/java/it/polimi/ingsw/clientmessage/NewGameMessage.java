package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.server.Connection;

public class NewGameMessage extends ClientMessage {
    String client;
    int numPlayers;

    public NewGameMessage(String client, int numPlayers) {
        this.client = client;
        this.numPlayers = numPlayers;
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

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public String getAction(){
        return NewGameMessage.NEWGAME;
    }
}
