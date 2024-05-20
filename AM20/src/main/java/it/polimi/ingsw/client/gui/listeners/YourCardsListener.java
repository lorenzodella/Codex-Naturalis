package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.CardButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YourCardsListener extends ClientController implements ActionListener {

    private MapListener mapListener;
    private CardButton[] cardButtons;

    public YourCardsListener(ClientSender sender, MapListener mapListener) {
        super(sender);
        this.mapListener = mapListener;
    }

    public void setCardButtons(CardButton[] cardButtons) {
        this.cardButtons = cardButtons;
    }

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
