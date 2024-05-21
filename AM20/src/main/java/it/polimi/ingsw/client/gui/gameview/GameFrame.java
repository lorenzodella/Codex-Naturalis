package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.Chat;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.GUIController;
import it.polimi.ingsw.model.PlayerStats;
import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.objective.PairOfObjectsObjectiveCard;
import it.polimi.ingsw.model.cards.objective.TrioOfObjectsObjectiveCard;
import it.polimi.ingsw.model.cards.playable.*;
import it.polimi.ingsw.model.util.XMLparser;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    private PlayerPanel playerPanel;
    private LogPanel logPanel;
    private YourCardsPanel yourCardsPanel;
    private TablePanel tablePanel;
    private DeckPanel deckPanel;
    private PlayerInfoPanel playerInfoPanel;
    private Chat chat;

    /*public GameFrame(){
        super("Codex Naturalis");
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

//        ObjectiveCard[] objectiveCards = new ObjectiveCard[2];
//        objectiveCards[0] = getExamplePairOfObjectsObjectiveCard();
//        objectiveCards[1] = getExampleTrioOfObjectsObjectiveCard();
//
//        List<PlayableCard> playableCards = new ArrayList<>();
//
//        playableCards.add(getExampleResourceCard("R23"));
//        playableCards.add(getExampleResourceCard("R14"));
//        playableCards.add(getExampleResourceCard("R37"));
//
//        PlayableCard resourceCardCovered = getExampleResourceCard("R14");
//        PlayableCard resourceCardVisible1 = getExampleResourceCard("R27");
//        PlayableCard resourceCardVisible2 = getExampleResourceCard("R38");
//
//        PlayableCard goldCardCovered = getExampleObjectGoldCard();
//        PlayableCard goldCardVisible1 = getExamplePointsGoldCard("G60");
//        PlayableCard goldCardVisible2 = getExampleCornerGoldCard("G74");
//
//

        CommonObjectivePanel commonObjectivePanel = new CommonObjectivePanel(objenullctiveCards);
        SecretObjectivePanel secretObjectivePanel = new SecretObjectivePanel(null);

        yourCardsPanel = new YourCardsPanel(null);


        ResourceCardsPanel resourceCardsPanelCovered = new ResourceCardsPanel(resourceCardCovered);
        ResourceCardsPanel resourceCardsPanelVisible = new ResourceCardsPanel(resourceCardVisible1, resourceCardVisible2);
        GoldCardsPanel goldCardsPanelCovered = new GoldCardsPanel(goldCardCovered);
        GoldCardsPanel goldCardsPanelVisible = new GoldCardsPanel(goldCardVisible1, goldCardVisible2);

        ResourceCardsDeckPanel resourceCardsDeckPanel = new ResourceCardsDeckPanel(resourceCardsPanelCovered, resourceCardsPanelVisible);
        GoldCardsDeckPanel goldCardsDeckPanel = new GoldCardsDeckPanel(goldCardsPanelCovered, goldCardsPanelVisible);

        deckPanel = new DeckPanel(goldCardsDeckPanel, resourceCardsDeckPanel);



//        StarterCard starterCard = getExampleStarterCard();
//        PlayerTable playerTable = new PlayerTable();
//        playerTable.insertStarterCard(PlayableCard.BACK, starterCard);

        tablePanel = new TablePanel(playerTable.getMap(), starterCard);

//        PlayerStats playerStats = new PlayerStats();
//
//        PlayerInfoPanel playerInfoPanel= new PlayerInfoPanel(3, playerStats, "ireneer");

        chat = new Chat("Server", null, null);
        logPanel = new LogPanel(chat);


        this.playerPanel = new PlayerPanel(commonObjectivePanel, secretObjectivePanel, yourCardsPanel ,tablePanel, deckPanel, playerInfoPanel, logPanel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Me", playerPanel);
        tabbedPane.add("You", new JPanel());
        add(tabbedPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }*/

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

    public static void main(String[] args) {
        GUI gui = new GUI();
        GUIController guiController = new GUIController(null, gui);
        gui.showStartScreen();
    }

    PairOfObjectsObjectiveCard getExamplePairOfObjectsObjectiveCard(){
        ArrayList<ObjectiveCard> PairOfObjectsObjectiveCard = XMLparser.parseObjectiveCards("objectiveCards.xml");
        return (PairOfObjectsObjectiveCard) PairOfObjectsObjectiveCard.stream().filter(x->x.getID().equals("O100")).findAny().orElse(null);
    }

    TrioOfObjectsObjectiveCard getExampleTrioOfObjectsObjectiveCard(){
        ArrayList<ObjectiveCard> TrioOfObjectsObjectiveCard = XMLparser.parseObjectiveCards("objectiveCards.xml");
        return (TrioOfObjectsObjectiveCard) TrioOfObjectsObjectiveCard.stream().filter(x->x.getID().equals("O99")).findAny().orElse(null);
    }

    StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

    ResourceCard getExampleResourceCard(String id){
        ArrayList<PlayableCard> ResourceCard = XMLparser.parseResourceCards("resourceCards.xml");
        return (ResourceCard) ResourceCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    ObjectGoldCard getExampleObjectGoldCard(){
        ArrayList<PlayableCard> ObjectGoldCard = XMLparser.parseGoldCards("goldCards.xml");
        return (ObjectGoldCard) ObjectGoldCard.stream().filter(x->x.getID().equals("G42")).findAny().orElse(null);
    }
    PointsGoldCard getExamplePointsGoldCard(String id){
        ArrayList<PlayableCard> PointsGoldCard = XMLparser.parseGoldCards("goldCards.xml");
        return (PointsGoldCard) PointsGoldCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }
    CornerGoldCard getExampleCornerGoldCard(String id){
        ArrayList<PlayableCard> CornerGoldCard = XMLparser.parseGoldCards("goldCards.xml");
        return (CornerGoldCard) CornerGoldCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }
}
