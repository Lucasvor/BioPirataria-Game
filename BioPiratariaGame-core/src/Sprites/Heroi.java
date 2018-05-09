package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BioPirataria;
import com.mygdx.game.PlayScreen;

public class Heroi extends Sprite {
	public World world;
	public Body b2body;
	private TextureRegion heroiStand;
	
	private float stateTimer; //variável de condição para o game over 
	private boolean heroiIsDead; //variável de condição para o game over
	
	public Heroi(PlayScreen screen) {
		super(screen.getAtlas().findRegion("heroi"));
		this.world = screen.getWorld();
		defineHeroi();
		heroiStand = new TextureRegion(getTexture(),0,0,32,60);
		setBounds(0, 0, 32, 60);
		setRegion(heroiStand);
	}
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() /2);
	}
	
	public Body getBody() {
		return this.b2body;
	}
	
	public Body getBulletBody() {
		BodyDef bodDef = new BodyDef();
		bodDef.type = BodyType.KinematicBody;
		bodDef.position.set(getX() + 10,getY() + 10);
		
		b2body = world.createBody(bodDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(10, 10);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 2f;
		shape.dispose();
		return b2body;
	
	}
	
	public boolean isDead() {
		return heroiIsDead;
	}
	
	public float getStateTimer() {
		return stateTimer;
	}
	
	public void defineHeroi() {
		BodyDef bdef = new BodyDef();
		
		//bdef.position.set(32 / BioPirataria.PPM,32 / BioPirataria.PPM);
		//bdef.position.set(128,128);
		//bdef.position.set(getX() / BioPirataria.PPM, getY() / BioPirataria.PPM);
		
		//posição inicial antiga//
		//bdef.position.set(120,120);
		bdef.position.set(450,20);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		//shape.setAsBox((getWidth()/2)/BioPirataria.PPM,(getHeight()/2)/ BioPirataria.PPM);
		shape.setAsBox(getWidth()/6, getHeight()/4);
		//shape.setRadius(20/BioPirataria.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef).setUserData("Corpo");;
		
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-10,35),new Vector2(10,35));
		fdef.shape = head;
		fdef.isSensor = true;
		b2body.createFixture(fdef).setUserData("Head");
		
	}
}
