package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;

public class ResourceCardsPanel extends JPanel {
    public ResourceCardsPanel(PlayableCard resourceCards1, PlayableCard resourceCards2) {
        super();

        ImagePanel o1 = new ImagePanel(ImagePanel.loadImage(resourceCards1));
        o1.setPreferredSize(GUIUtils.cardDim);

        ImagePanel o2 = new ImagePanel(ImagePanel.loadImage(resourceCards2));
        o2.setPreferredSize(GUIUtils.cardDim);


        add(o1);
        add(o2);

    }

    public ResourceCardsPanel(PlayableCard resourceCard) {
        super();

        resourceCard.setSide(PlayableCard.BACK);

        ImagePanel o1 = new ImagePanel(ImagePanel.loadImage(resourceCard));
        o1.setPreferredSize(GUIUtils.cardDim);

        add(o1);
    }
}
