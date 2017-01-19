package com.mycompany.a3;
/*
 *Bryce Hairabedian
 * CSC 133 Sec 1
 * Assignment 1
 */
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Net extends Catchers implements IDrawable, ICollider{
    public Net(){
        setColor(ColorUtil.BLUE);
    }
    public Net(GameWorld gw){
    	super(gw);
    	setColor(ColorUtil.BLUE);
    	this.setSize(100);//initial net size
    }
    public Net(GameWorld gw,int inSize){
    	super(gw, inSize);
    	setColor(ColorUtil.BLUE);
    	this.setSize(100);//initial net size
    }
    @Override
    public String toString(){
        String locationString = Math.round(getXLocation()*100.0)/100.0 +", "
                +Math.round(getYLocation()*100.0)/100.0;
        String colorString = "[" + ColorUtil.red(getColor()) + ","
                + ColorUtil.green(getColor()) + ","
                + ColorUtil.blue(getColor()) + "]";

        return ("NET; Location: " +locationString
                + " Color: " + colorString
                + " Size: "+ getSize());
    }
	public void draw(Graphics g, Point pCmp) {
		// TODO Auto-generated method stub
		int c = this.getSize()/2;
		g.setColor(this.getColor());
		
		g.drawRect(pCmp.getX() +(int)this.getXLocation() -c, 
				pCmp.getY() +(int)this.getYLocation() -c, 
				this.getSize(), this.getSize());
	}

    public boolean collidesWith(ICollider collidedIn) {
		boolean collidedBool = false;
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
    		collidedBool = true;
    		//handleCollision(collidedIn);
    	}
		return collidedBool;	
	}//collidesWith()
	
    public void handleCollision(ICollider collidedwith) {
	}

}
