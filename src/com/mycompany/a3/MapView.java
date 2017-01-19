package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer {
	
	private GameWorld targetWorld;
	private Point mvOrigin;
	private boolean pausedBool;
	
	public MapView(){
		//setLayout(new GridLayout(1, 1));
		this.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		mvOrigin = new Point(this.getX(), this.getY());
		pausedBool = false;
		this.setVisible(true);
	}
	public void update(Observable o, Object arg){
		//code here to call the method in GameWorld (Observable)
		//that outputs the game object information to the console
		
		//targetWorld.printMap();
		if(o instanceof GameWorld){
			targetWorld = (GameWorld)o;//update targetWorld
			
		}
		repaint();
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		IIterator locI = targetWorld.getGameObjIterator();
		//draw all objects that are drawable
		while(locI.hasNext()){
			GameObject go = locI.getNext();
			if(go instanceof IDrawable){
				((IDrawable)go).draw(g, mvOrigin);
			}			
		}
	}
	
	@Override 
	public void pointerPressed(int inX, int inY){
		System.out.println("Pointer pressed");
		if(pausedBool){
			
		}
		int x = inX - getParent().getAbsoluteX();
		int y = inY - getParent().getAbsoluteY();
		Point pPtr = new Point(x,y);
		Point pCmp = new Point(getX(), getY());
		IIterator locI = targetWorld.getGameObjIterator();
		
		if(pausedBool){
			while(locI.hasNext()){
				GameObject go =(GameObject)locI.getNext();
				if(go instanceof ISelectable){
					if(((ISelectable) go).contains(pPtr, pCmp)){
						((ISelectable)go).setSelected(true);
					}
					else{
						((ISelectable)go).setSelected(false);
					}
					repaint();
				}
			}
		}
	}
	
	public void setPaused(boolean p){
		pausedBool = p;
	}
	public void unpause(){
		IIterator locI = targetWorld.getGameObjIterator();
		while(locI.hasNext()){
			GameObject go =(GameObject)locI.getNext();
			if(go instanceof ISelectable){//unselect it
				((ISelectable)go).setSelected(false);
				repaint();
			}
		}
	}
	public void setTargetWorld(GameWorld gw){
		if(gw != null){
			targetWorld=gw;
		}
		else{
				System.out.println("Given null gw");
		}
	}
	
	
}
