package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.Chat;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.GUIController;
import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.PlayerStats;
import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.objective.PairOfObjectsObjectiveCard;
import it.polimi.ingsw.model.cards.objective.TrioOfObjectsObjectiveCard;
import it.polimi.ingsw.model.cards.playable.*;
import it.polimi.ingsw.model.util.XMLparser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameFrame extends JFrame {
    private PlayerPanel playerPanel;
    private HashMap<String, PlayerPanel> otherPlayerPanels;
    private Chat chat;
    private JTabbedPane tabbedPane;

    public GameFrame(){
        super("Codex Naturalis");
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }


        CommonObjectivePanel commonObjectivePanel = new CommonObjectivePanel();
        SecretObjectivePanel secretObjectivePanel = new SecretObjectivePanel();

        YourCardsPanel yourCardsPanel = new YourCardsPanel();

        ResourceCardsPanel resourceCardsPanelCovered = new ResourceCardsPanel(false);
        ResourceCardsPanel resourceCardsPanelVisible = new ResourceCardsPanel(true);
        GoldCardsPanel goldCardsPanelCovered = new GoldCardsPanel(false);
        GoldCardsPanel goldCardsPanelVisible = new GoldCardsPanel(true);
        ResourceCardsDeckPanel resourceCardsDeckPanel = new ResourceCardsDeckPanel(resourceCardsPanelCovered, resourceCardsPanelVisible);
        GoldCardsDeckPanel goldCardsDeckPanel = new GoldCardsDeckPanel(goldCardsPanelCovered, goldCardsPanelVisible);

        DeckPanel deckPanel = new DeckPanel(goldCardsDeckPanel, resourceCardsDeckPanel);


        TablePanel tablePanel = new TablePanel();

        PlayerStats playerStats = new PlayerStats();

        PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel(3, playerStats, "nickname");

        //chat = new Chat("Chat", Color.BLUE, Arrays.asList("uno", "due"));
        LogPanel logPanel = new LogPanel(null);


        this.playerPanel = new PlayerPanel(commonObjectivePanel, secretObjectivePanel, yourCardsPanel ,tablePanel, deckPanel, playerInfoPanel, logPanel);

        tabbedPane = new JTabbedPane();
        tabbedPane.add("Me", playerPanel);
        add(tabbedPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public LogPanel getLogPanel() {
        return playerPanel.getLogPanel();
    }

    public TablePanel getTablePanel() {
        return playerPanel.getTablePanel();
    }

    public YourCardsPanel getYourCardsPanel() {
        return playerPanel.getYourCardsPanel();
    }

    public DeckPanel getDeckPanel() {
        return playerPanel.getDeckPanel();
    }

    /*TODO ELE TIA i metodi che aggiornano i deck devono aggiornare tutti i deck di tutti i PlayerPanel
    fare metodo updateDecks() che cicla su tutti i deck dei playerPanel (anche quelli nella hashmap) e li aggiorna
    */
    //TODO ELE TIA uguale per i commonObjectives

    public void updateOtherPlayers(HashMap<String, PlayerInfo> otherPlayerInfo){
        if(otherPlayerPanels == null){
            createOtherPlayerPanels(otherPlayerInfo);
        }else{
            for(String playerName : otherPlayerInfo.keySet()){
                otherPlayerPanels.get(playerName).getTablePanel().update(otherPlayerInfo.get(playerName).getMap());
                otherPlayerPanels.get(playerName).getPlayerInfoPanel().update(otherPlayerInfo.get(playerName).getScore(), otherPlayerInfo.get(playerName).getStats() );
            }
        }
    }

    private void createOtherPlayerPanels(HashMap<String, PlayerInfo> otherPlayerInfo) {
        otherPlayerPanels = new HashMap<>();
        for(String playerName : otherPlayerInfo.keySet()){
            SecretObjectivePanel secretObjectivePanel = new SecretObjectivePanel();
            secretObjectivePanel.setHidden();
            YourCardsPanel yourCardsPanel = new YourCardsPanel();
            yourCardsPanel.setHidden();
            TablePanel tablePanel = new TablePanel();
            PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel(otherPlayerInfo.get(playerName).getScore(), otherPlayerInfo.get(playerName).getStats(), playerName);

            CommonObjectivePanel commonObjectivePanel = new CommonObjectivePanel(playerPanel.getCommonObjectivePanel());
            DeckPanel deckPanel = new DeckPanel(playerPanel.getDeckPanel());

            PlayerPanel otherPanel = new PlayerPanel(commonObjectivePanel, secretObjectivePanel, yourCardsPanel ,tablePanel, deckPanel, playerInfoPanel, new LogPanel(null));
            otherPlayerPanels.put(playerName, otherPanel);
            tabbedPane.add(playerName, otherPanel);
        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        GUIController guiController = new GUIController(null, gui);
        gui.showStartScreen();
    }

    StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

}
