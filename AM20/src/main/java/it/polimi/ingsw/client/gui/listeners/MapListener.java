package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.GUIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * YourCardsListener sents information about the selected card to the MapListener.
 * Every time that this listener detects a click on a button it calls a client sender's method in order to
 * send the request of playing the selected card in that specific spot, to the server.
 */
public class MapListener extends ClientController implements ActionListener {
    /**
     * button of the selected card sent from  {@link YourCardsListener}
     */
    private CardButton selectedCardButton;
    /**
     * reference to the GUIcontroller
     */
    private GUIController guiController;
    
    public MapListener(ClientSender sender, GUIController guiController) {
        super(sender);
        this.guiController = guiController;
        reset();
    }

    /**
     * Every time that the listener detects a click on this button, it calls a client sender's method in order to
     * send the request of playing this specific card "selectedCardButton" in this specific position, to the server.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (CardButton) e.getSource();
        String[] s = b.getName().split(";");
        if(selectedCardButton ==null) {
            guiController.log("You must select a card and a side before playing a card");
            return;
        }
        guiController.log("You played card " + Integer.parseInt(selectedCardButton.getName()) + " on card " + s[0] + " angle " + s[1] + " with side " + selectedCardButton.getCardSide());
        clientSender.playCard(guiController.getUsername(), Integer.parseInt(selectedCardButton.getName()), Integer.parseInt(s[1]), s[0],  selectedCardButton.getCardSide());
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
