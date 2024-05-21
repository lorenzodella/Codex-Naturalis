package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.listeners.DeckCoveredListener;
import it.polimi.ingsw.client.gui.listeners.DeckVisibleListener;

import javax.swing.*;
import java.awt.*;

public class DeckPanel extends JPanel {

    private GoldCardsDeckPanel goldCardsDeckPanel;
    private ResourceCardsDeckPanel resourceCardsDeckPanel;

    public DeckPanel(GoldCardsDeckPanel goldCardsDeckPanel, ResourceCardsDeckPanel resourceCardsDeckPanel) {
        super();
        this.goldCardsDeckPanel = goldCardsDeckPanel;
        this.resourceCardsDeckPanel = resourceCardsDeckPanel;

        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(GUIUtils.cardDim.width*2+40, GUIUtils.cardDim.height*4+40));

        add(goldCardsDeckPanel, BorderLayout.NORTH);
        add(resourceCardsDeckPanel, BorderLayout.SOUTH);
    }

    public void setDeckListener(DeckVisibleListener deckVisibleListener, DeckCoveredListener deckCoveredListener){
        goldCardsDeckPanel.setDeckListener(deckCoveredListener, deckVisibleListener);
        resourceCardsDeckPanel.setDeckListener(deckCoveredListener, deckVisibleListener);
    }
}
