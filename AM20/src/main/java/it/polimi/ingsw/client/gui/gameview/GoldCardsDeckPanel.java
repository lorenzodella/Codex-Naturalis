package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.model.cards.playable.GoldCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import it.polimi.ingsw.client.gui.listeners.DeckCoveredListener;
import it.polimi.ingsw.client.gui.listeners.DeckVisibleListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GoldCardsDeckPanel extends JPanel {
    GoldCardsPanel goldCardsPanelCovered;
    GoldCardsPanel goldCardsPanelVisible;
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

    //metodo che prende sempre 3 carte e che chiama i due update diversi (quando ho due parametri chiamo quello di due
    //mentre quello di uno per quello di uno
    public void update(GoldCard visibleCard1, GoldCard visibleCard2, GoldCard top){
        goldCardsPanelVisible.updateVisible(visibleCard1, visibleCard2);
        goldCardsPanelCovered.updateTop(top);
    }

    public void setDeckListener(DeckCoveredListener deckCoveredListener, DeckVisibleListener deckVisibleListener){
        goldCardsPanelCovered.setDeckCoveredListener(deckCoveredListener);
        goldCardsPanelVisible.setDeckVisibleListener(deckVisibleListener);
    }

    public void setCardsClickable(boolean clickable){
        goldCardsPanelCovered.setCardsClickable(clickable);
        goldCardsPanelVisible.setCardsClickable(clickable);
    }
}
