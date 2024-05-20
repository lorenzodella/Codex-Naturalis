package it.polimi.ingsw.client.gui.gameview;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel {
    private JTextArea logTextArea;
    public LogPanel(){
        super();
        setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(300, 300));
        setMaximumSize(new Dimension(300, 300));

        JButton chatButton = new JButton("Apri chat");
        add(chatButton, BorderLayout.SOUTH);

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        logTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        logScrollPane.setPreferredSize(new Dimension(100,100));
        add(logScrollPane, BorderLayout.CENTER);

    }

    public void log(String log){
        logTextArea.append(log + "\n");
    }

}
