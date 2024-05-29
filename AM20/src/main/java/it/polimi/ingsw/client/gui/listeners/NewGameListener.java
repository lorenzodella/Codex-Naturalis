package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    //metodo che permette di username e numPlayers
    public void showInputDialog(Component c) {
        JTextField nickname = new JTextField();
        JSpinner number = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
        ((JSpinner.DefaultEditor) number.getEditor()).getTextField().setEditable(false);
        Object[] message = {
                "Nickname:", nickname,
                "Number of players:", number
        };

        int option = JOptionPane.showConfirmDialog(c, message, "New game", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        //quando utente schiaccia ok
        if (option == JOptionPane.OK_OPTION) {
            if (!nickname.getText().isEmpty()) {
                clientSender.startNewGame(nickname.getText(), (Integer) number.getValue());
            } else {
                GUIUtils.showError(null, "Nickname cannot be empty");
            }
        } else {
            System.out.println("Game creation canceled");
        }
    }
}
