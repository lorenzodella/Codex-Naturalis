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
    /**
     * THE SERVER MANAGER CLASS ACTS LIKE AN INTERMEDIARY BETWEEN SERVER RMI AND SERVER SKT.
     * IT CONTAINS THE CONTROLLER REFERENCE AND THE CONNECTIONS HASHMAP THAT SPECIFIES, PER EACH PLAYER, THEIR TYPE OF CONNECTION
     */
public class ServerManager {
    /**
     * map that, per each player, says how they connected to the game (could be socket or RMI)
     */
    private HashMap<String, Connection> connections;
    //il controller ha la lista dei nickname dei player che sono realmente collegati
    /**
     * the controller reference
     */
    private Controller controller;
    /**
     * 60 seconds timer that's used every time that there's only one player left (and all others disconnected) in the game
     */
    private EndGameTimer timer;
    /**
     * thread that starts when the first player connects to the game and makes sure that the connection with all players
     * is still alive
     */
    private Thread t;

    public ServerManager(){
        timer = new EndGameTimer(this);
        reset();
    }

    /**
     * When all players disconnected the game, the server needs to reset to the initial state, waiting for another game to start.
     * it resets the controller reference and the connection hashmap.
     */
    public synchronized void reset(){
        System.out.println("Server reset");
        this.resetTimer();
        connections = new HashMap<>();
        controller = new Controller();
    }

        /**
         * Adds a connection to the connections hashmap that takes track of the nicknames of all players and their type of connection
         * @param nickname nickname of the player
         * @param connection type of connection
         */
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

    /**
     * Stops the EndGameTimer
     * @see EndGameTimer
     */
    public synchronized void resetTimer(){
        this.timer.stop();
    }
    /**
     * Starts the EndGameTimer
     * @see EndGameTimer
     */
    public synchronized void startTimer(Connection connection){
        this.timer.startCountdown(connection);
    }

    /**
     * It detects every disconnection:
     * 1. it removes the nickname from the connections hashmap
     * 2. it notifies the controller of the event
     * 3. it sends the acknowledge message informing all other players of teh event
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
                    } catch (IOException | NullPointerException e) {
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
