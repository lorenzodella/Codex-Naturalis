package it.polimi.ingsw.controller.messages;

import java.io.Serializable;

public class    Message implements Serializable {
    private String result;  // positivo
    private Exception exc; // negativo
    private int numOfConnectedPlayers;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Exception getExc() {
        return exc;
    }

    public void setExc(Exception exc) {
        this.exc = exc;
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
