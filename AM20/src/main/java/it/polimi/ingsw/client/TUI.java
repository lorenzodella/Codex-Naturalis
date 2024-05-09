package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.HashMap;
import java.util.List;

public class TUI implements UIManager {
    private List<PlayableCard> cards;
    private HashMap<String, List<String>> mapMsg;
    private ObjectiveCard[] secretObjectives;
    private PlayableCard goldTop;
    private PlayableCard resourceTop;
    private PlayableCard[] goldVisible;
    private PlayableCard[] resourceVisible;
    private PlayerInfo yourPlayerInfo;
    private HashMap<String, PlayerInfo> othersPlayerInfo;
    private ObjectiveCard[] commonObjectives;
    private StarterCard starterCard;


    @Override
    public void updateCards(List<PlayableCard> cards) {
        this.cards = cards;
    }

    @Override
    public void updateChatMessage(String sender, String message) {
        this.mapMsg.get(sender).add(message);
    }

    @Override
    public void updateSecretObjectives(ObjectiveCard[] secretObjectives) {
        this.secretObjectives = secretObjectives;
    }

    @Override
    public void updateGoldTop(PlayableCard goldTop) {
        this.goldTop = goldTop;
    }

    @Override
    public void updateResourceTop(PlayableCard resourceTop) {
        this.resourceTop = resourceTop;
    }

    @Override
    public void updateGoldVisible(PlayableCard[] goldVisible) {
        this.goldVisible = goldVisible;
    }

    @Override
    public void updateResourceVisible(PlayableCard[] resourceVisible) {
        this.resourceVisible = resourceVisible;
    }

    @Override
    public void updateYourPlayerInfo(PlayerInfo yourPlayerInfo) {
        this.yourPlayerInfo = yourPlayerInfo;
    }

    @Override
    public void updateOtherPlayerInfo(HashMap<String, PlayerInfo> otherPlayerInfo) {
        this.othersPlayerInfo = otherPlayerInfo;
    }

    @Override
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives) {
        this.commonObjectives = commonObjectives;
    }

    @Override
    public void updateStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
    }
}
