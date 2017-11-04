package view.menubar;

import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar
{
	private MenuFile menuFile;
	
	public MenuBar()
	{
		initialize();
	}
	
	private void initialize()
	{
		menuFile = new MenuFile();
		add(menuFile);
	}
}
