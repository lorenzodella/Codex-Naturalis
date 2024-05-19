package it.polimi.ingsw.client.gui.gameview;

import javax.swing.*;
import java.awt.*;

public class ResourceCardsDeckPanel extends JPanel {


    public ResourceCardsDeckPanel(ResourceCardsPanel resourceCardsPanelCovered, ResourceCardsPanel resourceCardsPanelVisible) {
        super();

        setLayout(new BorderLayout());

        add(resourceCardsPanelCovered, BorderLayout.NORTH);
        add(resourceCardsPanelVisible, BorderLayout.SOUTH);


    }
}
