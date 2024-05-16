package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
