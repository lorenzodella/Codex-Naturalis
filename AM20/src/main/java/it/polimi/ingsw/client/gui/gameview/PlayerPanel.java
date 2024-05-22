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
        this.commonObjectivePanel = commonObjectivePanel;
        this.secretObjectivePanel = secretObjectivePanel;
        this.yourCardsPanel = yourCardsPanel;
        this.tablePanel = tablePanel;
        this.deckPanel = deckPanel;
        this.playerInfoPanel = playerInfoPanel;
        this.logPanel = logPanel;

        //setBorder(new EmptyBorder(5,5,5,5));
        setLayout(new BorderLayout(5,5));

        JPanel sp = new JPanel();
        //ps: l'ordine conta
        sp.add(commonObjectivePanel);
        sp.add(secretObjectivePanel);
        sp.add(yourCardsPanel);

        //panel delle info a dx
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS));
        eastPanel.add(playerInfoPanel);
        eastPanel.add(deckPanel);
        eastPanel.add(Box.createVerticalGlue());
        if(logPanel!=null) {
            eastPanel.add(logPanel);
        }

        //panel di gioco centrale
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10,10));
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.add(sp, BorderLayout.SOUTH);


        add(centerPanel, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);

    }

    public PlayerInfoPanel getPlayerInfoPanel() {
        return playerInfoPanel;
    }

    public CommonObjectivePanel getCommonObjectivePanel() {
        return commonObjectivePanel;
    }

    public SecretObjectivePanel getSecretObjectivePanel() {
        return secretObjectivePanel;
    }

    public YourCardsPanel getYourCardsPanel() {
        return yourCardsPanel;
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }

    public DeckPanel getDeckPanel() {
        return deckPanel;
    }

    public LogPanel getLogPanel() {
        return logPanel;
    }
}
