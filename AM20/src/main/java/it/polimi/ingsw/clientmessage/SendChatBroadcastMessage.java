package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.controller.messages.AcknowledgeMessage;

public class SendChatBroadcastMessage extends ClientMessage {
    String sender;
    String message;

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
