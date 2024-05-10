package it.polimi.ingsw.client;

public abstract class ClientController {
    protected ClientSender clientSender;

    public ClientController(ClientSender sender) {
        this.clientSender = sender;
    }
}
