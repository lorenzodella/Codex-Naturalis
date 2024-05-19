package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;

public class GoldCardsDeckPanel extends JPanel {
    public GoldCardsDeckPanel(GoldCardsPanel goldCardsPanelCovered, GoldCardsPanel goldCardsPanelVisible) {
        super();

        setLayout(new BorderLayout());

        add(goldCardsPanelCovered, BorderLayout.NORTH);
        add(goldCardsPanelVisible, BorderLayout.SOUTH);


    }
}
