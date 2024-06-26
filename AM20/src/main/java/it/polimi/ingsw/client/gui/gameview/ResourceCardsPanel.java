package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.listeners.DeckCoveredListener;
import it.polimi.ingsw.client.gui.listeners.DeckVisibleListener;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.ResourceCard;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * The resource cards panel used for both visible cards and top of the deck
 */
public class ResourceCardsPanel extends JPanel {
    CardButton o1;
    CardButton o2;
    /**
     * If the cards are visible or not
     */
    boolean visible;

    public ResourceCardsPanel(boolean visible) {
        super();
        this.visible = visible;

        o1 = new CardButton();
        o1.setName(String.valueOf(Deck.RESOURCE_CARDS));
        o1.setPreferredSize(GUIUtils.cardDim);
        add(o1);

        if(visible) {
            o1.setName(Deck.RESOURCE_CARDS +";0");
            o2 = new CardButton();
            o2.setName(Deck.RESOURCE_CARDS +";1");
            o2.setPreferredSize(GUIUtils.cardDim);
            add(o2);
        }
    }

    public ResourceCardsPanel(ResourceCardsPanel copy){
        super();
        this.visible = copy.visible;

        o1 = new CardButton(copy.o1);
        o1.setName(copy.o1.getName());
        o1.setPreferredSize(GUIUtils.cardDim);
        add(o1);

        if(visible) {
            o2 = new CardButton(copy.o2);
            o2.setName(copy.o2.getName());
            o2.setPreferredSize(GUIUtils.cardDim);
            add(o2);
        }
    }

    /**
     * This method allows to update the cards every time that a player picks a card
     * @param visibleCard1 the new resource visible card 1
     * @param visibleCard2 the new resource visible card 2
     */
    public void updateVisible(ResourceCard visibleCard1, ResourceCard visibleCard2){
        if(visibleCard1!=null)
            o1.update(visibleCard1);
        else
            o1.clear();

        if(visibleCard2!=null)
            o2.update(visibleCard2);
        else
            o2.clear();
    }

    /**
     * This method allows to update the top of the deck
     * @param top the new top of the resource deck
     */
    public void updateTop(ResourceCard top){
        if(top != null){
            top.setSide(PlayableCard.BACK);
            o1.update(top);
            o1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.DARK_GRAY));
        }
        else{
            o1.clear();
        }
    }

    /**
     * This method allows to set the listener for the top of the deck
     * @param deckCoveredListener the listener
     */
    public void setDeckCoveredListener(DeckCoveredListener deckCoveredListener){
        o1.addActionListener(deckCoveredListener);
    }

    /**
     * This method allows to set the listener for the visible cards
     * @param deckVisibleListener the listener
     */
    public void setDeckVisibleListener(DeckVisibleListener deckVisibleListener){
        o1.addActionListener(deckVisibleListener);
        o2.addActionListener(deckVisibleListener);
    }

    /**
     * This method allows to set the cards clickable or not. Used when the player has to pick a card
     * @param clickable true if the cards have to be clickable, false otherwise
     */
    public void setCardsClickable(boolean clickable){
        o1.setClickable(clickable);
        if(o2!=null)
            o2.setClickable(clickable);
    }
}
