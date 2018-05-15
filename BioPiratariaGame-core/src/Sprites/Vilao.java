package Sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.BioPirataria;
import com.mygdx.game.Hud;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.YouWinScreen;

public class Vilao extends Enemy{
	private Game game;
	private TextureRegion vilaoStand;
	private static int vida;
	private boolean startgamevilao = false;
	public static int getVida() {
		return vida;
	}

	public static void lostVida(int life) {
		vida -= life;
		Hud.setvidaInimigo(vida);
	}

	public void setVida(int vida) {
		this.vida = vida;
	}
	public Vilao(PlayScreen screen, float x, float y, int vida,Game gm) {
		super(screen, x, y);
		// TODO Auto-generated constructor stub
		vilaoStand = new TextureRegion(new Texture("Human1.png"),0,0,100,100);
		setBounds(0, 0, 32, 60);
		setRegion(vilaoStand);
		this.vida = vida;
		this.game = gm;
	}

	public void update(float dt) {
		if(getVida() <= 0) {
			Gdx.app.log("Vilão morreu, youwin!", "");
			world.destroyBody(b2body);
			game.setScreen(new YouWinScreen(game));
		}else{
		b2body.setLinearVelocity(velocity);
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight() / 2);
		}
		
//		b2body.setLinearVelocity(velocity);
//		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight() / 2);
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
		bdef.position.set(getX(), getY()+350);
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
		fdef.restitution = 80;
		fdef.filter.categoryBits = BioPirataria.ENEMY_BIT;
		fdef.filter.maskBits = BioPirataria.HEROI_BIT | BioPirataria.BORDAS_BIT;
		
		b2body.createFixture(fdef).setUserData("Enemy");
		
		
		
	}
	public void startgamevilao() {
		if(!startgamevilao) {
			setVelocity(90, 90);
			startgamevilao = true;
	}
		}
	public void draw(Batch batch){
		super.draw(batch);
	}




}
