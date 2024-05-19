package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResourceCardsDeckPanel extends JPanel {


    public ResourceCardsDeckPanel(ResourceCardsPanel resourceCardsPanelCovered, ResourceCardsPanel resourceCardsPanelVisible) {
        super();

        setLayout(new BorderLayout());

        add(resourceCardsPanelCovered, BorderLayout.NORTH);
        add(resourceCardsPanelVisible, BorderLayout.SOUTH);


    }
}
