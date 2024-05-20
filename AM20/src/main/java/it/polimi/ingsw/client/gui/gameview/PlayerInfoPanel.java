package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.model.PlayerStats;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class PlayerInfoPanel extends JPanel {

    private int score;
    private PlayerStats stats;
    private String playerName;

    public PlayerInfoPanel(int score, PlayerStats stats, String playerName) {
        super();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(300, 300));

        Border lineBorder = new LineBorder(Color.BLACK);
        Border marginBorder = new EmptyBorder(0,7,0,7);
        Border compoundBorder = new CompoundBorder(lineBorder, marginBorder);
        setBorder(compoundBorder);

        JLabel playerNameLabel = new JLabel(playerName);
        playerNameLabel.setOpaque(false);
        playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        add(playerNameLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BorderLayout(5,5));

        JLabel scoreLabel = new JLabel("Score: "+score);
        scoreLabel.setForeground(Color.BLUE);
        scoreLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        scoreLabel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        centerPanel.add(scoreLabel, BorderLayout.NORTH);


        JPanel resourcesPanel = new JPanel();
        resourcesPanel.setOpaque(false);
        JLabel resourcesLabel = new JLabel("Resources: ");
        resourcesLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        resourcesPanel.setLayout(new BoxLayout(resourcesPanel, BoxLayout.PAGE_AXIS));
        resourcesPanel.add(resourcesLabel);

        JLabel plantsLabel = new JLabel("-Plants: " + stats.getNumberOfResources(Kingdom.Plant));
        plantsLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        plantsLabel.setForeground(Color.GREEN);
        resourcesPanel.add(plantsLabel);
        JLabel animalLabel = new JLabel("-Animals: " + stats.getNumberOfResources(Kingdom.Animal));
        animalLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        animalLabel.setForeground(Color.CYAN);
        resourcesPanel.add(animalLabel);
        JLabel fungiLabel = new JLabel("-Fungi: " + stats.getNumberOfResources(Kingdom.Fungi));
        fungiLabel.setFont(new Font("TimesNewRomans", Font.PLAIN, 15));
        fungiLabel.setForeground(Color.RED);
        resourcesPanel.add(fungiLabel);
        JLabel insectLabel = new JLabel("-Insect: " + stats.getNumberOfResources(Kingdom.Insect));
        insectLabel.setFont(new Font("TimesNewRomans", Font.PLAIN, 15));
        insectLabel.setForeground(Color.MAGENTA);
        resourcesPanel.add(insectLabel);

        centerPanel.add(resourcesPanel, BorderLayout.WEST);

        JPanel objectsPanel = new JPanel();
        objectsPanel.setOpaque(false);
        JLabel objectsLabel = new JLabel("Objects: ");
        objectsLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        objectsPanel.setLayout(new BoxLayout(objectsPanel, BoxLayout.PAGE_AXIS));
        objectsPanel.add(objectsLabel);
        JLabel inkwellLabel = new JLabel("-Inkwell: "+ stats.getNumberOfObjects(SpecialObject.Inkwell));
        inkwellLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        objectsPanel.add(inkwellLabel);
        JLabel quillLabel = new JLabel("-Quill: " + stats.getNumberOfObjects(SpecialObject.Quill));
        quillLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        objectsPanel.add(quillLabel);
        JLabel manuscriptLabel = new JLabel("-Manuscript: " + stats.getNumberOfObjects(SpecialObject.Manuscript));
        manuscriptLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        objectsPanel.add(manuscriptLabel);

        centerPanel.add(objectsPanel, BorderLayout.EAST);

        add(centerPanel, BorderLayout.CENTER);

    }
}
