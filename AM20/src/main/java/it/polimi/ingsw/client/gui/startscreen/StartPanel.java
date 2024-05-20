package it.polimi.ingsw.client.gui.startscreen;

import it.polimi.ingsw.client.gui.listeners.JoinGameListener;
import it.polimi.ingsw.client.gui.listeners.NewGameListener;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartPanel extends JPanel {
    //una scritta e due bottoni
    JButton newGameButton;
    JButton joinGameButton;
    JLabel title;

    public StartPanel(){
        super();
        setLayout(new BorderLayout(5,50));
        setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        //crea label
        title = new JLabel("CODEX NATURALIS");
        title.setOpaque(false);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Dialog", Font.BOLD, 50));
        title.setForeground(Color.RED);
        add(title, BorderLayout.NORTH);

        //crea button
        newGameButton = new JButton("Create a new game");
        newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        newGameButton.setFont(new Font("Dialog", Font.PLAIN, 20));
        newGameButton.setPreferredSize(new Dimension(300,50));
        add(newGameButton, BorderLayout.WEST);

        //crea button
        joinGameButton = new JButton("Join a game");
        joinGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        joinGameButton.setFont(new Font("Dialog", Font.PLAIN, 20));
        joinGameButton.setPreferredSize(new Dimension(300,50));
        add(joinGameButton, BorderLayout.EAST);
    }

    public void setNewGameListener(NewGameListener newGameListener){
        newGameButton.addActionListener(newGameListener);
    }
    public void setJoinGameListener(JoinGameListener joinGameListener){
        joinGameButton.addActionListener(joinGameListener);
    }

//    protected void paintComponent(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g.create();
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
//        super.paintComponent(g2);
//        Insets insets = getInsets(); //draw within border
//        int w = getWidth() - insets.left - insets.right;
//        int h = getHeight() - insets.top - insets.bottom;
//        renderImage(g2, loadImage(), insets.left, insets.top, w, h);
//        g2.dispose();
//    }
//
//    protected void renderImage(Graphics g, Image image, int x, int y, int w, int h){
//        g.drawImage(image, x, y, w, h, this);
//    }
//
//    private Image loadImage() {
//        String url = "src/main/resources/Background.png";
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(new File(url));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return image;
//    }
}
