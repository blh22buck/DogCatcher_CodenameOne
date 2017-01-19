package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class SoundCommand extends Command implements ActionListener, ICommand{
	private GameWorld target;
	private static SoundCommand locInstance;
	
	private SoundCommand(){super("Sound");}

	
	public void actionPerformed(ActionEvent evt) {
		target.toggleSound();
		SideMenuBar.closeCurrentMenu();
	}
	
	public void setTarget(GameWorld gw){
		target = gw;
	}
	
	public static SoundCommand getInstance(){
		if(locInstance == null){
			locInstance = new SoundCommand();
		}
		return (SoundCommand)locInstance;
	}

}
