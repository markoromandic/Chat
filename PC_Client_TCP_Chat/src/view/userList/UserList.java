package view.userList;

import javax.swing.JList;

import model.User;

public class UserList extends JList<User>
{
	UserListModel userListModel;
	
	public UserList()
	{
		initialize();
	}
	
	private void initialize()
	{
		userListModel = new UserListModel();
		setModel(userListModel);
		setCellRenderer(new UserListCellRender());
	}

	public UserListModel getUserListModel()
	{
		return userListModel;
	}

	public void setUserListModel(UserListModel userListModel)
	{
		this.userListModel = userListModel;
	}
}
