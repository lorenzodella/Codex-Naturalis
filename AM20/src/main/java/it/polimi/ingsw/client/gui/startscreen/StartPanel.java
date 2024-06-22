package it.polimi.ingsw.client.gui.startscreen;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.listeners.JoinGameListener;
import it.polimi.ingsw.client.gui.listeners.NewGameListener;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class StartPanel extends JPanel {
    //una scritta e due bottoni
    JButton newGameButton;
    JButton joinGameButton;
    JLabel title;

    Image image;

    public StartPanel(){
        super();
        setLayout(new BorderLayout(5,50));
        setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        //crea label
//        title = new JLabel("CODEX NATURALIS");
//        title.setOpaque(false);
//        title.setHorizontalAlignment(SwingConstants.CENTER);
//        title.setFont(new Font("Dialog", Font.BOLD, 50));
//        title.setForeground(Color.RED);
//        add(title, BorderLayout.NORTH);

        Dimension d = new Dimension(250, 80);

        //crea button
        newGameButton = new JButton("Create a new game");
        newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        newGameButton.setFont(new Font("Dialog", Font.PLAIN, 20));
        newGameButton.setMaximumSize(d);
        newGameButton.setPreferredSize(d);
        JPanel newGamePanel = new JPanel();
        newGamePanel.setLayout(new BoxLayout(newGamePanel, BoxLayout.Y_AXIS));
        newGamePanel.setOpaque(false);
        newGamePanel.add(Box.createVerticalGlue());
        newGamePanel.add(newGameButton);
        newGamePanel.add(Box.createVerticalGlue());
        add(newGamePanel, BorderLayout.WEST);

        //crea button
        joinGameButton = new JButton("Join a game");
        joinGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        joinGameButton.setFont(new Font("Dialog", Font.PLAIN, 20));
        joinGameButton.setMaximumSize(d);
        joinGameButton.setPreferredSize(d);
        JPanel joinGamePanel = new JPanel();
        joinGamePanel.setLayout(new BoxLayout(joinGamePanel, BoxLayout.Y_AXIS));
        joinGamePanel.setOpaque(false);
        joinGamePanel.add(Box.createVerticalGlue());
        joinGamePanel.add(joinGameButton);
        joinGamePanel.add(Box.createVerticalGlue());
        add(joinGamePanel, BorderLayout.EAST);

        image = GUIUtils.loadImage("/background.png");

    }

    public void setNewGameListener(NewGameListener newGameListener){
        newGameButton.addActionListener(newGameListener);
    }
    public void setJoinGameListener(JoinGameListener joinGameListener){
        joinGameButton.addActionListener(joinGameListener);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        super.paintComponent(g2);
        Insets insets = getInsets(); //draw within border
        int w = getWidth();
        int h = getHeight();
        renderImage(g2, image, 0,0, w, h);
        g2.dispose();
    }

    protected void renderImage(Graphics g, Image image, int x, int y, int w, int h){
        g.drawImage(image, x, y, w, h, this);
    }
}
