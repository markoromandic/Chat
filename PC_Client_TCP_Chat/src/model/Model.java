package model;

import java.util.ArrayList;

public class Model
{
	private Connection connection;
	
	private User userConnected;
	private ArrayList<User> onlineUsers;
	
	public Model()
	{
		connection = new Connection();
	}
	
	public ArrayList<User> getOnlineUsers()
	{
		return onlineUsers;
	}

	public void setOnlineUsers(ArrayList<User> onlineUsers)
	{
		this.onlineUsers = onlineUsers;
	}

	public Connection getConnection()
	{
		return connection;
	}
	
	public User getUserConnected()
	{
		return userConnected;
	}
	
	public void setUserConnected(String username)
	{
		this.userConnected = new User(username);
	}
}