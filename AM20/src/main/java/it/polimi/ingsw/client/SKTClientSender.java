package it.polimi.ingsw.client;

import it.polimi.ingsw.clientmessage.*;
import it.polimi.ingsw.controller.messages.ErrorMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SKTClientSender extends ClientSender {

    private ObjectOutputStream outputStream;
    private Socket socket;
    private UIUpdater uiUpdater;

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

        try {
            LoginMessage msg = new LoginMessage(client);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }
    }

    @Override
    void startNewGame(String client, int numPlayers) {
        try{
            NewGameMessage msg = new NewGameMessage(client, numPlayers);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }
    }

    @Override
    void chooseStarterCardSide(String nickname, int side) {
        try {
            ChooseStarterCardSideMessage msg = new ChooseStarterCardSideMessage(nickname, side);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    @Override
    void chooseObjective(String nickname, int index) {
        try {
            ChooseObjectiveMessage msg = new ChooseObjectiveMessage(nickname, index);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    @Override
    void playCard(String playerNickname, int cardIndex, int angle, String targetID, int side) {
        try {
            PlayCardMessage msg = new PlayCardMessage(playerNickname, cardIndex, angle, targetID, side);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    @Override
    void pickCard(String playerNickname, int deck) {
        try{
            PickCardDeckMessage msg = new PickCardDeckMessage(playerNickname, deck);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    @Override
    void pickCard(String playerNickname, int deck, int index) {
        try{
            PickCardVisibleMessage msg = new PickCardVisibleMessage(playerNickname, deck, index);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }
    }

    @Override
    void sendChatMessage(String sender, String recipient, String message) {
        try{
            SendChatMessage msg = new SendChatMessage(sender, recipient, message);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    @Override
    void sendBroadcastChatMessage(String sender, String message) {
        try {
            SendChatBroadcastMessage msg = new SendChatBroadcastMessage(sender, message);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }
}
