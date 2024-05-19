package it.polimi.ingsw.client.gui.gameview;

import javax.swing.*;
import java.awt.*;

public class DeckPanel extends JPanel {

    private GoldCardsPanel goldCardsPanel;
    private ResourceCardsDeckPanel resourceCardsDeckPanel;

    public DeckPanel(GoldCardsDeckPanel goldCardsDeckPanel, ResourceCardsDeckPanel resourceCardsDeckPanel) {
        super();

        setLayout(new BorderLayout());

        add(goldCardsDeckPanel, BorderLayout.NORTH);
        add(resourceCardsDeckPanel, BorderLayout.SOUTH);
    }
}
