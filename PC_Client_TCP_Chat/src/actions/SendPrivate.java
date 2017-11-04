package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import appCore.Core;

public class SendPrivate extends KeyAdapter implements ActionListener
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
		
		super.keyTyped(e);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		send();
	}
	
	private void send()
	{
		String userTo = Core.getInstance().getView().getSplitPanel().getChatPanel().getUserTo();
		String message = Core.getInstance().getView().getSplitPanel().getChatPanel().getNewMsg();
		
		if(userTo.length() != 0 && message.length() != 0)
		{
			Core.getInstance().getModel().getConnection().sendPrivateMsg(userTo, message);
			
			Core.getInstance().getView().getSplitPanel().getChatPanel().sendMsgView(Core.getInstance().getModel().getUserConnected().getUsername());
		}
		
	}

}
