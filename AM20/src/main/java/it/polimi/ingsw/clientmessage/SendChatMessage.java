package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.controller.messages.AcknowledgeMessage;

public class SendChatMessage extends ClientMessage{
    /**
     * the nickname of the sender
     */
    String sender;
    /**
     * the nickname of the player that is going to receive the message
     */
    String recipient;
    /**
     * the actual message that the sender wants to send
     */
    String message;

    public SendChatMessage(String sender, String recipient, String message) {
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

    public String getAction(){
        return SendChatMessage.SEND_CHAT;
    }
}
