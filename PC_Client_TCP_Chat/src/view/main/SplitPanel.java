package view.main;

import javax.swing.JSplitPane;

public class SplitPanel extends JSplitPane
{
	private ChatPanel chatPanel;
	private UserListPanel userListPanel;
		
	public SplitPanel()
	{
		initialize();
	}
	
	private void initialize()
	{
		chatPanel = new ChatPanel();
		userListPanel = new UserListPanel();
		
		setLeftComponent(userListPanel);
		setRightComponent(chatPanel);
		setDividerLocation(200);
		setOrientation(HORIZONTAL_SPLIT);
	}

	public ChatPanel getChatPanel()
	{
		return chatPanel;
	}

	public UserListPanel getUserListPanel()
	{
		return userListPanel;
	}
}
