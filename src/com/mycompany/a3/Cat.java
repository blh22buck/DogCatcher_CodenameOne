package com.mycompany.a3;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/*
 *Bryce Hairabedian
 * CSC 133 Sec 1
 * Assignment 1
 */
import com.codename1.charts.util.ColorUtil;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;


public class Cat extends Animals implements IDrawable, ICollider {
	private boolean kittenBool = false;
	
	private GameWorld gw;
	private int numberOfDraws=0;
	
	static Random random = new Random();
    //***Constructors
    public Cat(GameWorld gw){
    	super(gw);
    	this.gw =gw;
    	super.setSpeed(5);
    	this.setSize(getRandIntBetween(20,50));
    	setColor(ColorUtil.YELLOW);
    }
    public Cat(GameWorld gw, int inSize){
    	super(gw, inSize);
    	this.gw =gw;
    	super.setSpeed(5);
    	this.setSize(getRandIntBetween(20,50));
    	setColor(ColorUtil.YELLOW);
    }
	public Cat(){
    	setSpeed(5);
    	setColor(ColorUtil.YELLOW);
    	this.setSize(getRandIntBetween(20,50));
    }
	public Cat(Cat inCat, GameWorld gw){//kitten
		super(gw, getRandIntBetween(20,50));
		this.gw = gw;
		super.setSpeed(5);
		//this.setSize(getRandIntBetween(20,50));
		setColor(ColorUtil.GREEN);
		kittenBool = true;
		float catX = inCat.getXLocation() + getRandIntBetween(1, 10);
		float catY = inCat.getYLocation() + getRandIntBetween(1, 10);
		
		super.setLocation(catX, catY);
	}
    public Cat(Cat inCat){//new kitten
    	super.setSpeed(5);
    	kittenBool = true;
    	setSize(getRandIntBetween(20,50));
    	setColor(ColorUtil.GREEN);
    	
    	float catX = inCat.getXLocation() + getRandIntBetween(1, 10);
        float catY = inCat.getYLocation();

        catY += getRandIntBetween(1,10);//more randomness of kitten spawned location
        catX += getRandIntBetween(1,10);
    	this.setXLocation(catX);
    	this.setYLocation(catY);
    	
    	//TODO do something to timeOut kitten to Cat
    }
    //****Methods
    public boolean isAKitten(){
    	return kittenBool;
    }
    
    @Override
    public void handleCollision(ICollider collidedIn){
    	Animals tempAIn = null;
    	if((collidedIn instanceof Animals) == false){//Animal?
    		
    		}
    		else{//we haven't collided already
    			//add each other to collided list so don't
    			//collide again until its "handled"
    			tempAIn = (Animals)collidedIn;	
    			if(tempAIn instanceof Dog){
    				((Dog) tempAIn).scratchThisDog();
    			}
    			else if(tempAIn instanceof Cat){
    				//if we're both not a kitten
    				if ((this.isAKitten() != true) &&
    						(((Cat) tempAIn).isAKitten() !=true)){
    					this.gw.createKitten(this, gw);
    				}
    			}
    			else{}//some other animal
    		}	
    	}//handleCollision()
    
    @Override
    public boolean collidesWith(ICollider collidedIn) {
		boolean collidedBool = false;
		if(this.isAKitten()||((collidedIn instanceof Cat)&&((Cat)collidedIn).isAKitten())){
			return collidedBool;
		}
		GameObject collidedObj = (GameObject)collidedIn;
		
		double thisRad = this.getSize()/2;
		double collidedRad = collidedObj.getSize()/2;
				
    	double dx =(this.getXLocation() + thisRad)
    			-(collidedObj.getXLocation() + collidedRad); 
    	
    	double dy =(this.getYLocation() + thisRad)
    			-(collidedObj.getYLocation() + collidedRad); 
    	int sqRad = (int)(thisRad * thisRad + 2 * thisRad * collidedRad + collidedRad* collidedRad);
    	double dist = (dx*dx+dy*dy);
    	if(dist <= sqRad){
    		if(this.containsCollided((Animals)collidedIn) 
    				&& ((Animals)collidedIn).containsCollided((Animals)this)){
    			return false;
    		}else{
    			this.addToCollidedList((Animals)collidedIn);
    			((Animals)collidedIn).addToCollidedList((Animals)this);
    			return true;
    		}
    	}
    	else{
    		this.removeFromCollidedList((Animals)collidedIn);
    		((Animals)collidedIn).removeFromCollidedList((Animals)this);
    		return false;
    	}
	}//collidesWith()
        
    public static int getRandIntBetween(int min, int max){
        int r = random.nextInt(max - min) + min;
        return r;
    }
    
    @Override
    public String toString(){
        String locationString = Math.round(getXLocation()*100.0)/100.0 +", "
                +Math.round(getYLocation()*100.0)/100.0;
        String colorString = "[" + ColorUtil.red(getColor()) + ","
                                        + ColorUtil.green(getColor()) + ","
                                        + ColorUtil.blue(getColor()) + "]";
        return ("CAT; Location: " + locationString
                        + " Color: " + colorString
                        + " Size: "+ getSize()
                        + " Speed: " + getSpeed()
                        + " Direction: " + getDirection());
    }
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		Point top = new Point(pCmpRelPrnt.getX() + (int)this.getXLocation(), 
				pCmpRelPrnt.getY() + (int)this.getYLocation() + (getSize() / 2));
		
		Point left = new Point(pCmpRelPrnt.getX() + (int)this.getXLocation() - (getSize() / 2), 
				pCmpRelPrnt.getY() + (int)this.getYLocation() - (getSize() / 2));
		
		Point right = new Point(pCmpRelPrnt.getX() + (int)this.getXLocation() + (getSize() / 2), 
				pCmpRelPrnt.getY() + (int)this.getYLocation() - (getSize() / 2));
		
		int [] xPoints = new int [] {top.getX(), left.getX(), right.getX()};
		int [] yPoints = new int [] {top.getY(), left.getY(), right.getY()};
		g.fillPolygon(xPoints, yPoints, 3);//Triangle
		numberOfDraws++;
		if(kittenBool == true && numberOfDraws>400){
			kittenBool = false;
			this.setColor(ColorUtil.YELLOW);
		}
	}
	

}//END CAT