package it.polimi.ingsw.client.gui.startscreen;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.GUIController;
import it.polimi.ingsw.client.gui.GUIUtils;

import javax.swing.*;
import java.awt.*;

public class StartScreenFrame extends JFrame {
    private StartPanel startPanel;

    public StartScreenFrame(){
        super("Codex Naturalis");
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        setIconImage(new ImageIcon("src/main/resources/Icon.png").getImage());

        startPanel = new StartPanel();
        add(startPanel);

        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(GUIUtils.location);
    }

    public StartPanel getStartPanel() {
        return startPanel;
    }

    public static void main(String[] args) {
//        GUI gui = new GUI();
//        new GUIController(null, gui);
//        gui.showStartScreen();
        new StartScreenFrame().setVisible(true);
    }
}
