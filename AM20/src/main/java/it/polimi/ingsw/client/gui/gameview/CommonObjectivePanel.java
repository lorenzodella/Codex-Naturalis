package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class CommonObjectivePanel extends JPanel {
    private CardButton o1;
    private CardButton o2;

    public CommonObjectivePanel(ObjectiveCard[] objectiveCards){
        super();

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Common Objectives");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);


        o1 = objectiveCards!=null ? new CardButton(objectiveCards[0]) : new CardButton();
        o1.setPreferredSize(GUIUtils.cardDim);
        o2 = objectiveCards!=null ? new CardButton(objectiveCards[1]) : new CardButton();
        o2.setPreferredSize(GUIUtils.cardDim);

        add(o1);
        add(o2);



    }
}
