package com.mycompany.a3;
import java.util.Random;

/*
 *Bryce Hairabedian
 * CSC 133 Sec 1
 * Assignment 1
 */
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Dog extends Animals implements ICollider, IDrawable, ISelectable{
    //*********Attributes
    private int scratches;
    private boolean selectedBool = false;
    private Random random = new Random();
    private GameWorld gw;
    private int drawCount;
    //***Constructors
    public Dog(GameWorld gw){
    	super(gw);
    	this.gw = gw;
    	super.setSpeed(5);
    	this.setSize(getRandIntBetween(20,50));
    	setColor(ColorHelper.getRed(1));
    	scratches=0;
    }
    public Dog(GameWorld gw, int inSize){
    	super(gw, inSize);
    	this.gw = gw;
    	super.setSpeed(5);
    	//this.setSize(getRandIntBetween(20,50));
    	setColor(ColorHelper.getRed(1));
    	scratches=0;
    }
    public Dog(){
    	super();
    	this.setSize(getRandIntBetween(20,50));
        setColor(ColorHelper.getRed(1));//start the dog off with lightest shade of red
        scratches=0;
        setSpeed(5);
        
    }

    //****Methods
    @Override
    public void handleCollision(ICollider collidedIn){
    	Animals tempCat = null;
    	if(collidedIn instanceof Cat){//Cat?
    		tempCat = (Cat)collidedIn;
    		//have they collided with me already?
    		
    		if(tempCat.containsCollided(this)|| /*I on their list */
    				this.containsCollided(tempCat)){/*OR they're on my list */
    			//do nothing
    		
    		}
    		
    		else{//we haven't collided already
    			//add each other to collided list so don't
    			//collide again until its "handled"
    			tempCat.addToCollidedList(this);
    			this.addToCollidedList(tempCat);
    			
    			this.scratchThisDog();
    			//TODO in DOG play dogsound
    			
    			//Remove eachother
    			tempCat.removeFromCollidedList(this);
    			this.removeFromCollidedList(tempCat);
    		}
    		
    		
    	}
    	else{//collided with non-cat    		
    	}
    }//handleCollision()
    
    @Override
    public boolean collidesWith(ICollider collidedIn) {
		boolean collidedBool = false;
		GameObject collidedObj = (GameObject)collidedIn;
		
		float thisRad = this.getSize()/2;
		float collidedRad = collidedObj.getSize()/2;
				
		float dx =(this.getXLocation() + thisRad)
    			-(collidedObj.getXLocation() + collidedRad); 
    	
		float dy =(this.getYLocation() + thisRad)
    			-(collidedObj.getYLocation() + collidedRad); 
    	int sqRad = (int)(thisRad * thisRad + 2 * thisRad * collidedRad + collidedRad* collidedRad);
    	float dist = (dx*dx+dy*dy);
    	if(dist <= sqRad){
    		collidedBool = true;
    		//handleCollision(collidedIn);
    	}
		return collidedBool;	
	}//collidesWith()
    
    
    public void draw(Graphics g, Point pCmpRelPrnt){
    	int locX = (int) (pCmpRelPrnt.getX() + this.getXLocation() - this.getSize()/2);
    	int locY = (int)(pCmpRelPrnt.getY()+this.getYLocation() - this.getSize()/2);
    	if(this.isSelected()){
    		g.drawArc(locX, locY, this.getSize(), this.getSize(), 0, 360);
    	}
    	else{
    		g.setColor(this.getColor());//super.getColor
    		//circle
    		g.fillArc(locX, locY, this.getSize(), this.getSize(), 0, 360);
    	}
    }

    public void healThisDog(){
    	this.setScratches(0);
    	this.setSpeed(5);
    	this.setColor(ColorHelper.getRed(1));//lightest red
    	this.setSelected(false);
    }
    public void setSelected(boolean s){
    	selectedBool = s;
    }
    
    public boolean isSelected(){
    	return selectedBool;
    }
    public boolean contains(Point pPtr, Point pCmp){
    	boolean locBool = false;
    	int pX = pPtr.getX();
    	int pY = pPtr.getY();
    	
    	int locX = (int)this.getXLocation();
    	int locY = (int)this.getYLocation();
    	if((pX>=locX-getSize()/2)&&(pX<=locX+this.getSize()/2) 
    			&& (pY<=locY+getSize()/2)&&(pY >= locY-getSize()/2)){
    		
    		locBool = true;
    		System.out.println("contains == true");
    	}
    	else{ 
    		locBool = false;
    		System.out.println("contains == false");	
    	}
    	return locBool;
    }

    
    public int getRandIntBetween(int min, int max){
        int r = random.nextInt(max - min) + min;
        return r;
    }
    public int getScratches(){return this.scratches;}
    public void setScratches(int scratch){this.scratches = scratch;}
    public void scratchThisDog(){
        if(scratches<5){
            this.scratches++;
            if(gw.isSoundOn()){
            	(new Sound("dogRuff.wav")).play();
            }
            
            
        }
        else{
            scratches=5;
        }
        decreaseDogSpeed();
        switch(scratches){ //make the dog more and more red with each scratch
            case 1: //Light red
                setColor(ColorHelper.getRed(1));
                break;
            case 2:
                setColor(ColorHelper.getRed(2));//darker shade of red
                break;
            case 3:
                setColor(ColorHelper.getRed(3));//darker shade of red
                break;
            case 4:
                setColor(ColorHelper.getRed(4));//even darker shade of red
                break;
            case 5:
                setColor(ColorHelper.getRed(5));//Most Red
                break;
            default:
                setColor(ColorHelper.getRed(5));
                break;
        }
    }
    @Override
    public String toString(){
         String locationString = Math.round(getXLocation()*100.0)/100.0 +", "
                    +Math.round(getYLocation()*100.0)/100.0;

        String colorString = "[" + ColorUtil.red(getColor()) + ","
                    + ColorUtil.green(getColor()) + ","
                    + ColorUtil.blue(getColor()) + "]";

        return ("DOG; Location: " + locationString
                    + " Color: " + colorString
                    + " Size: "+ getSize()
                    + " Speed: " + getSpeed()
                    + " Direction: " + getDirection()
                    + " Scratches: " + getScratches()
            );
    }

    /*decreaseDogSpeed: decreases this dogs speed by one*/
    private void decreaseDogSpeed(){
        int locSpeed =getSpeed();
        if(locSpeed>0){ //if the dogs speed is in range subtract one
            locSpeed--;
            setSpeed(locSpeed);
        }
        else if(locSpeed<=0){
            setSpeed(0);
        }
        
    }
	
}//END DOG************************
