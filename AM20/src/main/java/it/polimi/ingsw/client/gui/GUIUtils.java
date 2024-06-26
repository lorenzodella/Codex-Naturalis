package it.polimi.ingsw.client.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * This class contains utility methods for the GUI.
 */
public class GUIUtils {

    /**
     * The dialog used to show messages.
     */
    private static JDialog dialog;

    /**
     * The dimension of the cards.
     */
    public static Dimension cardDim = new Dimension(150, 100);
    /**
     * The dimension of the corners.
     */
    public static int[] cornerGap = new int[]{38, 32};
    /**
     * The location to display the frame.
     */
    public static Point location = new Point(0,0);

    /**
     * Loads an image from the specified resource path.
     *
     * @param path the path of the image
     * @return the image
     */
    public static Image loadImage(String path) {
        URL url = GUIUtils.class.getResource(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(url));
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading image: "+url);
        }
        return image;
    }

    /**
     * Creates a combo box with the colors of the pawns.
     */
    public static JComboBox<ImageIcon> getImageIconJComboBox() {
        String[] colors = {"red", "blue", "green", "yellow"};
        ImageIcon[] icons = new ImageIcon[colors.length];
        URL url = null;
        for (int i = 0; i < colors.length; i++) {
            try {
                url = GUIUtils.class.getResource("/pawns/" + colors[i] + ".png");
                icons[i] = new ImageIcon(Objects.requireNonNull(url));
                icons[i].setDescription(colors[i]);
            } catch (NullPointerException e) {
                System.err.println("Error loading image: " + url);
            }
        }

        JComboBox<ImageIcon> colorComboBox = new JComboBox<>(icons);
        colorComboBox.setMaximumSize(new Dimension(100, 25));
        colorComboBox.setSelectedIndex(0);
        return colorComboBox;
    }

    /**
     * Shows a message dialog with the specified message.
     * @param parent the parent component
     * @param message the message to show
     */
    public static void showMessage(Component parent, String message){
        SwingUtilities.invokeLater( () -> {
            JOptionPane jOptionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            dialog = jOptionPane.createDialog("Message");
            dialog.setLocationRelativeTo(parent);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
        } );
    }

    /**
     * Disposes the current displaying dialog.
     */
    public static void disposeDialog(){
        if(dialog!=null) {
            dialog.dispose();
            dialog = null;
        }
    }

    /**
     * Shows an error dialog with the specified message.
     * @param parent the parent component
     * @param message the message to show
     */
    public static void showError(Component parent, String message){
        disposeDialog();
        SwingUtilities.invokeLater( () ->{
            //JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
            JOptionPane jOptionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
            dialog = jOptionPane.createDialog("Error");
            dialog.setLocationRelativeTo(parent); // Center the dialog
            dialog.setVisible(true);
        } );
    }

    /**
     * Shows an info dialog with the specified message.
     * @param parent the parent component
     * @param message the message to show
     */
    public static void showInfo(Component parent, String message){
        disposeDialog();
        SwingUtilities.invokeLater( () ->{
            //JOptionPane.showMessageDialog(parent, message, "Info", JOptionPane.INFORMATION_MESSAGE));
            JOptionPane jOptionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
            dialog = jOptionPane.createDialog("Info");
            dialog.setLocationRelativeTo(parent); // Center the dialog
            dialog.setVisible(true);
        } );
    }

    /**
     * Shows an info dialog to notify the user that a chat message has been received. It allows to open the chat.
     * @param parent the parent component
     * @param chat the chat to show
     */
    public static void showChatMessage(Component parent, Chat chat){
        disposeDialog();
        SwingUtilities.invokeLater( () ->{
            //JOptionPane.showMessageDialog(parent, message, "Info", JOptionPane.INFORMATION_MESSAGE));
            JButton[] buttons = new JButton[2];
            buttons[0] = new JButton("Ok");
            buttons[0].addActionListener(e -> disposeDialog());
            buttons[1] = new JButton("Open chat");
            buttons[1].addActionListener(e -> {
                chat.setLocationRelativeTo(parent);
                chat.setVisible(true);
                disposeDialog();
            });
            JOptionPane jOptionPane = new JOptionPane("You received a message", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, buttons, null);
            dialog = jOptionPane.createDialog("New chat message");
            dialog.setLocationRelativeTo(parent); // Center the dialog
            dialog.setVisible(true);
        } );
    }

    /**
     * Shows an info dialog with the specified message.
     * It is used to show important messages, so the dialog is displayed without closing others.
     * @param parent the parent component
     * @param message the message to show
     */
    public static void showImportantInfo(Component parent, String message){
        SwingUtilities.invokeLater( () ->{
            JOptionPane.showMessageDialog(parent, message, "Important", JOptionPane.INFORMATION_MESSAGE);
        } );
    }

}
