package it.polimi.ingsw.client.gui.gameview;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GoldCardsDeckPanel extends JPanel {
    public GoldCardsDeckPanel(GoldCardsPanel goldCardsPanelCovered, GoldCardsPanel goldCardsPanelVisible) {
        super();

        setLayout(new BorderLayout());
        TitledBorder titledBorder = new TitledBorder("Gold cards");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);
        setOpaque(false);

        add(goldCardsPanelCovered, BorderLayout.CENTER);
        add(goldCardsPanelVisible, BorderLayout.SOUTH);


    }
}
