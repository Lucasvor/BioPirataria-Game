package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Player extends Sprite implements InputProcessor
{

	private Vector2 velocity = new Vector2();
	float speed = 40*2 , gravity = 40*0.8f;
	public SpriteBatch batch;
	private int pontox=0 , pontoy=0;
	public boolean movendox1 = false; // x positivo
	public boolean movendox2 = false; // x negativo
	public boolean movendoy1 = false; // y positivo
	public boolean movendoy2 = false; // y negativo
	
	public Player(Sprite sprite) 
	{
		super(sprite);
		batch = new SpriteBatch();
	}
	
	public void update(float delta) 
	{
		if(velocity.x>speed) 
		{
			velocity.x =speed;
		}
		
		if(velocity.x<-speed) 
		{
			velocity.x =-speed;
		}
		
		if(velocity.y>speed) 
		{
			velocity.y =speed;
		}
		
		if(velocity.y<-speed) 
		{
			velocity.y =-speed;
		}
		
		this.setX(this.getX()+velocity.x*delta);
		this.setY(this.getY()+velocity.y*delta);
		
	}
	
	public void draw (SpriteBatch spritebatch)
	{
		update(Gdx.graphics.getDeltaTime());
		super.draw(batch);
	}
	
	

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
