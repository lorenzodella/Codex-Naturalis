package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinGameListener extends ClientController implements ActionListener {
    public JoinGameListener(ClientSender sender){
        super(sender);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //metodo che viene chiamato quando utente clicca sul pulsante
        showInputDialog();
    }

    public void showInputDialog(){
        JTextField nickname = new JTextField();
        Object[] message = {
                "Nickname:", nickname,
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        //quando utente schiaccia ok
        if (option == JOptionPane.OK_OPTION) {
            if (!nickname.getText().isEmpty()) {
                clientSender.login(nickname.getText());
                System.out.println("nickname: " + nickname.getText());
            } else {
                JOptionPane.showMessageDialog(null, "Nickname cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Login cancelled ");
        }
    }
}
