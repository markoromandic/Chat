package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import appCore.Core;
import net.miginfocom.swing.MigLayout;

public class SignInPanel extends JPanel
{
	private JLabel lbUsername, lbPassword, lbForgotPassword;
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JButton btSignIn;
	private JButton btRegister;
	
	public SignInPanel()
	{
		initialize();
	}
	
	private void initialize()
	{
		MigLayout migLayout = new MigLayout();
		
		setLayout(migLayout);
		
		lbUsername = new JLabel("Username: ");
		lbPassword = new JLabel("Password: ");
		
		lbForgotPassword = new JLabel("Forgot username/password");
		Font font = lbForgotPassword.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lbForgotPassword.setFont(font.deriveFont(attributes));
		Color blueLink = new Color(0, 102, 204);
		lbForgotPassword.setForeground(blueLink);
		lbForgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lbForgotPassword.addMouseListener(Core.getInstance().getActionManager().getForgotUsernamePassword());
		
		tfUsername = new JTextField();
		tfUsername.addKeyListener(Core.getInstance().getActionManager().getSignIn());
		pfPassword = new JPasswordField();
		pfPassword.addKeyListener(Core.getInstance().getActionManager().getSignIn());
		
		btRegister = new JButton("Register");
		btRegister.addActionListener(Core.getInstance().getActionManager().getOpenRegisterPanel());
		btRegister.addKeyListener(Core.getInstance().getActionManager().getOpenRegisterPanel());
		
		btSignIn = new JButton("Sign in");
		btSignIn.addActionListener(Core.getInstance().getActionManager().getSignIn());
		btSignIn.addKeyListener(Core.getInstance().getActionManager().getSignIn());
		
		add(lbUsername, "split2, sg lb");
		add(tfUsername, "sg tf, pushx, growx, wrap");
		add(lbPassword, "split2, sg lb");
		add(pfPassword, "sg tf, pushx, growx, wrap");
		add(lbForgotPassword, "split3");
		add(btRegister, "sg bt, tag ok");
		add(btSignIn, "sg bt, tag ok");
	}
	
	public String getUsername()
	{
		return tfUsername.getText();
	}
	
	public String getPassword()
	{
		return pfPassword.getText();
	}
	
	public void changeLbForgotPassword()
	{
		Font font = lbForgotPassword.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lbForgotPassword.setFont(font.deriveFont(attributes));
		Color blueLink = Color.BLUE;
		lbForgotPassword.setForeground(blueLink);
	}
	
	public void changeLbForgotPasswordBack()
	{
		Font font = lbForgotPassword.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lbForgotPassword.setFont(font.deriveFont(attributes));
		Color blueLink = new Color(0, 102, 204);
		lbForgotPassword.setForeground(blueLink);
	}
}
