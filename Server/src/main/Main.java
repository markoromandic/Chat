package main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import javax.jws.soap.SOAPBinding.Use;

import model.Message;
import model.User;

public class Main {
	private static HashMap<String,User> online_users;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Database database = new Database();
		online_users = new HashMap<>();
		long unixTime = System.currentTimeMillis() / 1000L;
		for(Message message:database.getMessages("petja", "petja"))System.out.println(message.toString() + " " + message.getTimestamp().getTime());
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(54321);
			while (true) {
				Socket sok = serverSocket.accept();
				sok.setKeepAlive(true);
				sok.setSoTimeout(7000);
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						new ServerThread(sok, database);
					}
				}).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static HashMap<String, User> getOnlineUsers(){
		return online_users;
	}
}
