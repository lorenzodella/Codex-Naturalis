package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.listeners.MapListener;
import it.polimi.ingsw.client.gui.listeners.YourCardsListener;

public class GUIController extends ClientController {

    private GUI gui;
    private String username;

    public GUIController(ClientSender sender, GUI gui) {
        super(sender);
        this.gui = gui;
        createListeners();
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    private void createListeners() {
        MapListener mapListener = new MapListener(clientSender, this);
        gui.addMapListener(mapListener);
        gui.addYourCardsListener(new YourCardsListener(clientSender, mapListener));
    }

    public void log(String log){
        gui.log(log);
    }
}
