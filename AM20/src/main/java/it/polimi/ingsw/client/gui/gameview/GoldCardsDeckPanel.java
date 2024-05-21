package it.polimi.ingsw.client.gui.gameview;

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

    public void setDeckListener(DeckCoveredListener deckCoveredListener, DeckVisibleListener deckVisibleListener){
        goldCardsPanelCovered.setDeckCoveredListener(deckCoveredListener);
        goldCardsPanelVisible.setDeckVisibleListener(deckVisibleListener);

    }
}
