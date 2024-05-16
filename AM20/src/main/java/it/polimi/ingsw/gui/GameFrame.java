package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.objective.PairOfObjectsObjectiveCard;
import it.polimi.ingsw.model.cards.objective.TrioOfObjectsObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
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

        CommonObjectivePanel commonObjectivePanel = new CommonObjectivePanel(objectiveCards);

        StarterCard starterCard = getExampleStarterCard();
        PlayerTable playerTable = new PlayerTable();
        playerTable.insertStarterCard(PlayableCard.BACK, starterCard);

        TablePanel tablePanel = new TablePanel(playerTable.getMap(), starterCard);
        tablePanel.update(playerTable.getMap());


        this.playerPanel = new PlayerPanel(commonObjectivePanel, tablePanel);


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
}
