package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.listeners.DeckCoveredListener;
import it.polimi.ingsw.client.gui.listeners.DeckVisibleListener;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.ResourceCard;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class ResourceCardsPanel extends JPanel {
    CardButton o1;
    CardButton o2;
    public ResourceCardsPanel(PlayableCard resourceCards1, PlayableCard resourceCards2) {
        super();

        o1 = new CardButton(resourceCards1);
        o1.setName(Deck.RESOURCE_CARDS +";0");
        o1.setPreferredSize(GUIUtils.cardDim);

        o2 = new CardButton(resourceCards2);
        o2.setName(Deck.RESOURCE_CARDS +";1");
        o2.setPreferredSize(GUIUtils.cardDim);

        add(o1);
        add(o2);
    }

    public ResourceCardsPanel(PlayableCard resourceCard) {
        super();

        resourceCard.setSide(PlayableCard.BACK);

        o1 = new CardButton(resourceCard);
        o1.setName(String.valueOf(Deck.RESOURCE_CARDS));
        o1.setPreferredSize(GUIUtils.cardDim);
        o1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.DARK_GRAY));

        add(o1);
    }
    //2 update
    public void updateVisible(ResourceCard visibleCard1, ResourceCard visibleCard2){
        if(visibleCard1!=null)
            o1.update(visibleCard1);
        else
            o1.clear();

        if(visibleCard2!=null)
            o2.update(visibleCard2);
        else
            o2.clear();
    }
    public void updateTop(ResourceCard top){
        if(top!=null)
            o1.update(top);
        else
            o1.clear();
    }

    public void setDeckCoveredListener(DeckCoveredListener deckCoveredListener){
        o1.addActionListener(deckCoveredListener);
    }

    public void setDeckVisibleListener(DeckVisibleListener deckVisibleListener){
        o1.addActionListener(deckVisibleListener);
        o2.addActionListener(deckVisibleListener);
    }
}
