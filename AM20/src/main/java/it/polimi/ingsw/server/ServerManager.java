package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;

import java.util.HashMap;

public class ServerManager {
    //per ogni utente dice se è connesso con RMI o SOCKET
    private HashMap<String, Connection> connections;
    private Controller controller;

    public ServerManager(){
        reset();
    }

    public void reset(){
        connections = new HashMap<>();
        controller = new Controller();
    }

    public void addConnection(String nickname, Connection connection){
        connections.put(nickname, connection);
    }

    public HashMap<String, Connection> getConnections() {
        return connections;
    }

    public Controller getController() {
        return controller;
    }
}
