package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.CardButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Every time that this listener detects a click on a card button it sends the chosen card to the mapListener,
 * who is going to detect the click that represents the position where the player wants to play the card.
 * Both the YourCardsListener and the mapListener call a client sender's method in order to
 * send the request of playing this specific card in that specific spot, to the server.
 */
public class YourCardsListener extends ClientController implements ActionListener {
    /**
     * reference to the mapListener
     */
    private MapListener mapListener;
    private CardButton[] cardButtons;

    public YourCardsListener(ClientSender sender, MapListener mapListener) {
        super(sender);
        this.mapListener = mapListener;
    }

    public void setCardButtons(CardButton[] cardButtons) {
        this.cardButtons = cardButtons;
    }

    /**
     * Every time that the listener detects a click on this button, it calls a MapListener's method in order to
     * send the request of choosing this card as a playibg card.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        CardButton button = (CardButton) e.getSource();
        if(button.isSelected()) {
            mapListener.reset();
            button.setSelected(false);
        }
        else {
            mapListener.setChosenCard(button);
            for (CardButton b : cardButtons) {
                b.setSelected(false);
            }
            button.setSelected(true);
        }
    }
}
