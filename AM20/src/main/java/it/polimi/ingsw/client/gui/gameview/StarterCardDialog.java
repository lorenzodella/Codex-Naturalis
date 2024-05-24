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

public class StarterCardDialog extends JDialog implements ActionListener{
    JButton confirm;
    CardButton starterCard;
    JTextArea message;

    public StarterCardDialog(Frame owner){
        super(owner, "SIDE OF YOUR STARTER CARD");

        JPanel confirmPanel = new JPanel();
        JPanel cardPanel = new JPanel();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        confirm = new JButton("confirm");
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

        pack();
    }
    static StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        StarterCardDialog d = new StarterCardDialog(null);
        d.update(getExampleStarterCard());
        d.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        starterCard.flip();
        confirm.setName(String.valueOf(starterCard.getCardSide()));
    }

    public void update(StarterCard card){
        starterCard.update(card);
    }
    public void setStarterCardListener(StarterCardListener starterCardListener){
        confirm.addActionListener(starterCardListener);
    }
}
