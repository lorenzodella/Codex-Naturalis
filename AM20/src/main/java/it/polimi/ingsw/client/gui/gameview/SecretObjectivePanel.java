package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SecretObjectivePanel extends JPanel {
    CardButton o1;
    public SecretObjectivePanel(){
        super();

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Secret Objectives");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);

        o1 = new CardButton();
        o1.setPreferredSize(GUIUtils.cardDim);

        add(o1);
    }

    public void update(ObjectiveCard objectiveCard){
        if(objectiveCard!=null)
            o1.update(objectiveCard);
        else
            o1.clear();
    }

    public void setHidden(){
        o1.setBackground(Color.GRAY);
    }
}
