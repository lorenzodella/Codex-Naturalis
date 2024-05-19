package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;

public class GoldCardsPanel extends JPanel {
    public GoldCardsPanel(PlayableCard goldCards1, PlayableCard goldCards2) {
        super();

        ImagePanel o1 = new ImagePanel(ImagePanel.loadImage(goldCards1));
        o1.setPreferredSize(GUIUtils.cardDim);

        ImagePanel o2 = new ImagePanel(ImagePanel.loadImage(goldCards2));
        o2.setPreferredSize(GUIUtils.cardDim);


        add(o1);
        add(o2);

    }

    public GoldCardsPanel(PlayableCard goldCard) {
        super();

        goldCard.setSide(PlayableCard.BACK);

        ImagePanel o1 = new ImagePanel(ImagePanel.loadImage(goldCard));
        o1.setPreferredSize(GUIUtils.cardDim);

        add(o1);
    }

}
