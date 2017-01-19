package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class PauseCommand extends Command implements ActionListener{
	private Game target;
	private static PauseCommand locInstance;
	
	public PauseCommand(){
		super("Pause");
	}
	
	public void actionPerformed(ActionEvent evt) {
		if(target.isPaused()){
			target.resumeTheGame();
		}
		else{
			target.pauseTheGame();
		}
	}

	public void setTarget(Game inGame) {
		target = inGame;
	}

	public static PauseCommand getInstance() {
		if (locInstance == null) {
			locInstance = new PauseCommand();
		}
		return (PauseCommand) locInstance;
	}


}
