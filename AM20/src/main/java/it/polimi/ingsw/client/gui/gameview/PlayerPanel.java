package it.polimi.ingsw.client.gui.gameview;

import javax.swing.*;
import java.awt.*;

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
