package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.InvalidDisconnectionException;
import it.polimi.ingsw.controller.exceptions.NoOneIsConnectedException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.exceptions.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

//classe che implementa la ricezione delle azioni dalla classe RMI
public class ServerRMI implements Loggable{

    //per ogni utente dice se è connesso con RMI o SOCKET
    private ServerManager manager;

    public ServerRMI(ServerManager manager) {
        this.manager = manager;
    }

    @Override
    public  ConnectionAckMessage login(String client, Connection callback) throws RemoteException, CannotJoinGameException {
        HashMap<String, ConnectionAckMessage> res;
        manager.addConnection(client, callback);
        res = this.manager.getController().joinGame(client);

        //stop countdown if someone joined
        this.manager.resetTimer();
        //send the message to other player if the message is significant
        for(String s : res.keySet()){
            if(res.get(s) != null && !s.equals(client)) {
                try {
                    this.manager.getConnections().get(s).callConnectionAckMessage(res.get(s));
                } catch (IOException e) {
                    this.manager.detectDisconnection(s);
                    return null;
                }
            }
        }
        return res.get(client);
    }

    @Override
    public Message startNewGame(String client, int numPlayers, Connection callback) throws RemoteException, InvalidArgumentException, InvalidPlayingException {
        this.manager.getConnections().put(client, callback);
        return this.manager.getController().newGame(client,numPlayers);
    }

    @Override
    public StarterCardAckMessage chooseStarterCardSide(String nickname, int side) throws RemoteException, InvalidArgumentException, InvalidPlayingException {
        HashMap<String, StarterCardAckMessage> res;
        res = this.manager.getController().chooseStarterCardSide(nickname, side);
        for(String s : res.keySet()){
            if(res.get(s) != null && !s.equals(nickname)) {
                try {
                    this.manager.getConnections().get(s).callStarterCardAckMessage(res.get(s));
                } catch (IOException e) {
                    this.manager.detectDisconnection(s);
                    return null;
                }
            }
        }
        return res.get(nickname);
    }

    @Override
    public ObjectiveAckMessage chooseObjective(String nickname, int index) throws RemoteException, InvalidArgumentException, InvalidPlayingException {
        HashMap<String, ObjectiveAckMessage> res;
        res = this.manager.getController().chooseObjective(nickname, index);
        for(String s : res.keySet()){
            if(res.get(s) != null && !s.equals(nickname)) {
                try {
                    this.manager.getConnections().get(s).callObjectiveAckMessage(res.get(s));
                } catch (IOException e) {
                    this.manager.detectDisconnection(s);
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
            res = this.manager.getController().playCard(playerNickname, cardIndex, angle, targetID, side);
            for (String s : res.keySet()) {
                if (res.get(s) != null && !s.equals(playerNickname)) {
                    try {
                        //il server, per ogni utente, manda il messaggio (destinato ad s) tramite l'interfaccia connection
                        //l'interfaccia connection è colei che capisce se l'utente s è collegato con socket o rmi e di conseguenza
                        //chiama i metodi coerenti con il modo di connessione
                        this.manager.getConnections().get(s).callAcknowledgeMessage(res.get(s));
                    } catch (IOException e) {
                        this.manager.detectDisconnection(s);
                    }
                }
            }
        }catch (NoOneIsConnectedException e){
            manager.reset();
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
            res = this.manager.getController().pickCard(playerNickname, deck);
            for (String s : res.keySet()) {
                if(res.get(s) != null && !s.equals(playerNickname)) {
                    try {
                        this.manager.getConnections().get(s).callAcknowledgeMessage(res.get(s));
                    } catch (IOException e) {
                        this.manager.detectDisconnection(s);
                    }
                }
            }
        } catch (NoOneIsConnectedException e){
            manager.reset();
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
            res = this.manager.getController().pickCard(playerNickname, deck, index);
            for (String s : res.keySet()) {
                if(res.get(s) != null && !s.equals(playerNickname)) {
                    try {
                        this.manager.getConnections().get(s).callAcknowledgeMessage(res.get(s));
                    } catch (IOException e) {
                        this.manager.detectDisconnection(s);
                    }
                }
            }
        } catch (NoOneIsConnectedException e){
            manager.reset();
            return null;
        }
        return res.get(playerNickname);
    }

    @Override
    public Message sendChatMessage(String sender, String recipient, String message) throws RemoteException {
        ChatMessage msg = new ChatMessage(sender, recipient, message);
        Message m = new Message();
        m.setResult("Message sent");
        try {
            manager.getConnections().get(recipient).callChatMessage(msg);
        } catch (IOException | NullPointerException e) {
            m.setResult("Recipient is not online");
        }
        return m;
    }

    @Override
    public Message sendBroadcastChatMessage(String sender, String message) {
        BroadcastChatMessage msg = new BroadcastChatMessage(sender, message);
        Message m = new Message();
        m.setResult("Message sent to all");
        for (Map.Entry<String, Connection> entry: manager.getConnections().entrySet()) {
            if(!entry.getKey().equals(sender)) {
                try {
                    entry.getValue().callChatMessage(msg);
                } catch (IOException e) {
                    if (m.getResult().contains("except")) {
                        m.setResult(m.getResult() + ", " + entry.getKey());
                    } else {
                        m.setResult(m.getResult() + " except " + entry.getKey());
                    }
                }
            }
        }
        return m;
    }
}