package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.fabric.xmlrpc.base.Data;

import model.Message;
import model.User;

public class ServerThread {
	Database database;
	Socket socket;
	PrintWriter printWriter;
	User user;

	public ServerThread(Socket socket, Database database) {
		this.socket = socket;
		this.database = database;
		BufferedReader bufferedReader = null;
		try {
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}
		// printWriter.println("welcome to my server");
		
		while (true) {
			String string = null;
			try {
				string = bufferedReader.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (string == null) {
				try {
					socket.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				if (user != null) {
					Main.getOnlineUsers().remove(user.getUsername());
					System.out.println("user " + user.getUsername() + " left");
					for (User user : Main.getOnlineUsers().values()) {
						// Update online_user list for every online client
						if (user != this.user)
							user.getServerThread().updateOnlineUsers();
					}
				}
				return;
			}
			System.out.println(new Date().toString() + ": " + string);
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(string);
			} catch (JSONException e) {
				// TODO: handle exception
				try {
					socket.connect(socket.getRemoteSocketAddress());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JSONObject jsonReply = new JSONObject();
				jsonReply.put("action", "json_error");
				jsonReply.put("error", "Unable to convert to json");
				printWriter.println(jsonReply.toString());
				continue;
			}
			if (jsonObject.getString("action").equals("register"))
				register(jsonObject.getString("username"), jsonObject.getString("password"));
			else if (jsonObject.getString("action").equals("login"))
				login(jsonObject.getString("username"), jsonObject.getString("password"));
			else if (jsonObject.getString("action").equals("private_message"))
				privateMessage(jsonObject.getString("to"), jsonObject.getString("message"));
			else if (jsonObject.getString("action").equals("public_message"))
				publicMessage(jsonObject.getString("message"));
			else if (jsonObject.getString("action").equals("get_messages"))
				getMessages(jsonObject.getString("username"));

		}
	}

	private void register(String username, String password) {
		JSONObject jsonObject;
		if (database.addUser(username, password)) {
			// add this user to online users
			user = new User(username, this);
			Main.getOnlineUsers().put(username, user);

			jsonObject = new JSONObject();
			jsonObject.put("success", true);
			JSONArray jsonUsers = new JSONArray();
			for (User user : Main.getOnlineUsers().values()) {
				// Update online_user list for every online client
				if (user != this.user)
					user.getServerThread().updateOnlineUsers();
				// Add to json every online client to send to current client
				JSONObject jsonUser = new JSONObject();
				jsonUser.put("username", user.getUsername());
				jsonUsers.put(jsonUser);
			}
			// jsonObject.put("online_users", jsonUsers);
		} else {
			jsonObject = new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("reason", "Username already exists");
		}
		printWriter.println(jsonObject.toString());
	}

	private void login(String username, String password) {
		JSONObject jsonObject;
		if (database.userExists(username, password)) {
			// add this user to online users
			user = new User(username, this);
			Main.getOnlineUsers().put(username, user);

			jsonObject = new JSONObject();
			jsonObject.put("success", true);
			JSONArray jsonUsers = new JSONArray();
			for (User user : Main.getOnlineUsers().values()) {
				// Update online_user list for every online client
				if (user != this.user)
					user.getServerThread().updateOnlineUsers();
				// Add to json every online client to send to current client
				JSONObject jsonUser = new JSONObject();
				jsonUser.put("username", user.getUsername());
				jsonUsers.put(jsonUser);
			}
			jsonObject.put("online_users", jsonUsers);
		} else {
			jsonObject = new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("reason", "Wrong username or password");
		}
		printWriter.println(jsonObject.toString());
	}

	private void updateOnlineUsers() {
		JSONObject jsonObject;
		jsonObject = new JSONObject();
		jsonObject.put("action", "update_online_users");
		JSONArray jsonUsers = new JSONArray();
		for (User user : Main.getOnlineUsers().values()) {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("username", user.getUsername());
			jsonUsers.put(jsonUser);
		}
		jsonObject.put("online_users", jsonUsers);
		printWriter.println(jsonObject.toString());
	}

	private void privateMessage(String to_username, String message) {
		if (Main.getOnlineUsers().containsKey(to_username)) {
			User other = Main.getOnlineUsers().get(to_username);
			other.getServerThread().sendMessage(message, user.getUsername());
		}
	}

	private void getMessages(String username) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "private_messages");
		jsonObject.put("username", username);
		JSONArray messages = new JSONArray();
		ArrayList<Message> messagesArrayList = database.getMessages(this.user.getUsername(), username);
		for(int i = 0;i < messagesArrayList.size();i++){
			JSONObject messageJSON = new JSONObject();
			messageJSON.put("id", messagesArrayList.get(i).getId());
			messageJSON.put("from", messagesArrayList.get(i).getFrom());
			messageJSON.put("to", messagesArrayList.get(i).getFrom());
			messageJSON.put("message", messagesArrayList.get(i).getMessage());
			messageJSON.put("read", messagesArrayList.get(i).isRead());
			messageJSON.put("datetime", messagesArrayList.get(i).getTimestamp().getTime());
			messages.put(messageJSON);
		}
		jsonObject.put("messages", messages);
		printWriter.println(jsonObject.toString());
	}
	
	private void sendMessage(String message, String from) {
		// Send message to this client
		Message mesageObj = database.addMessage(from, user.getUsername(), message);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "private_message");
		jsonObject.put("id", mesageObj.getId());
		jsonObject.put("from", mesageObj.getFrom());
		jsonObject.put("to", mesageObj.getFrom());
		jsonObject.put("message", mesageObj.getMessage());
		jsonObject.put("read", mesageObj.isRead());
		jsonObject.put("datetime", mesageObj.getTimestamp().getTime());
		printWriter.println(jsonObject.toString());
	}

	private void sendPublicMessage(String message, String from) {
		// Send message to this client
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "public_message");
		jsonObject.put("from", from);
		jsonObject.put("message", message);
		printWriter.println(jsonObject.toString());
	}

	private void publicMessage(String message) {
		for (User user : Main.getOnlineUsers().values()) {
			if (!user.equals(this.user)) {
				user.getServerThread().sendPublicMessage(message, this.user.getUsername());
			}
		}
	}
}
