package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.listeners.DeckCoveredListener;
import it.polimi.ingsw.client.gui.listeners.DeckVisibleListener;
import it.polimi.ingsw.model.cards.playable.GoldCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

public class GoldCardsPanel extends JPanel {
    CardButton o1;
    CardButton o2;

    public GoldCardsPanel(PlayableCard goldCards1, PlayableCard goldCards2) {
        super();

        o1 = new CardButton(goldCards1);
        o1.setPreferredSize(GUIUtils.cardDim);

        o2 = new CardButton(goldCards2);
        o2.setPreferredSize(GUIUtils.cardDim);

        add(o1);
        add(o2);
    }

    //top
    public GoldCardsPanel(PlayableCard goldCard) {
        super();
        goldCard.setSide(PlayableCard.BACK);

        o1 = new CardButton(goldCard);
        o1.setPreferredSize(GUIUtils.cardDim);
        o1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.DARK_GRAY));

        add(o1);
    }

    public void setDeckCoveredListener(DeckCoveredListener deckCoveredListener){
        o1.addActionListener(deckCoveredListener);
    }

    public void setDeckVisibleListener(DeckVisibleListener deckVisibleListener){
        o1.addActionListener(deckVisibleListener);
        o2.addActionListener(deckVisibleListener);
    }

    //2 update
    public void updateVisible(GoldCard visibleCard1, GoldCard visibleCard2){
        if(visibleCard1 != null)
            o1.update(visibleCard1);
        else
            o1.clear();

        if(visibleCard2 != null)
            o2.update(visibleCard2);
        else
            o2.clear();
    }
    public void updateTop(GoldCard top){
        if(top != null)
            o1.update(top);
        else
            o1.clear();
    }
}
