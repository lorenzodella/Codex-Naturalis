package it.polimi.ingsw.client.gui;

import javax.swing.*;
import java.awt.*;

public class GUIUtils {

    private static JDialog dialog;

    public static Dimension cardDim = new Dimension(150, 100);
    public static void showMessage(String message){
        JOptionPane jOptionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        dialog = jOptionPane.createDialog("Info");
        dialog.setLocationRelativeTo(null); // Center the dialog
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        //dialog.setUndecorated(true);
        dialog.setVisible(true);
    }

    public static void disposeMessage(){
        dialog.dispose();
    }

}
