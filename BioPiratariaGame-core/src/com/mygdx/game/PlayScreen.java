package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import Sprites.Heroi;
import Sprites.Vilao;
import Tools.B2WorldCreator;

public class PlayScreen implements Screen
{

	//texturas do jogo // 
	
	@SuppressWarnings("unused")
	private BioPirataria game;
	private Game gm;
	private TextureAtlas atlas;
	
	//Sprite player;
	
	private OrthographicCamera gamecam;
	private Viewport gamePort;

	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	private World world;
	private Box2DDebugRenderer b2ddr;
	
	public int velocity = 50;
	
	//private Player player;
	
	private Music music;
	private Hud hud;
	private Heroi heroi;
	private Vilao vilao;
	float stateTime;
	float timePontos;
	//avisa se o heroi está na lava
	
	public boolean heroiLava;
	
	// tempo para o heroi leva dano continuo
	
	private float tempolava;
	private float tempohudmessage;
	private boolean timemessage1 = true, timemessage2 = true,timemessage3 = true,timemessage4 = true,timemessage5 = true;
	private boolean paraInimigo = true;

	
	PlayScreen(BioPirataria game, Game gm) {
		atlas = new TextureAtlas("heroi2.pack");
		this.game = game;
		this.gm = gm;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(BioPirataria.V_WIDTH , BioPirataria.V_HEIGHT,gamecam);
//

		mapLoader = new TmxMapLoader();
		map = mapLoader.load("JogoFINALLLLLL.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2, 0);
		
        world = new World(new Vector2(0,0), true);
        b2ddr = new Box2DDebugRenderer();
        
        new B2WorldCreator(this);
        

		hud = new Hud(BioPirataria.batch, gm);
		vilao = new Vilao(this, 100,200,1000,gm);
        heroi = new Heroi(this, 100, gm);
        

        // bullet
//        bullet = new Bullet(heroi.b2body.getPosition(), new Vector2(10,0));
//        bulletTexture = new Texture("bala1.png");
//        
//        bulletManager = new ArrayList<Bullet>();
        
        //Pegando colisão
        
        world.setContactListener(new WorldContactListener(heroi,this,vilao));
        
        //music = BioPirataria.manager.get("Songs/Venus.ogg", Music.class);
        //music.setLooping(true);
       // music.play();
        
        
        //Gdx.input.setCursorImage
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}
	
	public void handleInput(float dt){
		
	    
		
	if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)){	
		heroi.getBody().applyLinearImpulse(new Vector2(-velocity,velocity), heroi.getBody().getWorldCenter(), true);
		
	}else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)){
		heroi.getBody().applyLinearImpulse(new Vector2(-velocity,-velocity), heroi.getBody().getWorldCenter(), true);
	}else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)){
		heroi.getBody().applyLinearImpulse(new Vector2(velocity,velocity), heroi.getBody().getWorldCenter(), true);
	}else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)){
		heroi.getBody().applyLinearImpulse(new Vector2(velocity,-velocity), heroi.getBody().getWorldCenter(), true);
	}
	else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
    	//gamecam.position.x -= 100 * dt;
    heroi.getBody().applyLinearImpulse(new Vector2(-velocity,0), heroi.getBody().getWorldCenter(), true);
    	
    }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
    	//gamecam.position.x += 100 * dt;
	    	heroi.getBody().applyLinearImpulse(new Vector2(velocity,0), heroi.getBody().getWorldCenter(), true);
	    }else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
	    	//gamecam.position.y += 100 * dt;
	    	heroi.getBody().applyLinearImpulse(new Vector2(0,velocity), heroi.getBody().getWorldCenter(), true);
    }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
    	//gamecam.position.y -= 100 * dt;
	    	heroi.getBody().applyLinearImpulse(new Vector2(0,-velocity), heroi.getBody().getWorldCenter(), true);
	    }else {
	    	heroi.getBody().setLinearVelocity(0,0);
	    }
	    
    	if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//    		Bullet myBullet = new Bullet(heroi.b2body.getPosition(),new Vector2(0,20));
//    		bulletManager.add(myBullet);
    		BioPirataria.manager.get("Songs/Tiro2.mp3",Sound.class).play();
    		heroi.atirar();
    	}
    	if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
    		vilao.atirar();
    	}
	    // personagem com desenho
		
		//heroi.b2body.getLinearVelocityFromLocalPoint(new Vector2(0,0));
		//heroi.getBody().setLinearVelocity(new Vector2(0,0));

    }
	public void update(float dt){
	    handleInput(dt);
	    world.step(1/60f, 6, 2);
	    heroi.update(dt);
	    vilao.update(dt);
	    hud.update(dt);
	    
	    //atualiza vida/score e tempo.
	    
	    hud.update(dt);
	    
	    //gamecam.position.set(heroi.getX(),heroi.getY(),0);
	    
	    stateTime += dt;
	    tempolava += dt;
	    timePontos += dt;
	    tempohudmessage += dt;
	    if(stateTime > 6) {
	    	vilao.startgamevilao();
	    	Vector3 position = gamecam.position;
		    position.y = gamecam.position.y+1.5f;
		   // //Gdx.app.log("Posição y:"+vilao.getY(), "");
		    
		   gamecam.position.set(gamecam.position.x,vilao.getY(),0);
		   if(vilao.getY() >= 18999) {
			   if(paraInimigo) {
				   Hud.hudanimtext("É Agora que a verdadeira batalha começa!",100, 250);
				   vilao.setVida(1000);
			   vilao.setVelocity(90, 0);
			   paraInimigo = false;
			   tempohudmessage= 0;
		   }
			   if(tempohudmessage > 1) {
			   vilao.atirar();
			   tempohudmessage= 0;
		}
			   }
		    
		}else {
			if(tempohudmessage > 1 && tempohudmessage < 2) {
				if(timemessage1) {
					timemessage1 = false;
			Hud.hudanimtext("Salve os seus animais!!!",200, 250);
				}
				
			}
			else if(tempohudmessage > 2 && tempohudmessage < 3) {
				if(timemessage2) {
					timemessage2 = false;
				Hud.hudanimtext("3",BioPirataria.V_WIDTH/2,BioPirataria.V_HEIGHT/2);
				}
				}
			else if(tempohudmessage > 3 && tempohudmessage < 4) {
				if(timemessage3) {
					timemessage3 = false;
				Hud.hudanimtext("2",BioPirataria.V_WIDTH/2,BioPirataria.V_HEIGHT/2);
				}}
			else if(tempohudmessage > 4 && tempohudmessage < 5) {
				if(timemessage4) {
					timemessage4 = false;
				Hud.hudanimtext("1",BioPirataria.V_WIDTH/2,BioPirataria.V_HEIGHT/2);
				}
			}
			else if(tempohudmessage > 5 && tempohudmessage < 6) {
				if(timemessage5) {
					timemessage5 = false;
				Hud.hudanimtext("Vaaaaaaaiiiiiiii",280, 250);
				}
			}
				
			gamecam.position.set(gamecam.position.x,gamecam.position.y+1,0);
		}
	    if(timePontos > 3) {
	    	Hud.addPontos(1);
	    	timePontos = 0;
	    }
	    if(tempolava > 1 && heroiLava) {
	    	Hud.lostLife(1);
	    	tempolava = 0;
	    }
	    
	    //gamecam.position.x = heroi.b2body.getPosition().y;
	    gamecam.update();
	    
	    //gamecam.position.y *= 5;
	    renderer.setView(gamecam);
    }

	@Override
	public void show() 
	{
		
	}

	@Override
	public void render(float dt) 
	{
		// definições de renderização do jogo //
		
	    update(dt);
	    
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		BioPirataria.batch.setProjectionMatrix(gamecam.combined);
		
		renderer.render();
		b2ddr.render(world,gamecam.combined);
		BioPirataria.batch.begin();
		heroi.draw(game.batch);
		vilao.draw(game.batch);
		BioPirataria.batch.end();
		

		BioPirataria.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
	}	
	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}
	public TiledMap getMap() {
		return map;
	}
	public World getWorld() {
		return world;
	}
	public Game getGame() {
		return game;
	}
	
	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2ddr.dispose();
		hud.dispose();
		
	}

}
