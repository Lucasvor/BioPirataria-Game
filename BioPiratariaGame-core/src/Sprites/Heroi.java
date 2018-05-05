package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BioPirataria;

public class Heroi extends Sprite {
	public World world;
	public Body b2body;
	
	public Heroi(World world) {
		this.world = world;
		defineHeroi();
	}
	
	public void defineHeroi() {
		BodyDef bdef = new BodyDef();
		//bdef.position.set(32 / BioPirataria.PPM,32 / BioPirataria.PPM);
		bdef.position.set(40,40);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(20);
		//shape.setRadius(20/BioPirataria.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
	}
}
