package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.GUIUtils;
import it.polimi.ingsw.client.gui.CardButton;
import it.polimi.ingsw.client.gui.listeners.MapListener;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;
import it.polimi.ingsw.model.util.DynamicMap;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

/**
 * Scrollable panel which displays player's map of cards.
 */
public class TablePanel extends JScrollPane {
    private DynamicMap<String, PlayableCard> map;
    private StarterCard starterCard;
    private SpringLayout layout;
    private JLayeredPane layeredPane;
    
    private ActionListener buttonListener;

    /**
     * Creates a new panel which displays cards in the <code>map</code>, starting from the <code>starterCard</code>.
     * @param map map containing the cards
     * @param starterCard starter card of the player
     */
    public TablePanel(DynamicMap<String, PlayableCard> map, StarterCard starterCard){
        super(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.map = map;
        this.starterCard = starterCard;

        layout = new SpringLayout();

        layeredPane = new JLayeredPane() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(map.width()*(GUIUtils.cardDim.width+100), (map.height())*(GUIUtils.cardDim.height+100));
            }
        };
        layeredPane.setLayout(layout);

        Dragger dragger = new Dragger(layeredPane);
        layeredPane.setAutoscrolls(true);
        layeredPane.addMouseListener(dragger);
        layeredPane.addMouseMotionListener(dragger);
        layeredPane.setFocusable(true);

        setViewportView(layeredPane);
        update(map);
    }

    /**
     * Add a listener for click of buttons. It's called when user want to insert a new card.
     * @param buttonListener ActionListener to be added to every button
     */
    public void setMapListener(MapListener buttonListener){
        this.buttonListener = buttonListener;
        for(Component c : layeredPane.getComponents()){
            CardButton b = (CardButton) c;
            if(b.isClickable())
                b.addActionListener(buttonListener);
        }
    }

    /**
     * Fill the panel with images of the cards.
     */
    private void addCards() {
        HashSet<PlayableCard> alreadyPrinted = new HashSet<>();
        PlayableCard card = starterCard;
        alreadyPrinted.add(card);
        JComponent b = createCard(card);
        layeredPane.add(b);
        layeredPane.setLayer(b, card.getOrder());
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, b, 0, SpringLayout.HORIZONTAL_CENTER, layeredPane);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, b, 0, SpringLayout.VERTICAL_CENTER, layeredPane);

        for (int c = Corner.UL; c <= Corner.DR; c++) {
            try {
                addCardsRecursive(alreadyPrinted, b, card, c);
            } catch (TargetNotPresentException | InvalidPositionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Fill the panel with cards images for each corner of the given card.
     * @param alreadyPrinted cards already printed (if <code>card</code> is one of them
     *                       don't display it)
     * @param old last image inserted (used to display the new image near it)
     * @param card last printed card
     * @param corner corner of the old card where the new image must be printed
     * @throws TargetNotPresentException <code>card</code> is not present in the map
     * @throws InvalidPositionException <code>corner</code> is not valid
     */
    private void addCardsRecursive(HashSet<PlayableCard> alreadyPrinted,
                                   Component old,
                                   PlayableCard card, int corner) throws TargetNotPresentException, InvalidPositionException {

        card = map.getElementAt(card.getID(), corner);
        if(card!=null && !alreadyPrinted.contains(card)){
            alreadyPrinted.add(card);
            // if card is not dummy, create a card image, otherwise create a button
            Component b = card.isValid() ? createCard(card) : createButton(card);
            layeredPane.add(b);
            layeredPane.setLayer(b, card.getOrder());

            layout.putConstraint(
                    corner <= Corner.UR ? SpringLayout.SOUTH : SpringLayout.NORTH,
                    b,
                    corner <= Corner.UR ? 50 : -50,
                    corner <= Corner.UR ? SpringLayout.NORTH : SpringLayout.SOUTH,
                    old);
            layout.putConstraint(
                    corner % 2 == 0 ? SpringLayout.EAST : SpringLayout.WEST,
                    b,
                    corner % 2 == 0 ? 40 : -40,
                    corner % 2 == 0 ? SpringLayout.WEST : SpringLayout.EAST,
                    old);

            if(card.isValid())
                for (int c = Corner.UL; c <= Corner.DR; c++) {
                    addCardsRecursive(alreadyPrinted, b, card, c);
                }
        }
    }

    private JButton createCard1(PlayableCard card){
        JButton b = new JButton();
        b.setPreferredSize(GUIUtils.cardDim);
        b.setOpaque(true);
        b.setFocusPainted(false);
        b.setText(card.getID() +"-"+ card.getOrder() +"-"+ card.getSide());
        return b;
    }

    /**
     * Create an ImagePanel with the card image.
     * @param card the card whose image will be displayed
     * @return an ImagePanel to display
     */
    private CardButton createCard(PlayableCard card){
        CardButton imgPanel = new CardButton(card);
        imgPanel.setBorder(new LineBorder(Color.BLACK, 1));
        imgPanel.setPreferredSize(GUIUtils.cardDim);
        return imgPanel;
    }

    /**
     * Create a transparent button where a dummy card is present. The <code>name</code> attribute of
     * the button is set to the ID of the dummy card.
     * @param card dummy card
     * @return a JButton to display
     */
    private CardButton createButton(PlayableCard card){
        CardButton b = new CardButton(card.getID());
        b.addActionListener(buttonListener);

        return b;
    }

    /**
     * Update viewport with a new map of cards.
     * @param map map of PlayableCards to display
     */
    public void update(DynamicMap<String, PlayableCard> map){
        this.map = map;
        layeredPane.removeAll();
        addCards();

        layeredPane.revalidate();
        layeredPane.repaint();
        revalidate();
        repaint();

        //System.out.println(map);
    }

}

/**
 * MouseListener which listen for mouse dragging in order to move viewport of this scrollpane
 */
class Dragger extends MouseAdapter implements MouseMotionListener
{
    private Point origin;
    private JComponent view;

    public Dragger(JComponent view){
        this.view = view;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        view.requestFocus();
        origin = new Point(e.getPoint());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (origin != null) {
            JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, view);
            if (viewPort != null) {
                int deltaX = origin.x - e.getX();
                int deltaY = origin.y - e.getY();

                Rectangle rect = viewPort.getViewRect();
                rect.x += deltaX;
                rect.y += deltaY;

                view.scrollRectToVisible(rect);
            }
        }
    }
}
