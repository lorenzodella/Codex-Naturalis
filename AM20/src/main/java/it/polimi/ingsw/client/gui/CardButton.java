package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Simple JButton with an image as background.
 * It's used for displaying card images
 */
public class CardButton extends JButton {
    /**
     * If the button is clickable
     */
    private boolean clickable = false;
    /**
     * The card associated with the button
     */
    private Card card;

    /**
     * Constructor for a CardButton with a name. Used for dummy cards in the map.
     * @param name the name of the button
     */
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

    /**
     * Constructor for a CardButton with a card. Used for cards in the game.
     * @param card the card associated with the button
     */
    public CardButton(Card card) {
        super();
        this.card = card;
        set();
        setFocusPainted(false);
    }

    /**
     * Constructor for a CardButton with a card. Used for cards in the game.
     * @param card the card associated with the button
     * @param clickable if the button is clickable
     */
    public CardButton(Card card, boolean clickable){
        super();
        this.card = card;
        this.clickable = clickable;
        set();
        setFocusPainted(false);
    }

    /**
     * Constructor for an empty card button.
     */
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

    /**
     * Set the icons for the button. The icons are set for different states of the button.
     * @param image the image to set as icon
     */
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

    /**
     * Enable the mouse flipping for the card. The card will flip when the right mouse button is clicked.
     */
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

    /**
     * Get the card side associated with the button.
     * @return the side of the card
     */
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

    /**
     * Load the image of the card.
     * @return the image of the card
     */
    private Image loadCardImage() {
        String side = card.getSide() == PlayableCard.FRONT ? "front" : "back";
        return GUIUtils.loadImage("/CODEX_cards_gold_"+side+"/"+card.getID()+".png");
    }

    /**
     * Update the card associated with the button.
     * @param card the new card to associate with the button
     */
    public void update(Card card){
        this.card = card;
        set();
    }

    /**
     * Clear the button. The button will be empty and not clickable.
     */
    public void clear(){
        //carta con interno grigio, contorno visibile e non cliccable
        this.card = null;
        setPreferredSize(GUIUtils.cardDim);
        setContentAreaFilled(false);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setClickable(false);
        setIcons(null);
    }

    /**
     * Hide the button. The button will be gray and not clickable.
     */
    public void hid(){
        setOpaque(true);
        setBackground(Color.LIGHT_GRAY);
        setEnabled(false);
    }

    /**
     * Flip the card associated with the button.
     */
    public void flip(){
        if(card!=null) {
            card.flip();
            setIcons(loadCardImage());
        }
    }

}
