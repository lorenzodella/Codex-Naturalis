package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.model.PawnColor;

public abstract class ClientSender {
    public abstract void login(String client, PawnColor color);
    public abstract void startNewGame(String client, PawnColor color, int numPlayers);
    public abstract void chooseStarterCardSide(String nickname, int side);
    public abstract void chooseObjective(String nickname, int index);
    public abstract void playCard(String playerNickname, int cardIndex, int angle, String targetID, int side);
    public abstract void pickCard(String playerNickname, int deck);
    public abstract void pickCard(String playerNickname, int deck, int index);
    public abstract void sendChatMessage(String sender, String recipient, String message);
    public abstract void sendBroadcastChatMessage(String sender, String message);
}
