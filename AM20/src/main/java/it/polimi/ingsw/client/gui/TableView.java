package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.gui.gameview.TablePanel;
import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.util.DynamicMap;
import it.polimi.ingsw.model.util.XMLparser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class TableView extends JFrame implements ActionListener{
    PlayerTable playerTable;
    DynamicMap<String, PlayableCard> mat;
    TablePanel tablePanel;
    Dimension d = new Dimension(200, 133);

    public TableView(PlayerTable playerTable){
        super("table");
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        this.playerTable = playerTable;
        this.mat = playerTable.getMap();

        try {
            tablePanel = new TablePanel(mat, findStarterCard());
            //tablePanel.setMapListener(this);
            tablePanel.update(mat);
        } catch (TargetNotPresentException e) {
            throw new RuntimeException(e);
        }

        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(tablePanel);
        setVisible(true);
    }


    private StarterCard findStarterCard() throws TargetNotPresentException {
        return (StarterCard) mat.find("S85");
    }

    static StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

    static List<PlayableCard> getGoldCards(){
        return XMLparser.parseResourceCards("resourceCards.xml");
    }

    public static void main(String[] args) throws TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException {
        StarterCard starterCard = getExampleStarterCard();
        List<PlayableCard> list = getGoldCards();
        PlayerTable playerTable = new PlayerTable();
        playerTable.insertStarterCard(PlayableCard.BACK, starterCard);

//        playerTable.insertCard(list.get(0), Corner.UL, starterCard.getID(), PlayableCard.BACK);
//        playerTable.insertCard(list.get(1), Corner.UR, starterCard.getID(), PlayableCard.BACK);
//        playerTable.insertCard(list.get(2), Corner.DL, starterCard.getID(), PlayableCard.BACK);
//        playerTable.insertCard(list.get(3), Corner.DR, starterCard.getID(), PlayableCard.BACK);

//        playerTable.insertCard(list.get(0), Corner.UR, starterCard.getID(), PlayableCard.BACK);
//        playerTable.insertCard(list.get(1), Corner.UL, list.get(0).getID(), PlayableCard.BACK);
//        playerTable.insertCard(list.get(2), Corner.DL, list.get(1).getID(), PlayableCard.BACK);
//        playerTable.insertCard(list.get(3), Corner.UL, list.get(2).getID(), PlayableCard.BACK);

//        System.out.println(playerTable.getMatrix());
//        playerTable.insertCard(list.get(0), Corner.UR, starterCard.getID(), PlayableCard.FRONT);
//        System.out.println(playerTable.getMatrix());
//        playerTable.insertCard(list.get(1), Corner.UL, starterCard.getID(), PlayableCard.FRONT);
//        System.out.println(playerTable.getMatrix());

        TableView f = new TableView(playerTable);

        //JOptionPane.showMessageDialog(f, "Caca", "Errore", JOptionPane.PLAIN_MESSAGE);
        //JOptionPane.showInputDialog(f, "inserisci il numero di giocatori", "info", JOptionPane.PLAIN_MESSAGE);
        showInputDialog();

        JDialog d = new JDialog();
        //d.setUndecorated(true);
        d.add(new JLabel("ciao"));
        d.setVisible(true);
    }

    public static void showInputDialog(){
        JTextField username = new JTextField();
        JTextField password = new JPasswordField();
        Object[] message = {
                "Username:", username,
                "Password:", password
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            if (username.getText().equals("h") && password.getText().equals("h")) {
                System.out.println("Login successful");
            } else {
                System.out.println("login failed");
            }
        } else {
            System.out.println("Login canceled");
        }
    }

    int n = 2;
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        String[] s = b.getName().split(";");
        try {
            List<PlayableCard> list = getGoldCards();
            playerTable.insertCard(list.get(n), Integer.parseInt(s[1]), s[0], 1);
            n++;

            tablePanel.update(playerTable.getMap());
        } catch (InvalidPositionException | TargetNotPresentException | InvalidAngleCoveredException |
                 RequirementsNotRespectedException ex) {
            throw new RuntimeException(ex);
        }
    }
}





