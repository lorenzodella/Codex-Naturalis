package it.polimi.ingsw.controller.messages;

import java.io.Serializable;

public class Message implements Serializable {
    private String result;  // positivo
    private Exception exc; // negativo

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

    @Override
    public String toString() {
        return "Message{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
