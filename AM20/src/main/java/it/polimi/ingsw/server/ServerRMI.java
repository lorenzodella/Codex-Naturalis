package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.InvalidDisconnectionException;
import it.polimi.ingsw.controller.exceptions.NoOneIsConnectedException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.exceptions.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ServerRMI implements Loggable{

    private HashMap<String, Callback> callbacks;
    private EndGameTimer timer;
    private Controller controller;

    public ServerRMI() {
        timer = new EndGameTimer();
        restart();
    }

    private void restart() {
        timer.stop();
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

        //stop countdown if someone joined
        timer.stop();
        //send the message to other player if the message is significant
        for(String s : res.keySet()){
            if(res.get(s) != null) {
                try {
                    this.callbacks.get(s).callConnectionAckMessage(res.get(s));
                } catch (RemoteException e) {
                    detectDisconnection(s);
                    return null;
                }
            }
        }
        return res.get(client);
    }

    @Override
    public Message starNewGame(String client, int numPlayers, Callback callback) throws RemoteException, InvalidArgumentException, InvalidPlayingException {
        this.callbacks.put(client, callback);
        return this.controller.newGame(client,numPlayers);
    }

    private void detectDisconnection(String nickname) {
        HashMap<String, AcknowledgeMessage> res;
        this.callbacks.remove(nickname);
        try {
            res = this.controller.disconnectPlayer(nickname);
            //if there's one player left start countdown
            Map.Entry<String, AcknowledgeMessage> m = res.entrySet().iterator().next();
            if(m.getValue().getNumOfConnectedPlayers()==1)
                timer.startCountdown(callbacks.get(m.getKey()));

            for (String s : res.keySet()) {
                try {
                    this.callbacks.get(s).callAcknowledgeMessage(res.get(s));
                } catch (RemoteException e) {
                    detectDisconnection(s);
                }
            }
        } catch (InvalidConnectionStateException | InvalidArgumentException e){
            throw new RuntimeException(e);
        } catch (NoOneIsConnectedException e){
            restart();
        } catch (InvalidDisconnectionException e) {
            Message message = new Message();
            message.setResult(e.toString());
            for (Callback c : callbacks.values()) {
                try {
                    c.callStopGame(message);
                } catch (RemoteException ignored) {

                }
            }
            restart();
        }
    }

    @Override
    public StarterCardAckMessage chooseStarterCardSide(String nickname, int side) throws RemoteException, InvalidArgumentException, InvalidPlayingException {
        HashMap<String, StarterCardAckMessage> res;
        res = this.controller.chooseStarterCardSide(nickname, side);
        for(String s : res.keySet()){
            try {
                this.callbacks.get(s).callStarterCardAckMessage(res.get(s));
            } catch (RemoteException e) {
                detectDisconnection(s);
                return null;
            }
        }
        return res.get(nickname);
    }

    @Override
    public ObjectiveAckMessage chooseObjective(String nickname, int index) throws RemoteException, InvalidArgumentException, InvalidPlayingException {
        HashMap<String, ObjectiveAckMessage> res;
        res = this.controller.chooseObjective(nickname, index);
        for(String s : res.keySet()){
            if(res.get(s) != null) {
                try {
                    this.callbacks.get(s).callObjectiveAckMessage(res.get(s));
                } catch (RemoteException e) {
                    detectDisconnection(s);
                    return null;
                }
            }
        }
        return res.get(nickname);
    }

    @Override
    public AcknowledgeMessage playCard(String playerNickname, int cardIndex, int angle, String targetID, int side)
            throws RemoteException, InvalidArgumentException, RequirementsNotRespectedException,
            InvalidPlayingException, TargetNotPresentException, InvalidAngleCoveredException,
            InvalidPositionException {
        HashMap<String, AcknowledgeMessage> res;
        try{
            res = this.controller.playCard(playerNickname, cardIndex, angle, targetID, side);
            for (String s : res.keySet()) {
                try {
                    this.callbacks.get(s).callAcknowledgeMessage(res.get(s));
                } catch (RemoteException e) {
                    detectDisconnection(s);
                }
            }
        }catch (NoOneIsConnectedException e){
            restart();
            return null;
        }
        return res.get(playerNickname);
    }

    @Override
    public AcknowledgeMessage pickCard(String playerNickname, int deck)
            throws RemoteException, InvalidArgumentException, InvalidPlayingException,
            FinishedCardStackException {
        HashMap<String, AcknowledgeMessage> res;
        try{
            res = this.controller.pickCard(playerNickname, deck);
            for (String s : res.keySet()) {
                try {
                    this.callbacks.get(s).callAcknowledgeMessage(res.get(s));
                } catch (RemoteException e) {
                    detectDisconnection(s);
                }
            }
        } catch (NoOneIsConnectedException e){
            restart();
            return null;
        }
        return res.get(playerNickname);
    }

    @Override
    public AcknowledgeMessage pickCard(String playerNickname, int deck, int index)
            throws RemoteException, InvalidArgumentException, InvalidPlayingException,
            FinishedCardStackException {
        HashMap<String, AcknowledgeMessage> res;
        try {
            res = this.controller.pickCard(playerNickname, deck, index);
            for (String s : res.keySet()) {
                try {
                    this.callbacks.get(s).callAcknowledgeMessage(res.get(s));
                } catch (RemoteException e) {
                    detectDisconnection(s);
                }
            }
        } catch (NoOneIsConnectedException e){
            restart();
            return null;
        }
        return res.get(playerNickname);
    }
}