package it.polimi.ingsw.client;


import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.controller.messages.ChatMessage;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface UIManager {

    public void setNickname(String nickname);
    public String getNickname();
    public void startGame();
    public void updateCards(List<PlayableCard> cards);
    public void updateChatMessage(ChatMessage msg);
    public void updateSecretObjectives(ArrayList<ObjectiveCard> secretObjectives);
    public void updateGold(PlayableCard goldTop, PlayableCard[] goldVisible);
    public void updateResource(PlayableCard resourceTop, PlayableCard[] resourceVisible);
    public void updateYourPlayerInfo(PlayerInfo yourPlayerInfo);
    public void updateOtherPlayerInfo(HashMap<String, PlayerInfo> otherPlayerInfo);
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives);
    public void updateStarterCard(StarterCard starterCard);
    public void showResult(String result);
    public void showNextTurn(String nextPlayer);
    public void showMustPick();
    public void showError(String error);
    public void showCommand();
}
