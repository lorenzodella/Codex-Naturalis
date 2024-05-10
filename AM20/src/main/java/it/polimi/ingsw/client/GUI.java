package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.HashMap;
import java.util.List;

public class GUI implements UIManager {

    @Override
    public void updateCards(List<PlayableCard> cards) {

    }

    @Override
    public void updateChatMessage(String sender, String message) {

    }

    @Override
    public void updateSecretObjectives(ObjectiveCard[] secretObjectives) {

    }

    @Override
    public void updateGoldTop(PlayableCard goldTop) {

    }

    @Override
    public void updateResourceTop(PlayableCard resourceTop) {

    }

    @Override
    public void updateGoldVisible(PlayableCard[] goldVisible) {

    }

    @Override
    public void updateResourceVisible(PlayableCard[] resourceVisible) {

    }

    @Override
    public void updateYourPlayerInfo(PlayerInfo yourPlayerInfo) {

    }

    @Override
    public void updateOtherPlayerInfo(HashMap<String, PlayerInfo> otherPlayerInfo) {

    }

    @Override
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives) {

    }

    @Override
    public void updateStarterCard(StarterCard starterCard) {

    }

    @Override
    public void showResult(String result) {

    }

    @Override
    public void showNextTurn(String nextPlayer) {

    }

    @Override
    public void showMustPick() {

    }

    @Override
    public void showError(String error) {

    }
}
