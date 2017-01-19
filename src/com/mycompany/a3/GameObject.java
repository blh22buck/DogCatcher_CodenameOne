package com.mycompany.a3;

import java.util.Random;

//*************************************GAME OBJECT */
public abstract class GameObject{
//*********************Attributes
    private int color;
    private int size, heightBound, widthBound;
    private float xLocation;
    private float yLocation;
    private GameWorld targetWorld;
    private Random random = new Random();
    private int mvAbsY, mvAbsX, scoreY;
    
//*********************Constructors
    public GameObject(GameWorld gw){
    	targetWorld = gw;
    	heightBound = gw.getMapHeight();
    	widthBound = gw.getMapWidth();
    	mvAbsY = gw.getAbsY();
    	mvAbsX = gw.getAbsX();
    	scoreY = gw.getScoreY();
    	
    	xLocation = (random.nextInt(targetWorld.getMapWidth() - (2 * this.getSize())) 
    			+ targetWorld.getAbsX() + this.getSize());
    	
    	yLocation =(random.nextInt(targetWorld.getMapHeight() - targetWorld.getAbsY() 
    			- gw.getScoreY()  - (2 * this.getSize())) + targetWorld.getAbsY() 
    			+ targetWorld.getScoreY() + this.getSize());
    
    }
    public GameObject(GameWorld gw, int inSize){
    	targetWorld = gw;
    	this.setSize(inSize);
    	heightBound = gw.getMapHeight();
    	widthBound = gw.getMapWidth();
    	mvAbsY = gw.getAbsY();
    	mvAbsX = gw.getAbsX();
    	scoreY = gw.getScoreY();
    
    	xLocation = (random.nextInt(targetWorld.getMapWidth() - (2 * this.getSize())) 
    			+ targetWorld.getAbsX() + this.getSize());
    	
    	yLocation =(random.nextInt(targetWorld.getMapHeight() - targetWorld.getAbsY() 
    			- gw.getScoreY()  - (2 * this.getSize())) + targetWorld.getAbsY() 
    			+ targetWorld.getScoreY() + this.getSize());
    
    }
	
	public GameObject(){ }
//*********************Methods
	
	private float getRandomXLocation(){
        random = new Random();
		return random.nextFloat()*heightBound;
    }
	private float getRandomYLocation(){
        random = new Random();
		return random.nextFloat()*widthBound;
    }
    public int getSize(){return size;}
    public void setTargetWorld(GameWorld gw){
		if(gw != null){
			targetWorld=gw;
			heightBound = gw.getMapHeight();
	    	widthBound = gw.getMapWidth();
	    	xLocation = getRandomXLocation();
	    	yLocation = getRandomYLocation();
		}
		else{
				System.out.println("Given null gw");
		}
	}
    
    //Sets the size but checks the objects range 
    public void setSize(int inSize){
        this.size = inSize;
    	
    }//setSize()
    public int getHeightBound() {
		return heightBound;
	}
	public int getWidthBound() {
		return widthBound;
	}

    public int getColor(){return this.color;}
    public void setColor(int setColor){this.color = setColor;}

    public float getXLocation(){
        return this.xLocation;
    }
    public float getYLocation(){
        return this.yLocation;
    }

    public void setXLocation(float x){
        if(x < widthBound && x > 0){
            this.xLocation=x;
        }
    }
    public void setYLocation(float y){
        if(y < heightBound && y > 0){
            this.yLocation = y;
        }
    }
    public void setLocation(float x, float y){
    	this.xLocation =x; this.yLocation=y;
    }
} //**********END GAME OBJECT