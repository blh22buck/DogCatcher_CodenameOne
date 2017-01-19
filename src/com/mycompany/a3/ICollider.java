package com.mycompany.a3;

public interface ICollider {
	public boolean collidesWith(ICollider collidedwith);
	public void handleCollision(ICollider collidedwith);
}
