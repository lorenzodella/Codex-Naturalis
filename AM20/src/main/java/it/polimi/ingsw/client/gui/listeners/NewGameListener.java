package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUIUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameListener extends ClientController implements ActionListener {
    public NewGameListener(ClientSender sender){
        super(sender);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //metodo che viene chiamato quando utente clicca sul pulsante
        showInputDialog();
    }

    //metodo che permette di username e numPlayers
    public void showInputDialog() {
        JTextField nickname = new JTextField();
        JSpinner number = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
        ((JSpinner.DefaultEditor) number.getEditor()).getTextField().setEditable(false);
        Object[] message = {
                "Nickname:", nickname,
                "Number of players:", number
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        //quando utente schiaccia ok
        if (option == JOptionPane.OK_OPTION) {
            if (!nickname.getText().isEmpty()) {
                clientSender.startNewGame(nickname.getText(), (Integer) number.getValue());
                GUIUtils.showMessage("Game created, waiting for other players");
            } else {
                JOptionPane.showMessageDialog(null, "Nickname cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Game creation canceled");
        }
    }
}
