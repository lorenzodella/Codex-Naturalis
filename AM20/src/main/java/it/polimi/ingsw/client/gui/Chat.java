/* Della Matera Lorenzo 4E
 * classe Chat che implementa una chat che permette a server/client di comunicare
 */

package it.polimi.ingsw.client.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.List;

public class Chat extends JFrame implements ActionListener{
	private JLabel main;
	private JLabel sender;
	private JScrollPane centerP;
	private JPanel visorP;
	private JPanel bottomP;
	private JPanel topP;
	private JTextField input;
	private JButton send;
	private Font f;

	private JPanel lastPanel;
	private SpringLayout layout;
	private LocalDateTime date;

	public static void main(String[] args) {
		Chat c = new Chat("Server", Color.green, Arrays.asList("Tia", "Lore"));
	}

	public Chat(String nomedellaltro, Color coloredellaltro, List<String> nicknames) {
		super();
		try {
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
				 UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		}

		//setto il titolo
		if(coloredellaltro.equals(Color.red))
			//se ho ricevuto il rosso del server vuol dire che sono il client
			setTitle("Chat - client");
		else
			//se ho ricevuto il verde del client vuol dire che sono il server
			setTitle("Chat - server");
		
		//setto le impostazioni di base
		setSize(350,465);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//creo font e dimensione predefiniti
		f = new Font(Font.DIALOG, Font.PLAIN, 15);
		Dimension d = new Dimension(250,35);
		
		//creo il pannello principale
		main=new JLabel();
		main.setLayout(new BorderLayout());
		
		//creo il pannello superiore
		topP = new JPanel();
		topP.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
		topP.setBackground(coloredellaltro);
		//creo il nome dell'avversario con cui comunico
		sender = new JLabel();
		sender.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		sender.setOpaque(false);
		sender.setText(nomedellaltro);
		//e lo aggiungo al pannello superiore
		topP.add(sender);
		
		//creo il pannello che visualizza i messaggi
		visorP = new JPanel();
		visorP.setOpaque(false);
		layout = new SpringLayout();
		visorP.setLayout(layout);       
		
		//creo il pannello di scorrimento centrale
		centerP = new JScrollPane(visorP, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		centerP.setPreferredSize(new Dimension(300,300));
		centerP.getViewport().setOpaque(false);
		centerP.setOpaque(false);
		
		//creo il pannello inferiore con il campo per inserire il messaggio e il pulsante di invio
		bottomP = new JPanel(new BorderLayout());
		bottomP.setOpaque(false);

		JPanel recipientP = new JPanel();

		JLabel recipientL = new JLabel("Recipient: ");
		recipientP.add(recipientL);
		JComboBox<String> comboBox = new JComboBox<>(nicknames.toArray(new String[0]));
		comboBox.setPreferredSize(new Dimension(100, 25));
		comboBox.addItem("Broadcast");
		recipientP.add(comboBox);

		JPanel inputPanel = new JPanel();
		
		//aggiungo il TextField per scrivere il messaggio
		input = new JTextField();
		input.setFont(f);
		input.setPreferredSize(d);
		//aggiungo un ascoltatore per confermare con Invio
		input.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					sendMessage(input.getText());
			}
			public void keyReleased(KeyEvent e) {}
			
		});
		inputPanel.add(input);
		
		//aggiungo il pulsante di invio
		send = new JButton("Send");
		send.addActionListener(this);
		inputPanel.add(send);

		bottomP.add(recipientP, BorderLayout.NORTH);
		bottomP.add(inputPanel, BorderLayout.SOUTH);

		//aggiungo i pannelli al main e lo aggiungo al frame
		main.add(topP, BorderLayout.NORTH);
		main.add(centerP, BorderLayout.CENTER);
		main.add(bottomP, BorderLayout.SOUTH);

		add(main);
		setVisible(true);
		input.requestFocus();
	}
	
	//quando premo il pulsante di invio
	public void actionPerformed(ActionEvent e) {
		sendMessage(input.getText());
	}
	
	//aggiunge al visore il messaggio e l'orario passato per parametro
	public void aggiornaPannello(JTextArea message, JTextArea time, int align) {
		//creo il layout e il pannello del messaggio
		FlowLayout fl = new FlowLayout(align);
		fl.setHgap(2);
		JPanel panel = new JPanel(fl);
		panel.setOpaque(false);
		
		if(align==FlowLayout.LEFT) {
			//aggiungo orario e messaggio al pannello
			panel.add(message);
			panel.add(time);
			
			//setto la posizione del pannello nel visore e aggiorno la dimensione
			layout.putConstraint(SpringLayout.WEST, panel, 3, SpringLayout.WEST, visorP);
			layout.putConstraint(SpringLayout.NORTH, panel, 5, SpringLayout.SOUTH, lastPanel);
		}
		else if(align==FlowLayout.RIGHT) {
			//aggiungo orario e messaggio al pannello
			panel.add(time);
			panel.add(message);
			
			//setto la posizione del pannello nel visore e aggiorno la dimensione
			layout.putConstraint(SpringLayout.EAST, panel, -3, SpringLayout.EAST, visorP);
			layout.putConstraint(SpringLayout.NORTH, panel, 5, SpringLayout.SOUTH, lastPanel);
		}
		
		//aggiungo il pannello al visore
        visorP.add(panel);
        visorP.validate();
        
        //se il pannello � molto in basso aumento la dimensione del visore
        if(panel.getLocation().y>=270)
			visorP.setPreferredSize(new Dimension(visorP.getWidth(), visorP.getHeight() + panel.getPreferredSize().height + 5 ));

        //sposta la barra verticale
        centerP.getViewport().setViewPosition(new Point(0,panel.getLocation().y));
        centerP.validate();
        
        //aggiorno la variabile che memorizza l'ultimo pannello aggiunto
      	lastPanel = panel;
	}
	
	//manda il messaggio e mostralo nel visore
	public void sendMessage(String text) {
		//controlla che il messaggio non sia vuoto
		/*if(input.getText().trim().isEmpty()) {
			input.setText("");
			input.requestFocus();
			return;
		}*/
		
		//ottieni data e ora attuale
		LocalDateTime now = LocalDateTime.now();
		checkDate(now);
		
		//creo la label con l'orario
		JTextArea time = new JTextArea(date.format(DateTimeFormatter.ofPattern("HH:mm")));
		time.setEditable(false);
		time.setFont(new Font("Dialog", Font.PLAIN, 12));
		time.setOpaque(false);
		time.setMargin(new Insets(10,0,0,0));
		
		//creo la text area con il messaggio da inviare
		JTextArea message = new JTextArea(text);
		//se il messaggio � troppo lungo vai a capo
		if(message.getText().length()>20) {
			message.setColumns(14);
			message.setWrapStyleWord(true);
			message.setLineWrap(true);
		}
		message.setEditable(false);
		message.setFont(f);
		message.setBackground(Color.white);
		message.setMargin(new Insets(2,7,5,7));
		
		//invio il messaggio
		//c.invia("message_" + input.getText());
		
		//aggiungi messaggio e orario al visore
		aggiornaPannello(message, time, FlowLayout.RIGHT);
		
		//ripulisco l'area di input
		input.setText("");
		input.requestFocus();
	}
	
	//mostra nel visore il messaggio arrivato
	public void receiveMessage(String text) {
		//ottieni data e ora attuale
		LocalDateTime now = LocalDateTime.now();
		checkDate(now);
		
		//creo la label con l'orario
		JTextArea time = new JTextArea(date.format(DateTimeFormatter.ofPattern("HH:mm")));
		time.setName("time");
		time.setEditable(false);
		time.setFont(new Font("Dialog", Font.PLAIN, 12));
		time.setOpaque(false);
		time.setMargin(new Insets(10,0,0,0));
		
		//creo la text area con il messaggio da inviare
		JTextArea message = new JTextArea(text);
		message.setName("message");
		//se il messaggio � troppo lungo vai a capo
		if(message.getText().length()>20) {
			message.setColumns(14);
			message.setWrapStyleWord(true);
			message.setLineWrap(true);
		}
		message.setEditable(false);
		message.setFont(f);
		message.setBackground(topP.getBackground());
		message.setMargin(new Insets(2,7,5,7));
		
		aggiornaPannello(message, time, FlowLayout.LEFT);
	}
	
	//controlla se � cambiata la data rispetto all'ultimo messaggio inviato/ricevuto
	private void checkDate(LocalDateTime now) {
		//se il giorno � diverso
		if(date==null || date.getDayOfMonth() != now.getDayOfMonth()) {
			//aggiorno la data
			date = now;
			
			//aggiungo la label e il pannello con la data al visore
			JLabel data = new JLabel(date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
			data.setFont(new Font("Dialog", Font.PLAIN, 12));
			data.setOpaque(false);
			JPanel p = new JPanel();
			p.setOpaque(false);
			p.add(data);
			visorP.add(p);
			
			//setto la posizione del pannello nel visore
			try {
				layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, p, 0, SpringLayout.HORIZONTAL_CENTER, visorP);
				layout.putConstraint(SpringLayout.NORTH, p, lastPanel.getHeight()+5, SpringLayout.NORTH, lastPanel);
			} catch(NullPointerException ex) {
				layout.putConstraint(SpringLayout.NORTH, p, 5, SpringLayout.NORTH, visorP);
			}
			//aggiorno la variabile che memorizza l'ultimo pannello aggiunto
			lastPanel = p;
			
			centerP.validate();
		}
	}
}
