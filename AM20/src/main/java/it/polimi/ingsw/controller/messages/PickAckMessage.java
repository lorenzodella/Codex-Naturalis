package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.model.cards.playable.PlayableCard;

import java.util.List;

public class PickAckMessage extends AcknowledgeMessage{

    /**
     * This attribute stands for the card that's now found on the top of the gold deck, after the player picked a card
     */
    private PlayableCard goldTop;
    /**
     * This attribute stands for the card that's now found on the top of the resource deck, after the player picked a card
     */
    private PlayableCard resourceTop;
    /**
     * This attribute stands for two gold visible cards that are now on the table, after the player picked a card
     */
    private PlayableCard[] goldVisible;
    /**
     * This attribute stands for two resource visible cards that are now on the table, after the player picked a card
     */
    private PlayableCard[] resourceVisible;

    @Override
    public String getAction() {
        return AcknowledgeMessage.PICK;
    }

    @Override
    public PlayableCard getGoldTop() {
        return goldTop;
    }

    @Override
    public void setGoldTop(PlayableCard goldTop) {
        this.goldTop = goldTop;
    }

    @Override
    public PlayableCard getResourceTop() {
        return resourceTop;
    }

    @Override
    public void setResourceTop(PlayableCard resourceTop) {
        this.resourceTop = resourceTop;
    }

    @Override
    public PlayableCard[] getGoldVisible() {
        return goldVisible;
    }

    @Override
    public void setGoldVisible(PlayableCard[] goldVisible) {
        this.goldVisible = goldVisible;
    }

    @Override
    public PlayableCard[] getResourceVisible() {
        return resourceVisible;
    }

    @Override
    public void setResourceVisible(PlayableCard[] resourceVisible) {
        this.resourceVisible = resourceVisible;
    }

    @Override
    public String toString() {
        return "PickAckMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }


}
