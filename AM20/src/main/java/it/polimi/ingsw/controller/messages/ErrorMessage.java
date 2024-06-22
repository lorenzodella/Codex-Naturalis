package it.polimi.ingsw.controller.messages;

public class ErrorMessage extends Message{
    /**
     * this attribute stands for the type of error
     */
    private String error;

    public ErrorMessage(Exception e){
        this.error = e.toString();

    }

    public String getError() {
        return error;
    }

    public ErrorMessage(String error){
        this.error = error;
    }

    @Override
    public String getType() {
        return Message.ERROR;
    }
}
