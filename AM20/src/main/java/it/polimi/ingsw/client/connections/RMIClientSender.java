package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.server.Loggable;

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

    @Override
    public void login(String client) {
        try {
            ConnectionAckMessage msg = stub.login(client, receiver);
            receiver.callConnectionAckMessage(msg);
            //System.out.println("stub.login: \n"+ msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (CannotJoinGameException e) {
            receiver.callErrorMessage(new ErrorMessage(e));
        }
    }

    @Override
    public void startNewGame(String client, int numPlayers) {
        try {
            ConnectionAckMessage msg = stub.startNewGame(client, numPlayers, receiver);
            receiver.callConnectionAckMessage(msg);
            //System.out.println("stub.startNewGame: \n"+ msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (InvalidArgumentException | InvalidPlayingException e) {
            receiver.callErrorMessage(new ErrorMessage(e));
        }
    }

    @Override
    public void chooseStarterCardSide(String nickname, int side) {
        try {
            StarterCardAckMessage msg = stub.chooseStarterCardSide(nickname, side);
            receiver.callStarterCardAckMessage(msg);
            //System.out.println("stub.chooseStarterCardSide: \n"+msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (InvalidArgumentException | InvalidPlayingException e) {
            receiver.callErrorMessage(new ErrorMessage(e));
        }
    }

    @Override
    public void chooseObjective(String nickname, int index) {
        try {
            ObjectiveAckMessage msg = stub.chooseObjective(nickname, index);
            receiver.callObjectiveAckMessage(msg);
            //System.out.println("stub.chooseObjective: \n"+msg);

        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (InvalidArgumentException | InvalidPlayingException e) {
            receiver.callErrorMessage(new ErrorMessage(e));
        }

    }

    @Override
    public void playCard(String playerNickname, int cardIndex, int angle, String targetID, int side) {
        try {
            AcknowledgeMessage msg = this.stub.playCard(playerNickname, cardIndex, angle, targetID, side);
            receiver.callAcknowledgeMessage(msg);
            //System.out.println("stub.playCard: \n"+msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (InvalidArgumentException | RequirementsNotRespectedException | InvalidPlayingException |
                 TargetNotPresentException | InvalidAngleCoveredException | InvalidPositionException e) {
            receiver.callErrorMessage(new ErrorMessage(e));
        }


    }

    @Override
    public void pickCard(String playerNickname, int deck) {
        try {
            AcknowledgeMessage msg = this.stub.pickCard(playerNickname, deck);
            receiver.callAcknowledgeMessage(msg);
            //System.out.println("stub.pickCardDeck: \n"+msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (InvalidArgumentException | InvalidPlayingException | FinishedCardStackException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void pickCard(String playerNickname, int deck, int index) {
        try {
            AcknowledgeMessage msg = this.stub.pickCard(playerNickname, deck, index);
            receiver.callAcknowledgeMessage(msg);
            //System.out.println("stub.playCard: \n"+msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        } catch (InvalidArgumentException | InvalidPlayingException | FinishedCardStackException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendChatMessage(String sender, String recipient, String message) {
        try {

            Message msg = this.stub.sendChatMessage(sender, recipient, message);
            receiver.callMessage(msg);
            //System.out.println("stub.sendChatMessage: \n"+msg);
        } catch (RemoteException e) {
            receiver.callErrorMessage(new ErrorMessage("Server not reachable"));
        }

    }

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
}
