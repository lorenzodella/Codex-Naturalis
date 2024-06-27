package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.Chat;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.GUIController;
import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.PlayerStats;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.*;
import it.polimi.ingsw.model.util.XMLparser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * The class GameFrame represents the main frame of the game.
 * It contains the panels that represent the game elements.
 */
public class GameFrame extends JFrame {
    /**
     * Displays the player's game elements.
     */
    private PlayerPanel playerPanel;
    /**
     * Contains the panels that represent the game elements of the other players.
     */
    private HashMap<String, PlayerPanel> otherPlayerPanels;
    /**
     * The chat panel.
     */
    private Chat chat;
    /**
     * The tabbed pane that contains the player's panel and the other players' panels.
     */
    private JTabbedPane tabbedPane;

    public GameFrame(){
        super("Codex Naturalis");

        try{
            setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("/icon.png"))));
        } catch (IOException | NullPointerException e){
            System.err.println("Error loading icon");
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

        PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel("You");

        chat = new Chat(this);
        LogPanel logPanel = new LogPanel(chat);


        this.playerPanel = new PlayerPanel(commonObjectivePanel, secretObjectivePanel, yourCardsPanel ,tablePanel, deckPanel, playerInfoPanel, logPanel);

        tabbedPane = new JTabbedPane();
        tabbedPane.add("You", playerPanel);
        add(tabbedPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(GUIUtils.location);

        setResizable(false);
    }

    public TablePanel getTablePanel(){
        return playerPanel.getTablePanel();
    }

    public YourCardsPanel getYourCardsPanel(){
        return playerPanel.getYourCardsPanel();
    }

    public DeckPanel getDeckPanel(){
        return playerPanel.getDeckPanel();
    }

    public LogPanel getLogPanel() {
        return playerPanel.getLogPanel();
    }

    public SecretObjectivePanel getSecretObjectivePanel(){
        return playerPanel.getSecretObjectivePanel();
    }
    public Chat getChat(){
        return chat;
    }

    /**
     * Sets the player's nickname, updating the player's panel and the chat.
     * @param nickname the player's nickname
     */
    public void setNickname(String nickname){
        playerPanel.getPlayerInfoPanel().setNickname(nickname);
        chat.setNickname(nickname);
    }

    /**
     * Updates the panels showing gold cards in every player's panel.
     * @param goldTop the top gold card
     * @param goldVisible the visible gold cards
     */
    public void updateGoldDeckPanels(PlayableCard goldTop, PlayableCard[] goldVisible){
        playerPanel.getDeckPanel().updateGold((GoldCard) goldVisible[0], (GoldCard) goldVisible[1], (GoldCard) goldTop);
        for(PlayerPanel otherPlayerPanel: otherPlayerPanels.values()){
            otherPlayerPanel.getDeckPanel().updateGold((GoldCard) goldVisible[0], (GoldCard) goldVisible[1], (GoldCard) goldTop);
        }
    }

    /**
     * Updates the panels showing resource cards in every player's panel.
     * @param resourceTop the top resource card
     * @param resourceVisible the visible resource cards
     */
    public void updateResourceCardsPanels(PlayableCard resourceTop, PlayableCard[] resourceVisible){
        playerPanel.getDeckPanel().updateResource((ResourceCard) resourceVisible[0], (ResourceCard) resourceVisible[1], (ResourceCard) resourceTop);
        for(PlayerPanel otherPlayerPanel: otherPlayerPanels.values()){
            otherPlayerPanel.getDeckPanel().updateResource((ResourceCard) resourceVisible[0], (ResourceCard) resourceVisible[1], (ResourceCard) resourceTop);
        }
    }

    /**
     * Updates the panels showing common objective cards in every player's panel.
     * @param commonObjectives the common objective cards
     */
    public void updateCommonObjectivePanels(ObjectiveCard[] commonObjectives){
        playerPanel.getCommonObjectivePanel().update(commonObjectives);
        for(PlayerPanel otherPlayerPanel: otherPlayerPanels.values()){
            otherPlayerPanel.getCommonObjectivePanel().update(commonObjectives);
        }
    }

    /**
     * Updates the player's info panel, showing the player's score, stats and color.
     * @param playerInfo the player's info
     */
    public void updateYourInfo(PlayerInfo playerInfo){
        if(playerInfo.getMap()!=null)
            playerPanel.getTablePanel().update(playerInfo.getMap());
        playerPanel.getPlayerInfoPanel().update(playerInfo.getScore(), playerInfo.getStats(), playerInfo.getColor() );

    }

    /**
     * Updates the other players info panel, showing the player's score, stats and color.
     * @param otherPlayerInfo the other players' info
     */
    public void updateOtherPlayers(HashMap<String, PlayerInfo> otherPlayerInfo){
        if(otherPlayerPanels == null){
            createOtherPlayerPanels(otherPlayerInfo);
        }
        for(String playerName : otherPlayerInfo.keySet()){
            if(otherPlayerInfo.get(playerName).getMap()!=null)
                otherPlayerPanels.get(playerName).getTablePanel().update(otherPlayerInfo.get(playerName).getMap());
            otherPlayerPanels.get(playerName).getPlayerInfoPanel().update(otherPlayerInfo.get(playerName).getScore(), otherPlayerInfo.get(playerName).getStats(), otherPlayerInfo.get(playerName).getColor() );
        }
    }

    /**
     * The first time the other players' panels are created, they are added to the tabbed pane.
     * @param otherPlayerInfo the other players' info
     */
    private void createOtherPlayerPanels(HashMap<String, PlayerInfo> otherPlayerInfo) {
        otherPlayerPanels = new HashMap<>();
        chat.setPlayers(new ArrayList<>(otherPlayerInfo.keySet()));
        for(String playerName : otherPlayerInfo.keySet()){
            SecretObjectivePanel secretObjectivePanel = new SecretObjectivePanel();
            secretObjectivePanel.setHidden();
            YourCardsPanel yourCardsPanel = new YourCardsPanel(playerName);
            yourCardsPanel.setHidden();
            TablePanel tablePanel = new TablePanel();
            PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel(playerName);

            CommonObjectivePanel commonObjectivePanel = new CommonObjectivePanel(playerPanel.getCommonObjectivePanel());
            DeckPanel deckPanel = new DeckPanel(playerPanel.getDeckPanel());

            PlayerPanel otherPanel = new PlayerPanel(commonObjectivePanel, secretObjectivePanel, yourCardsPanel ,tablePanel, deckPanel, playerInfoPanel, null);
            otherPlayerPanels.put(playerName, otherPanel);
            tabbedPane.add(playerName, otherPanel);
        }
    }

}
