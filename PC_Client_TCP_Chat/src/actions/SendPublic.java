package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import appCore.Core;

public class SendPublic extends KeyAdapter implements ActionListener
{
	@Override
	public void keyPressed(KeyEvent e)
	{
		int keyPressedNum = e.getKeyCode();
		String keyChar = KeyEvent.getKeyText(keyPressedNum);
		
		if(keyChar.equals("Enter"))
		{
			send();
		}
		
		super.keyPressed(e);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		send();
	}
	
	private void send()
	{
		String message = Core.getInstance().getView().getSplitPanel().getChatPanel().getNewMsg();
		
		Core.getInstance().getModel().getConnection().sendPublicMsg(message);
		
		Core.getInstance().getView().getSplitPanel().getChatPanel().sendMsgView(Core.getInstance().getModel().getUserConnected().getUsername());
	}
}
