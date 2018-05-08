package Sprites;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.PlayScreen;

public class Vilao extends Enemy{

	public Vilao(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void defineEnemy() {
		// TODO Auto-generated method stub
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
		b2body.createFixture(fdef).setUserData("Enemy");;
		
	}

}
