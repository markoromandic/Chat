package view.userList;

import javax.swing.DefaultListModel;

import appCore.Core;
import model.User;

public class UserListModel extends DefaultListModel<User>
{
	public UserListModel()
	{
		initialize();
	}
	
	private void initialize()
	{
		for (User user : Core.getInstance().getModel().getOnlineUsers())
		{
			addElement(user);
		}
	}
	
	public void refreshList()
	{
		removeAllElements();
		for (User user : Core.getInstance().getModel().getOnlineUsers())
		{
			addElement(user);
		}
	}
}
