package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.model.cards.playable.PlayableCard;

import java.util.List;

public class PickAckMessage extends AcknowledgeMessage{
    private PlayableCard goldTop;
    private PlayableCard resourceTop;
    private PlayableCard[] goldVisible;
    private PlayableCard[] resourceVisible;


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
