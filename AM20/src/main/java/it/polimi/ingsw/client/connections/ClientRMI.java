package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.client.UIUpdater;
import it.polimi.ingsw.server.rmi.Loggable;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class that represents a client that can connect to a server using RMI.
 */
public class ClientRMI extends Client {

    private RMIClientReceiver receiver;

    public ClientRMI(UIUpdater updater) {
        super(updater);
    }

    /**
     * Connects the client to the server using RMI.
     * @param host the ip of the host to connect to
     * @param port the port of the host to connect to
     * @throws IOException if an I/O error occurs when connecting to the server
     * @throws NotBoundException if the specified name in the registry is not currently bound
     */
    public void connect(String host, int port) throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        Loggable stub = (Loggable) registry.lookup("Loggable");
        receiver = new RMIClientReceiver(updater);
        sender = new RMIClientSender(stub, receiver);
    }

}
