package it.polimi.ingsw.controller.messages;

public class BroadcastChatMessage extends ChatMessage{
    /**
     * this is the broadcast message class and thanks to this method a sender can send the same specific message to all players at once
     * @param sender the player that needs to send a message
     * @param message the specific message that the sender needs to send
     */

    public BroadcastChatMessage(String sender, String message) {
        super(sender, null, message);
    }


}
