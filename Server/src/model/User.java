package model;

import java.net.Socket;

import main.ServerThread;

public class User {
	private String username;
	private ServerThread serverThread;
	public User(String username, ServerThread serverThread) {
		super();
		this.username = username;
		this.serverThread = serverThread;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public ServerThread getServerThread() {
		return serverThread;
	}
	public void setServerThread(ServerThread serverThread) {
		this.serverThread = serverThread;
	}
	
}
