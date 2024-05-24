package it.polimi.ingsw.client.gui;

import javax.swing.*;
import java.awt.*;

public class GUIUtils {

    private static JDialog dialog;

    public static Dimension cardDim = new Dimension(150, 100);
    public static int[] cornerGap = new int[]{38, 32};
    public static void showMessage(String message){
        JOptionPane jOptionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        dialog = jOptionPane.createDialog("Info");
        dialog.setLocationRelativeTo(null); // Center the dialog
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        //dialog.setUndecorated(true);
        dialog.setVisible(true);
    }

    public static void disposeMessage(){
        if(dialog!=null)
            dialog.dispose();
    }
    public static void showError(String message){
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(String message){
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

}
