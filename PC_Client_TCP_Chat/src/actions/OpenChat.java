package actions;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;

import appCore.Core;
import model.User;

public class OpenChat extends KeyAdapter implements MouseListener
{
	@Override
	public void keyPressed(KeyEvent e)
	{
		int keyPressedNum = e.getKeyCode();
		String keyChar = KeyEvent.getKeyText(keyPressedNum);
		
		if(keyChar.equals("Enter"))
		{
			User user = Core.getInstance().getView().getSplitPanel().getUserListPanel().getSelectedUser();
			
			if(user != null && user.getUsername().equals("public chat"))
			{
				String username = user.getUsername();
				
				Core.getInstance().getView().getSplitPanel().getChatPanel().initializeChat(username, false);
			}
			else if(user != null)
			{
				String username = user.getUsername();
				
				Core.getInstance().getView().getSplitPanel().getChatPanel().initializeChat(username, true);
			}
			
			Core.getInstance().getView().getSplitPanel().getChatPanel().setCaretToBottom();
			
		}
		super.keyPressed(e);
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getClickCount() == 2) 
		{
			JList theList = (JList) e.getSource();
			
			int index = theList.locationToIndex(e.getPoint());
			
			User user = (User)theList.getModel().getElementAt(index);
			
			if(user != null && user.getUsername().equals("public chat"))
			{
				String username = user.getUsername();
				
				Core.getInstance().getView().getSplitPanel().getChatPanel().initializeChat(username, false);
			}
			else if(user != null)
			{
				String username = user.getUsername();
				
				Core.getInstance().getView().getSplitPanel().getChatPanel().initializeChat(username, true);
			}
			
			Core.getInstance().getView().getSplitPanel().getChatPanel().setCaretToBottom();
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
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
