package it.polimi.ingsw.client.gui.gameview;
//5
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.ResourceCard;

import it.polimi.ingsw.client.gui.listeners.DeckCoveredListener;
import it.polimi.ingsw.client.gui.listeners.DeckVisibleListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * The resource cards deck panel contains the two resource visible cards and the top of the resource deck
 */
public class ResourceCardsDeckPanel extends JPanel {
    ResourceCardsPanel resourceCardsPanelCovered;
    ResourceCardsPanel resourceCardsPanelVisible;

    /**
     * The panel that contains two other panels:
     * 1. the two resource visible cards
     * 2. the top of the resource deck
     * @param resourceCardsPanelCovered panel of the top of the resource deck
     * @param resourceCardsPanelVisible panel of the two visible cards
     */
    public ResourceCardsDeckPanel(ResourceCardsPanel resourceCardsPanelCovered, ResourceCardsPanel resourceCardsPanelVisible) {
        super();
        this.resourceCardsPanelCovered = resourceCardsPanelCovered;
        this.resourceCardsPanelVisible = resourceCardsPanelVisible;

        setLayout(new BorderLayout());
        TitledBorder titledBorder = new TitledBorder("Resource cards");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);
        setOpaque(false);

        add(resourceCardsPanelCovered, BorderLayout.CENTER);
        add(resourceCardsPanelVisible, BorderLayout.SOUTH);
    }

    public ResourceCardsDeckPanel(ResourceCardsDeckPanel copy){
        super();
        this.resourceCardsPanelCovered = new ResourceCardsPanel(copy.resourceCardsPanelCovered);
        this.resourceCardsPanelVisible = new ResourceCardsPanel(copy.resourceCardsPanelVisible);

        setLayout(new BorderLayout());
        TitledBorder titledBorder = new TitledBorder("Resource cards");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);
        setOpaque(false);

        add(resourceCardsPanelCovered, BorderLayout.CENTER);
        add(resourceCardsPanelVisible, BorderLayout.SOUTH);
    }

    /**
     * This method allows to update the cards every time that a player picks a card
     * @param visibleCard1 the new resource visible card 1
     * @param visibleCard2 the new resource visible card 2
     * @param top the new top of the resource deck
     */
    public void update(ResourceCard visibleCard1, ResourceCard visibleCard2, ResourceCard top){
        resourceCardsPanelVisible.updateVisible(visibleCard1, visibleCard2);
        resourceCardsPanelCovered.updateTop(top);
    }

    /**
     * This method allows to set the listeners for the resource cards deck
     * @param deckCoveredListener the listener for the covered deck
     * @param deckVisibleListener the listener for the visible deck
     */
    public void setDeckListener(DeckCoveredListener deckCoveredListener, DeckVisibleListener deckVisibleListener){
        resourceCardsPanelCovered.setDeckCoveredListener(deckCoveredListener);
        resourceCardsPanelVisible.setDeckVisibleListener(deckVisibleListener);
    }

    /**
     * This method allows to set the cards clickable or not. This is useful when the player has to pick a card
     * @param clickable true if the cards are clickable, false otherwise
     */
    public void setCardsClickable(boolean clickable){
        resourceCardsPanelCovered.setCardsClickable(clickable);
        resourceCardsPanelVisible.setCardsClickable(clickable);
    }
}
