package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.Chat;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

/**
 * Panel that contains the log of the game. It is updated every time a player makes a move.
 * It also contains a button that opens the chat
 */
public class LogPanel extends JPanel {
    private JTextArea logTextArea;
    private JButton chatButton;

    /**
     * Panel that contains all the texts that describe every single move of every single player
     * @param chat the chat that is opened by the chat button
     */
    public LogPanel(Chat chat){
        super();

        setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(300, 300));
        setMaximumSize(new Dimension(300, 300));

        chatButton = new JButton("Apri chat");
        chatButton.addActionListener(e -> chat.setVisible(true));
        add(chatButton, BorderLayout.SOUTH);

        logTextArea = new JTextArea();
        DefaultCaret caret = (DefaultCaret)logTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        logTextArea.setAutoscrolls(true);
        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        logTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        logScrollPane.setAutoscrolls(true);
        logScrollPane.setPreferredSize(new Dimension(100,100));
        add(logScrollPane, BorderLayout.CENTER);

    }

    /**
     * This method allows to add a new log string to the log panel
     * @param log the message to print
     */
    public void log(String log){
        logTextArea.append(log + "\n");
    }

    /**
     * This method allows to disable the chat button. Used when the game is over
     */
    public void disableChat(){
        chatButton.removeActionListener(chatButton.getActionListeners()[0]);
    }

}
