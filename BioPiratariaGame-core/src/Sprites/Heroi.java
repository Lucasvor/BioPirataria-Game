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
import com.mygdx.game.YouWinScreen;

public class Heroi extends Sprite {
	public enum State {STANDING , RUNNING, DEAD};
	public PlayScreen screen1;
	public State currentState;
	public State previousState;
	public World world;
	public Body b2body;
	// variaveis da animação//
	private TextureRegion heroiStand;
	private Animation<TextureRegion> heroiRun;
	
	private float stateTimer1;
	private boolean runningRigth;
	private boolean runningUP;
	private boolean runningLeft;
	private boolean runningDown;
	private boolean heroiIsDead;
	
	private boolean afastaheroi; //afasta o heroi quando encosta nele
	
	private Array<Tiro> tiros;
	private Game game;
	
	private static int vida;
	public static int getVida() {
		return vida;
	}
	public static void lostVida(int life) {
		vida -= life;
		Hud.setvidaHeroi(vida);
	}
	public void setVida(int vida) {
		this.vida = vida;
	}
	
	public Heroi(PlayScreen screen1, int vida, Game gm) {
		//super(screen.getAtlas().findRegion("heroifrente"));
		super(screen1.getAtlas().findRegion("heroicosta"));
		this.world = screen1.getWorld();
		this.screen1 = screen1;
		this.vida = vida;
		this.game = gm;
		
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer1 = 0;
		runningDown = true;
		runningRigth = true;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 1; i < 4;i++)
			frames.add(new TextureRegion(getTexture(),i*16,0,15,31));
		heroiRun = new Animation(0.1f,frames);
		frames.clear();
		
		heroiStand = new TextureRegion(getTexture(),2,33,15,31);
		setBounds(0,0, 29, 62);
		setRegion(heroiStand);
		
		
		//ANIMAÇÕES//
		
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer1 = 0;
		runningRigth = true;
		//runningUP = true;
		
		
//		Array<TextureRegion> frames = new Array<TextureRegion>();
//		for(int i = 0 ; i < 1 ; i++)
//			frames.add(new TextureRegion(getTexture(),i*0,0,32,60));
//		heroiRun = new Animation (0.1f , frames);
//		frames.clear();
		
		defineHeroi();
		/*heroiStand = new TextureRegion(getTexture(),33,152,29,50);
		setBounds(33,152, 29, 50);
		setRegion(heroiStand);*/
		/*heroiStand = new TextureRegion(getTexture(),0,0,32,205);
		setBounds(0,0, 32, 205);
		setRegion(heroiStand);
		heroiDead = new TextureRegion(getTexture(), 96, 0, 16, 16);
		 */
		
		
		tiros = new Array<Tiro>();
		
		//ANIMAÇÕES//
		
	}

	public void afastaheroi() {
		Gdx.app.log("Afasta heroi", "");
		afastaheroi = true;
		//setPosition(b2body.getPosition().x - 200, b2body.getPosition().y);
	}
	
	public void update(float dt) {
		if(getVida() <= 0) {
			Gdx.app.log("Acabaram suas vidas, gameover!", "");
			game.setScreen(new GameOverScreen(game));
		}
		if(afastaheroi) {
			b2body.setTransform(new Vector2(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() /2-100), 0);// move o personagem para baixo.
			
			setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() /2-100);
			//setRegion(getFrame(dt));
			afastaheroi= false;
		}else {
			setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() /2);
			//setPosition(b2body.getPosition().x, b2body.getPosition().y);
			setRegion(getFrame(dt));
		}
		
		setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y- getHeight() /2);
		
		for(Tiro tiro: tiros) {
			tiro.update(dt);
			
			if(tiro.isDestroyed()) {
				tiros.removeValue(tiro, true);
			}
		}
	}
	public void atirar() {
		tiros.add(new Tiro(screen1,b2body.getPosition().x,b2body.getPosition().y,true));
	}
	public void draw(Batch batch) {
		super.draw(batch);
		for(Tiro tiro:tiros)
			tiro.draw(batch);
	}
	public TextureRegion getFrame(float dt) {
		currentState = getState();
		TextureRegion region;
		switch(currentState) 
		{
			case RUNNING:
			   region =  heroiRun.getKeyFrame(stateTimer1,true);
			   break;
		   case STANDING:
			   default:
				   region = heroiStand; //possivel erro//
				   break;
		}
		
		if((b2body.getLinearVelocity().x > 0 || !runningRigth) && !region.isFlipX()) 
		{
			region.flip(true, false);
			runningRigth = false;
		}
		else if((b2body.getLinearVelocity().x < 0 || runningRigth) && region.isFlipX()) 
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
		if(heroiIsDead)
	        return State.DEAD;
	    else if(b2body.getLinearVelocity().x != 0 || b2body.getLinearVelocity().y != 0)
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
		return heroiIsDead;
	}
	
	public void defineHeroi() {
		BodyDef bdef = new BodyDef();
		
		//bdef.position.set(32 / BioPirataria.PPM,32 / BioPirataria.PPM);
		//bdef.position.set(128,128);
		//bdef.position.set(getX() / BioPirataria.PPM, getY() / BioPirataria.PPM);
		
		//posição inicial antiga//
		//bdef.position.set(120,120);
		bdef.position.set(400,180);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		//shape.setAsBox((getWidth()/2)/BioPirataria.PPM,(getHeight()/2)/ BioPirataria.PPM);
		shape.setAsBox(getWidth()/6, getHeight()/4);
		//shape.setRadius(20/BioPirataria.PPM);
		
		fdef.shape = shape;
		fdef.filter.categoryBits = BioPirataria.HEROI_BIT;
		fdef.filter.maskBits = BioPirataria.TERRAIN_BIT | BioPirataria.ENEMY_BIT | BioPirataria.BORDAS_BIT | BioPirataria.LAVA_BIT | BioPirataria.AGUA_BIT;
		b2body.createFixture(fdef).setUserData("Corpo");
		
//		EdgeShape head = new EdgeShape();
//		head.set(new Vector2(-10,35),new Vector2(10,35));
//		fdef.shape = head;
//		fdef.isSensor = true;
//		b2body.createFixture(fdef).setUserData("Head");
		
	}

}
