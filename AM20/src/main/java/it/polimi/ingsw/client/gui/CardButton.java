package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Simple JPanel with an image as background.
 * It's used for displaying card images
 */
public class CardButton extends JButton {
    private Image image;
    private boolean clickable = false;
    private Card card;

    public CardButton(String name){
        super();
        setName(name);
        setPreferredSize(GUIUtils.cardDim);
        setContentAreaFilled(false);
        setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        clickable = true;
        enableMouseFlipping();
    }

    public CardButton(Card card) {
        super();
        this.card = card;
        set();
    }

    public CardButton(Card card, boolean clickable){
        super();
        this.card = card;
        this.clickable = clickable;
        set();
    }

    public CardButton(){
        super();
        clear();
    }

    public CardButton(CardButton copy){
        super();
        this.card = copy.card;
        this.clickable = false;
        if (card == null) {
            clear();
        } else {
            set();
        }
    }

    private void set(){
        image = loadImage();
        setBorder(BorderFactory.createEmptyBorder());
        CardIcon icon = new CardIcon(image);
        //per un bottone cliccabile
        setIcon(icon);
        //per un bottone non cliccabile
        setDisabledIcon(icon);
        //quando è cliccato
        setPressedIcon(icon);
        //quando è selezionato
        setSelectedIcon(new CardIcon(image, true));
        //per sbiadire
        setRolloverIcon(new CardIcon(image, true));

        setClickable(clickable);

    }

    public void enableMouseFlipping(){
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3 && clickable){
                    flip();
                }
            }
        });
    }

    public int getCardSide(){
        return card.getSide();
    }

    public void setClickable(boolean clickable){
        this.clickable = clickable;
        setEnabled(clickable);
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setSelected(boolean selected){
        super.setSelected(selected);
        //setIcon(new CardIcon(image, selected));
    }

    //    protected void paintComponent(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g.create();
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
//        super.paintComponent(g2);
//        Insets insets = getInsets(); //draw within border
//        int w = getWidth() - insets.left - insets.right;
//        int h = getHeight() - insets.top - insets.bottom;
//        renderImage(g2, image, insets.left, insets.top, w, h);
//        g2.dispose();
//    }
//
//    protected void renderImage(Graphics g, Image image, int x, int y, int w, int h){
//        g.drawImage(image, x, y, w, h, this);
//    }

    public Image loadImage() {
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

    public void update(Card card){
        this.card = card;
        set();
    }

    public void clear(){
        //carta con interno grigio, contorno visibile e non cliccable
        this.card = null;
        setPreferredSize(GUIUtils.cardDim);
        setContentAreaFilled(false);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setClickable(false);
    }

    public void hid(){
        setOpaque(true);
        setBackground(Color.LIGHT_GRAY);
        setEnabled(false);
    }

    public void flip(){
        card.flip();
        image = loadImage();
        CardIcon icon = new CardIcon(image, isSelected());
        setIcon(icon);
        setPressedIcon(icon);
        setSelectedIcon(new CardIcon(image, true));
        setDisabledIcon(icon);
        setRolloverIcon(new CardIcon(image, true));
    }

}
