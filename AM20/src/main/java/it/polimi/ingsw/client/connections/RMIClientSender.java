package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.server.Loggable;

import java.io.IOException;
import java.rmi.RemoteException;

public class RMIClientSender extends ClientSender {

    private RMIClientReceiver receiver;
    private Loggable stub;

//    public RMIClientSender(String host, int port) throws RemoteException, NotBoundException {
//        Registry registry = LocateRegistry.getRegistry(host, port);
//        stub = (Loggable) registry.lookup("Loggable");
//        //receiver = new RMIClientReceiver();
//    }

    public RMIClientSender(Loggable stub, RMIClientReceiver receiver){
        this.stub = stub;
        this.receiver = receiver;
    }

    /**
     * This method call the stub of the server through the login() method and return the message received to the RMIClientReceiver
     * In addition, it manages the different Exception sending them to the RMIClientReceiver
     * @param client username of the player
     * @param color color chosen by the player
     */
    @Override
    public void login(String client, PawnColor color) {
        try {
            ConnectionAckMessage msg = stub.login(client, color, receiver);
            receiver.callConnectionAckMessage(msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (CannotJoinGameException e) {
            receiver.callErrorMessage(new ErrorMessage(e));
        }
    }

    /**
     * This method call the stub of the server through the startNewGame() method and return the message received to the RMIClientReceiver
     * In addition, it manages the different Exception sending them to the RMIClientReceiver
     * @param client username of the first player
     * @param color color chosen by the player
     * @param numPlayers num of player required by the first player
     */
    @Override
    public void startNewGame(String client, PawnColor color, int numPlayers) {
        try {
            ConnectionAckMessage msg = stub.startNewGame(client, color, numPlayers, receiver);
            receiver.callConnectionAckMessage(msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (InvalidArgumentException | InvalidPlayingException e) {
            receiver.callErrorMessage(new ErrorMessage(e));
        }
    }

    /**
     *  This method call the stub of the server through the chooseStarterCardSide() method and return the message received to the RMIClientReceiver
     * In addition, it manages the different Exception sending them to the RMIClientReceiver
     * @param nickname username of the player that has chosen the side of the starter card
     * @param side side chosen (0 for back / 1 for front)
     */
    @Override
    public void chooseStarterCardSide(String nickname, int side) {
        try {
            StarterCardAckMessage msg = stub.chooseStarterCardSide(nickname, side);
            receiver.callStarterCardAckMessage(msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (InvalidArgumentException | InvalidPlayingException e) {
            receiver.callErrorMessage(new ErrorMessage(e));
        }
    }

    /**
     * This method call the stub of the server through the chooseObjective() method and return the message received to the RMIClientReceiver
     * In addition, it manages the different Exception sending them to the RMIClientReceiver
     * @param nickname username of the player that chooses the secret objective
     * @param index index of the card chosen (0 or 1)
     */
    @Override
    public void chooseObjective(String nickname, int index) {
        try {
            ObjectiveAckMessage msg = stub.chooseObjective(nickname, index);
            receiver.callObjectiveAckMessage(msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (InvalidArgumentException | InvalidPlayingException e) {
            receiver.callErrorMessage(new ErrorMessage(e));
        }

    }

    /**
     * This method call the stub of the server through the playCard() method and return the message received to the RMIClientReceiver
     * In addition, it manages the different Exception sending them to the RMIClientReceiver
     * @param playerNickname username of the player
     * @param cardIndex index of the hand card chosen
     * @param angle angle on which the user want to place the chosen card
     * @param targetID ID of the card the user want to play on the cardIndex card
     * @param side side of the card chosen (0 for back / 1 for front)
     */
    @Override
    public void playCard(String playerNickname, int cardIndex, int angle, String targetID, int side) {
        try {
            AcknowledgeMessage msg = this.stub.playCard(playerNickname, cardIndex, angle, targetID, side);
            receiver.callAcknowledgeMessage(msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (InvalidArgumentException | RequirementsNotRespectedException | InvalidPlayingException |
                 TargetNotPresentException | InvalidAngleCoveredException | InvalidPositionException e) {
            receiver.callErrorMessage(new ErrorMessage(e));
        }


    }

    /**
     * This method call the stub of the server through the pickCard() method and return the message received to the RMIClientReceiver
     * In addition, it manages the different Exception sending them to the RMIClientReceiver
     * @param playerNickname username of the player
     * @param deck deck from which the user want to pick the top card (0 for gold deck / 1 for resource deck)
     */
    @Override
    public void pickCard(String playerNickname, int deck) {
        try {
            AcknowledgeMessage msg = this.stub.pickCard(playerNickname, deck);
            receiver.callAcknowledgeMessage(msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (InvalidArgumentException | InvalidPlayingException | FinishedCardStackException e) {
            receiver.callErrorMessage(new ErrorMessage(e));
        }

    }

    /**
     * This method call the stub of the server through the pickCard() method and return the message received to the RMIClientReceiver
     * In addition, it manages the different Exception sending them to the RMIClientReceiver
     * @param playerNickname username of the player
     * @param deck  deck from which the user want to pick one of the two visible card (0 for gold deck / 1 for resource deck)
     * @param index index of the card chosen between the two visible card
     */
    @Override
    public void pickCard(String playerNickname, int deck, int index) {
        try {
            AcknowledgeMessage msg = this.stub.pickCard(playerNickname, deck, index);
            receiver.callAcknowledgeMessage(msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (InvalidArgumentException | InvalidPlayingException | FinishedCardStackException e) {
            receiver.callErrorMessage(new ErrorMessage(e));
        }

    }

    /**
     * This method call the stub of the server through the sendChatMessage() method and return the message received to the RMIClientReceiver
     * In addition, it manages the different Exception sending them to the RMIClientReceiver
     * @param sender username of the message sender
     * @param recipient username of the recipient user
     * @param message message that the sender want to send to the recipient
     */
    @Override
    public void sendChatMessage(String sender, String recipient, String message) {
        try {

            Message msg = this.stub.sendChatMessage(sender, recipient, message);
            receiver.callMessage(msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    /**
     * This method call the stub of the server through the sendBroadcastChatMessage() method and return the message received to the RMIClientReceiver
     * In addition, it manages the different Exception sending them to the RMIClientReceiver
     * @param sender username of the message sender
     * @param message message to send in broadcast to all the player
     */
    @Override
    public void sendBroadcastChatMessage(String sender, String message) {
        try {
            Message msg = this.stub.sendBroadcastChatMessage(sender, message);
            receiver.callMessage(msg);
            //System.out.println("stub.sendBroadcastChatMessage: \n"+msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        }

    }

    /**
     * This method allows the client to understand if the server is down
     * @throws IOException if the server isn't available
     */
    @Override
    public void sendPingMessage() throws IOException {
        stub.ping();
    }
}
