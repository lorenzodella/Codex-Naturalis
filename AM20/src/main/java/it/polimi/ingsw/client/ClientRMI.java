package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.messages.ErrorMessage;
import it.polimi.ingsw.server.Loggable;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI extends Client {

    private RMIClientReceiver receiver;

    public ClientRMI(UIUpdater updater) {
        super(updater);
    }

    public void connect(String host, int port) {
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            Loggable stub = (Loggable) registry.lookup("Loggable");
            receiver = new RMIClientReceiver(updater);
            sender = new RMIClientSender(stub, receiver);

        } catch (IOException | NotBoundException e) {
            updater.errorMessage(new ErrorMessage("Server not reachable"));
        }
    }

}
