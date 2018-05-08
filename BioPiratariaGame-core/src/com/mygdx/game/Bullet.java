package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Bullet {
	
	//definições de velocidade da bala //

	public Vector2 bulletLocation = new Vector2(0,0);
	public Vector2 bulletVelocity = new Vector2(0,0);

	public Bullet(Vector2 sentLocation, Vector2 sentVelocity) {
		bulletLocation = new Vector2(sentLocation.x,sentLocation.y);
		bulletVelocity = new Vector2(sentVelocity.x,sentVelocity.y);
	}
	
	public void Update() {
		bulletLocation.x += bulletVelocity.x;
		bulletLocation.y += bulletVelocity.y;
	}
}
