package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import appCore.Core;

public class OpenRegisterPanel extends KeyAdapter implements ActionListener
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
		Core.getInstance().getView().initializeRegisterUser();
	}

}
