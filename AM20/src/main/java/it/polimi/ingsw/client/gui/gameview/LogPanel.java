package it.polimi.ingsw.client.gui.gameview;

import it.polimi.ingsw.client.gui.Chat;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class LogPanel extends JPanel {
    private JTextArea logTextArea;
    private Chat chat;

    public LogPanel(Chat chat){
        super();
        this.chat = chat;

        setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(300, 300));
        setMaximumSize(new Dimension(300, 300));

        JButton chatButton = new JButton("Apri chat");
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

    public void log(String log){
        logTextArea.append(log + "\n");
    }

}
