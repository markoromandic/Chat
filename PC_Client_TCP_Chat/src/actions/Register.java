package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import appCore.Core;

public class Register extends KeyAdapter implements ActionListener
{
	@Override
	public void keyPressed(KeyEvent e)
	{
		int keyPressedNum = e.getKeyCode();
		String keyChar = KeyEvent.getKeyText(keyPressedNum);
		
		if(keyChar.equals("Enter"))
		{
			perform();
		}	
		
		super.keyPressed(e);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		perform();
	}
	
	private void perform()
	{
		String username = Core.getInstance().getView().getRegisterUserPanel().getUsername();
		
		String password = Core.getInstance().getView().getRegisterUserPanel().getPassword();
		
		String email = Core.getInstance().getView().getRegisterUserPanel().getEmail();
		
		if (!username.equals("") && !password.equals("") && !password.equals("$") && !email.equals(" ") && !email.equals("") && !email.equals("  "))
		{
			if(Core.getInstance().getModel().getConnection().sendRegisterMsg(username, password, email))
			{
				System.out.println(username + " is registered");
				Core.getInstance().getView().initializeSignIn();
			}
			else
			{
				System.err.println("User with "+ username +  " EXIST");
			}
		}
		else 
		{
			if(username.equals(""))
			{
				System.err.println("Username field is empty");
			}
			if(password.equals(""))
			{
				System.err.println("Password field and confirm password field are not same");
			}
			else if(password.equals("$"))
			{
				System.err.println("Password field is empty");
			}
			if(email.equals(" "))
			{
				System.err.println("Email field and confirm email field are not same");
			}
			else if(email.equals(""))
			{
				System.err.println("Email is not in correct format");
			}
			else if(email.equals("  "))
			{
				System.err.println("Email field is empty");
			}
		}
	}
}
