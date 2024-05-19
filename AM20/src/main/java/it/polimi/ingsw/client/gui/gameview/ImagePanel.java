package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Simple JPanel with an image as background.
 * It's used for displaying card images
 */
class ImagePanel extends JPanel {
    private final Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Insets insets = getInsets(); //draw within border
        int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;
        renderImage(g, image, insets.left, insets.top, w, h);
    }

    protected void renderImage(Graphics g, Image image, int x, int y, int w, int h){
        g.drawImage(image, x, y, w, h, this);
    }

    public static Image loadImage(PlayableCard card) {
        String side = card.getSide() == PlayableCard.FRONT ? "front" : "back";
        String url = "src/main/resources/CODEX_cards_gold_"+side+"/"+card.getID()+".png";
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    public static Image loadImage(ObjectiveCard card) {
        String url = "src/main/resources/CODEX_cards_gold_front/"+card.getID()+".png";
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

}
