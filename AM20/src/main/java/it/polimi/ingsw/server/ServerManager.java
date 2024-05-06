package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.exceptions.InvalidDisconnectionException;
import it.polimi.ingsw.controller.exceptions.NoOneIsConnectedException;
import it.polimi.ingsw.controller.messages.AcknowledgeMessage;
import it.polimi.ingsw.controller.messages.Message;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.model.exceptions.InvalidConnectionStateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerManager {
    //per ogni utente dice se è connesso con RMI o SOCKET

    //per ogni utente che si è collegato all'inzio della partita ho memorizzato il modo in cui si sono connessi
    private HashMap<String, Connection> connections;
    //il controller ha la lista dei nickname dei player che sono realmente collegati
    private Controller controller;
    private EndGameTimer timer;

    public ServerManager(){
        timer = new EndGameTimer(this);
        reset();
    }

    public synchronized void reset(){
        this.resetTimer();
        connections = new HashMap<>();
        controller = new Controller();
    }

    public synchronized void addConnection(String nickname, Connection connection){
        connections.put(nickname, connection);
        Thread t = new Thread(new PingThread(this));
        t.start();
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

    //questo è il metodo che gestisce quando un client si è disconnesso
    public void detectDisconnection(String nickname) {
        System.out.println(nickname + " disconnected!");
        HashMap<String, AcknowledgeMessage> res;
        this.getConnections().remove(nickname);
        try {
            res = this.getController().disconnectPlayer(nickname);
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
            throw new RuntimeException(e);
        } catch (NoOneIsConnectedException e){
            //if everyone disconnected, reset server w/o telling something to clients
            reset();
        } catch (InvalidDisconnectionException e) {
            //if someone disconnected during preliminary phase of the game, reset server after telling that to remaining clients
            Message message = new Message();
            message.setResult(e.toString());
            for (Connection c : getConnections().values()) {
                try {
                    c.callStopGame(message);
                } catch (IOException ignored) {

                }
            }
            reset();
        }
    }
}
