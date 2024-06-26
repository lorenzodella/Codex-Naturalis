package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.listeners.StarterCardListener;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.util.XMLparser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class is the dialog that allows the player to choose the side of the starter card
 */
public class StarterCardDialog extends JDialog implements ActionListener{
    JButton confirm;
    /**
     * The card that the player can choose
     */
    CardButton starterCard;
    JTextArea message;

    public StarterCardDialog(Frame owner){
        super(owner, "Starter Card side");

        JPanel confirmPanel = new JPanel();
        JPanel cardPanel = new JPanel();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        confirm = new JButton("Confirm");
        starterCard = new CardButton();
        starterCard.setClickable(true);
        starterCard.addActionListener(this);
        message = new JTextArea("You received your starter card, choose the side you prefer to play. \n" +
                                "Click to flip the card.");
        //non permettere la modifica del messaggio
        message.setEditable(false);
        message.setFocusable(false);
        //imposta lo sfondo del messaggio come la finestra
        message.setOpaque(false);
        starterCard.setPreferredSize(GUIUtils.cardDim);

        panel.add(message, BorderLayout.NORTH);

        cardPanel.add(starterCard);
        panel.add(cardPanel, BorderLayout.CENTER);

        confirmPanel.add(confirm);
        panel.add(confirmPanel, BorderLayout.SOUTH);

        add(panel);

        //non chiudere
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        setResizable(false);
        pack();
    }

    /**
     * This method allows to flip the starter card to choose the other side
     * @param e the click event
     */
    @Override
    public void actionPerformed(ActionEvent e){
        starterCard.flip();
        confirm.setName(String.valueOf(starterCard.getCardSide()));
    }

    /**
     * This method allows to update teh starter card every time that there's an update
     * @param card teh specific starter card
     */
    public void update(StarterCard card){
        starterCard.update(card);
    }

    /**
     * This method allows to set the listener for the starter card
     * @param starterCardListener the listener for the starter card
     */
    public void setStarterCardListener(StarterCardListener starterCardListener){
        confirm.addActionListener(starterCardListener);
    }
}
