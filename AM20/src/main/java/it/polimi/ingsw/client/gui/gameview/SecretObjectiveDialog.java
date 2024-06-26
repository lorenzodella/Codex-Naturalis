package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.listeners.SecretObjectiveListener;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.objective.TrioOfObjectsObjectiveCard;
import it.polimi.ingsw.model.cards.objective.VerticalConfigurationObjectiveCard;
import it.polimi.ingsw.model.util.XMLparser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class is the dialog that allows the player to choose the secret objective
 */
public class SecretObjectiveDialog extends JDialog implements ActionListener{
    JButton confirm;
    CardButton objective0;
    CardButton objective1;
    JTextArea message;

    public SecretObjectiveDialog(Frame owner){
        super(owner, "Secret Objective");

        JPanel confirmPanel = new JPanel();
        JPanel panel = new JPanel();
        JPanel objectivePanel = new JPanel();
        panel.setLayout(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        confirm = new JButton("Confirm");
        objective0 = new CardButton();
        objective0.setClickable(true);
        objective0.addActionListener(this);
        objective0.setName("0");
        objective1 = new CardButton();
        objective1.setClickable(true);
        objective1.addActionListener(this);
        objective1.setName("1");
        message = new JTextArea("You received your two secret objectives, choose the one you'd like to play with");

        //non permettere la modifica del messaggio
        message.setEditable(false);
        message.setFocusable(false);
        //imposta lo sfondo del messaggio come la finestra
        message.setOpaque(false);

        objective0.setPreferredSize(GUIUtils.cardDim);
        objective1.setPreferredSize(GUIUtils.cardDim);

        panel.add(message, BorderLayout.NORTH);
        objectivePanel.add(objective0);
        objectivePanel.add(objective1);
        panel.add(objectivePanel, BorderLayout.CENTER);
        add(panel);

        confirmPanel.add(confirm);
        panel.add(confirmPanel, BorderLayout.SOUTH);

        //non chiudere
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        pack();
    }

    /**
     * This method allows to select one of the two secret objectives
     * @param e the click event
     */
    @Override
    public void actionPerformed(ActionEvent e){
        objective1.setSelected(false);
        objective0.setSelected(false);

        //button è l'objective scelto
        CardButton button = (CardButton) e.getSource();
        //lo evidenzio quando lo scelgo
        button.setSelected(true);

        //confirm è quello che io mando al server
        //confirm deve avere lo stesso nome dell'objective scelto
        confirm.setName(button.getName());
    }

    /**
     * This method allows to update the secret objectives
     * @param card0 the first secret objective
     * @param card1 the second secret objective
     */
    public void update(ObjectiveCard card0, ObjectiveCard card1){
        objective0.update(card0);
        objective1.update(card1);
    }

    /**
     * This method allows to set the listener for the secret objective
     * @param secretObjectiveListener the listener for the secret objective
     */
    public void setObjectiveListener(SecretObjectiveListener secretObjectiveListener){
        confirm.addActionListener(secretObjectiveListener);
    }
}
