package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;

import javax.swing.*;
import java.awt.*;

public class DeckPanel extends JPanel {

    private GoldCardsPanel goldCardsPanel;
    private ResourceCardsDeckPanel resourceCardsDeckPanel;

    public DeckPanel(GoldCardsDeckPanel goldCardsDeckPanel, ResourceCardsDeckPanel resourceCardsDeckPanel) {
        super();

        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(GUIUtils.cardDim.width*2+40, GUIUtils.cardDim.height*4+40));

        add(goldCardsDeckPanel, BorderLayout.NORTH);
        add(resourceCardsDeckPanel, BorderLayout.SOUTH);
    }
}
