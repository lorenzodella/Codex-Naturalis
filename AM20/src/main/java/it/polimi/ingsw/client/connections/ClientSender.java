package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.model.PawnColor;

import java.io.IOException;

public interface ClientSender {
    void login(String client, PawnColor color);
    void startNewGame(String client, PawnColor color, int numPlayers);
    void chooseStarterCardSide(String nickname, int side);
    void chooseObjective(String nickname, int index);
    void playCard(String playerNickname, int cardIndex, int angle, String targetID, int side);
    void pickCard(String playerNickname, int deck);
    void pickCard(String playerNickname, int deck, int index);
    void sendChatMessage(String sender, String recipient, String message);
    void sendBroadcastChatMessage(String sender, String message);
    void sendPingMessage() throws IOException;
}
