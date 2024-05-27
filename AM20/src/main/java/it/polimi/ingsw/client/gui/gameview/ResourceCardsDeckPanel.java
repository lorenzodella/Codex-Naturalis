package it.polimi.ingsw.client.gui.gameview;
//5
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.ResourceCard;

import it.polimi.ingsw.client.gui.listeners.DeckCoveredListener;
import it.polimi.ingsw.client.gui.listeners.DeckVisibleListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ResourceCardsDeckPanel extends JPanel {
    ResourceCardsPanel resourceCardsPanelCovered;
    ResourceCardsPanel resourceCardsPanelVisible;

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

    public void update(ResourceCard visibleCard1, ResourceCard visibleCard2, ResourceCard top){
        resourceCardsPanelVisible.updateVisible(visibleCard1, visibleCard2);
        resourceCardsPanelCovered.updateTop(top);
    }

    public void setDeckListener(DeckCoveredListener deckCoveredListener, DeckVisibleListener deckVisibleListener){
        resourceCardsPanelCovered.setDeckCoveredListener(deckCoveredListener);
        resourceCardsPanelVisible.setDeckVisibleListener(deckVisibleListener);
    }

    public void setCardsClickable(boolean clickable){
        resourceCardsPanelCovered.setCardsClickable(clickable);
        resourceCardsPanelVisible.setCardsClickable(clickable);
    }
}
