package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import appCore.Core;

public class SignIn extends KeyAdapter implements ActionListener
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
		String username = Core.getInstance().getView().getSignInPanel().getUsername();
		String password = Core.getInstance().getView().getSignInPanel().getPassword();
		
		if(username.length() != 0 && password.length() != 0)
		{
			if(Core.getInstance().getModel().getConnection().sendSignInMsg(username, password))
			{
				Core.getInstance().getModel().setUserConnected(username);
				Core.getInstance().getView().initializeSplitPanel();
			}
		}
		else 
		{
			System.out.println("EMPTY");
		}
	}
	
}
