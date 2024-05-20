package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class CommonObjectivePanel extends JPanel {

    public CommonObjectivePanel(ObjectiveCard[] objectiveCards){
        super();

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Common Objectives");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);


        CardButton o1 = new CardButton(objectiveCards[0]);
        o1.setPreferredSize(GUIUtils.cardDim);
        CardButton o2 = new CardButton(objectiveCards[1]);
        o2.setPreferredSize(GUIUtils.cardDim);

        add(o1);
        add(o2);



    }
}
