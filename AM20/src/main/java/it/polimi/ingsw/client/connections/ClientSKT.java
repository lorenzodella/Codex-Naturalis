package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.client.UIUpdater;
import it.polimi.ingsw.controller.messages.ErrorMessage;

import java.io.IOException;
import java.net.Socket;


/**
 * Class that represents a client that can connect to a server using Socket.
 */
public class ClientSKT extends Client {

    private SKTClientReceiver receiver;

    public ClientSKT(UIUpdater updater) {
        super(updater);
    }

    /**
     * Connects the client to the server using Socket.
     * @param host the ip of the host to connect to
     * @param port the port of the host to connect to
     * @throws IOException if an I/O error occurs when connecting to the server
     */
    public void connect(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        System.out.println("Connection established");
        receiver = new SKTClientReceiver(socket, updater);
        new Thread(receiver).start();
        sender = new SKTClientSender(socket, updater);
    }

    public ClientSender getSender() {
        return sender;
    }

}
