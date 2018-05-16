package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Laser extends Sprite {
	
	//podia ter feito outro objeto mas preferi copiar a classe // 
	
	PlayScreen screen;
    World world;
    //Array<TextureRegion> frames;
    //Animation fireAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy=false;
    boolean fireRight;
    Body b2body;
    private TextureRegion laserStand;
    
    public Laser(PlayScreen screen, float x, float y, boolean fireRight) {
    	this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        laserStand = new TextureRegion(new Texture("laser.png"),0,0,11,39);
		setBounds(x, y, 11, 36);
		setRegion(laserStand);
        defineFireBall();
    }

	private void defineFireBall() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(),getY()+20);
		bdef.type = BodyType.DynamicBody;
		
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(getWidth(), getHeight());
		fdef.shape = shape;
		if(fireRight) {
		fdef.filter.categoryBits = BioPirataria.LASER_BIT;
		fdef.filter.maskBits = BioPirataria.TERRAIN_BIT | BioPirataria.ENEMY_BIT;
		}else {
			fdef.filter.categoryBits = BioPirataria.LASERENEMY_BIT;
			fdef.filter.maskBits = BioPirataria.TERRAIN_BIT | BioPirataria.HEROI_BIT;
		}
		b2body.createFixture(fdef).setUserData(this);
		
		if(fireRight)
		b2body.setLinearVelocity(new Vector2(0,1000));
		else
		b2body.setLinearVelocity(new Vector2(0,-1000));
	}
	public void update(float dt) {
		stateTime += dt;
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight() /2);
		if((stateTime > 3 || setToDestroy)&& !destroyed) {
			world.destroyBody(b2body);
			destroyed = true;
			Gdx.app.log("Bala foi destruida", "");
		}	
	}
	public  void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

}