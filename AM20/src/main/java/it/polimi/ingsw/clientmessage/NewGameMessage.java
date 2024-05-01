package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.server.Connection;

public class NewGameMessage extends ClientMessage {
    String client;
    int numPlayers;
    Connection callback;

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

    public Connection getCallback() {
        return callback;
    }

    public void setCallback(Connection callback) {
        this.callback = callback;
    }

    public String getAction(){
        return NewGameMessage.NEWGAME;
    }
}
