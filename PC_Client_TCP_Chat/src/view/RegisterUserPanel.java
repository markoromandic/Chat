package view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import appCore.Core;
import model.EmailValidator;
import net.miginfocom.swing.MigLayout;

public class RegisterUserPanel extends JPanel
{
	private JLabel lbUsername, lbEmail, lbRepEmail, lbPassword, lbRepPassword, lbError;
	
	private JTextField tfUsername, tfEmail, tfRepEmail;
	private JPasswordField pfPassword, pfRepPassword;
	
	private JButton btRegister;
	
	public RegisterUserPanel()
	{
		initialize();
	}
	
	private void initialize()
	{
		MigLayout migLayout = new MigLayout();
		
		setLayout(migLayout);
		
		lbUsername = new JLabel("Username: ");
		lbEmail = new JLabel("Email: ");
		lbRepEmail = new JLabel("Confirm email: ");
		lbPassword = new JLabel("Password: ");
		lbRepPassword = new JLabel("Confirm password: ");
		
		tfUsername = new JTextField();
		tfUsername.addKeyListener(Core.getInstance().getActionManager().getRegister());
		
		tfEmail = new JTextField();
		tfEmail.addKeyListener(Core.getInstance().getActionManager().getRegister());
		
		tfRepEmail = new JTextField();
		tfRepEmail.addKeyListener(Core.getInstance().getActionManager().getRegister());
		
		pfPassword = new JPasswordField();
		pfPassword.addKeyListener(Core.getInstance().getActionManager().getRegister());
		
		pfRepPassword = new JPasswordField();
		pfRepPassword.addKeyListener(Core.getInstance().getActionManager().getRegister());
		
		lbError = new JLabel("ERROR");
		
		btRegister =  new JButton("Register");
		btRegister.addActionListener(Core.getInstance().getActionManager().getRegister());
		btRegister.addKeyListener(Core.getInstance().getActionManager().getRegister());
		
		add(lbUsername, "split2, sg lb");
		add(tfUsername, "sg txt, pushx, growx, wrap");
		add(lbEmail, "split2, sg lb");
		add(tfEmail, "sg txt, pushx, growx, wrap");
		add(lbRepEmail, "split2, sg lb");
		add(tfRepEmail, "sg txt, pushx, growx, wrap");
		add(lbPassword, "split2, sg lb");
		add(pfPassword, "sg txt, pushx, growx, wrap");
		add(lbRepPassword, "split2, sg lb");
		add(pfRepPassword, "sg txt, pushx, growx, wrap");
		add(lbError, "split2");
		add(btRegister, "tag ok");
	}
	
	public String getUsername()
	{
		if(tfUsername.getText().length() != 0)
			return tfUsername.getText().trim();
		else
			return "";
	}
	
	public String getPassword()
	{
		String password = pfPassword.getText();
		String reppassword = pfRepPassword.getText();
		
		if(password.equals(reppassword) && password.length() > 0)
			return password;
		else if(password.length() == 0)
			return "$";
		else 
			return "";
	}
	
	public String getEmail()
	{
		String email = tfEmail.getText().trim();
		String repemail = tfRepEmail.getText().trim();
		
		if(email.equals(repemail) && email.length() > 0)
		{
			EmailValidator emailValidator = new EmailValidator();
			if(emailValidator.validate(email))
				return email;
			else
				return "";
		}
		else if(email.length() == 0)
			return "  ";
		else
			return " ";
	}
}
