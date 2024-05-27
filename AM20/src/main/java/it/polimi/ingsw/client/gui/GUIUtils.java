package it.polimi.ingsw.client.gui;

import javax.swing.*;
import java.awt.*;

public class GUIUtils {

    private static JDialog dialog;

    public static Dimension cardDim = new Dimension(150, 100);
    //public static Dimension cardDimSelected = new Dimension(cardDim.width+10, cardDim.height+10);
    public static int[] cornerGap = new int[]{38, 32};
    public static Point location;
    public static void showMessage(Component parent, String message){
        SwingUtilities.invokeLater( () -> {
            JOptionPane jOptionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            dialog = jOptionPane.createDialog("Message");
            dialog.setLocationRelativeTo(parent); // Center the dialog
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
        } );
    }

    public static void disposeMessage(){
        if(dialog!=null) {
            dialog.dispose();
            dialog = null;
        }
    }
    public static void showError(Component parent, String message){
        disposeMessage();
        SwingUtilities.invokeLater( () ->{
            //JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
            JOptionPane jOptionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
            dialog = jOptionPane.createDialog("Error");
            dialog.setLocationRelativeTo(parent); // Center the dialog
            dialog.setVisible(true);
        } );
    }

    public static void showInfo(Component parent, String message){
        disposeMessage();
        SwingUtilities.invokeLater( () ->{
            //JOptionPane.showMessageDialog(parent, message, "Info", JOptionPane.INFORMATION_MESSAGE));
            JOptionPane jOptionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
            dialog = jOptionPane.createDialog("Info");
            dialog.setLocationRelativeTo(parent); // Center the dialog
            dialog.setVisible(true);
        } );
    }

    public static void showChatMessage(Component parent, Chat chat){
        disposeMessage();
        SwingUtilities.invokeLater( () ->{
            //JOptionPane.showMessageDialog(parent, message, "Info", JOptionPane.INFORMATION_MESSAGE));
            JButton[] buttons = new JButton[2];
            buttons[0] = new JButton("Ok");
            buttons[0].addActionListener(e -> disposeMessage());
            buttons[1] = new JButton("Open chat");
            buttons[1].addActionListener(e -> {
                chat.setLocationRelativeTo(parent);
                chat.setVisible(true);
                disposeMessage();
            });
            JOptionPane jOptionPane = new JOptionPane("You received a message", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, buttons, null);
            dialog = jOptionPane.createDialog("New chat message");
            dialog.setLocationRelativeTo(parent); // Center the dialog
            dialog.setVisible(true);
        } );
    }

}
