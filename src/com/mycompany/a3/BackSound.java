package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BackSound implements Runnable {
	private Media m;
	
	public BackSound(String fileName){
		try{
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
			m = MediaManager.createMedia(is, "audio/wav", this);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	public void pause(){ m.pause(); }
	public void play(){ m.play(); } 
	public void run() {
		m.setTime(0);
		m.play();
	}
}
