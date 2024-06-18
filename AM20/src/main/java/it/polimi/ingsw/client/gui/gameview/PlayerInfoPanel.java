package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.PlayerStats;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class PlayerInfoPanel extends JPanel {
    JLabel playerNameLabel;
    JLabel scoreLabel;

    //stats delle resource
    JLabel animalLabel;
    JLabel plantsLabel;
    JLabel insectLabel;
    JLabel fungiLabel;

    //stats degli object
    JLabel inkwellLabel;
    JLabel quillLabel;
    JLabel manuscriptLabel;

    /**
     * The player info panel is a panel that's found inside the player panel and it says the player's score, stats and
     * the player name.
     * Ps: the player stats are the most important thing in this panel because it shows you the occurrences of every specific
     * resource and every object that a player has in their player table.
     * @param playerName teh player's name
     */

    public PlayerInfoPanel(String playerName) {
        super();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(300, 300));

        Border lineBorder = new LineBorder(Color.BLACK);
        Border marginBorder = new EmptyBorder(0,7,0,7);
        Border compoundBorder = new CompoundBorder(lineBorder, marginBorder);
        setBorder(compoundBorder);

        playerNameLabel = new JLabel(playerName);
        playerNameLabel.setOpaque(false);
        playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        add(playerNameLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BorderLayout(5,5));

        scoreLabel = new JLabel("Score: 0");
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

        //plants
        plantsLabel = new JLabel("-Plants: 0");
        plantsLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        plantsLabel.setForeground(Color.GREEN);
        resourcesPanel.add(plantsLabel);

        //animals
        animalLabel = new JLabel("-Animals: 0");
        animalLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        animalLabel.setForeground(Color.CYAN);
        resourcesPanel.add(animalLabel);

        //fungi
        fungiLabel = new JLabel("-Fungi: 0");
        fungiLabel.setFont(new Font("TimesNewRomans", Font.PLAIN, 15));
        fungiLabel.setForeground(Color.RED);
        resourcesPanel.add(fungiLabel);

        //insect
        insectLabel = new JLabel("-Insect: 0");
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

        //inkwell
        inkwellLabel = new JLabel("-Inkwell: 0");
        inkwellLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        objectsPanel.add(inkwellLabel);
        //quilllabel
        quillLabel = new JLabel("-Quill: 0");
        quillLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        objectsPanel.add(quillLabel);
        //manuscript
        manuscriptLabel = new JLabel("-Manuscript: 0");
        manuscriptLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        objectsPanel.add(manuscriptLabel);

        centerPanel.add(objectsPanel, BorderLayout.EAST);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * This method allows to update the player stats and the player score every time that something happens
     * @param score the player new score
     * @param stats the player new stats
     */
    public void update(int score, PlayerStats stats, PawnColor color){
        switch (color){
            case BLEU:
                playerNameLabel.setForeground(Color.BLUE);
                scoreLabel.setForeground(Color.BLUE);
                break;
            case VERT:
                playerNameLabel.setForeground(Color.GREEN);
                scoreLabel.setForeground(Color.GREEN);
                break;
            case ROUGE:
                playerNameLabel.setForeground(Color.RED);
                scoreLabel.setForeground(Color.RED);
                break;
            case JAUNE:
                playerNameLabel.setForeground(Color.YELLOW);
                scoreLabel.setForeground(Color.YELLOW);
                break;
        }

        scoreLabel.setText("Score: "+score);

        animalLabel.setText("-Animals: " + stats.getNumberOfResources(Kingdom.Animal));
        plantsLabel.setText("-Plants: " + stats.getNumberOfResources(Kingdom.Plant));
        insectLabel.setText("-Insect: " + stats.getNumberOfResources(Kingdom.Insect));
        fungiLabel.setText("-Fungi: " + stats.getNumberOfResources(Kingdom.Fungi));

        inkwellLabel.setText("-Inkwell: "+ stats.getNumberOfObjects(SpecialObject.Inkwell));
        quillLabel.setText("-Quill: " + stats.getNumberOfObjects(SpecialObject.Quill));
        manuscriptLabel.setText("-Manuscript: " + stats.getNumberOfObjects(SpecialObject.Manuscript));
    }

    public void setNickname(String nickname){
        playerNameLabel.setText(nickname);
    }
}
