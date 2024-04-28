package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.StopGameException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.exceptions.*;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerRMI implements Loggable{

    private HashMap<String, Callback> callbacks;

    private Controller controller;

    public ServerRMI() {
        this.controller = new Controller();
        this.callbacks = new HashMap<>();
    }


    public static void main(String[] args){

        System.out.println("Hello from ServerRMI");

        ServerRMI obj = new ServerRMI();

        try {
            Loggable stub = (Loggable) UnicastRemoteObject.exportObject(obj, Integer.parseInt(args[0]));

            Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[0]));

            registry.bind("Loggable", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public  ConnectionAckMessage login(String client, Callback callback) throws RemoteException, CannotJoinGameException {
        HashMap<String, ConnectionAckMessage> res;
        this.callbacks.put(client, callback);
        res = this.controller.joinGame(client);

        //send the message to other player if the message is significant
        for(String s : res.keySet()){  //meglio fare così oppure this.controller.getPlayers() ?
            if(res.get(s) != null)
                this.callbacks.get(s).callConnectionAckMessage(res.get(s));

        }
        return res.get(client);
    }

    @Override
    public Message starNewGame(String client, int numPlayers, Callback callback) throws RemoteException, InvalidArgumentException, InvalidPlayingException {
        this.callbacks.put(client, callback);
        return this.controller.newGame(client,numPlayers);
    }

    @Override
    public AcknowledgeMessage disconnectPlayer(String nickname) throws RemoteException, InvalidArgumentException, StopGameException, InvalidConnectionStateException {
        HashMap<String, AcknowledgeMessage> res;
        //io non fare this.callbacks.remove(nickname)
        res = this.controller.disconnectPlayer(nickname);

        for(String s : res.keySet()){
            this.callbacks.get(s).callAcknowledgeMessage(res.get(s));

        }
        return res.get(nickname);
    }

    @Override
    public StarterCardAckMessage chooseStarterCardSide(String nickname, int side) throws RemoteException, InvalidArgumentException, InvalidPlayingException {
        HashMap<String, StarterCardAckMessage> res;
        res = this.controller.chooseStarterCardSide(nickname, side);
        for(String s : res.keySet()){
            this.callbacks.get(s).callStarterCardAckMessage(res.get(s));
        }
        return res.get(nickname);
    }

    @Override
    public ObjectiveAckMessage chooseObjective(String nickname, int index) throws RemoteException, InvalidArgumentException, InvalidPlayingException {
        HashMap<String, ObjectiveAckMessage> res;
        res = this.controller.chooseObjective(nickname, index);
        for(String s : res.keySet()){
            if(res.get(s) != null)
                this.callbacks.get(s).callObjectiveAckMessage(res.get(s));
        }
        return res.get(nickname);
    }

    @Override
    public AcknowledgeMessage playCard(String playerNickname, int cardIndex, int angle, String targetID, int side) throws RemoteException, InvalidArgumentException, RequirementsNotRespectedException, InvalidPlayingException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, StopGameException {
        HashMap<String, AcknowledgeMessage> res;
        res = this.controller.playCard(playerNickname, cardIndex, angle, targetID, side);
        for(String s : res.keySet()){
             this.callbacks.get(s).callAcknowledgeMessage(res.get(s));
        }
        return res.get(playerNickname);
    }

    @Override
    public AcknowledgeMessage pickCard(String playerNickname, int deck) throws RemoteException, InvalidArgumentException, InvalidPlayingException, FinishedCardStackException, StopGameException {
        HashMap<String, AcknowledgeMessage> res;
        res = this.controller.pickCard(playerNickname, deck);
        for(String s : res.keySet()){
             this.callbacks.get(s).callAcknowledgeMessage(res.get(s));
        }
        return res.get(playerNickname);
    }

    @Override
    public AcknowledgeMessage pickCard(String playerNickname, int deck, int index) throws RemoteException, InvalidArgumentException, InvalidPlayingException, FinishedCardStackException, StopGameException {
        HashMap<String, AcknowledgeMessage> res;
        res = this.controller.pickCard(playerNickname, deck, index);
        for(String s : res.keySet()){
             this.callbacks.get(s).callAcknowledgeMessage(res.get(s));
        }
        return res.get(playerNickname);
    }
}