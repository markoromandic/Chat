package appCore;

import actions.ActionManager;
import model.Model;
import view.View;

public class Core
{
	private static Core instance;
	private Model model;
	private ActionManager actionManager;
	private View view;

	public static Core getInstance()
	{
		if(instance == null)
		{
			instance = new Core();
			instance.initialize();
		}
			
		return instance;
	}

	private void initialize()
	{
		model = new Model();
		actionManager = new ActionManager();
		view = new View();
	}

	public Model getModel()
	{
		return model;
	}

	public ActionManager getActionManager()
	{
		return actionManager;
	}

	public View getView()
	{
		return view;
	}
}
