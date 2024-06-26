package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.GUIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Listener that detects clicks on the visible cards buttons.
 */
public class DeckVisibleListener extends ClientController implements ActionListener {
    private CardButton selectedCardButton;
    private GUIController guiController;

    public DeckVisibleListener(ClientSender sender, GUIController guiController) {
        super(sender);
        this.guiController = guiController;
    }

    /**
     * Every time that the listener detects a click on this button, it calls a client sender's method in order to
     * send the request of drawing the visible card, to the server.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        String[] s = b.getName().split(";");
        clientSender.pickCard(guiController.getUsername(), Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        if(Integer.parseInt(s[0])==1) {
            if(Integer.parseInt(s[1])==0) {
                guiController.log("You have picked the first Resource card");
            }
            else{
                guiController.log("You have picked the second Resource card");
            }
        }else{
            if(Integer.parseInt(s[1])==0) {
                guiController.log("You have picked the first Gold card");
            }
            else{
                guiController.log("You have picked the second Gold card");
            }
        }

    }
}
