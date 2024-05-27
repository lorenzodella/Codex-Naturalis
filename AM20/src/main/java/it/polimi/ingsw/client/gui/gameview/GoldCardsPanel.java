package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.listeners.DeckCoveredListener;
import it.polimi.ingsw.client.gui.listeners.DeckVisibleListener;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.cards.playable.GoldCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

public class GoldCardsPanel extends JPanel {
    CardButton o1;
    CardButton o2;
    boolean visible;

    public GoldCardsPanel(boolean visible) {
        super();
        this.visible = visible;

        o1 = new CardButton();
        o1.setName(String.valueOf(Deck.GOLD_CARDS));
        o1.setPreferredSize(GUIUtils.cardDim);
        add(o1);

        if(visible) {
            o1.setName(Deck.GOLD_CARDS +";0");
            o2 = new CardButton();
            o2.setName(Deck.GOLD_CARDS +";1");
            o2.setPreferredSize(GUIUtils.cardDim);
            add(o2);
        }
    }

    public GoldCardsPanel(GoldCardsPanel copy){
        super();
        this.visible = copy.visible;

        o1 = new CardButton(copy.o1);
        o1.setName(copy.o1.getName());
        o1.setPreferredSize(GUIUtils.cardDim);
        add(o1);

        if(visible) {
            o2 = new CardButton(copy.o2);
            o2.setName(copy.o2.getName());
            o2.setPreferredSize(GUIUtils.cardDim);
            add(o2);
        }
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
        if(top != null){
            top.setSide(PlayableCard.BACK);
            o1.update(top);
            o1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.DARK_GRAY));
        }
        else{
            o1.clear();
        }
    }

    public void setCardsClickable(boolean clickable){
        o1.setClickable(clickable);
        if(o2!=null)
            o2.setClickable(clickable);
    }
}
