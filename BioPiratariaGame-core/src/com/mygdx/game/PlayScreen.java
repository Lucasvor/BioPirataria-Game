package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Player;

import Sprites.Heroi;
import Tools.B2WorldCreator;

public class PlayScreen implements Screen{

	@SuppressWarnings("unused")
	private BioPirataria game;
	//Sprite player;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;

	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	private World world;
	private Box2DDebugRenderer b2ddr;
	
	private Player player;
	
	private Heroi heroi;
	
	
	PlayScreen(BioPirataria game) {
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(BioPirataria.V_WIDTH, BioPirataria.V_HEIGHT,gamecam);
		hud = new Hud(BioPirataria.batch);

		mapLoader = new TmxMapLoader();
		map = mapLoader.load("map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		gamecam.position.set(BioPirataria.V_WIDTH/2,BioPirataria.V_HEIGHT/2, 0);
		
        world = new World(new Vector2(0,0), true);
        b2ddr = new Box2DDebugRenderer();
        
        new B2WorldCreator(world,map);
        
        heroi = new Heroi(world);
	}
	public void handleInput(float dt){
	    if(Gdx.input.isTouched())
	        gamecam.position.y += 300 *dt;
	    
	    if(Gdx.input.isKeyPressed(Input.Keys.UP))
	    	heroi.b2body.applyLinearImpulse(0,30f,heroi.b2body.getPosition().x,heroi.b2body.getPosition().y,true);
	    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
	    	heroi.b2body.applyLinearImpulse(30f,0,heroi.b2body.getPosition().x,heroi.b2body.getPosition().y,true);
	    if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
	    	heroi.b2body.applyLinearImpulse(-30f,0,heroi.b2body.getPosition().x,heroi.b2body.getPosition().y,true);
	    if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
	    	heroi.b2body.applyLinearImpulse(0,-30,heroi.b2body.getPosition().x,heroi.b2body.getPosition().y,true);
		 //   if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
	  //heroi.b2body.applyLinearImpulse(new Vector2(30.4f,0), heroi.b2body.getWorldCenter(), true);
	  	  //  if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && heroi.b2body.getLinearVelocity().x <= 30)
	  	  //  	heroi.b2body.applyLinearImpulse(new Vector2(30.4f,0),heroi.b2body.getWorldCenter(), true);
	  	  //  if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && heroi.b2body.getLinearVelocity().x >= -30)
	  	  //  	heroi.b2body.applyLinearImpulse(new Vector2(-30.4f,0),heroi.b2body.getWorldCenter(), true);
	    
	    
    }
	public void update(float dt){
	    handleInput(dt);
	    world.step(1/60f, 60, 2);
	    gamecam.update();
	    renderer.setView(gamecam);
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		player = new Player(new Sprite(new Texture("player1sprite.png")));
		Gdx.input.setInputProcessor(player);
		
	}

	@Override
	public void render(float delta) {

	    update(delta);
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		BioPirataria.batch.setProjectionMatrix(gamecam.combined);
		BioPirataria.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		renderer.render();
		b2ddr.render(world,gamecam.combined);
		
//		player.batch.begin();
//		player.draw(new SpriteBatch(1000));
	//	player.batch.end();
	
		hud.stage.draw();
		
		if(Gdx.input.isKeyPressed(Keys.W)) {
			player.setY(player.getY()+5);
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			player.setY(player.getY()-5);
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			player.setX(player.getX()-5);
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			player.setX(player.getX()+5);
		}

	
		BioPirataria.batch.begin();
		BioPirataria.batch.draw(player, player.getX(), player.getY());
		BioPirataria.batch.end();
		
		

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		gamePort.update(width, height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		player.getTexture().dispose();
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2ddr.dispose();
		hud.dispose();
		
	}

}
