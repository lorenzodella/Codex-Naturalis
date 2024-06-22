package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Simple JPanel with an image as background.
 * It's used for displaying card images
 */
public class CardButton extends JButton {
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
        setFocusPainted(false);
    }

    public CardButton(Card card) {
        super();
        this.card = card;
        set();
        setFocusPainted(false);
    }

    public CardButton(Card card, boolean clickable){
        super();
        this.card = card;
        this.clickable = clickable;
        set();
        setFocusPainted(false);
    }

    public CardButton(){
        super();
        clear();
        setFocusPainted(false);
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
        setFocusPainted(false);
    }

    private void set(){
        setBorder(BorderFactory.createEmptyBorder());
        setIcons(loadCardImage());

        setClickable(clickable);

    }

    private void setIcons(Image image){
        CardIcon icon = null, translucentIcon = null;
        if(image!=null) {
            icon = new CardIcon(image);
            translucentIcon = new CardIcon(image, true);
        }

        //per un bottone cliccabile
        setIcon(icon);
        //per un bottone non cliccabile
        setDisabledIcon(icon);
        //quando è cliccato
        setPressedIcon(icon);
        //quando è selezionato
        setSelectedIcon(translucentIcon);
        //per sbiadire
        setRolloverIcon(translucentIcon);
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

    @Override
    public void setSelected(boolean selected){
        super.setSelected(selected);
        setBorder(selected ? BorderFactory.createLineBorder(Color.BLACK, 1) : BorderFactory.createEmptyBorder());
        //setPreferredSize(selected ? GUIUtils.cardDimSelected : GUIUtils.cardDim);
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

    private Image loadCardImage() {
        String side = card.getSide() == PlayableCard.FRONT ? "front" : "back";
        return GUIUtils.loadImage("/CODEX_cards_gold_"+side+"/"+card.getID()+".png");
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
        setIcons(null);
    }

    public void hid(){
        setOpaque(true);
        setBackground(Color.LIGHT_GRAY);
        setEnabled(false);
    }

    public void flip(){
        if(card!=null) {
            card.flip();
            setIcons(loadCardImage());
        }
    }

}
