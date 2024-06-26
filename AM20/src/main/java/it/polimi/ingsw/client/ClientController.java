package it.polimi.ingsw.client;
import it.polimi.ingsw.client.connections.ClientSender;
/**
 * ABSTRACT CLASS THAT CONTAINS THE REFERENCE TO THE CLIENT SENDER.
 * IT'S IMPLEMENTED FROM THE LISTENERS OF TUI AND GUI (IN ORDER TO SEND MESSAGES TO THE SERVER TO NOTIFY IT OF THE CHANGES).
 */
public abstract class ClientController {
    protected ClientSender clientSender;
    public ClientController(ClientSender sender) {
        this.clientSender = sender;
    }
}
