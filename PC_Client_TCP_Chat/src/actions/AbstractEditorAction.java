package actions;

import java.awt.Image;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public abstract class AbstractEditorAction extends AbstractAction
{
	public Icon loadIcon(String imageURL)
	{
		Icon icon = null;
		
		if (!imageURL.equals("") && (new File(imageURL)).exists()) 
		{       
			ImageIcon iconNotResized = new ImageIcon(imageURL);
			
			Image imgResizing =	iconNotResized.getImage();
			imgResizing = imgResizing.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			icon = new ImageIcon(imgResizing);
	    } 
		else 
	    {                                     
	        System.err.println("RESOURCE_NOT_FOUND_SYSTEM" + imageURL);
	    }

		return icon;
	}
}
