package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.model.cards.playable.GoldCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.ResourceCard;
import it.polimi.ingsw.client.gui.listeners.DeckCoveredListener;
import it.polimi.ingsw.client.gui.listeners.DeckVisibleListener;

import javax.swing.*;
import java.awt.*;

/**
 * The deck panel is a big panel that contains the two gold and resource deck panels
 */
public class DeckPanel extends JPanel {

    private GoldCardsDeckPanel goldCardsDeckPanel;
    private ResourceCardsDeckPanel resourceCardsDeckPanel;

    public DeckPanel(GoldCardsDeckPanel goldCardsDeckPanel, ResourceCardsDeckPanel resourceCardsDeckPanel) {
        super();
        this.goldCardsDeckPanel = goldCardsDeckPanel;
        this.resourceCardsDeckPanel = resourceCardsDeckPanel;

        //ISTANZA ATTRIBUTI
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(GUIUtils.cardDim.width*2+40, GUIUtils.cardDim.height*4+40));

        add(goldCardsDeckPanel, BorderLayout.NORTH);
        add(resourceCardsDeckPanel, BorderLayout.SOUTH);
    }

    public DeckPanel(DeckPanel copy){
        super();
        this.goldCardsDeckPanel = new GoldCardsDeckPanel(copy.goldCardsDeckPanel);
        this.resourceCardsDeckPanel = new ResourceCardsDeckPanel(copy.resourceCardsDeckPanel);

        //ISTANZA ATTRIBUTI
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(GUIUtils.cardDim.width*2+40, GUIUtils.cardDim.height*4+40));

        add(goldCardsDeckPanel, BorderLayout.NORTH);
        add(resourceCardsDeckPanel, BorderLayout.SOUTH);
    }

    public void setDeckListener(DeckVisibleListener deckVisibleListener, DeckCoveredListener deckCoveredListener){
        goldCardsDeckPanel.setDeckListener(deckCoveredListener, deckVisibleListener);
        resourceCardsDeckPanel.setDeckListener(deckCoveredListener, deckVisibleListener);
    }

    /**
     * This method allows to update the two gold visible cards and the top of the gold deck, every time that
     * there's un update
     * @param visibleCard1 gold visible card 1
     * @param visibleCard2 gold visible card 2
     * @param top the top of the gold deck
     */
    public void updateGold(GoldCard visibleCard1, GoldCard visibleCard2, GoldCard top){
        goldCardsDeckPanel.update(visibleCard1, visibleCard2, top);
    }

    /**
     * This method allows to update the two resource visible cards and the top of the gold deck, every time that
     * there's un update
     * @param visibleCard1 resource visible card 1
     * @param visibleCard2 resource visible card 2
     * @param top the top of the resource deck
     */
    public void updateResource(ResourceCard visibleCard1, ResourceCard visibleCard2, ResourceCard top){
        resourceCardsDeckPanel.update(visibleCard1, visibleCard2, top);
    }

    public void setCardsClickable(boolean clickable){
        goldCardsDeckPanel.setCardsClickable(clickable);
        resourceCardsDeckPanel.setCardsClickable(clickable);
    }
}
