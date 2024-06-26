package it.polimi.ingsw.controller.messages;

/**
 * DAL SERVER
 * Message that is sent from a sender to a receiver every time that someone wants to talk to another player in private
 */
public class ChatMessage extends Message{

    /**
     * The player that sends the message
     */
    private String sender;
    /**
     * The player that receives a message
     */
    private String recipient;
    /**
     * The actual message
     */
    private String message;

    public ChatMessage(String sender, String recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getType() {
        return Message.CHAT;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
