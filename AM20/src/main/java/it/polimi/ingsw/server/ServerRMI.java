package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.InvalidDisconnectionException;
import it.polimi.ingsw.controller.exceptions.NoOneIsConnectedException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.exceptions.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

//classe che implementa la ricezione delle azioni dalla classe RMI
public class ServerRMI extends UnicastRemoteObject implements Loggable{

    /**
     * this attribute stands as a reference to the serverManager
     */
    //per ogni utente dice se è connesso con RMI o SOCKET
    private ServerManager manager;

    public ServerRMI(ServerManager manager) throws RemoteException {
        super();
        this.manager = manager;
    }

    //TODO: RIGUARDARE
    /**
     * This method actually allows the client "client" to join the game
     * @param client the client that's joining the game
     * @param callback client's reference that allows the server to contact the client when something significant happens
     * @return a connectionAckMessage
     * @throws RemoteException exception that may occur during the execution of a remote method call
     * @throws CannotJoinGameException exception thrown by the controller's joinGame method
     */
    @Override
    public  ConnectionAckMessage login(String client, PawnColor color, Connection callback) throws RemoteException, CannotJoinGameException {
        HashMap<String, ConnectionAckMessage> res;
        if(!manager.getConnections().containsKey(client)){
            manager.addConnection(client, callback);
        }
        try {
            res = this.manager.getController().joinGame(client, color);
        }catch (CannotJoinGameException e){
            //if the saved connection is the one of the client who can't join the game, remove it
            if(manager.getConnections().get(client).equals(callback)){
                manager.getConnections().remove(client);
            }
            throw e;
        }

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

    //TODO: RIGUARDARE
    /**
     * This method actually allows, to the client that want to start a new game, to start a new game
     * @param client the client that just started the game, the one that created it
     * @param numPlayers the number of players that the client "client" want to play with
     * @param callback client's reference that allows the server to contact the client when something significant happens
     * @return a message to the client "client"
     * @throws RemoteException exception that may occur during the execution of a remote method call
     * @throws InvalidArgumentException exception thrown by the controller's newGame method
     * @throws InvalidPlayingException exception thrown by the controller's newGame method
     */
    @Override
    public ConnectionAckMessage startNewGame(String client, PawnColor color, int numPlayers, Connection callback) throws RemoteException, InvalidArgumentException, InvalidPlayingException {
        if(!manager.getConnections().containsKey(client)){
            manager.addConnection(client, callback);
        }
        try {
            return this.manager.getController().newGame(client, color, numPlayers);
        } catch (InvalidArgumentException | InvalidPlayingException e) {
            //if the saved connection is the one of the client who can't join the game, remove it
            if(manager.getConnections().get(client).equals(callback)){
                manager.getConnections().remove(client);
            }
            throw e;
        }
    }

    //TODO: RIGUARDARE
    /**
     * This method actually allows the player that has "nickname" as their nickname to choose the starter card side.
     * @param nickname the nickname of the player that's choosing the side of the card
     * @param side the side of the card that the player's just chosen (could only be front or back)
     * @return a starterCardAckMessage to the player that's just chosen the side
     * @throws RemoteException exception that may occur during the execution of a remote method call
     * @throws InvalidArgumentException exception thrown by the controller's chooseStarterCardSide method
     * @throws InvalidPlayingException exception thrown by the controller's chooseStarterCardSide method
     */
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

    //TODO: RIGUARDARE
    /**
     * This method actually allows the player that has "nickname" as their nickname to choose their secret objective
     * between the 2 possible objectives.
     * @param nickname the nickname of the player that's choosing the side of the card
     * @param index the index, of the 2 items' array that contains the two possible secret objectives, of the specific
     *              secret objective that's been chosen.
     * @return an objectiveAckMessage to the player that's just chosen the objective
     * @throws RemoteException exception that may occur during the execution of a remote method call
     * @throws InvalidArgumentException exception thrown by the controller's chooseObjective method
     * @throws InvalidPlayingException exception thrown by the controller's chooseObjective method
     */
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

    //TODO: RIGUARDARE
    /**
     * This method actually allows the player that has "playerNickname" as their nickname to play the card that's found
     * in the "cardIndex" position of the 3 items' array of their own cards.
     * @param playerNickname the nickname of the player that's playing the card
     * @param cardIndex the specific index of the card that the player wants to play now
     * @param angle the angle of the targetID card is going to be covered with the card that is being played
     * @param targetID the ID of the card that the player wants to cover by playing their card
     * @param side this attribute stands for the side of the card that's just been played (it could be front or back only)
     * @return an AcknowledgeMessage to the player that's just played the card
     * @throws RemoteException exception that may occur during the execution of a remote method call
     * @throws InvalidArgumentException exception thrown by the controller's playCard method
     * @throws RequirementsNotRespectedException exception thrown by the controller's playCard method
     * @throws InvalidPlayingException exception thrown by the controller's playCard method
     * @throws TargetNotPresentException exception thrown by the controller's playCard method
     * @throws InvalidAngleCoveredException exception thrown by the controller's playCard method
     * @throws InvalidPositionException exception thrown by the controller's playCard method
     */
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
    //TODO: RIGUARDARE

    /**
     * This method actually allows the player that has "playerNickname" as their nickname to pick a card from the specific
     * "deck".
     * @param playerNickname the nickname of the player that needs to pick a card
     * @param deck the specific deck where the player is picking up the card from (could only be gold or resource)
     * @return an AcknowledgeMessage to the player that's just picked the card
     * @throws RemoteException exception that may occur during the execution of a remote method call
     * @throws InvalidArgumentException exception thrown by the controller's pickCard method
     * @throws InvalidPlayingException exception thrown by the controller's pickCard method
     * @throws FinishedCardStackException exception thrown by the controller's pickCard method
     */
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

    //TODO: RIGUARDARE
    /**
     * This method actually allows the player that has "playerNickname" as their nickname to pick the card that's found
     * in the index "index" of the "deck"'s visible cards.
     * @param playerNickname the nickname of the player that needs to pick a card
     * @param deck the specific deck where the player is picking up the card from (could only be gold or resource)
     * @param index the index, of the 2 items' array, of the card that the player's chosen to pick up
     * @return an AcknowledgeMessage to the player that's just picked the card
     * @throws RemoteException exception that may occur during the execution of a remote method call
     * @throws InvalidArgumentException exception thrown by the controller's pickCard method
     * @throws InvalidPlayingException exception thrown by the controller's pickCard method
     * @throws FinishedCardStackException exception thrown by the controller's pickCard method
     */
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
    //TODO: RIGUARDARE
    /**
     * This method allows the player "sender" to actually send a message to the "recipient" that contains "message" in it.
     * @param sender the nickname of the player that's sending a message
     * @param recipient the nickname of the player that's receiving the message
     * @param message the actual message
     * @return a Message to the "recipient" player
     * @throws RemoteException exception that may occur during the execution of a remote method call
     */
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
    //TODO: RIGUARDARE

    /**
     * This method allows the sender player to send a message to all other players.
     * @param sender the nickname of the player that's sending the message
     * @param message the actual message
     * @return the result of this action to the sender's client
     */
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

    @Override
    public void ping() {
        //do nothing
    }
}