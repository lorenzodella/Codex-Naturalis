package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.model.PawnColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Listener that detects clicks on "create a new game" button, during the preliminary phase, and
 * displays a pop up with all the requested information that you need to send to the server in order to create a new game.
 */
public class NewGameListener extends ClientController implements ActionListener {
    public NewGameListener(ClientSender sender){
        super(sender);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        //metodo che viene chiamato quando utente clicca sul pulsante
        showInputDialog(b.getRootPane());
    }

    /**
     * Every time that the listener detects a click on this button, it shows a pop up with all the requested information
     * that the player needs to give in order to create a game.
     * Once the player has filled up with all the info, it calls a client sender's method in order to send the request of
     * creating the game, to the server.
     * @param c parent component
     */
    public void showInputDialog(Component c) {
        JComboBox<ImageIcon> colorComboBox = GUIUtils.getImageIconJComboBox();

        JTextField nickname = new JTextField();
        JSpinner number = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
        ((JSpinner.DefaultEditor) number.getEditor()).getTextField().setEditable(false);
        Object[] message = {
                "Nickname:", nickname,
                "Color:", colorComboBox,
                "Number of players:", number
        };

        int option = JOptionPane.showConfirmDialog(c, message, "New game", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        //quando utente schiaccia ok
        if (option == JOptionPane.OK_OPTION) {
            if (!nickname.getText().isEmpty()) {
                ImageIcon selectedIcon = (ImageIcon) colorComboBox.getSelectedItem();
                String nick = nickname.getText().substring(0,1).toUpperCase() + nickname.getText().substring(1);
                clientSender.startNewGame(nick, PawnColor.parsePawnColor(selectedIcon.getDescription()), (Integer) number.getValue());
            } else {
                GUIUtils.showError(null, "Nickname cannot be empty");
            }
        } else {
            System.out.println("Game creation canceled");
        }
    }


}
