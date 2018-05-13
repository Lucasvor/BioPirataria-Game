package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.mygdx.game.entities.Player;
import Sprites.Heroi;
import Sprites.Vilao;
import Tools.B2WorldCreator;

public class PlayScreen implements Screen
{

	//texturas do jogo // 
	
	@SuppressWarnings("unused")
	private BioPirataria game;
	private TextureAtlas atlas;
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
	private Music music;
	
	private Heroi heroi;
	private Vilao vilao;
	float stateTime;
	
	PlayScreen(BioPirataria game) {
		atlas = new TextureAtlas("somenteHeroi.pack");
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(BioPirataria.V_WIDTH , BioPirataria.V_HEIGHT,gamecam);
		hud = new Hud(BioPirataria.batch);

		mapLoader = new TmxMapLoader();
		map = mapLoader.load("JogoFINALLLLLL.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2, 0);
		
        world = new World(new Vector2(0,0), true);
        b2ddr = new Box2DDebugRenderer();
        
        new B2WorldCreator(this);
        
        heroi = new Heroi(this);
        vilao = new Vilao(this, 100,200,1000);
        // bullet
//        bullet = new Bullet(heroi.b2body.getPosition(), new Vector2(10,0));
//        bulletTexture = new Texture("bala1.png");
//        
//        bulletManager = new ArrayList<Bullet>();
        
        //Pegando colisão
        world.setContactListener(new WorldContactListener(heroi));
        
        music = BioPirataria.manager.get("Songs/Venus.ogg", Music.class);
        music.setLooping(true);
        music.play();
        
        
        //Gdx.input.setCursorImage
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}
	
	public void handleInput(float dt){
	   // if(Gdx.input.isTouched())
	    //    gamecam.position.y += 300 *dt;
	    
//	    if(Gdx.input.isKeyPressed(Input.Keys.UP))
//	    	heroi.b2body.applyLinearImpulse(0,30f,heroi.b2body.getPosition().x,heroi.b2body.getPosition().y,true);
//	    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
//	    	heroi.b2body.applyLinearImpulse(30f,0,heroi.b2body.getPosition().x,heroi.b2body.getPosition().y,true);
//	    if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
//	    	heroi.b2body.applyLinearImpulse(-30f,0,heroi.b2body.getPosition().x,heroi.b2body.getPosition().y,true);
//	    if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
//	    	heroi.b2body.applyLinearImpulse(0,-30,heroi.b2body.getPosition().x,heroi.b2body.getPosition().y,true);
		
	     //faz a camera se mover e  velocidade do heroi//
	if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)){	
		heroi.getBody().applyLinearImpulse(new Vector2(-50,50), heroi.getBody().getWorldCenter(), true);
		
	}else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)){
		heroi.getBody().applyLinearImpulse(new Vector2(-50,-50), heroi.getBody().getWorldCenter(), true);
	}else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)){
		heroi.getBody().applyLinearImpulse(new Vector2(50,50), heroi.getBody().getWorldCenter(), true);
	}else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)){
		heroi.getBody().applyLinearImpulse(new Vector2(50,-50), heroi.getBody().getWorldCenter(), true);
	}
	else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
    	//gamecam.position.x -= 100 * dt;
    heroi.getBody().applyLinearImpulse(new Vector2(-80,0), heroi.getBody().getWorldCenter(), true);
    	
    }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
    	//gamecam.position.x += 100 * dt;
	    	heroi.getBody().applyLinearImpulse(new Vector2(80,0), heroi.getBody().getWorldCenter(), true);
	    }else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
	    	//gamecam.position.y += 100 * dt;
	    	heroi.getBody().applyLinearImpulse(new Vector2(0,80), heroi.getBody().getWorldCenter(), true);
    }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
    	//gamecam.position.y -= 100 * dt;
	    	heroi.getBody().applyLinearImpulse(new Vector2(0,-80), heroi.getBody().getWorldCenter(), true);
	    }else {
	    	heroi.getBody().setLinearVelocity(0,0);
	    }
    //else if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
	//    	new Bullet(world, this, new Texture("laser.png"), 100, 100);
	    
    	if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//    		Bullet myBullet = new Bullet(heroi.b2body.getPosition(),new Vector2(0,20));
//    		bulletManager.add(myBullet);
    		heroi.atirar();
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
	    //atualiza vida/score e tempo.
	    hud.update(dt);
	    //gamecam.position.set(heroi.getX(),heroi.getY(),0);
	    stateTime += dt;
	    if(stateTime > 3) {
	    	Vector3 position = gamecam.position;
		    position.y = gamecam.position.y+1;
		    gamecam.position.set(position);
		}
	    
	    //gamecam.position.x = heroi.b2body.getPosition().y;
	    gamecam.update();
	    
	    //gamecam.position.y *= 5;
	    
	    renderer.setView(gamecam);
    }


	@Override
	public void show() 
	{
		// TODO Auto-generated method stub
		
		// textura do personagem principal //
		
	//	player = new Player(new Sprite(new Texture("player1sprite.png")));
	//	Gdx.input.setInputProcessor(player);
		
	}

	@Override
	public void render(float delta) 
	{
		
		// definições de renderização do jogo //
		
		
	    update(delta);
		// TODO Auto-generated method stub
	    
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
		

		if(gameOver()) {
			game.setScreen(new GameOverScreen(game));
			dispose();
		}
	}

	public boolean gameOver() {
		if (player.currentState == Heroi.State.DEAD && player.getStateTimer() > 3){
			return true;
		}
		return false;
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
	public TiledMap getMap() {
		return map;
	}
	public World getWorld() {
		return world;
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
