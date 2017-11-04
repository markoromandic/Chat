package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import appCore.Core;

public class JSONIncomingMessages implements Runnable
{
	@Override
	public void run()
	{
		BufferedReader getMsg;

		try
		{
			getMsg = new BufferedReader(new InputStreamReader(Core.getInstance().getModel().getConnection().getSock().getInputStream()));

			while (true)
			{
				String jsonMsg = getMsg.readLine();

				JSONObject jsonMessage = new JSONObject(jsonMsg);

				String action = jsonMessage.getString("action");

				if (action.equals("private_message"))
				{
					String username = jsonMessage.getString("from");
					String message = jsonMessage.getString("message");
					
					User user = null;
					for (User u : Core.getInstance().getModel().getOnlineUsers())
					{
						if (u.getUsername().equals(username))
						{
							user = u;
							break;
						}
					}
					
					if(user != null)
					{
						Message newMessage = new Message(jsonMessage.getLong("id"), username, jsonMessage.getString("to"), message, jsonMessage.getBoolean("read"), new Timestamp(jsonMessage.getLong("datetime")));
						
						user.getMessages().add(newMessage);
						
						if (Core.getInstance().getView().getSplitPanel().getUserListPanel().getSelectedUser().getUsername().equals(user.getUsername()))
						{
							Core.getInstance().getView().getSplitPanel().getChatPanel().printMsg(newMessage);
						}
					}
				}
				else if (action.equals("public_message"))
				{
					String username = jsonMessage.getString("from");
					String message = jsonMessage.getString("message");

					Core.getInstance().getView().getSplitPanel().getChatPanel().printPublicMsg(username, message);
				}
				else if (action.equals("update_online_users"))
				{
					JSONArray jsonArray = jsonMessage.getJSONArray("online_users");

					ArrayList<User> userList = new ArrayList<>();

					userList.add(new User("public chat"));

					for (int i = 0; i < jsonArray.length(); i++)
					{
						JSONObject userJson = jsonArray.getJSONObject(i);
						userList.add(new User(userJson.getString("username")));
					}

					Core.getInstance().getModel().setOnlineUsers(userList);

					Core.getInstance().getView().getSplitPanel().getUserListPanel().getUserList().getUserListModel().refreshList();
				}
				else if (action.equals("private_messages"))
				{
					// User we got the messages for
					User user = null;
					for (User u : Core.getInstance().getModel().getOnlineUsers())
					{
						if (u.getUsername().equals(jsonMessage.get("username")))
						{
							user = u;
						}
					}
					if (user != null)
					{
						JSONArray jsonArray = jsonMessage.getJSONArray("messages");
						ArrayList<Message> newMessages = new ArrayList<>();
						for (int i = 0; i < jsonArray.length(); i++)
						{
							// TODO Razbi ovo u vise linija mrzelo me samo da
							JSONObject messageJson = jsonArray.getJSONObject(i);
							Message message = new Message(messageJson.getLong("id"), messageJson.getString("from"), messageJson.getString("to"), messageJson.getString("message"), messageJson.getBoolean("read"), new Timestamp(messageJson.getLong("datetime")));
							if (!user.getMessages().contains(message))
							{
								user.getMessages().add(message);
								newMessages.add(message);
							}
						}
						// If user is selected add new messages to view
						if (Core.getInstance().getView().getSplitPanel().getUserListPanel().getSelectedUser().getUsername().equals(user.getUsername()))
						{
							Collections.sort(newMessages);
							for (int i = 0; i < newMessages.size(); i++)
							{
								Core.getInstance().getView().getSplitPanel().getChatPanel().printMsg(newMessages.get(i));
							}
						}
					}
				}
			}
		}
		catch (InterruptedIOException iioe)
		{
			System.err.println("Remote host timed out during read operation");
		}
		catch (IOException e)
		{
			System.out.println("DISCONECTED FROM SERVER");
		}
	}
}
