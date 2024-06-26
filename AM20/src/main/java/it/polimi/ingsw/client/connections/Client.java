package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.client.UIUpdater;

import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * Abstract class that represents a client that can connect to a server.
 */
public abstract class Client {

    protected ClientSender sender;
    protected UIUpdater updater;

    public Client(UIUpdater updater) {
        this.updater = updater;
    }

    public ClientSender getSender() {
        return sender;
    }

    /**
     * Connects the client to the server.
     * @param host the ip of the host to connect to
     * @param port the port of the host to connect to
     * @throws IOException if an I/O error occurs when connecting to the server
     * @throws NotBoundException if the specified name in the registry is not currently bound
     */
    public abstract void connect(String host, int port) throws IOException, NotBoundException;
}
