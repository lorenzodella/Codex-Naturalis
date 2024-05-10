package it.polimi.ingsw.client;

public abstract class ClientController {
    private ClientSender sender;

    public ClientController(ClientSender sender) {
        this.sender = sender;
    }
}
