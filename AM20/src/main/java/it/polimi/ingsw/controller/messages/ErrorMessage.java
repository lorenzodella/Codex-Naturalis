package it.polimi.ingsw.controller.messages;

public class ErrorMessage extends Message{


    private String error;

    public ErrorMessage(Exception e){
        this.error = e.toString();

    }

    public ErrorMessage(String error){
        this.error = error;
    }
}
