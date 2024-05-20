package it.polimi.ingsw.client.gui.gameview;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PlayerPanel extends JPanel {
    private CommonObjectivePanel commonObjectivePanel;
    private SecretObjectivePanel secretObjectivePanel;
    private YourCardsPanel yourCardsPanel;
    private TablePanel tablePanel;
    private DeckPanel deckPanel;
    private PlayerInfoPanel playerInfoPanel;
    private LogPanel logPanel;

    public PlayerPanel(CommonObjectivePanel commonObjectivePanel, SecretObjectivePanel secretObjectivePanel,
                       YourCardsPanel yourCardsPanel , TablePanel tablePanel, DeckPanel deckPanel,
                       PlayerInfoPanel playerInfoPanel, LogPanel logPanel){
        super();

        //setBorder(new EmptyBorder(5,5,5,5));
        setLayout(new BorderLayout(5,5));

        JPanel sp = new JPanel();
        sp.add(commonObjectivePanel);
        sp.add(secretObjectivePanel);
        sp.add(yourCardsPanel);

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS));
        //eastPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        eastPanel.add(playerInfoPanel);
        eastPanel.add(Box.createVerticalGlue());
        eastPanel.add(deckPanel);
        eastPanel.add(Box.createVerticalGlue());
        eastPanel.add(logPanel);
        //eastPanel.add(Box.createRigidArea(new Dimension(0, 10)));


        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10,10));
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.add(sp, BorderLayout.SOUTH);


        add(centerPanel, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);

    }


}
