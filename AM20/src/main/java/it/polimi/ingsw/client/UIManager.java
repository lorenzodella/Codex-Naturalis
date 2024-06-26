package it.polimi.ingsw.client;
import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.controller.messages.ChatMessage;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * INTERFACE THAT REPRESENTS THE UI AND IT EXPOSES THE METHODS THAT ALLOW TO MODIFY THE UI (GUI OR TUI)
 */
public interface UIManager {

    void setNickname(String nickname);
    String getNickname();
    void showStartGame();
    void showReconnection(String result, boolean isJoining);
    void showStarterCard();
    void showStartChoosingObjective();
    void showObjectiveMessage();
    void showStartPlaying();
    void showPickAck();
    void showPlayAck();
    void updateCards(List<PlayableCard> cards);
    void updateChatMessage(ChatMessage msg);
    void updateSecretObjectives(ArrayList<ObjectiveCard> secretObjectives);
    void updateGold(PlayableCard goldTop, PlayableCard[] goldVisible);
    void updateResource(PlayableCard resourceTop, PlayableCard[] resourceVisible);
    void updateYourPlayerInfo(PlayerInfo yourPlayerInfo);
    void updateOtherPlayerInfo(HashMap<String, PlayerInfo> otherPlayerInfo);
    void updateCommonObjectives(ObjectiveCard[] commonObjectives);
    void updateStarterCard(StarterCard starterCard);
    void showResult(String result);
    void showImportantMessage(String result, String importantMessage);
    void showNextTurn(String nextPlayer);
    void showMustPick();
    void showError(String error);
    void showCommand();
    void showConnection();
    void updateStopGame();
}
