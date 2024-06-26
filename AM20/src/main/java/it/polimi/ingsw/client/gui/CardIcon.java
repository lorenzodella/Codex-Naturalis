package it.polimi.ingsw.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * This class extends ImageIcon to allow for translucent images.
 */
public class CardIcon extends ImageIcon {

    private boolean translucent = false;

    public CardIcon(Image image) {
        super(image);
    }

    public CardIcon(Image image, boolean translucent) {
        super(image);
        this.translucent = translucent;
    }

    /**
     * Paints the icon.  The image is reduced or magnified to fit the component to which
     * it is painted.
     * <P>
     * If the proportion has not been specified, or has been specified as <code>true</code>,
     * the aspect ratio of the image will be preserved by padding and centering the image
     * horizontally or vertically.  Otherwise the image may be distorted to fill the
     * component it is painted to.
     * <P>
     * If this icon has no image observer,this method uses the <code>c</code> component
     * as the observer.
     *
     * @param c the component to which the Icon is painted.  This is used as the
     *          observer if this icon has no image observer
     * @param g the graphics context
     * @param x not used.
     * @param y not used.
     *
     * @see ImageIcon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
     */
    @Override
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        Image image = getImage();
        if (image == null) {
            return;
        }
        Insets insets = ((Container) c).getInsets();
        x = insets.left;
        y = insets.top;

        int w = c.getWidth() - x - insets.right;
        int h = c.getHeight() - y - insets.bottom;

        int iw = image.getWidth(c);
        int ih = image.getHeight(c);

        if (iw * h < ih * w) {
            iw = (h * iw) / ih;
            x += (w - iw) / 2;
            w = iw;
        } else {
            ih = (w * ih) / iw;
            y += (h - ih) / 2;
            h = ih;
        }

        ImageObserver io = getImageObserver();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, translucent ? 0.7f : 1.0f));
        g2.drawImage(image, x, y, w, h, io == null ? c : io);
    }

    /**
     * Overridden to return 0.  The size of this Icon is determined by
     * the size of the component.
     *
     * @return 0
     */
    @Override
    public int getIconWidth() {
        return 0;
    }

    /**
     * Overridden to return 0.  The size of this Icon is determined by
     * the size of the component.
     *
     * @return 0
     */
    @Override
    public int getIconHeight() {
        return 0;
    }
}
