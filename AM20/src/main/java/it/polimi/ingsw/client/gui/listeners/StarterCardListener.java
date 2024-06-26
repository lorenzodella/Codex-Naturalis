package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUIController;
import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener that detects clicks on the starter card during the Starter phase
 */
public class StarterCardListener extends ClientController implements ActionListener {
    private GUIController guiController;

    public StarterCardListener(ClientSender sender, GUIController guiController) {
        super(sender);
        this.guiController = guiController;
    }
    /**
     * Every time that the listener detects a click on this button, it calls a client sender's method in order to
     * send the request of choosing this side of the card, to the server.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //capisco qual è il side scelto
        JButton b = (JButton) e.getSource();
        String s = b.getName();

        if(s!=null) {
            //mando messaggio al server
            clientSender.chooseStarterCardSide(guiController.getUsername(), Integer.parseInt(s));
            if (Integer.parseInt(s) == PlayableCard.FRONT) {
                guiController.log("You just chose the front side of your starter card");
            } else {
                guiController.log("You just chose the back side of your starter card");
            }
        }else
            GUIUtils.showError(b.getRootPane(), "Choose the side of your starter card and confirm");

    }
}
