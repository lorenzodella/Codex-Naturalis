package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.objective.PairOfObjectsObjectiveCard;
import it.polimi.ingsw.model.cards.objective.TrioOfObjectsObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.ResourceCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.util.XMLparser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerPanel extends JPanel {
    private CommonObjectivePanel commonObjectivePanel;
    private SecretObjectivePanel secretObjectivePanel;
    private YourCardsPanel yourCardsPanel;
    private TablePanel tablePanel;
    private DeckPanel deckPanel;

    public PlayerPanel(CommonObjectivePanel commonObjectivePanel, SecretObjectivePanel secretObjectivePanel, YourCardsPanel yourCardsPanel , TablePanel tablePanel, DeckPanel deckPanel){
        super();


        setLayout(new BorderLayout());

        JPanel sp = new JPanel();
        sp.add(commonObjectivePanel);
        sp.add(secretObjectivePanel);
        sp.add(yourCardsPanel);

        add(sp, BorderLayout.SOUTH);
        add(tablePanel, BorderLayout.WEST);
        add(deckPanel, BorderLayout.EAST);

    }


}
