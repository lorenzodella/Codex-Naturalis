package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.controller.messages.AcknowledgeMessage;

public class SendChatBroadcastMessage extends ClientMessage {
    /**
     * the nickname of the sender
     */
    String sender;
    /**
     * the actual message that the sender wants to send
     */
    String message;

    public SendChatBroadcastMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction(){
        return SendChatBroadcastMessage.SEND_CHAT_BROADCAST;
    }
}
