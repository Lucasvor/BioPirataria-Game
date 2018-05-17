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
	public enum State {STANDING , RUNNING, RUNNING2, RUNNING3, DEAD}; //variaveis de estado
	public PlayScreen screen; //tela
	public State currentState; //estado atual
	public State previousState; // estado anterior
	public World world; // mundo 
	public Body b2body; //box2dbody
	
	// variaveis da animação//
	
	private TextureRegion heroiStand; //textura estatica
	private Animation<TextureRegion> heroiRun; //textura que armazena a animação 1
	private Animation<TextureRegion> heroiRun2; //textura que armazena a animação 2
	
	private float stateTimer1; //tempo do estado
	private boolean runningRigth; //variavel booleana para rodar a textura

	private boolean afastaheroi; //afasta o heroi quando encosta nele
	private Array<Tiro> tiros; // importa os tiros da classe tiro e define a variavel
	
	private Game game;
	
	public Heroi(PlayScreen screen, int vidaHeroi, Game gm) {
		super(screen.getAtlas().findRegion("heroicosta")); //textura utilizada para as animações do heroi
		this.world = screen.getWorld();
		this.screen = screen;

		this.game = gm;
		
		//dando valor as variaveis
		
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer1 = 0;
		runningRigth = true;
		
		//ANIMAÇÕES//
		
		//cria vetores para animação
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 1; i < 4;i++)// imagem 1 até imagem que for menor que a imagem 4 incrementa imagem
			frames.add(new TextureRegion(getTexture(),i*16,0,15,31)); //cria textura com a posição da imagem png
		heroiRun = new Animation(0.1f,frames); // armazena a animação na variavel 1
		frames.clear();
	
		for(int i = 8; i < 12;i++) // imagem 8 até imagem que for menor que a imagem 12 incrementa imagem
			frames.add(new TextureRegion(getTexture(),i*16,0,15,31));//cria textura com a posição da imagem png
		heroiRun2 = new Animation(0.1f,frames);// armazena a animação na variavel 2
		
		heroiStand = new TextureRegion(getTexture(),0,0,15,31); //cria a textura para quando pesonagem estiver parado
		setBounds(0,0, 29, 62);
		setRegion(heroiStand);
		
		defineHeroi();
		
		//cria vetor para os tiros
		tiros = new Array<Tiro>();
		
	}

	public void afastaheroi() {
		Gdx.app.log("Afasta heroi", "");
		afastaheroi = true;
	}
	
	public void update(float dt) {

		if(afastaheroi) {
			b2body.setTransform(new Vector2(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() /2-100), 0);// move o personagem para baixo.
			
			setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() /2-100);
			afastaheroi= false;
		}else {
			setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() /2);
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
	public void atirar() { //cria colisão para os tiros do heroi
		tiros.add(new Tiro(screen,b2body.getPosition().x,b2body.getPosition().y,true));
	}
	public void draw(Batch batch) { //desenha os tiros na tela
		super.draw(batch);
		for(Tiro tiro:tiros)
			tiro.draw(batch);
	}
	public TextureRegion getFrame(float dt) { // estado atual das animações
		currentState = getState();
		TextureRegion region;
		switch(currentState) 
		{
			case RUNNING:
			   region =  heroiRun.getKeyFrame(stateTimer1,true);
			   break;
			case RUNNING2:
				region = heroiRun2.getKeyFrame(stateTimer1,true);
				break;
		   case STANDING:
			   default:
				   region = heroiStand;
				   break;
		}
		
		// faz com que a textura mude de lado
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
		
		stateTimer1 = currentState == previousState ? stateTimer1 + dt : 0 ;
		previousState = currentState;
		return region;
	}
	
	public State getState() // esse metodo so pode ser alterado por quem trabalha na UBISOFT //
	{
		if(b2body.getLinearVelocity().y > b2body.getLinearVelocity().x) // se eixo y for maior que x retorna animação 1
    	return State.RUNNING;
	else if (b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y != 0) //se eixo x for igual 0 e eixo y difernte de 0 retorna animação 1
		return State.RUNNING;
    else if(b2body.getLinearVelocity().y != 0 || b2body.getLinearVelocity().x != 0) //se eixo y for diferente de 0 ou eixo x for diferente de 0 retorna animação 2
    	return State.RUNNING2;
	else // se os 2 eixos forem iguais a 0 retorna textura padrao
		return State.STANDING;
	}
	
	public Body getBody() {
		return this.b2body;
	}
	
	public void defineHeroi() {
		BodyDef bdef = new BodyDef(); // cria a area de colisão do heroi
		
		//Posição Inicial do HEROI //
		
		bdef.position.set(400,180);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		//area de colisão//
		
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(getWidth()/6, getHeight()/4);
		
		// heroi reconhece quando esta tocando no terreno , no tiro do inimigo , no imimigo , nas bordas do mapa , na lava e na agua
		fdef.shape = shape;
		fdef.filter.categoryBits = BioPirataria.HEROI_BIT;
		fdef.filter.maskBits = BioPirataria.TERRAIN_BIT |BioPirataria.TIROENEMY_BIT| BioPirataria.ENEMY_BIT | BioPirataria.BORDAS_BIT | BioPirataria.LAVA_BIT | BioPirataria.AGUA_BIT  ;
		b2body.createFixture(fdef).setUserData("Corpo");	
	}
	public void finaldojogo() {
		b2body.setTransform(new Vector2(450,18700), 0); 
	}

}
