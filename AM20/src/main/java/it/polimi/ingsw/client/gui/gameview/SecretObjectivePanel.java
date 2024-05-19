package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class SecretObjectivePanel extends JPanel {
    public SecretObjectivePanel(ObjectiveCard objectiveCard){
        super();

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Secret Objectives");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);


        ImagePanel o1 = new ImagePanel(ImagePanel.loadImage(objectiveCard));
        o1.setPreferredSize(GUIUtils.cardDim);

        add(o1);

    }
}
