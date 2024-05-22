package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.listeners.*;

public class GUIController extends ClientController {

    private GUI gui;
    private String username;

    public GUIController(ClientSender sender, GUI gui) {
        super(sender);
        this.gui = gui;
        createListeners();
    }

    public String getUsername() {
        return gui.getNickname();
    }

    private void createListeners() {
        gui.addNewGameListener(new NewGameListener(clientSender));
        gui.addJoinGameListener(new JoinGameListener(clientSender));
        gui.addDeckListener(new DeckCoveredListener(clientSender, this), new DeckVisibleListener(clientSender, this));
        gui.addChatListener(new ChatListener(clientSender, this));

        MapListener mapListener = new MapListener(clientSender, this);
        gui.addMapListener(mapListener);
        gui.addYourCardsListener(new YourCardsListener(clientSender, mapListener));
    }

    public void log(String log){
        gui.log(log);
    }
}
