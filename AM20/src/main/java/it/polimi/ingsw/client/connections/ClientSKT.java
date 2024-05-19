package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.client.UIUpdater;
import it.polimi.ingsw.controller.messages.ErrorMessage;

import java.io.IOException;
import java.net.Socket;

public class ClientSKT extends Client {

    private SKTClientReceiver receiver;

    public ClientSKT(UIUpdater updater) {
        super(updater);
    }

    public void connect(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            System.out.println("Connection established");
            receiver = new SKTClientReceiver(socket, updater);
            new Thread(receiver).start();
            sender = new SKTClientSender(socket, updater);
        } catch (IOException e) {
            updater.errorMessage(new ErrorMessage("Server not reachable"));
        }
    }

    public ClientSender getSender() {
        return sender;
    }

}
