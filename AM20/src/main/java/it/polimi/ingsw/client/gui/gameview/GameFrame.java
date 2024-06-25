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

public class GameFrame extends JFrame {
    private PlayerPanel playerPanel;
    private HashMap<String, PlayerPanel> otherPlayerPanels;
    private Chat chat;
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

        PlayerStats playerStats = new PlayerStats();

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

    public void setNickname(String nickname){
        playerPanel.getPlayerInfoPanel().setNickname(nickname);
        chat.setNickname(nickname);
    }


    public void updateGoldDeckPanels(PlayableCard goldTop, PlayableCard[] goldVisible){
        playerPanel.getDeckPanel().updateGold((GoldCard) goldVisible[0], (GoldCard) goldVisible[1], (GoldCard) goldTop);
        for(PlayerPanel otherPlayerPanel: otherPlayerPanels.values()){
            otherPlayerPanel.getDeckPanel().updateGold((GoldCard) goldVisible[0], (GoldCard) goldVisible[1], (GoldCard) goldTop);
        }
    }

    public void updateResourceCardsPanels(PlayableCard resourceTop, PlayableCard[] resourceVisible){
        playerPanel.getDeckPanel().updateResource((ResourceCard) resourceVisible[0], (ResourceCard) resourceVisible[1], (ResourceCard) resourceTop);
        for(PlayerPanel otherPlayerPanel: otherPlayerPanels.values()){
            otherPlayerPanel.getDeckPanel().updateResource((ResourceCard) resourceVisible[0], (ResourceCard) resourceVisible[1], (ResourceCard) resourceTop);
        }
    }

    public void updateCommonObjectivePanels(ObjectiveCard[] commonObjectives){
        playerPanel.getCommonObjectivePanel().update(commonObjectives);
        for(PlayerPanel otherPlayerPanel: otherPlayerPanels.values()){
            otherPlayerPanel.getCommonObjectivePanel().update(commonObjectives);
        }
    }


    public void updateYourInfo(PlayerInfo playerInfo){
        if(playerInfo.getMap()!=null)
            playerPanel.getTablePanel().update(playerInfo.getMap());
        playerPanel.getPlayerInfoPanel().update(playerInfo.getScore(), playerInfo.getStats(), playerInfo.getColor() );

    }

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
