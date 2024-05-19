package it.polimi.ingsw.client.connections;

import it.polimi.ingsw.client.UIUpdater;

public abstract class Client {

    protected ClientSender sender;
    protected UIUpdater updater;

    public Client(UIUpdater updater) {
        this.updater = updater;
    }

    public ClientSender getSender() {
        return sender;
    }

    public abstract void connect(String host, int port);
}
