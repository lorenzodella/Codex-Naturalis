package it.polimi.ingsw.client.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUIUtils {

    private static JDialog dialog;

    public static Dimension cardDim = new Dimension(150, 100);
    //public static Dimension cardDimSelected = new Dimension(cardDim.width+10, cardDim.height+10);
    public static int[] cornerGap = new int[]{38, 32};
    public static Point location = new Point(0,0);

    public static JComboBox<ImageIcon> getImageIconJComboBox() {
        ImageIcon rougeIcon = new ImageIcon("src/main/resources/rouge.png");
        rougeIcon.setDescription("rouge");
        ImageIcon bleuIcon = new ImageIcon("src/main/resources/bleu.png");
        bleuIcon.setDescription("bleu");
        ImageIcon vertIcon = new ImageIcon("src/main/resources/vert.png");
        vertIcon.setDescription("vert");
        ImageIcon jauneIcon = new ImageIcon("src/main/resources/jaune.png");
        jauneIcon.setDescription("jaune");

        ImageIcon[] icons = {rougeIcon, bleuIcon, vertIcon, jauneIcon};
        JComboBox<ImageIcon> colorComboBox = new JComboBox<>(icons);
        colorComboBox.setMaximumSize(new Dimension(100, 25));
        colorComboBox.setSelectedIndex(0);
        return colorComboBox;
    }

    public static void showMessage(Component parent, String message){
        SwingUtilities.invokeLater( () -> {
            JOptionPane jOptionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            dialog = jOptionPane.createDialog("Message");
            dialog.setLocationRelativeTo(parent); // Center the dialog
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
        } );
    }

    public static void disposeDialog(){
        if(dialog!=null) {
            dialog.dispose();
            dialog = null;
        }
    }
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

    public static void showImportantInfo(Component parent, String message){
        SwingUtilities.invokeLater( () ->{
            JOptionPane.showMessageDialog(parent, message, "Important", JOptionPane.INFORMATION_MESSAGE);
        } );
    }

}
