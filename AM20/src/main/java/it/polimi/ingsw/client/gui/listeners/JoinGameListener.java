package it.polimi.ingsw.client.gui.listeners;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.connections.ClientSender;
import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.model.PawnColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinGameListener extends ClientController implements ActionListener {
    public JoinGameListener(ClientSender sender){
        super(sender);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        //metodo che viene chiamato quando utente clicca sul pulsante
        showInputDialog(b.getRootPane());
    }

    public void showInputDialog(Component c){
        JComboBox<ImageIcon> colorComboBox = GUIUtils.getImageIconJComboBox();

        JTextField nickname = new JTextField();
        Object[] message = {
                "Nickname:", nickname,
                "Color:", colorComboBox
        };

        int option = JOptionPane.showConfirmDialog(c, message, "Join game", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        //quando utente schiaccia ok
        if (option == JOptionPane.OK_OPTION) {
            if (!nickname.getText().isEmpty()) {
                ImageIcon selectedIcon = (ImageIcon) colorComboBox.getSelectedItem();
                String nick = nickname.getText().substring(0,1).toUpperCase() + nickname.getText().substring(1);
                clientSender.login(nick, PawnColor.parsePawnColor(selectedIcon.getDescription()));
                System.out.println("nickname: " + nickname.getText());
            } else {
                GUIUtils.showError(null, "Nickname cannot be empty");
            }
        } else {
            System.out.println("Login cancelled ");
        }
    }


}
