package actions;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import appCore.Core;

public class ForgotUsernamePassword implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e)
	{
		System.out.println("NOT YET");
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		Core.getInstance().getView().getSignInPanel().changeLbForgotPassword();	
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		Core.getInstance().getView().getSignInPanel().changeLbForgotPasswordBack();		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}
}
