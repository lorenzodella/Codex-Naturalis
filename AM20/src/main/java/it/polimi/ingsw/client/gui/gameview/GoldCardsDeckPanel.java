package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.model.cards.playable.GoldCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import it.polimi.ingsw.client.gui.listeners.DeckCoveredListener;
import it.polimi.ingsw.client.gui.listeners.DeckVisibleListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * The gold cards deck panel contains the two gold visible cards and the top of the gold deck
 */
public class GoldCardsDeckPanel extends JPanel {
    GoldCardsPanel goldCardsPanelCovered;
    GoldCardsPanel goldCardsPanelVisible;

    /**
     * The panel that contains two other panels:
     * 1. the two gold visible cards
     * 2. the top of the gold deck
     * @param goldCardsPanelCovered panel that contains the top of the gold card deck
     * @param goldCardsPanelVisible panel that contains the two gold visible cards
     */
    public GoldCardsDeckPanel(GoldCardsPanel goldCardsPanelCovered, GoldCardsPanel goldCardsPanelVisible) {
        super();
        this.goldCardsPanelCovered = goldCardsPanelCovered;
        this.goldCardsPanelVisible = goldCardsPanelVisible;

        setLayout(new BorderLayout());
        TitledBorder titledBorder = new TitledBorder("Gold cards");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);
        setOpaque(false);

        add(goldCardsPanelCovered, BorderLayout.CENTER);
        add(goldCardsPanelVisible, BorderLayout.SOUTH);
    }

    public GoldCardsDeckPanel(GoldCardsDeckPanel copy){
        super();
        this.goldCardsPanelCovered = new GoldCardsPanel(copy.goldCardsPanelCovered);
        this.goldCardsPanelVisible = new GoldCardsPanel(copy.goldCardsPanelVisible);

        setLayout(new BorderLayout());
        TitledBorder titledBorder = new TitledBorder("Gold cards");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);
        setOpaque(false);

        add(goldCardsPanelCovered, BorderLayout.CENTER);
        add(goldCardsPanelVisible, BorderLayout.SOUTH);
    }

    /**
     * This method allows to update the cards every time that a player picks a card
     * @param visibleCard1 the new gold visible card 1
     * @param visibleCard2 the new gold visible card 2
     * @param top the new top of the gold deck
     */
    public void update(GoldCard visibleCard1, GoldCard visibleCard2, GoldCard top){
        goldCardsPanelVisible.updateVisible(visibleCard1, visibleCard2);
        goldCardsPanelCovered.updateTop(top);
    }

    public void setDeckListener(DeckCoveredListener deckCoveredListener, DeckVisibleListener deckVisibleListener){
        goldCardsPanelCovered.setDeckCoveredListener(deckCoveredListener);
        goldCardsPanelVisible.setDeckVisibleListener(deckVisibleListener);
    }

    /**
     * This method allows to set the cards clickable or not. This is useful when the player has to pick a card
     * @param clickable true if the cards are clickable, false otherwise
     */
    public void setCardsClickable(boolean clickable){
        goldCardsPanelCovered.setCardsClickable(clickable);
        goldCardsPanelVisible.setCardsClickable(clickable);
    }
}
