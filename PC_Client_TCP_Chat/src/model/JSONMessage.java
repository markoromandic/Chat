package model;

import org.json.JSONObject;

public class JSONMessage
{
	private boolean singin;
	private boolean registered;
	
	private JSONObject jsonMsg;
	
	@Override
	public String toString()
	{
		return jsonMsg.toString();
	}

	public boolean isSingin()
	{
		return singin;
	}

	public boolean isRegistered()
	{
		return registered;
	}
	
}
