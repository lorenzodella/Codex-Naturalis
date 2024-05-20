package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.GUIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapListener extends ClientController implements ActionListener {
    private CardButton selectedCardButton;
    private GUIController guiController;
    
    public MapListener(ClientSender sender, GUIController guiController) {
        super(sender);
        this.guiController = guiController;
        reset();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        String[] s = b.getName().split(";");
        if(selectedCardButton ==null) {
            guiController.log("You must select a card and a side before playing a card");
            return;
        }
        //clientSender.playCard(guiController.getUsername(), Integer.parseInt(button.getName()), Integer.parseInt(s[1]), s[0], button.getCardSide());
        guiController.log("You played card " + Integer.parseInt(selectedCardButton.getName()) + " on card " + s[0] + " angle " + s[1] + " with side " + selectedCardButton.getCardSide());
        selectedCardButton.setSelected(false);
        reset();
    }
    
    public void setChosenCard(CardButton button) {
        this.selectedCardButton = button;
    }

    public boolean isCardChosen() {
        return selectedCardButton !=null;
    }

    public void reset(){
        selectedCardButton = null;
    }
}
