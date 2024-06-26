package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUIController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Every time that a player clicks the send button in the chat, the chatListener receives the information about the message
 * and it calls the client sender's method in order to send this message.
 */
public class ChatListener extends ClientController {
    private GUIController guiController;

    public ChatListener(ClientSender sender, GUIController guiController) {
        super(sender);
        this.guiController = guiController;
    }

    /**
     * method that allows the sender to send the message to the receiver through the client sender
     * @param message body of the message
     * @param recipient nickname of the receiver of the message
     */
    public void send(String message, String recipient) {
        if(recipient.equals("everyone"))
            clientSender.sendBroadcastChatMessage(guiController.getUsername(), message);
        else
            clientSender.sendChatMessage(guiController.getUsername(), recipient, message);
    }
}
