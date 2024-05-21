package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class CommonObjectivePanel extends JPanel {
    private CardButton o1;
    private CardButton o2;

    public CommonObjectivePanel(){
        super();

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Common Objectives");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);


        CardButton o1 = new CardButton();
        o1.setPreferredSize(GUIUtils.cardDim);
        CardButton o2 = new CardButton();
        o2.setPreferredSize(GUIUtils.cardDim);

        add(o1);
        add(o2);
    }

    public void update(ObjectiveCard[] objectiveCards){
        if(objectiveCards[0]!= null)
            o1.update(objectiveCards[0]);
        else
            o1.clear();
        if(objectiveCards[1]!= null)
            o2.update(objectiveCards[1]);
        else
            o2.clear();
    }
}
