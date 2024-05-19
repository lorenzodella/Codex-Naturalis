package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class SecretObjectivePanel extends JPanel {
    public SecretObjectivePanel(ObjectiveCard objectiveCard){
        super();

        setBorder(new TitledBorder("Secret Objectives"));


        ImagePanel o1 = new ImagePanel(ImagePanel.loadImage(objectiveCard));
        o1.setPreferredSize(GUIUtils.cardDim);

        add(o1);

    }
}
