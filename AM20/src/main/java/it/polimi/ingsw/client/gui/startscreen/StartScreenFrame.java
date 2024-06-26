package it.polimi.ingsw.client.gui.startscreen;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.GUIController;
import it.polimi.ingsw.client.gui.GUIUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class StartScreenFrame extends JFrame {
    private StartPanel startPanel;

    public StartScreenFrame(){
        super("Codex Naturalis");

        try{
            setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("/icon.png"))));
        } catch (IOException | NullPointerException e){
            System.err.println("Error loading icon");
        }

        startPanel = new StartPanel();
        add(startPanel);

        setSize(1000, 750);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(GUIUtils.location);
    }

    public StartPanel getStartPanel() {
        return startPanel;
    }
}
