package it.polimi.ingsw.controller.messages;

import java.io.Serializable;

public class Message implements Serializable {
    private String result;  // positivo
    private int numOfConnectedPlayers;

    public static final String GENERIC = "generic";
    public static final String CONNECTIONACK = "connectionAck";
    public static final String STARTERCARDACK = "starterCardAck";
    public static final String ACKNOWLEDGE = "acknowledge";
    public static final String CHAT = "chat";
    public static final String OBJECTIVEACK = "objectiveAck";


    public String getType(){
        return Message.GENERIC;
    }

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
