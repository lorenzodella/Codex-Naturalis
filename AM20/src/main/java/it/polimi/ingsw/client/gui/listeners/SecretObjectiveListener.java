package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUIController;
import it.polimi.ingsw.client.gui.GUIUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Listener that detects clicks on the secret objective cards during the Objective phase
 */
public class SecretObjectiveListener extends ClientController implements ActionListener {
    private GUIController guiController;

    public SecretObjectiveListener(ClientSender sender, GUIController guiController) {
        super(sender);
        this.guiController = guiController;
    }
    /**
     * Every time that the listener detects a click on this button, it calls a client sender's method in order to
     * send the request of choosing this card as a secret object, to the server.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //capisco qual è l'obiettivo scelto
        JButton b = (JButton) e.getSource();
        String s = b.getName();

        if(s!=null) {
            //mando messaggio al server
            clientSender.chooseObjective(guiController.getUsername(), Integer.parseInt(s));
            if (Integer.parseInt(s) == 1) {
                guiController.log("You just chose the objective number 1");
            } else {
                guiController.log("You just chose the objective number 0");
            }
        }else
            GUIUtils.showError(b.getRootPane(),"Choose one secret objective and click confirm");
    }
}
