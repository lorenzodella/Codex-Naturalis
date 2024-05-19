package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class CommonObjectivePanel extends JPanel {

    public CommonObjectivePanel(ObjectiveCard[] objectiveCards){
        super();

        setBorder(new TitledBorder("Common Objectives"));


        ImagePanel o1 = new ImagePanel(ImagePanel.loadImage(objectiveCards[0]));
        o1.setPreferredSize(GUIUtils.cardDim);
        ImagePanel o2 = new ImagePanel(ImagePanel.loadImage(objectiveCards[1]));
        o2.setPreferredSize(GUIUtils.cardDim);

        add(o1);
        add(o2);



    }
}
