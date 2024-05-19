package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.List;

public class YourCardsPanel extends JPanel {
    public YourCardsPanel(List<PlayableCard> playableCards) {
        super();

        setBorder(new TitledBorder("Your cards"));


        ImagePanel o1 = new ImagePanel(ImagePanel.loadImage(playableCards.get(0)));
        o1.setPreferredSize(GUIUtils.cardDim);

        ImagePanel o2 = new ImagePanel(ImagePanel.loadImage(playableCards.get(1)));
        o2.setPreferredSize(GUIUtils.cardDim);

        ImagePanel o3 = new ImagePanel(ImagePanel.loadImage(playableCards.get(2)));
        o3.setPreferredSize(GUIUtils.cardDim);

        add(o1);
        add(o2);
        add(o3);

    }
}
