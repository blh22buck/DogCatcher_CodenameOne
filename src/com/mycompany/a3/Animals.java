package com.mycompany.a3;


import java.util.ArrayList;
import java.util.Random;

/*
 *Bryce Hairabedian
 * CSC 133 Sec 1
 * Assignment 3
 */
abstract class Animals extends GameObject implements IMoving, ICollider{

    private int speed;
    private int direction;
    private Random random;
    private GameWorld gw;
    private ArrayList<Animals> collidedList;
    private int upper, lower, left, right;
    //***********Constructors
    public Animals(GameWorld gw){
    	super(gw);
    	this.gw = gw;
    	//setSpeed taken care of in CAT or DOG objects
    	this.direction = getRandomDirection();
    	collidedList = new ArrayList<Animals>();
    }
    public Animals(GameWorld gw, int inSize){
    	super(gw, inSize);
    	this.gw = gw;
    	//setSpeed taken care of in CAT or DOG objects
    	this.direction = getRandomDirection();
    	collidedList = new ArrayList<Animals>();
    }
    public Animals(){
    	super();
    	collidedList = new ArrayList<Animals>();
    }
    		
    @Override
    public void setSize(int s){
    	if(s >= 20 || s<=50)
    		super.setSize(s);
    }
    

    //*********Methods
    public boolean containsCollided(Animals objIn){
    	if(collidedList.contains(objIn)){
    		return true;
    	}
    	else{return false;}
    }
    public void addToCollidedList(Animals objIn){
    	collidedList.add(objIn);
    }
    public void removeFromCollidedList(Animals objIn){
    	collidedList.remove(objIn);
    }
    
    ////Overriden in the Cat and Dog class
    public boolean collidesWith(ICollider collidedIn) {
    	return false;
    }
    ////Overriden in the Cat and Dog class
    public void handleCollision(ICollider collidedObj){}
    
    private int getRandomDirection(){
    	random = new Random();
    	return random.nextInt(360);
    }
    public int getSpeed(){return this.speed;}
    public void setSpeed(int inSpeed){this.speed = inSpeed;}
    
    public int getDirection(){return this.direction;}
    public void setDirection(int inDirection){this.direction = inDirection;}

    
    
    //******All Animals move the same way
    public void move(int elapsedTime){
    	float X_newLocation, Y_newLocation;
    	float delta_X = (float) ((float)Math.cos(90-getDirection())*getSpeed()*elapsedTime/50.0);//calculate the
        float delta_Y = (float) ((float)Math.sin(90-getDirection())*getSpeed()*elapsedTime/50.0);

        X_newLocation = (getXLocation()+delta_X);//get new xLocation by adding the change to the direction
        Y_newLocation = (getYLocation()+delta_Y);
        
        upper = gw.getmvAbsY() + gw.getScoreY();
        left = gw.getmvAbsX();
        right = gw.getmvAbsX() + gw.getgwWidth();
        lower = gw.getgwHeight() + gw.getBottomY(); //<-this one!! good
        
        if((	   X_newLocation < right -(this.getSize()/2) )
        		&&(X_newLocation > left + (this.getSize()/2))
        		&&(Y_newLocation < lower - (this.getSize()/2) )
        		&&(Y_newLocation > upper - this.getSize()/2)){
        	this.setLocation(X_newLocation, Y_newLocation);
        	
        }//end If
        else{
        	setDirection(getRandomDirection());
        }

    }//move()
    
}//END ANIMAL***********************