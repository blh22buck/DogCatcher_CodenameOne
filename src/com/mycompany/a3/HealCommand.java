package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class HealCommand extends Command implements ActionListener, ICommand {
	private GameWorld target;
	private static HealCommand locInstance;

	private HealCommand() {
		super("Heal");
	}

	public void actionPerformed(ActionEvent evt) {
		target.heal();
	}

	public void setTarget(GameWorld gw) {
		target = gw;
	}

	public static HealCommand getInstance() {
		if (locInstance == null) {
			locInstance = new HealCommand();
		}
		return (HealCommand) locInstance;
	}

}