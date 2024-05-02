package it.polimi.ingsw.controller.messages;

import java.io.Serializable;

public class    Message implements Serializable {
    private String result;  // positivo
    private int numOfConnectedPlayers;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getNumOfConnectedPlayers() {
        return numOfConnectedPlayers;
    }

    public void setNumOfConnectedPlayers(int numOfConnectedPlayers) {
        this.numOfConnectedPlayers = numOfConnectedPlayers;
    }

    @Override
    public String toString() {
        return "Message{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
