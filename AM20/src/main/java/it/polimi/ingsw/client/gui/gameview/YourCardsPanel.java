package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.listeners.YourCardsListener;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.List;

public class YourCardsPanel extends JPanel {

    CardButton[] cardButtons;

    public YourCardsPanel(List<PlayableCard> playableCards) {
        super();

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Your cards");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);

        cardButtons = new CardButton[3];

        cardButtons[0] = new CardButton(playableCards.get(0));
        cardButtons[0].setName("0");
        cardButtons[0].setPreferredSize(GUIUtils.cardDim);

        cardButtons[1] = new CardButton(playableCards.get(1));
        cardButtons[1].setName("1");
        cardButtons[1].setPreferredSize(GUIUtils.cardDim);

        cardButtons[2] = new CardButton(playableCards.get(2));
        cardButtons[2].setName("2");
        cardButtons[2].setPreferredSize(GUIUtils.cardDim);

        add(cardButtons[0]);
        add(cardButtons[1]);
        add(cardButtons[2]);

        setCardsClickable(true);
    }

    public void setCardsClickable(boolean clickable){
        for(CardButton card : cardButtons){
            card.setClickable(clickable);
        }
    }

    public void setYourCardsListener(YourCardsListener listener){
        listener.setCardButtons(cardButtons);
        for(CardButton card : cardButtons){
            card.addActionListener(listener);
        }
    }
}
