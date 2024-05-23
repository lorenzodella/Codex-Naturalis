package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUIController;
import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StarterCardListener extends ClientController implements ActionListener {
    private GUIController guiController;

    public StarterCardListener(ClientSender sender, GUIController guiController) {
        super(sender);
        this.guiController = guiController;
    }

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
            GUIUtils.showError("Choose the side of your starter card and confirm");

    }
}
