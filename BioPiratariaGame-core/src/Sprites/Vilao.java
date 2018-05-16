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
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BioPirataria;
import com.mygdx.game.Hud;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.Tiro;
import com.mygdx.game.YouWinScreen;

public class Vilao extends Enemy{
	private Game game;
	private TextureRegion vilaoStand;
	private static int vida;
	private boolean startgamevilao = false;
	private Array<Tiro> tiros;
	PlayScreen screen;
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
		vilaoStand = new TextureRegion(new Texture("Human1.png"),0,0,100,100);
		setBounds(0, 0, 32, 60);
		setRegion(vilaoStand);
		this.vida = vida;
		this.game = gm;
		this.screen = screen;
		tiros = new Array<Tiro>();
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
		for(Tiro tiro: tiros) {
			tiro.update(dt);
			
			if(tiro.isDestroyed()) {
				tiros.removeValue(tiro, true);
			}
		}
		
	}
	public void atirar() {
		tiros.add(new Tiro(screen,b2body.getPosition().x-50,b2body.getPosition().y-50,false));
		Gdx.app.log("Vilao atirou", "");
	}


	@Override
	protected void defineEnemy() {
		BodyDef bdef = new BodyDef();
		
		//posição de inicio do inimigo //
		
		bdef.position.set(getX(), getY()+350);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		
		FixtureDef fdef = new FixtureDef();
		fdef.filter.groupIndex = 2;
		
		//PolygonShape shape = new PolygonShape();
		CircleShape dynamicCircle = new CircleShape(); 
		
		dynamicCircle.setRadius(30f); 
		
		fdef.shape = dynamicCircle;
		fdef.restitution = 80;
		fdef.filter.categoryBits = BioPirataria.ENEMY_BIT;
		fdef.filter.maskBits = BioPirataria.HEROI_BIT | BioPirataria.BORDAS_BIT;
		
		b2body.createFixture(fdef).setUserData("Enemy");
		
		
		
	}
	public void startgamevilao() {
		if(!startgamevilao) {
			setVelocity(500, 500);
			startgamevilao = true;
	}
		}
	public void draw(Batch batch) {
		super.draw(batch);
		for(Tiro tiro:tiros)
			tiro.draw(batch);
	}




}
