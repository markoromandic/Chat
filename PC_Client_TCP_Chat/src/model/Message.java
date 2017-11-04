package model;

import java.sql.Timestamp;

public class Message implements Comparable<Message>
{
	private long id;
	private String from;
	private String to;
	private String message;
	private boolean read;
	private Timestamp timestamp;

	public Message(long id, String from, String to, String message, boolean read, Timestamp timestamp)
	{
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.message = message;
		this.read = read;
		this.timestamp = timestamp;
	}

	@Override
	public int compareTo(Message o)
	{
		return timestamp.getTime() > o.timestamp.getTime() ? 1 : -1;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Message)
		{
			Message other = (Message) obj;
			return other.id == id;
		}
		else
			return false;
	}

	public String getFrom()
	{
		return from;
	}

	public String getTo()
	{
		return to;
	}

	public String getMessage()
	{
		return message;
	}

	public boolean isRead()
	{
		return read;
	}

	public Timestamp getTimestamp()
	{
		return timestamp;
	}

	public long getId()
	{
		return id;
	}
}
