package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.listeners.*;

/**
 * GUIController class is the controller of the GUI. It extends the ClientController class and it is used to manage the
 * GUI and the listeners of the GUI.
 */
public class GUIController extends ClientController {

    /**
     * GUI reference to the GUI object to pass the listeners.
     */
    private GUI gui;

    public GUIController(ClientSender sender, GUI gui) {
        super(sender);
        this.gui = gui;
        createListeners();
    }
    /**
     * Method to get the username of the player stored in the GUI.
     * @return the username of the player.
     */
    public String getUsername() {
        return gui.getNickname();
    }

    /**
     * Method to create the listeners for the GUI.
     */
    private void createListeners() {
        gui.addStarterCardListener(new StarterCardListener(clientSender, this));
        gui.addSecretObjectiveListener(new SecretObjectiveListener(clientSender, this));

        gui.addNewGameListener(new NewGameListener(clientSender));
        gui.addJoinGameListener(new JoinGameListener(clientSender));
        gui.addDeckListener(new DeckCoveredListener(clientSender, this), new DeckVisibleListener(clientSender, this));
        gui.addChatListener(new ChatListener(clientSender, this));

        MapListener mapListener = new MapListener(clientSender, this);
        gui.addMapListener(mapListener);
        gui.addYourCardsListener(new YourCardsListener(clientSender, mapListener));

    }

    /**
     * Method to show a message on the GUI log area.
     * @param log the message to show.
     */
    public void log(String log){
        gui.log(log);
    }
}
