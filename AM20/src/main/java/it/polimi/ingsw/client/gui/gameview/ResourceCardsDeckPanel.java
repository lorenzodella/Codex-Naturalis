package it.polimi.ingsw.client.gui.gameview;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ResourceCardsDeckPanel extends JPanel {


    public ResourceCardsDeckPanel(ResourceCardsPanel resourceCardsPanelCovered, ResourceCardsPanel resourceCardsPanelVisible) {
        super();

        setLayout(new BorderLayout());
        TitledBorder titledBorder = new TitledBorder("Resource cards");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);
        setOpaque(false);

        add(resourceCardsPanelCovered, BorderLayout.CENTER);
        add(resourceCardsPanelVisible, BorderLayout.SOUTH);


    }
}
