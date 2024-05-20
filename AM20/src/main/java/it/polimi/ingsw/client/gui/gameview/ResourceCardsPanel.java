package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class ResourceCardsPanel extends JPanel {
    public ResourceCardsPanel(PlayableCard resourceCards1, PlayableCard resourceCards2) {
        super();

        CardButton o1 = new CardButton(resourceCards1);
        o1.setPreferredSize(GUIUtils.cardDim);

        CardButton o2 = new CardButton(resourceCards2);
        o2.setPreferredSize(GUIUtils.cardDim);


        add(o1);
        add(o2);

    }

    public ResourceCardsPanel(PlayableCard resourceCard) {
        super();

        resourceCard.setSide(PlayableCard.BACK);

        CardButton o1 = new CardButton(resourceCard);
        o1.setPreferredSize(GUIUtils.cardDim);
        o1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.DARK_GRAY));

        add(o1);
    }
}
