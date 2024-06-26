package it.polimi.ingsw.controller.messages;
/**
 * Message that needs to be sent, to the player that just made a mistake, to inform him of the error that just occurred
 */
public class ErrorMessage extends Message{
    /**
     * type of error
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
