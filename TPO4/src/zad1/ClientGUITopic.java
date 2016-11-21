package zad1;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

public class ClientGUITopic extends JFrame {

	private static final long serialVersionUID = 1L;

	private String separator = "-----------------------\n";

	private JPanel mainPanel;
	private JPanel LogingPanel;
	private JPanel ChatPanel;
	private JTextField textField;
	private JTextArea chatTextArea;
	private JTextArea msgTextArea;
	private JButton btnSend;
	private JScrollPane chatScroll;
	private JScrollPane msgScroll;

	private String login;
	private static String DestinationName1;
	private static String DestinationName2;

	public ClientGUITopic() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);
		mainPanel.setLayout(new CardLayout(0, 0));

		LogingPanel = new JPanel();
		mainPanel.add(LogingPanel, "name_167754409975858");
		LogingPanel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(136, 94, 164, 22);
		LogingPanel.add(textField);
		textField.setColumns(10);

		JLabel lblLogin = new JLabel("User name");
		lblLogin.setBounds(54, 97, 70, 16);
		LogingPanel.add(lblLogin);

		JButton btnLogIn = new JButton("Log in");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loginButtonClicked();
			}
		});
		btnLogIn.setBounds(156, 154, 97, 25);
		LogingPanel.add(btnLogIn);

		ChatPanel = new JPanel();
		mainPanel.add(ChatPanel, "name_167993342401307");
		ChatPanel.setLayout(null);

		chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);

		chatScroll = new JScrollPane(chatTextArea);
		chatScroll.setBounds(0, 0, 422, 193);
		ChatPanel.add(chatScroll);

		DefaultCaret chatCaret = (DefaultCaret) chatTextArea.getCaret();
		chatCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		msgTextArea = new JTextArea();

		msgScroll = new JScrollPane(msgTextArea);
		msgScroll.setBounds(0, 206, 298, 37);
		ChatPanel.add(msgScroll);

		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				sendMsg(login + " says: " + msgTextArea.getText() + "\n" + separator);
				msgTextArea.setText("");
			}

		});
		btnSend.setBounds(313, 206, 97, 25);
		ChatPanel.add(btnSend);

	}
 
	private void loginButtonClicked() {
		if (!textField.getText().equals("")) {
			login = textField.getText();

			CardLayout cl = (CardLayout) (mainPanel.getLayout());
			cl.last(mainPanel);
			new ReciveMessages().start();
		}
	}

	private void sendMsg(String sMsg) {

		new Sender(DestinationName1, sMsg);
	}

	class ReciveMessages extends Thread {

		public void run() {

			while (true) {
				Receiver receiver = new Receiver(DestinationName2);

				String msg = receiver.getMSG();

				if (msg.equals("")) {

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					chatTextArea.append(msg);
				}
			}
		}

	}

	public static void main(String[] args) {

		DestinationName1 = args[0];
		DestinationName2 = args[1]; 


		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUITopic frame = new ClientGUITopic();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
