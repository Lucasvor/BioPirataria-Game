package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BioPirataria;
import com.mygdx.game.PlayScreen;

public class Heroi extends Sprite {
	public World world;
	public Body b2body;
	private TextureRegion heroiStand;
	
	public Heroi(World world,PlayScreen screen) {
		super(screen.getAtlas().findRegion("heroi"));
		this.world = world;
		defineHeroi();
		heroiStand = new TextureRegion(getTexture(),0,0,128,128);
		setBounds(0, 0, 128, 128);
		setRegion(heroiStand);
	}
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() /2);
	}
	
	public void defineHeroi() {
		BodyDef bdef = new BodyDef();
		//bdef.position.set(32 / BioPirataria.PPM,32 / BioPirataria.PPM);
		bdef.position.set(128,128);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(23,64);
		//shape.setRadius(20/BioPirataria.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
	}
}
