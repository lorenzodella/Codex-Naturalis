package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.exceptions.InvalidDisconnectionException;
import it.polimi.ingsw.controller.exceptions.NoOneIsConnectedException;
import it.polimi.ingsw.controller.messages.AcknowledgeMessage;
import it.polimi.ingsw.controller.messages.Message;
import it.polimi.ingsw.controller.messages.StopGameMessage;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.model.exceptions.InvalidConnectionStateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerManager {
    //per ogni utente dice se è connesso con RMI o SOCKET

    //per ogni utente che si è collegato all'inzio della partita ho memorizzato il modo in cui si sono connessi
    /**
     * This attribute is a map that, per each player, says how they connected to the game (if with socket or RMI)
     */
    private HashMap<String, Connection> connections;
    //il controller ha la lista dei nickname dei player che sono realmente collegati
    /**
     * This attribute stands for the controller reference
     */
    private Controller controller;
    /**
     * This attribute is a timer
     */
    private EndGameTimer timer;
    private Thread t;

    public ServerManager(){
        timer = new EndGameTimer(this);
        reset();
    }

    public synchronized void reset(){
        System.out.println("Server reset");
        this.resetTimer();
        connections = new HashMap<>();
        controller = new Controller();
    }

    public synchronized void addConnection(String nickname, Connection connection){
        connections.put(nickname, connection);
        if(t==null || !t.isAlive()) {
            t = new Thread(new PingThread(this));
            t.start();
        }
    }

    public synchronized HashMap<String, Connection> getConnections() {
        return connections;
    }

    public synchronized Controller getController() {
        return controller;
    }

    public synchronized void resetTimer(){
        this.timer.stop();
    }

    public synchronized void startTimer(Connection connection){
        this.timer.startCountdown(connection);
    }

    /**
     * This method allows the serverManager to remove the player that's just left the game :
     * 1. it removes the nickname for the connected players' nicknames
     * 2. it sends an AcknowledgeMessage to all other players telling them that the "nickname" player just left the game
     * @param nickname the nickname of the player that just left the game
     */
    //questo è il metodo che gestisce quando un client si è disconnesso
    public void detectDisconnection(String nickname) {
        HashMap<String, AcknowledgeMessage> res;
        this.getConnections().remove(nickname);
        try {
            res = this.getController().disconnectPlayer(nickname);
            System.out.println(nickname + " disconnected!");
            //if there's one player left start countdown
            Map.Entry<String, AcknowledgeMessage> m = res.entrySet().iterator().next();
            if(m.getValue().getNumOfConnectedPlayers()==1)
                this.startTimer(getConnections().get(m.getKey()));

            for (String s : res.keySet()) {
                if(res.get(s) != null && !s.equals(nickname)) {
                    try {
                        this.getConnections().get(s).callAcknowledgeMessage(res.get(s));
                    } catch (IOException e) {
                        detectDisconnection(s);
                    }
                }
            }
        } catch (InvalidConnectionStateException | InvalidArgumentException e){
            System.err.println("Someone suspicious disconnected...");
        } catch (NoOneIsConnectedException e){
            //if everyone disconnected, reset server w/o telling something to clients
            System.err.println(e.toString());
            reset();
        } catch (InvalidDisconnectionException e) {
            System.err.println(e.toString());
            //if someone disconnected during preliminary phase of the game, reset server after telling that to remaining clients
            StopGameMessage message = new StopGameMessage();
            message.setResult(e.toString());
            for (Connection c : getConnections().values()) {
                try {
                    c.callStopGame(message);
                } catch (IOException ignored) {

                }
            }
            //reset();
        }
    }
}
