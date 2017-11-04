package view.main;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appCore.Core;
import model.User;
import net.miginfocom.swing.MigLayout;
import view.userList.UserList;

public class UserListPanel extends JPanel
{
	private UserList userList;
	private User selectedUser;
	
	public UserListPanel()
	{
		initialize();
	}
	
	private void initialize()
	{
		MigLayout migLayout = new MigLayout();
		
		setLayout(migLayout);
		
		userList = new UserList();
		userList.addMouseListener(Core.getInstance().getActionManager().getOpenChat());
		userList.addKeyListener(Core.getInstance().getActionManager().getOpenChat());
		JScrollPane scUserList = new JScrollPane(userList);
		scUserList.setBorder(BorderFactory.createTitledBorder("Online users"));
		
		selectedUser = Core.getInstance().getModel().getUserConnected();
		add(scUserList, "push, grow");
	}

	public UserList getUserList()
	{
		return userList;
	}
	
	public User getSelectedUser()
	{
		if(userList.getSelectedValue() != null)
			selectedUser = userList.getSelectedValue();
		return selectedUser;
	}
}
