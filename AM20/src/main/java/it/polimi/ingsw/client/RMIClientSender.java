package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.messages.ErrorMessage;
import it.polimi.ingsw.controller.messages.Message;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.model.exceptions.InvalidPlayingException;
import it.polimi.ingsw.server.Loggable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClientSender extends ClientSender{

    private RMIClientReceiver receiver;
    private Loggable stub;

    public RMIClientSender(String host, int port) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        stub = (Loggable) registry.lookup("Loggable");
        receiver = new RMIClientReceiver();
    }


    @Override
    void login(String client) {
        try {
            Message msg = stub.login(client, receiver);
            System.out.println("stub.login: \n"+ msg);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (CannotJoinGameException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void startNewGame(String client, int numPlayers) {
        try {
            Message msg = stub.startNewGame(client, numPlayers, receiver);
            receiver.callMessage(msg);
            System.out.println("stub.startNewGame: \n"+ msg);
        } catch (RemoteException e) {
                receiver.callErrorMessage(new ErrorMessage(e));
        } catch (InvalidArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvalidPlayingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void chooseStarterCardSide(String nickname, int side) {
        try {
            stub.chooseStarterCardSide(nickname, side);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (InvalidArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvalidPlayingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void chooseObjective(String nickname, int index) {

    }

    @Override
    void playCard(String playerNickname, int cardIndex, int angle, String targetID, int side) {

    }

    @Override
    void pickCard(String playerNickname, int deck) {

    }

    @Override
    void pickCard(String playerNickname, int deck, int index) {

    }

    @Override
    void sendChatMessage(String sender, String recipient, String message) {

    }

    @Override
    void sendBroadcastChatMessage(String sender, String message) {

    }
}
