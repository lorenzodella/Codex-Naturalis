package it.polimi.ingsw.client;


import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.HashMap;
import java.util.List;

public interface UIManager {

    public void updateCards(List<PlayableCard> cards);
    public void updateChatMessage(String sender, String message);
    public void updateSecretObjectives(ObjectiveCard[] secretObjectives);
    public void updateGoldTop(PlayableCard goldTop);
    public void updateResourceTop(PlayableCard resourceTop);
    public void updateGoldVisible(PlayableCard[] goldVisible);
    public void updateResourceVisible(PlayableCard[] resourceVisible);
    public void updateYourPlayerInfo(PlayerInfo yourPlayerInfo);
    public void updateOtherPlayerInfo(HashMap<String, PlayerInfo> otherPlayerInfo);
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives);
    public void updateStarterCard(StarterCard starterCard);
}
