package Sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BioPirataria;
import com.mygdx.game.GameOverScreen;
import com.mygdx.game.Hud;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.Tiro;

public class Heroi extends Sprite {
	public enum State {STANDING , RUNNING, DEAD};
	public PlayScreen screen;
	public State currentState;
	public State previousState;
	public World world;
	public Body b2body;
	// variaveis da animação//
	private TextureRegion heroiStand;
	private Animation heroiRun;
	private float stateTimer1;
	private boolean runningRigth;
	private boolean runningUP;
	
	private float stateTimer; //variável de condição para o game over 
	private TextureRegion heroiDead; //variável de condição para o game over
	private Game game;
	private boolean afastaheroi; //afasta o heroi quando encosta nele
	
	private Array<Tiro> tiros;
	
	public Heroi(PlayScreen screen) {
		super(screen.getAtlas().findRegion("somenteHeroi"));
		this.world = screen.getWorld();
		this.screen = screen;
		
		//ANIMAÇÕES//
		
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer1 = 0;
		runningRigth = true;
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 1 ; i < 4 ; i++)
			frames.add(new TextureRegion(getTexture(),i*0,0,32,205));
		heroiRun = new Animation (0.1f , frames);
		frames.clear();
		
		defineHeroi();
		
		/*heroiStand = new TextureRegion(getTexture(),33,152,29,50);
		setBounds(33,152, 29, 50);
		setRegion(heroiStand);*/
		/*heroiStand = new TextureRegion(getTexture(),0,0,32,205);
		setBounds(0,0, 32, 205);
		setRegion(heroiStand);
		heroiDead = new TextureRegion(getTexture(), 96, 0, 16, 16);
		 */
		
		heroiStand = new TextureRegion(getTexture(),32,0,32,205);
		setBounds(32,0, 32, 205);
		setRegion(heroiStand);
		
		tiros = new Array<Tiro>();
		
		//ANIMAÇÕES//
		
	}
	public void afastaheroi() {
		Gdx.app.log("Afasta heroi", "");
		afastaheroi = true;
		//setPosition(b2body.getPosition().x - 200, b2body.getPosition().y);
	}
	
	public void update(float dt) {
		
		if(afastaheroi) {
			b2body.setTransform(new Vector2(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() /2-200), 0);// move o personagem para baixo.
			
			//setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() /2-200);
			setRegion(getFrame(dt));
			afastaheroi= false;
		}else {
			setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() /2);
			setRegion(getFrame(dt));
		}
		
		
		
		for(Tiro tiro: tiros) {
			tiro.update(dt);
			
			if(tiro.isDestroyed()) {
				tiros.removeValue(tiro, true);
			}
			}
	}
	public void atirar() {
		tiros.add(new Tiro(screen,b2body.getPosition().x,b2body.getPosition().y,true));
	}
	public void draw(Batch batch) {
		super.draw(batch);
		for(Tiro tiro:tiros)
			tiro.draw(batch);
	}
	public TextureRegion getFrame(float dt) 
	{
		currentState = getState();
		TextureRegion region;
		switch(currentState) 
		{
		case DEAD:
			region = heroiDead;
			break;
			case RUNNING:
			   region = (TextureRegion) heroiRun.getKeyFrame(stateTimer1 , true);
			   break;
		   case STANDING:
			   default:
				   region = heroiStand; //possivel erro//
				   break;
		}
		
		if((b2body.getLinearVelocity().x < 0 || !runningRigth) && !region.isFlipX()) 
		{
			region.flip(true, false);
			runningRigth = false;
		}
		else if((b2body.getLinearVelocity().x > 0 || runningRigth) && region.isFlipX()) 
		{
			region.flip(true , false);
			runningRigth = true;
		}
		
		/*POSSIVEL ERRO DE MOVIMENTAÇÃO PARA CIMA E PARA BAIXO//
		if((b2body.getLinearVelocity().y < 0 || !runningUP) && !region.isFlipY()) 
		{
			region.flip(true, false);
			runningUP = false;
		}
		else if((b2body.getLinearVelocity().y > 0 || runningUP) && region.isFlipY()) 
		{
			region.flip(true , false);
			runningUP = true;
		}
		//POSSIVEL ERRO DE MOVIMENTAÇÃO PARA CIMA E PARA BAIXO*/
		
		stateTimer1 = currentState == previousState ? stateTimer1 + dt : 0 ;
		previousState = currentState;
		return region;
	}
	
	public State getState() //possivel erro//
	{
		if(b2body.getLinearVelocity().x != 0)
			return State.RUNNING;
		else
			return State.STANDING;
	}
	
	
	public Body getBody() {
		return this.b2body;
	}
	
//	public Body getBulletBody() {
//		BodyDef bodDef = new BodyDef();
//		bodDef.type = BodyType.KinematicBody;
//		bodDef.position.set(getX() + 10,getY() + 10);
//		
//		b2body = world.createBody(bodDef);
//		PolygonShape shape = new PolygonShape();
//		shape.setAsBox(10, 10);
//		FixtureDef fdef = new FixtureDef();
//		fdef.shape = shape;
//		fdef.density = 2f;
//		shape.dispose();
//		return b2body;
//	
//	}
	
	public boolean isDead() {
		if(Hud.getLife() <= 0) {
		return true;
		}else {
			return false;
		}
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
		fdef.filter.categoryBits = BioPirataria.HEROI_BIT;
		fdef.filter.maskBits = BioPirataria.TERRAIN_BIT | BioPirataria.ENEMY_BIT | BioPirataria.BORDAS_BIT;
		b2body.createFixture(fdef).setUserData("Corpo");
		
//		EdgeShape head = new EdgeShape();
//		head.set(new Vector2(-10,35),new Vector2(10,35));
//		fdef.shape = head;
//		fdef.isSensor = true;
//		b2body.createFixture(fdef).setUserData("Head");
		
	}
}
