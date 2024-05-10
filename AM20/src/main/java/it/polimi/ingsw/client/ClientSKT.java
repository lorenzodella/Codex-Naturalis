package it.polimi.ingsw.client;

import java.io.IOException;
import java.net.Socket;

public class ClientSKT {

    private SKTClientReceiver receiver;
    private SKTClientSender sender;
    private UIUpdater updater;

    public ClientSKT(UIUpdater updater) {
        this.updater = updater;
    }

    public void connect(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            System.out.println("Connection established");
            receiver = new SKTClientReceiver(socket, updater);
            new Thread(receiver).start();
            sender = new SKTClientSender(socket, updater);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
