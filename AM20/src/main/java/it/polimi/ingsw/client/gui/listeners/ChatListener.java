package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUIController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatListener extends ClientController {
    private GUIController guiController;

    public ChatListener(ClientSender sender, GUIController guiController) {
        super(sender);
        this.guiController = guiController;
    }

    public void send(String message, String recipient) {
        if(recipient.equals("everyone"))
            clientSender.sendBroadcastChatMessage(guiController.getUsername(), message);
        else
            clientSender.sendChatMessage(guiController.getUsername(), recipient, message);
    }
}
