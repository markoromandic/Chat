package main;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.Message;

public class Database {
	Connection connection;

	public Database() {
		try {
			// System.out.println("insert into users(\"username\",\"password\")
			// values(\"petja\",\"petja\")");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?useSSL=false", "root", "admin");

			System.out.println(userExists("petja", "petja"));

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from users");

			while (resultSet.next()) {
				System.out.println(resultSet.getString("username") + " " + resultSet.getString("password"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public synchronized boolean addUser(String username, String password) {
		try {
			Statement statement = connection.createStatement();
			statement.execute("insert into users(username,password) values(\"" + username + "\",\"" + password + "\")");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public synchronized boolean userExists(String username, String password) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from users where username = '" + username + "' and password = '" + password + "'");
			return resultSet.next();
		} catch (Exception e) {
			
			// TODO: handle exception
			return false;
		}
	}

	public synchronized Message addMessage(String from, String to, String message) {
		try {
			PreparedStatement statement = connection
					.prepareStatement("insert into messages(`from`,`to`,`datetime`,body,`read`) values(?,?,now(),?,?)");
			statement.setString(1, from);
			statement.setString(2, to);
			// statement.setTimestamp(3, new
			// Timestamp(System.currentTimeMillis()));
			statement.setString(3, message);
			statement.setBoolean(4, false);
			statement.executeUpdate();
			statement.close();
			System.out.println(message);
			Statement statement_get = connection.createStatement();
			ResultSet resultSet = statement_get.executeQuery("select * from messages where (`from`='" + from + "' and `to`='"
					+ to + "')  ORDER BY id DESC limit 1");
			Message messageObj = null;
			while (resultSet.next()) {
				messageObj = new Message(resultSet.getLong("id"), resultSet.getString("from"),
						resultSet.getString("to"), resultSet.getString("body"), resultSet.getBoolean("read"),
						resultSet.getTimestamp("datetime"));
			}
			System.out.println(messageObj.getMessage());
			statement_get.close();
			return messageObj;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public synchronized ArrayList<Message> getMessages(String from, String to) {
		ArrayList<Message> messages = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from messages where (`from`='" + from + "' and `to`='"
					+ to + "') OR (`from`='" + to + "' and `to`='"
					+ from + "') ORDER BY id DESC limit 100");
			while (resultSet.next()) {
				messages.add(new Message(resultSet.getLong("id"), resultSet.getString("from"),
						resultSet.getString("to"), resultSet.getString("body"), resultSet.getBoolean("read"),
						resultSet.getTimestamp("datetime")));
			}
			return messages;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}
}
