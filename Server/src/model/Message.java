package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Message {
	private long id;
	private String from;
	private String to;
	private String message;
	private boolean read;
	private Timestamp timestamp;

	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
	public String getMessage() {
		return message;
	}
	public boolean isRead() {
		return read;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public long getId() {
		return id;
	}
	public Message(long id, String from, String to, String message, boolean read, Timestamp timestamp) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.message = message;
		this.read = read;
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", from=" + from + ", to=" + to + ", message=" + message + ", read=" + read
				+ ", timestamp=" + timestamp + "]";
	}
	
}
