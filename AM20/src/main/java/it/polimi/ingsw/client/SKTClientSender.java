package it.polimi.ingsw.client;

import it.polimi.ingsw.clientmessage.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SKTClientSender extends ClientSender {

    private ObjectOutputStream outputStream;
    private Socket socket;

    //TODO
    //gestire quando va istanziato il SKTClientSender
    public SKTClientSender(Socket socket) throws IOException{
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.socket = socket;
    }

    //TODO
    //come gestire exception del writeObject e il callback è corretto
    @Override
    void login(String client) {

        LoginMessage msg = new LoginMessage(client, new SKTClientSender(socket));
        outputStream.writeObject(msg);

    }

    @Override
    void startNewGame(String client, int numPlayers) {
        NewGameMessage msg = new NewGameMessage(client, numPlayers, new SKTClientSender(socket));
        outputStream.writeObject(msg);

    }

    @Override
    void chooseStarterCardSide(String nickname, int side) {
        ChooseStarterCardSideMessage msg = new ChooseStarterCardSideMessage(nickname,side);
        outputStream.writeObject(msg);

    }

    @Override
    void chooseObjective(String nickname, int index) {
        ChooseObjectiveMessage msg = new ChooseObjectiveMessage(nickname, index);
        outputStream.writeObject(msg);

    }

    @Override
    void playCard(String playerNickname, int cardIndex, int angle, String targetID, int side) {
        PlayCardMessage msg = new PlayCardMessage(playerNickname, cardIndex, angle, targetID, side);
        outputStream.writeObject(msg);

    }

    @Override
    void pickCard(String playerNickname, int deck) {
        PickCardDeckMessage msg = new PickCardDeckMessage(playerNickname, deck);
        outputStream.writeObject(msg);

    }

    @Override
    void pickCard(String playerNickname, int deck, int index) {
        PickCardVisibleMessage msg = new PickCardVisibleMessage(playerNickname, deck, index);
        outputStream.writeObject(msg);

    }

    @Override
    void sendChatMessage(String sender, String recipient, String message) {
        SendChatMessage msg = new SendChatMessage(sender, recipient, message);
        outputStream.writeObject(msg);

    }

    @Override
    void sendBroadcastChatMessage(String sender, String message) {
        SendChatBroadcastMessage msg = new SendChatBroadcastMessage(sender, message);
        outputStream.writeObject(msg);

    }
}
