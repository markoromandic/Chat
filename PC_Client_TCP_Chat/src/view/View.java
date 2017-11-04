package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import appCore.Core;
import net.miginfocom.swing.MigLayout;
import view.main.ChatPanel;
import view.main.SplitPanel;
import view.menubar.MenuBar;

public class View extends JFrame
{
	private MenuBar menuBar;
	
	private SignInPanel signInPanel;
	private ChatPanel chatPanel;
	private RegisterUserPanel registerUserPanel;
	private SplitPanel splitPanel;
	private MigLayout migLayout;
	
	public View()
	{
		initializeView();
	}
	
	private void initializeView()
	{
		migLayout = new MigLayout();
		setLayout(migLayout);
		setTitle("RAF Chat");
		
		signInPanel = new SignInPanel();
		getContentPane().add(signInPanel, "push, grow");
		
		menuBar = new MenuBar();
		
		setJMenuBar(menuBar);
		
		setResizable(false);
		setMinimumSize(new Dimension(400, 150));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		ImageIcon iconNotResized = new ImageIcon("images/rafChatLogo.png");

		Image icon = iconNotResized.getImage();
		icon = icon.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		setIconImage(icon);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		
		setVisible(true);
		
		pack();
	}
	
	public void initializeSignIn()
	{
		signInPanel = new SignInPanel();
		setContentPane(signInPanel);
		
		setResizable(false);
		setMinimumSize(new Dimension(400, 150));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		
		setVisible(true);
		
		pack();
	}
	
	public void initializeRegisterUser()
	{
		registerUserPanel = new RegisterUserPanel();
		
		setContentPane(registerUserPanel);
		revalidate();
		validate();
		
		setMinimumSize(new Dimension(350, 175));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		
		setVisible(true);
		
		pack();
	}
	
	public void initializeSplitPanel()
	{
		splitPanel = new SplitPanel();
		
		setContentPane(splitPanel);
		revalidate();
		validate();
		
		setTitle("RAF Chat [" + Core.getInstance().getModel().getUserConnected() + "]");
		
		setMinimumSize(new Dimension(500, 250));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		
		setVisible(true);
		
		pack();
	}

	public SignInPanel getSignInPanel()
	{
		return signInPanel;
	}

	public RegisterUserPanel getRegisterUserPanel()
	{
		return registerUserPanel;
	}

	public SplitPanel getSplitPanel()
	{
		return splitPanel;
	}
	
	
	
	
	
//	public void initializePrivateChat()
//	{
//		chatPanel = new ChatPanel();
//		
//		setContentPane(chatPanel);
//		
//		revalidate();
//		validate();
//		
//		setMinimumSize(new Dimension(300, 475));
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		
//		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
//		
//		setVisible(true);
//		
//		chatPanel.getTfNewMsg().requestFocusInWindow();
//		
//		pack();
//	}
}
