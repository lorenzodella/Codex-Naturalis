package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.exceptions.InvalidDisconnectionException;
import it.polimi.ingsw.controller.exceptions.NoOneIsConnectedException;
import it.polimi.ingsw.controller.messages.AcknowledgeMessage;
import it.polimi.ingsw.controller.messages.Message;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.model.exceptions.InvalidConnectionStateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PingThread implements Runnable{
    /*mi serve avere la lista dei player connessi che ha il controller
      l'hashmap che specifica com'è connesso ogni player*/
    private ServerManager serverManager; //this

    public PingThread(ServerManager serverManager){
        this.serverManager = serverManager;
    }

    /*scrivo funzione che dovrà eseguire il thread:
      per capire se l'utente, al quale si sta rivolgendo, è ancora connesso o meno */
    @Override
    public void run() {
        Set<String> connectedPlayers;
        do {
            connectedPlayers = serverManager.getController().getConnectedPlayers();
            for (String nickname : connectedPlayers) {
                //capisco in che modo si è connesso questo client
                Connection connection = serverManager.getConnections().get(nickname);
                try {
                    //chiamo metodo per capire se il player (nickname) è ancora connesso
                    connection.callPingMessage(new Message());
                } catch (IOException e) {
                    //se il player non è più connesso
                    serverManager.detectDisconnection(nickname);
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored) {
            }
        } while (!connectedPlayers.isEmpty());
    }
}
