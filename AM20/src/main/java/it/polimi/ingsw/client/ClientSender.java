package it.polimi.ingsw.client;

public abstract class ClientSender {
    abstract void login(String client);
    abstract void startNewGame(String client, int numPlayers);
    abstract void chooseStarterCardSide(String nickname, int side);
    abstract void chooseObjective(String nickname, int index);
    abstract void playCard(String playerNickname, int cardIndex, int angle, String targetID, int side);
    abstract void pickCard(String playerNickname, int deck);
    abstract void pickCard(String playerNickname, int deck, int index);
    abstract void sendChatMessage(String sender, String recipient, String message);
    abstract void sendBroadcastChatMessage(String sender, String message);
}
