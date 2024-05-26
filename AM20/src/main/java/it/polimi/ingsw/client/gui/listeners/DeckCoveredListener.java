package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeckCoveredListener extends ClientController implements ActionListener {
    private GUIController guiController;

    public DeckCoveredListener(ClientSender sender, GUIController guiController) {
        super(sender);
        this.guiController = guiController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        String s = b.getName();
        try{
            clientSender.pickCard(guiController.getUsername(), Integer.parseInt(s));
            if(Integer.parseInt(s)==1) {
                guiController.log("You have picked a card from Resource Deck");
            }else{
                guiController.log("You have picked a card from Gold Deck");
            }
        }catch (NumberFormatException exception){
            
        }


    }
}
