package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.client.UIUpdater;
import it.polimi.ingsw.clientmessage.*;
import it.polimi.ingsw.controller.messages.ErrorMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SKTClientSender extends ClientSender {

    private ObjectOutputStream outputStream;
    private Socket socket;
    private UIUpdater uiUpdater;

    public SKTClientSender(Socket socket, UIUpdater updater) throws IOException{
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.socket = socket;
        this.uiUpdater = updater;
    }

    @Override
    public void login(String client) {

        try {
            LoginMessage msg = new LoginMessage(client);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }
    }

    @Override
    public void startNewGame(String client, int numPlayers) {
        try{
            NewGameMessage msg = new NewGameMessage(client, numPlayers);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }
    }

    @Override
    public void chooseStarterCardSide(String nickname, int side) {
        try {
            ChooseStarterCardSideMessage msg = new ChooseStarterCardSideMessage(nickname, side);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    @Override
    public void chooseObjective(String nickname, int index) {
        try {
            ChooseObjectiveMessage msg = new ChooseObjectiveMessage(nickname, index);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    @Override
    public void playCard(String playerNickname, int cardIndex, int angle, String targetID, int side) {
        try {
            PlayCardMessage msg = new PlayCardMessage(playerNickname, cardIndex, angle, targetID, side);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    @Override
    public void pickCard(String playerNickname, int deck) {
        try{
            PickCardDeckMessage msg = new PickCardDeckMessage(playerNickname, deck);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    @Override
    public void pickCard(String playerNickname, int deck, int index) {
        try{
            PickCardVisibleMessage msg = new PickCardVisibleMessage(playerNickname, deck, index);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }
    }

    @Override
    public void sendChatMessage(String sender, String recipient, String message) {
        try{
            SendChatMessage msg = new SendChatMessage(sender, recipient, message);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    @Override
    public void sendBroadcastChatMessage(String sender, String message) {
        try {
            SendChatBroadcastMessage msg = new SendChatBroadcastMessage(sender, message);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }
}
