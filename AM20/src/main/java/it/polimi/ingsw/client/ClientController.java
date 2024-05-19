package it.polimi.ingsw.client;

import it.polimi.ingsw.client.connections.ClientSender;

public abstract class ClientController {
    protected ClientSender clientSender;

    public ClientController(ClientSender sender) {
        this.clientSender = sender;
    }
}
