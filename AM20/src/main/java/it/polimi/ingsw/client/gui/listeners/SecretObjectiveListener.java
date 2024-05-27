package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUIController;
import it.polimi.ingsw.client.gui.GUIUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecretObjectiveListener extends ClientController implements ActionListener {
    private GUIController guiController;

    public SecretObjectiveListener(ClientSender sender, GUIController guiController) {
        super(sender);
        this.guiController = guiController;
    }

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
