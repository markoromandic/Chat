package view.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.json.JSONObject;

import appCore.Core;
import model.Message;
import model.User;
import net.miginfocom.swing.MigLayout;

public class ChatPanel extends JPanel
{
	private JTextPane tpChat;
	private JTextField tfNewMsg;
	private JButton btSend;

	private String userTo;

	public ChatPanel()
	{
		initialize();
	}

	private void initialize()
	{
		MigLayout migLayout = new MigLayout();
		setLayout(migLayout);

		JLabel lbLogo = new JLabel();

		ImageIcon logo = new ImageIcon("images/rafChatLogo.png");
		Image logoConverter = logo.getImage();
		logoConverter = logoConverter.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		logo = new ImageIcon(logoConverter);
		lbLogo = new JLabel(logo);

		add(lbLogo, "push, grow");
	}

	public void initializeChat(String username, boolean type)
	{
		userTo = username;

		removeAll();

		setBorder(BorderFactory.createTitledBorder(username));

		tpChat = new JTextPane();
		tpChat.setMinimumSize(new Dimension(245, 375));
		tpChat.setEditable(false);
		JScrollPane scChat = new JScrollPane(tpChat);
		scChat.setMinimumSize(new Dimension(245, 375));

		tfNewMsg = new JTextField();

		btSend = new JButton("Send");

		tfNewMsg.setMinimumSize(new Dimension(150, (int) btSend.getPreferredSize().getHeight()));

		if (type)
		{
			tfNewMsg.addKeyListener(Core.getInstance().getActionManager().getSendPrivate());
			btSend.addActionListener(Core.getInstance().getActionManager().getSendPrivate());
			btSend.addKeyListener(Core.getInstance().getActionManager().getSendPrivate());
		}
		else
		{
			tfNewMsg.addKeyListener(Core.getInstance().getActionManager().getSendPublic());
			btSend.addActionListener(Core.getInstance().getActionManager().getSendPublic());
			btSend.addKeyListener(Core.getInstance().getActionManager().getSendPublic());
		}

		MigLayout migLayout = new MigLayout();

		setLayout(migLayout);

		add(scChat, "push, grow, wrap");
		add(tfNewMsg, "split 2, pushx, growx");
		add(btSend);

		revalidate();
		validate();

		Core.getInstance().getView().pack();

		tfNewMsg.requestFocusInWindow();

		if (!type)
			return;
		
		// Show stored messages
		for (User user : Core.getInstance().getModel().getOnlineUsers())
		{
			if (user.getUsername().equals(userTo))
			{
				Collections.sort(user.getMessages());
				for (int i = 0; i < user.getMessages().size(); i++)
				{
					printMsg(user.getMessages().get(i));
				}
				break;
			}
		}

		// Get messages from server
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "get_messages");
		jsonObject.put("username", userTo);
		Core.getInstance().getModel().getConnection().getSendMsg().println(jsonObject.toString());
	}

	public void sendMsgView(String username)
	{
		if (!tfNewMsg.getText().equals(""))
		{
			Date date = new Date();

			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

			StyledDocument doc = tpChat.getStyledDocument();

			SimpleAttributeSet stMe = new SimpleAttributeSet();
			StyleConstants.setAlignment(stMe, StyleConstants.ALIGN_JUSTIFIED);

			SimpleAttributeSet stFriend = new SimpleAttributeSet();
			StyleConstants.setBold(stFriend, true);
			StyleConstants.setAlignment(stFriend, StyleConstants.ALIGN_RIGHT);

			StyleConstants.setForeground(stFriend, Color.BLUE);

			try
			{
				doc.insertString(doc.getLength(), username + " - " + dateFormat.format(date) + "\n", stFriend);
				doc.insertString(doc.getLength(), tfNewMsg.getText() + "\n", stMe);

				DefaultCaret caret = (DefaultCaret) tpChat.getCaret();
				caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			}
			catch (BadLocationException e)
			{
				e.printStackTrace();
			}

			tfNewMsg.setText("");
			tfNewMsg.requestFocusInWindow();
		}
	}

	public void printPublicMsg(String username, String message)
	{
		Date date = new Date();

		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

		StyledDocument doc = tpChat.getStyledDocument();

		SimpleAttributeSet stFriend = new SimpleAttributeSet();
		StyleConstants.setBold(stFriend, true);
		StyleConstants.setAlignment(stFriend, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(stFriend, Color.RED);

		try
		{
			doc.insertString(doc.getLength(), username + " - " + dateFormat.format(date) + "\n", stFriend);
			doc.insertString(doc.getLength(), message + "\n", stFriend);

			DefaultCaret caret = (DefaultCaret) tpChat.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setCaretToBottom()
	{
		DefaultCaret caret = (DefaultCaret) tpChat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	public void printMsg(Message message)
	{
		Date date = new Date(message.getTimestamp().getTime());

		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");

		StyledDocument doc = tpChat.getStyledDocument();

		SimpleAttributeSet stFriend = new SimpleAttributeSet();
		StyleConstants.setBold(stFriend, true);
		StyleConstants.setAlignment(stFriend, StyleConstants.ALIGN_RIGHT);
		if (Core.getInstance().getModel().getUserConnected().getUsername().equals(message.getFrom()))
			StyleConstants.setForeground(stFriend, Color.BLUE);
		else
			StyleConstants.setForeground(stFriend, Color.RED);

		try
		{
			doc.insertString(doc.getLength(), message.getFrom() + " - " + dateFormat.format(date) + "\n", stFriend);
			doc.insertString(doc.getLength(), message.getMessage() + "\n", stFriend);

			DefaultCaret caret = (DefaultCaret) tpChat.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public String getNewMsg()
	{
		return tfNewMsg.getText();
	}

	public String getUserTo()
	{
		return userTo;
	}

	public JTextField getTfNewMsg()
	{
		return tfNewMsg;
	}
}
