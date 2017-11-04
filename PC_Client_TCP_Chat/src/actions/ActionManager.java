package actions;

public class ActionManager
{
	private SendPrivate sendPrivate;
	private SignIn signIn;
	private ForgotUsernamePassword forgotUsernamePassword;
	private OpenRegisterPanel openRegisterPanel;
	private Register register;
	private OpenChat openChat;
	private SendPublic sendPublic;
	
	public ActionManager()
	{
		sendPrivate = new SendPrivate();
		signIn = new SignIn();
		forgotUsernamePassword = new ForgotUsernamePassword();
		openRegisterPanel = new OpenRegisterPanel();
		register = new Register();
		openChat = new OpenChat();
		sendPublic = new SendPublic();
	}

	public SendPrivate getSendPrivate()
	{
		return sendPrivate;
	}

	public SignIn getSignIn()
	{
		return signIn;
	}

	public ForgotUsernamePassword getForgotUsernamePassword()
	{
		return forgotUsernamePassword;
	}

	public OpenRegisterPanel getOpenRegisterPanel()
	{
		return openRegisterPanel;
	}

	public Register getRegister()
	{
		return register;
	}

	public OpenChat getOpenChat()
	{
		return openChat;
	}

	public SendPublic getSendPublic()
	{
		return sendPublic;
	}
}
