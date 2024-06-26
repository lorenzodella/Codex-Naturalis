package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.listeners.YourCardsListener;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

/**
 * Panel that contains the hand cards of a player
 */
public class YourCardsPanel extends JPanel {

    /**
     * The three cards of the player
     */
    CardButton[] cardButtons;

    public YourCardsPanel() {
        super();

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Your cards");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);

        createCards();

        //setCardsClickable(true);
    }

    public YourCardsPanel(String nickname){
        super();

        TitledBorder titledBorder = BorderFactory.createTitledBorder(nickname + "'s cards");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);

        createCards();
    }

    private void createCards() {
        cardButtons = new CardButton[3];

        cardButtons[0] = new CardButton();
        cardButtons[0].setName("0");
        cardButtons[0].setPreferredSize(GUIUtils.cardDim);
        cardButtons[0].enableMouseFlipping();

        cardButtons[1] = new CardButton();
        cardButtons[1].setName("1");
        cardButtons[1].setPreferredSize(GUIUtils.cardDim);
        cardButtons[1].enableMouseFlipping();

        cardButtons[2] = new CardButton();
        cardButtons[2].setName("2");
        cardButtons[2].setPreferredSize(GUIUtils.cardDim);
        cardButtons[2].enableMouseFlipping();

        //aggiungo fisicamente i bottoni
        add(cardButtons[0]);
        add(cardButtons[1]);
        add(cardButtons[2]);
    }

    /**
     * This method allows to update the cards every time there's a change
     * @param cards the cards of the player
     */
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

    /**
     * This method allows to set the cards clickable or not. Used when it's the player's turn
     * @param clickable true if the cards are clickable, false otherwise
     */
    public void setCardsClickable(boolean clickable){
        for(CardButton card : cardButtons){
            card.setClickable(clickable);
        }
    }

    /**
     * This method allows to set the listener of the cards
     * @param listener the listener of the cards
     */
    public void setYourCardsListener(YourCardsListener listener){
        listener.setCardButtons(cardButtons);
        for(CardButton card : cardButtons){
            card.addActionListener(listener);
        }
    }

    /**
     * This method allows to hide the cards for other players
     */
    public void setHidden(){
        for(CardButton card : cardButtons){
            card.hid();
        }

    }
}
