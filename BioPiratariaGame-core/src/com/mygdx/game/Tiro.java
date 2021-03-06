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
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Tiro extends Sprite{
    PlayScreen screen;
    World world;
    
    float stateTime;
    boolean destroyed;
    boolean setToDestroy=false;
    boolean fireRight;
    Body b2body;
    private TextureRegion tiroStand;
    
    public Tiro(PlayScreen screen, float x, float y, boolean fireRight) {
    	this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        if(fireRight) {
        tiroStand = new TextureRegion(new Texture("bala.png"),0,0,16,16); //cria uma textura da bala do heroi
        setBounds(x, y, 16, 16);
        }
        else
        {
        	tiroStand = new TextureRegion(new Texture("laser.png"),0,0,11,39); //cria uma textura do laser do inimigo
		setBounds(x, y, 11, 39);
        }
		setRegion(tiroStand);
        defineFireBall();
    }

	private void defineFireBall() {
		
		//cria a colis�o das balas e indica quando elas s�o destruidas
		
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(),getY()+20);
		bdef.type = BodyType.DynamicBody;
		
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(getWidth(), getHeight());
		fdef.shape = shape;
		if(fireRight) {
		fdef.filter.categoryBits = BioPirataria.TIRO_BIT;
		fdef.filter.maskBits = BioPirataria.TERRAIN_BIT | BioPirataria.ENEMY_BIT;
		}else {
			fdef.filter.categoryBits = BioPirataria.TIROENEMY_BIT;
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
			if(fireRight)
			Gdx.app.log("Bala foi destruida", "");
			else
				Gdx.app.log("Bala inimiga foi destruida", "");
		}	
	}
	public  void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }
}
