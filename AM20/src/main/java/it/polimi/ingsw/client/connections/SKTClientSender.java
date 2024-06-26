package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.client.UIUpdater;
import it.polimi.ingsw.client.clientmessage.*;
import it.polimi.ingsw.controller.messages.ErrorMessage;
import it.polimi.ingsw.model.PawnColor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class is the SKTClientSender class of the client side of the application.
 * It allows the client to send messages to the server using a socket connection.
 * It implements the ClientSender interface in order to be called from UI listeners.
 */
public class SKTClientSender implements ClientSender {

    /**
     * The ObjectOutputStream used to write the messages to the server.
     */
    private ObjectOutputStream outputStream;
    /**
     * The socket used to connect to the server.
     */
    private UIUpdater uiUpdater;

    public SKTClientSender(Socket socket, UIUpdater updater) throws IOException{
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.uiUpdater = updater;
    }

    /**
     * This method allows the client to send a {@link LoginMessage} to the server.
     * @param client username of the player
     * @param color color chosen by the player
     */
    @Override
    public void login(String client, PawnColor color) {

        try {
            LoginMessage msg = new LoginMessage(client, color);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }
    }

    /**
     * This method allows the client to send a {@link NewGameMessage} to the server.
     * @param client username of the first player
     * @param color color chosen by the player
     * @param numPlayers num of player required by the first player
     */
    @Override
    public void startNewGame(String client, PawnColor color, int numPlayers) {
        try{
            NewGameMessage msg = new NewGameMessage(client, color, numPlayers);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }
    }

    /**
     * This method allows the client to send a {@link ChooseStarterCardSideMessage} to the server.
     * @param nickname username of the player
     * @param side side chosen by the player
     */
    @Override
    public void chooseStarterCardSide(String nickname, int side) {
        try {
            ChooseStarterCardSideMessage msg = new ChooseStarterCardSideMessage(nickname, side);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    /**
     * This method allows the client to send a {@link ChooseObjectiveMessage} to the server.
     * @param nickname username of the player
     * @param index index of the objective chosen by the player
     */
    @Override
    public void chooseObjective(String nickname, int index) {
        try {
            ChooseObjectiveMessage msg = new ChooseObjectiveMessage(nickname, index);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    /**
     * This method allows the client to send a {@link PlayCardMessage} to the server.
     * @param playerNickname username of the player
     * @param cardIndex index of the card played by the player
     * @param angle angle to cover
     * @param targetID id of the card over which the player wants to play the card
     * @param side side chosen by the player
     */
    @Override
    public void playCard(String playerNickname, int cardIndex, int angle, String targetID, int side) {
        try {
            PlayCardMessage msg = new PlayCardMessage(playerNickname, cardIndex, angle, targetID, side);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    /**
     * This method allows the client to send a {@link PickCardDeckMessage} to the server.
     * @param playerNickname username of the player
     * @param deck deck chosen by the player
     */
    @Override
    public void pickCard(String playerNickname, int deck) {
        try{
            PickCardDeckMessage msg = new PickCardDeckMessage(playerNickname, deck);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    /**
     * This method allows the client to send a {@link PickCardVisibleMessage} to the server.
     * @param playerNickname username of the player
     * @param deck deck chosen by the player
     * @param index index of the card chosen by the player
     */
    @Override
    public void pickCard(String playerNickname, int deck, int index) {
        try{
            PickCardVisibleMessage msg = new PickCardVisibleMessage(playerNickname, deck, index);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }
    }

    /**
     * This method allows the client to send a {@link SendChatMessage} to the server.
     * @param sender username of the sender
     * @param recipient username of the recipient
     * @param message message sent by the sender
     */
    @Override
    public void sendChatMessage(String sender, String recipient, String message) {
        try{
            SendChatMessage msg = new SendChatMessage(sender, recipient, message);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    /**
     * This method allows the client to send a {@link SendChatBroadcastMessage} to the server.
     * @param sender username of the sender
     * @param message message sent by the sender
     */
    @Override
    public void sendBroadcastChatMessage(String sender, String message) {
        try {
            SendChatBroadcastMessage msg = new SendChatBroadcastMessage(sender, message);
            outputStream.writeObject(msg);
        } catch (IOException e) {
            uiUpdater.errorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    /**
     * This method allows the client to send a {@link ClientMessage} to the server.
     * @throws IOException if the server is not reachable
     */
    @Override
    public void sendPingMessage() throws IOException {
        outputStream.writeObject(new ClientMessage());
    }
}
