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
    private LogPanel logPanel;
    private YourCardsPanel yourCardsPanel;
    private TablePanel tablePanel;
    private DeckPanel deckPanel;
    private PlayerInfoPanel playerInfoPanel;
    private CommonObjectivePanel commonObjectivePanel;
    private SecretObjectivePanel secretObjectivePanel;
    private HashMap<String, PlayerPanel> otherPlayerPanels;
    private Chat chat;

    public GameFrame(){
        super("Codex Naturalis");
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }


        commonObjectivePanel = new CommonObjectivePanel();
        secretObjectivePanel = new SecretObjectivePanel();

        yourCardsPanel = new YourCardsPanel();

        ResourceCardsPanel resourceCardsPanelCovered = new ResourceCardsPanel(false);
        ResourceCardsPanel resourceCardsPanelVisible = new ResourceCardsPanel(true);
        GoldCardsPanel goldCardsPanelCovered = new GoldCardsPanel(false);
        GoldCardsPanel goldCardsPanelVisible = new GoldCardsPanel(true);

        ResourceCardsDeckPanel resourceCardsDeckPanel = new ResourceCardsDeckPanel(resourceCardsPanelCovered, resourceCardsPanelVisible);
        GoldCardsDeckPanel goldCardsDeckPanel = new GoldCardsDeckPanel(goldCardsPanelCovered, goldCardsPanelVisible);

        deckPanel = new DeckPanel(goldCardsDeckPanel, resourceCardsDeckPanel);



        StarterCard starterCard = getExampleStarterCard();
        PlayerTable playerTable = new PlayerTable();
        playerTable.insertStarterCard(PlayableCard.BACK, starterCard);

        tablePanel = new TablePanel(playerTable.getMap(), starterCard);

        PlayerStats playerStats = new PlayerStats();

        playerInfoPanel = new PlayerInfoPanel(3, playerStats, "nickname");

        //chat = new Chat("Chat", Color.BLUE, Arrays.asList("uno", "due"));
        logPanel = new LogPanel(null);


        this.playerPanel = new PlayerPanel(commonObjectivePanel, secretObjectivePanel, yourCardsPanel ,tablePanel, deckPanel, playerInfoPanel, logPanel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Me", playerPanel);
        tabbedPane.add("You", new JPanel());
        add(tabbedPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public LogPanel getLogPanel() {
        return logPanel;
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }

    public YourCardsPanel getYourCardsPanel() {
        return yourCardsPanel;
    }

    public DeckPanel getDeckPanel() {
        return deckPanel;
    }

    public void updateOtherPlayers(HashMap<String, PlayerInfo> otherPlayerInfo){
        if(otherPlayerPanels == null){
            createOtherPlayerPanels(otherPlayerInfo);
        }else{
            for(String playerName : otherPlayerInfo.keySet()){
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
            //TablePanel tablePanel = new TablePanel();
            PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel(otherPlayerInfo.get(playerName).getScore(), otherPlayerInfo.get(playerName).getStats(), playerName);
            PlayerPanel otherPanel = new PlayerPanel(commonObjectivePanel, secretObjectivePanel, yourCardsPanel ,tablePanel, deckPanel, playerInfoPanel, logPanel);
            otherPlayerPanels.put(playerName, otherPanel);
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
