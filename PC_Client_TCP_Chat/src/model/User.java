package model;

import java.util.ArrayList;

public class User
{
	private String username;
	private ArrayList<Message> messages;
	
	public User(String username)
	{
		this.username = username;
		messages = new ArrayList<>();
	}
	
	public String getUsername()
	{
		return username;
	}
	
	@Override
	public String toString()
	{
		return username;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
}
