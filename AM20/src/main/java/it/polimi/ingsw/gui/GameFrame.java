package it.polimi.ingsw.gui;

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
    PlayerPanel playerPanel;
    public GameFrame(){
        super("Codex Naturalis");

        ObjectiveCard[] objectiveCards = new ObjectiveCard[2];
        objectiveCards[0] = getExamplePairOfObjectsObjectiveCard();
        objectiveCards[1] = getExampleTrioOfObjectsObjectiveCard();

        List<PlayableCard> playableCards = new ArrayList<>();

        playableCards.add(getExampleResourceCard("R23"));
        playableCards.add(getExampleResourceCard("R14"));
        playableCards.add(getExampleResourceCard("R37"));

        PlayableCard resourceCardCovered = getExampleResourceCard("R14");
        PlayableCard resourceCardVisible1 = getExampleResourceCard("R27");
        PlayableCard resourceCardVisible2 = getExampleResourceCard("R38");

        PlayableCard goldCardCovered = getExampleObjectGoldCard();
        PlayableCard goldCardVisible1 = getExamplePointsGoldCard("G60");
        PlayableCard goldCardVisible2 = getExampleCornerGoldCard("G74");


        ResourceCardsPanel resourceCardsPanelCovered = new ResourceCardsPanel(resourceCardCovered);
        ResourceCardsPanel resourceCardsPanelVisible = new ResourceCardsPanel(resourceCardVisible1, resourceCardVisible2);

        GoldCardsPanel goldCardsPanelCovered = new GoldCardsPanel(goldCardCovered);
        GoldCardsPanel goldCardsPanelVisible = new GoldCardsPanel(goldCardVisible1, goldCardVisible2);


        CommonObjectivePanel commonObjectivePanel = new CommonObjectivePanel(objectiveCards);
        SecretObjectivePanel secretObjectivePanel = new SecretObjectivePanel(objectiveCards[0]);
        YourCardsPanel yourCardsPanel = new YourCardsPanel(playableCards);
        ResourceCardsDeckPanel resourceCardsDeckPanel = new ResourceCardsDeckPanel(resourceCardsPanelCovered, resourceCardsPanelVisible);
        GoldCardsDeckPanel goldCardsDeckPanel = new GoldCardsDeckPanel(goldCardsPanelCovered, goldCardsPanelVisible);

        DeckPanel deckPanel = new DeckPanel(goldCardsDeckPanel, resourceCardsDeckPanel);



        StarterCard starterCard = getExampleStarterCard();
        PlayerTable playerTable = new PlayerTable();
        playerTable.insertStarterCard(PlayableCard.BACK, starterCard);

        TablePanel tablePanel = new TablePanel(playerTable.getMap(), starterCard);
        tablePanel.update(playerTable.getMap());


        this.playerPanel = new PlayerPanel(commonObjectivePanel, secretObjectivePanel, yourCardsPanel ,tablePanel, deckPanel);


        add(playerPanel);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        GameFrame f = new GameFrame();
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
