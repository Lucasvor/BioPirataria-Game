package Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.BioPirataria;
import com.mygdx.game.PlayScreen;

public class Vilao extends Enemy{
	private TextureRegion vilaoStand;
	
	public Vilao(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		// TODO Auto-generated constructor stub
		vilaoStand = new TextureRegion(new Texture("Human1.png"),0,0,100,100);
		setBounds(0, 0, 32, 60);
		setRegion(vilaoStand);
	}

	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight() / 2);
		
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
		bdef.position.set(450,400);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		
		FixtureDef fdef = new FixtureDef();
		fdef.filter.groupIndex = 2;
		
		//PolygonShape shape = new PolygonShape();
		CircleShape dynamicCircle = new CircleShape(); 
		
		//shape.setAsBox((getWidth()/2)/BioPirataria.PPM,(getHeight()/2)/ BioPirataria.PPM);
		//shape.setAsBox(100, 100);
		dynamicCircle.setRadius(30f); 
		//shape.setRadius(20/BioPirataria.PPM);
		
		fdef.shape = dynamicCircle;
		fdef.filter.categoryBits = BioPirataria.ENEMY_BIT;
		fdef.filter.maskBits = BioPirataria.HEROI_BIT;
		
		b2body.createFixture(fdef).setUserData("Enemy");
		
	}

}
