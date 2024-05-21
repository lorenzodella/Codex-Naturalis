package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.listeners.YourCardsListener;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class YourCardsPanel extends JPanel {

    CardButton[] cardButtons;

    public YourCardsPanel() {
        super();

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Your cards");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);

        cardButtons = new CardButton[3];


        cardButtons[0] = new CardButton();
        cardButtons[0].setName("0");
        cardButtons[0].setPreferredSize(GUIUtils.cardDim);


        cardButtons[1] = new CardButton();
        cardButtons[1].setName("1");
        cardButtons[1].setPreferredSize(GUIUtils.cardDim);


        cardButtons[2] = new CardButton();
        cardButtons[2].setName("2");
        cardButtons[2].setPreferredSize(GUIUtils.cardDim);

        //aggiungo fisicamente i bottoni
        add(cardButtons[0]);
        add(cardButtons[1]);
        add(cardButtons[2]);

        setCardsClickable(true);
    }

    public void update(List<PlayableCard> cards){
        if(cards.size()>=1)
            cardButtons[0].update(cards.get(0));
        else
            cardButtons[0].clear();

        if(cards.size()>=2) {
            cardButtons[1].update(cards.get(1));
        }else {
            cardButtons[1].clear();
        }

        if(cards.size()>=3) {
            cardButtons[2].update(cards.get(2));
        }else {
            cardButtons[2].clear();
        }

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

    public void setHidden(){
        for(CardButton card : cardButtons){
            card.hid();
        }

    }
}
