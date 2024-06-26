/* Della Matera Lorenzo 4E
 * classe Chat che implementa una chat che permette a server/client di comunicare
 */

package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.gui.listeners.ChatListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.List;

/**
 * Class Chat creates a chat window that allows client to communicate to each other
 */
public class Chat extends JDialog implements ActionListener{

    private JLabel senderLabel;
	private JScrollPane centerP;
	private JPanel visorP;
    private JPanel topP;
	private JTextField input;
    private Font f;
	private JPanel lastPanel;
	private SpringLayout layout;
	private LocalDateTime date;
	private JComboBox<String> comboBox;
	private ChatListener chatListener;

	public Chat(Frame parent) {
		super(parent);

		setTitle("Chat");
		
		setSize(350,465);
		setResizable(false);
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		f = new Font(Font.DIALOG, Font.PLAIN, 15);
		Dimension d = new Dimension(250,35);

        JLabel main = new JLabel();
		main.setLayout(new BorderLayout());
		
		topP = new JPanel();
		topP.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
		topP.setBackground(Color.GREEN);
		senderLabel = new JLabel();
		senderLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		senderLabel.setOpaque(false);
		senderLabel.setText("Chat");
		topP.add(senderLabel);
		
		visorP = new JPanel();
		visorP.setOpaque(false);
		layout = new SpringLayout();
		visorP.setLayout(layout);       
		
		centerP = new JScrollPane(visorP, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		centerP.setPreferredSize(new Dimension(300,300));
		centerP.getViewport().setOpaque(false);
		centerP.setOpaque(false);

        JPanel bottomP = new JPanel(new BorderLayout());
		bottomP.setOpaque(false);

		JPanel recipientP = new JPanel();

		JLabel recipientL = new JLabel("To: ");
		recipientP.add(recipientL);
		comboBox = new JComboBox<>();
		comboBox.setPreferredSize(new Dimension(100, 25));
		comboBox.addItem("everyone");
		recipientP.add(comboBox);

		JPanel inputPanel = new JPanel();
		
		input = new JTextField();
		input.setFont(f);
		input.setPreferredSize(d);
		input.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					sendMessage(input.getText(), String.valueOf(comboBox.getSelectedItem()));
			}
		});
		inputPanel.add(input);

        JButton send = new JButton("Send");
		send.addActionListener(this);
		inputPanel.add(send);

		bottomP.add(recipientP, BorderLayout.NORTH);
		bottomP.add(inputPanel, BorderLayout.SOUTH);

		main.add(topP, BorderLayout.NORTH);
		main.add(centerP, BorderLayout.CENTER);
		main.add(bottomP, BorderLayout.SOUTH);

		add(main);
		input.requestFocus();
	}

	/**
	 * Method sets the players to which the client can send messages
	 * @param players is a list of players
	 */
	public void setPlayers(List<String> players){
		for(String player : players)
			comboBox.addItem(player);
	}
	public void setNickname(String nickname){
		senderLabel.setText(nickname);
	}

	/**
	 * Method sets the chat listener
	 * @param chatListener is the chat listener
	 */
	public void setChatListener(ChatListener chatListener){
		this.chatListener = chatListener;
	}

	/**
	 * Method called when user clicks on the send button
	 * @param e is the event
	 */
	public void actionPerformed(ActionEvent e) {
		sendMessage(input.getText(), String.valueOf(comboBox.getSelectedItem()));
	}

	/**
	 * Method updates the chat window with the message sent by the client
	 * @param message is the panel displaying the message sent by the client
	 * @param time is the panel displaying the time the message was sent
	 * @param align is the alignment of the message (left if received, right if sent)
	 */
	private void update(JPanel message, JTextArea time, int align) {
		FlowLayout fl = new FlowLayout(align);
		fl.setHgap(2);
		JPanel panel = new JPanel(fl);
		panel.setOpaque(false);
		
		if(align==FlowLayout.LEFT) {
			panel.add(message);
			panel.add(time);
			
			layout.putConstraint(SpringLayout.WEST, panel, 3, SpringLayout.WEST, visorP);
			layout.putConstraint(SpringLayout.NORTH, panel, 5, SpringLayout.SOUTH, lastPanel);
		}
		else if(align==FlowLayout.RIGHT) {
			panel.add(time);
			panel.add(message);
			
			layout.putConstraint(SpringLayout.EAST, panel, -3, SpringLayout.EAST, visorP);
			layout.putConstraint(SpringLayout.NORTH, panel, 5, SpringLayout.SOUTH, lastPanel);
		}
		
		visorP.add(panel);
        visorP.validate();
        
        if(panel.getLocation().y>=270)
			visorP.setPreferredSize(new Dimension(visorP.getWidth(), visorP.getHeight() + panel.getPreferredSize().height + 5 ));

        centerP.getViewport().setViewPosition(new Point(0,panel.getLocation().y));
        centerP.validate();
        
      	lastPanel = panel;
		input.requestFocus();
	}

	/**
	 * Method notifies the listener and adds the message to the chat window
	 * @param text is the message to be sent
	 * @param recipient is the recipient of the message
	 */
	private void sendMessage(String text, String recipient) {
		if(input.getText().trim().isEmpty()) {
			input.setText("");
			input.requestFocus();
			return;
		}
		
		LocalDateTime now = LocalDateTime.now();
		checkDate(now);
		
		JTextArea time = new JTextArea(date.format(DateTimeFormatter.ofPattern("HH:mm")));
		time.setFocusable(false);
		time.setEditable(false);
		time.setFont(new Font("Dialog", Font.PLAIN, 12));
		time.setOpaque(false);
		time.setMargin(new Insets(10,0,0,0));
		
		JTextArea message = new JTextArea(text);
		message.setFocusable(false);
		if(message.getText().length()>20) {
			message.setColumns(14);
			message.setWrapStyleWord(true);
			message.setLineWrap(true);
		}
		message.setEditable(false);
		message.setFont(f);
		message.setBackground(Color.white);
		message.setMargin(new Insets(2,7,5,7));

		JLabel label = new JLabel(recipient);
		label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		label.setForeground(Color.GREEN);

		JPanel msgP = new JPanel(new BorderLayout());
		msgP.setBackground(Color.WHITE);
		msgP.add(message, BorderLayout.CENTER);
		msgP.add(label, BorderLayout.NORTH);

		chatListener.send(text, recipient);
		
		update(msgP, time, FlowLayout.RIGHT);
		
		input.setText("");
	}

	/**
	 * Method adds the message received by the client to the chat window
	 * @param text is the message received
	 * @param sender is the sender of the message
	 */
	public void receiveMessage(String text, String sender) {
		LocalDateTime now = LocalDateTime.now();
		checkDate(now);
		
		JTextArea time = new JTextArea(date.format(DateTimeFormatter.ofPattern("HH:mm")));
		time.setName("time");
		time.setFocusable(false);
		time.setEditable(false);
		time.setFont(new Font("Dialog", Font.PLAIN, 12));
		time.setOpaque(false);
		time.setMargin(new Insets(10,0,0,0));
		
		JTextArea message = new JTextArea(text);
		message.setFocusable(false);
		message.setName("message");
		if(message.getText().length()>20) {
			message.setColumns(14);
			message.setWrapStyleWord(true);
			message.setLineWrap(true);
		}
		message.setEditable(false);
		message.setFont(f);
		message.setBackground(topP.getBackground());
		message.setMargin(new Insets(2,7,5,7));

		JLabel label = new JLabel(sender);
		label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		label.setForeground(Color.WHITE);

		JPanel msgP = new JPanel(new BorderLayout());
		msgP.setBackground(Color.GREEN);
		msgP.add(message, BorderLayout.CENTER);
		msgP.add(label, BorderLayout.NORTH);
		
		update(msgP, time, FlowLayout.LEFT);
	}

	/**
	 * Method checks if the date of the message is the same as the current date
	 * @param now is the current date
	 */
	private void checkDate(LocalDateTime now) {
		if(date==null || date.getDayOfMonth() != now.getDayOfMonth()) {
			date = now;
			
			JLabel data = new JLabel(date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
			data.setFont(new Font("Dialog", Font.PLAIN, 12));
			data.setOpaque(false);
			JPanel p = new JPanel();
			p.setOpaque(false);
			p.add(data);
			visorP.add(p);
			
			try {
				layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, p, 0, SpringLayout.HORIZONTAL_CENTER, visorP);
				layout.putConstraint(SpringLayout.NORTH, p, lastPanel.getHeight()+5, SpringLayout.NORTH, lastPanel);
			} catch(NullPointerException ex) {
				layout.putConstraint(SpringLayout.NORTH, p, 5, SpringLayout.NORTH, visorP);
			}
			lastPanel = p;
			
			centerP.validate();
		}
	}
}
