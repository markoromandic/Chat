package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import appCore.Core;

public class Connection
{
	private Socket sock;
	private PrintWriter sendMsg = null;
	private BufferedReader getMsg = null;
	
	public Connection()
	{
		initializeConnection();
	}
	
	private boolean initializeConnection()
	{
		String host = "petjahomeserver.hopto.org";
		int port = 54321;
		
		try
		{
			sock = new Socket();
			sock.connect(new InetSocketAddress(host, port), 2000);
			
			if(!sock.isConnected())
				return false;
			
			getMsg = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			sendMsg = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()), true);
			
			System.out.println("CLIENT HAS CONNECTED TO SERVER [" + sock.getInetAddress() + "]");
			
			startTimer();
			
			return true;
		}
		catch (IOException e)
		{
			System.err.println("CANNOT CONNECT TO SERVER");
		}
		
		return false;
	}
	
	public boolean sendSignInMsg(String username, String password)
	{
		try
		{
			JSONObject jsonMsg;

			jsonMsg = new JSONObject();
			
			jsonMsg.put("action", "login");
			jsonMsg.put("username", username);
			jsonMsg.put("password", password);
			
			
			sendMsg.println(jsonMsg.toString());
			
			String messageFromServer = getMsg.readLine();
			
			jsonMsg = new JSONObject(messageFromServer);
			
			jsonMsg = new JSONObject(messageFromServer);
			
			if(jsonMsg.getBoolean("success"))
			{
				ArrayList<User> onlineUsers;
				
				onlineUsers = new ArrayList<>();
				
				JSONArray jsonOnlineUsers = jsonMsg.getJSONArray("online_users");
				
				User publicUser = new User("public chat");
				onlineUsers.add(publicUser);
				
				for(int i = 0; i < jsonOnlineUsers.length(); i++)
				{
					JSONObject user = jsonOnlineUsers.getJSONObject(i); 
					User u = new User(user.getString("username"));
					onlineUsers.add(u);
				}
				
				Core.getInstance().getModel().setOnlineUsers(onlineUsers);
				
				new Thread(new JSONIncomingMessages()).start();
				
				return true;
			}
			else 
			{
				System.out.println(jsonMsg.getString("reason"));
				
				return false;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("DISCONECTED FROM SERVER");
		}
		
		return false;
	}
	
	public boolean sendRegisterMsg(String username, String password, String email)
	{
		try
		{
			JSONObject jsonMsg = new JSONObject();
			
			jsonMsg.put("action", "register");
			jsonMsg.put("username", username);
			jsonMsg.put("email", email);
			jsonMsg.put("password", password);

			sendMsg.println(jsonMsg.toString());
			String messageFromServer = getMsg.readLine();
		
			jsonMsg = new JSONObject(messageFromServer);
			
			if(jsonMsg.getBoolean("success"))
			{
				return true;
			}
			else
			{
				System.err.println(jsonMsg.getString("reason"));
				return false;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void sendPrivateMsg(String userTo, String message)
	{
		JSONObject jsonMsg = new JSONObject();
		
		jsonMsg.put("action", "private_message");		
		jsonMsg.put("to", userTo);
		jsonMsg.put("message", message);
		
		sendMsg.println(jsonMsg.toString());
	}
	
	public void sendPublicMsg(String message)
	{
		JSONObject jsonMsg = new JSONObject();
		
		jsonMsg.put("action", "public_message");
		
		jsonMsg.put("message", message);
		
		sendMsg.println(jsonMsg.toString());
	}
	
	public Socket getSock()
	{
		return sock;
	}

	public PrintWriter getSendMsg() {
		return sendMsg;
	}
	
	private void startTimer()
	{
		  Timer t = new Timer();
		  t.schedule(new TimerTask() 
		  {	
		      @Override public void run() 
		      {
		    	  JSONObject jsonMsg = new JSONObject();
		    	  jsonMsg.put("action", "live");
		    	  
		    	  sendMsg.println(jsonMsg.toString());
		      }
		  }, 0L, 5000L);
	}
	
}


