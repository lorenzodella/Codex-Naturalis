package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Panel that contains the secret objective of a player
 */
public class SecretObjectivePanel extends JPanel {
    /**
     * The secret objective card
     */
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

    /**
     * This method allows to update the secret objective cards every time there's a change
     * @param objectiveCard the secret objective card
     */
    public void update(ObjectiveCard objectiveCard){
        if(objectiveCard!=null)
            o1.update(objectiveCard);
        else
            o1.clear();
    }

    /**
     * This method allows to hide the secret objective card for other players
     */
    public void setHidden(){
        o1.hid();
    }
}
