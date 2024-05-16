package it.polimi.ingsw.gui;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {
    private CommonObjectivePanel commonObjectivePanel;
    private SecretObjectivePanel secretObjectivePanel;
    private YourCardsPanel yourCardsPanel;
    private TablePanel tablePanel;

    public PlayerPanel(CommonObjectivePanel commonObjectivePanel, TablePanel tablePanel){
        super();


        setLayout(new BorderLayout());

        JPanel sp = new JPanel();
        sp.add(commonObjectivePanel);

        add(sp, BorderLayout.SOUTH);
        add(tablePanel, BorderLayout.WEST);

    }

}
