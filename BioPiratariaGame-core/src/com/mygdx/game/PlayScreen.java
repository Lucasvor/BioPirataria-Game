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
	
	// variaveis da camera //
	
	private OrthographicCamera gamecam;
	private Viewport gamePort;

	//variaveis do mapa TIled //
	
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	//variaveis do b2dbody//
	
	private World world;
	private Box2DDebugRenderer b2ddr;
	
	public int velocity = 50;
	
	
	
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
		atlas = new TextureAtlas("heroi2.pack"); // pack de texturas do personagem principal
		this.game = game;
		this.gm = gm;
		gamecam = new OrthographicCamera(); // cria nova camera 
		gamePort = new FitViewport(BioPirataria.V_WIDTH , BioPirataria.V_HEIGHT,gamecam); // cria resolução da tela
//

		mapLoader = new TmxMapLoader(); // cria o mapa 
		map = mapLoader.load("JogoFINALLLLLL.tmx"); // textura do mapa em arquivo Tiled
		renderer = new OrthogonalTiledMapRenderer(map); // renderiza o mapa
		gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2, 0); //posição da camera
		
        world = new World(new Vector2(0,0), true); //Vector 2 para movimentação
        b2ddr = new Box2DDebugRenderer(); // box2dbody para colisão
        
        new B2WorldCreator(this);
        

		hud = new Hud(BioPirataria.batch, gm); // cria HUD
		vilao = new Vilao(this, 100,200,1000,gm); //cria vilao
        heroi = new Heroi(this, 100, gm); // cria heroi
        

        
        //Pegando colisão
        
        world.setContactListener(new WorldContactListener(heroi,this,vilao));
        
        
        // Musica do jogo
        
        music = BioPirataria.manager.get("Songs/Venus.ogg", Music.class);
        music.setLooping(true);
        music.play();
        
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}
	
	public void handleInput(float dt){
		
	    
		//Define as teclas que serão utilizadas para a movimentação do personagem e para qual lado elas levam
		
	if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)){	
		heroi.getBody().applyLinearImpulse(new Vector2(-velocity,velocity), heroi.getBody().getWorldCenter(), true);
		
	}else if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)){
		heroi.getBody().applyLinearImpulse(new Vector2(-velocity,-velocity), heroi.getBody().getWorldCenter(), true);
	}else if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)){
		heroi.getBody().applyLinearImpulse(new Vector2(velocity,velocity), heroi.getBody().getWorldCenter(), true);
	}else if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)){
		heroi.getBody().applyLinearImpulse(new Vector2(velocity,-velocity), heroi.getBody().getWorldCenter(), true);
	}
	else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
    heroi.getBody().applyLinearImpulse(new Vector2(-velocity,0), heroi.getBody().getWorldCenter(), true);
    	
    }else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
	    	heroi.getBody().applyLinearImpulse(new Vector2(velocity,0), heroi.getBody().getWorldCenter(), true);
	    }else if(Gdx.input.isKeyPressed(Input.Keys.W)) {
	    	heroi.getBody().applyLinearImpulse(new Vector2(0,velocity), heroi.getBody().getWorldCenter(), true);
    }else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
	    	heroi.getBody().applyLinearImpulse(new Vector2(0,-velocity), heroi.getBody().getWorldCenter(), true);
	    }else {
	    	heroi.getBody().setLinearVelocity(0,0);
	    }
	    
	// define o botão para atirar e o som que faz ao realizar o disparo
    	if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
    		BioPirataria.manager.get("Songs/Tiro2.mp3",Sound.class).play();
    		heroi.atirar();
    	}
    	// define o botão para o vilão atirar (somente para teste)
    	if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
    		vilao.atirar();
    	}
    	//define o botão para levar o pesonagem ao fim do jogo (somente para teste)
    	if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
    		vilao.finaldoJogo();
    		heroi.finaldojogo();
    	}

    }
	public void update(float dt){
	    handleInput(dt);
	    world.step(1/60f, 6, 2);
	    heroi.update(dt);
	    vilao.update(dt);
	    hud.update(dt);
	    
	    //atualiza vida/score e tempo.
	    
	    hud.update(dt);
	    
	    stateTime += dt;
	    tempolava += dt;
	    timePontos += dt;
	    tempohudmessage += dt;
	    
	    if(stateTime > 6) { // define quanto tempo o vilão demora para começar a andar no inicio do jogo e a posição da camera
	    	vilao.startgamevilao();
	    	Vector3 position = gamecam.position;
		    position.y = gamecam.position.y+1.5f;
		    
		   gamecam.position.set(gamecam.position.x,vilao.getY(),0); //coloca a camera em uma posição especifica
		   if(vilao.getY() >= 19050) { //quando vilão atingir um certo ponto do mapa ele para de andar
			   if(paraInimigo) {
				   Hud.hudanimtext("É Agora que a verdadeira batalha começa!",150, 250,2); // mostra na tela a mensagem nas coordenadas apresentadas
				   vilao.setVida(1000); // muda a vida do vilão para 1000 (boss final)
			   vilao.setVelocity(90, 0); // muda a velocidade em que se move
			   paraInimigo = false;
			   tempohudmessage= 0;
		   }
			   
			   
			   //Tempo das mensagens na tela no fim do jogo
			   if(tempohudmessage > 0.5) {
			   vilao.atirar();
			   tempohudmessage= 0;
			   }
			   
		   }
		    
		}else {  //Tempo das mensagens na tela no inicio do jogo
			if(tempohudmessage > 1 && tempohudmessage < 2) {
				if(timemessage1) {
					timemessage1 = false;
			Hud.hudanimtext("Mate o inimigo e Salve os animais",70, 250,3);
				}
				
			}
			else if(tempohudmessage > 2 && tempohudmessage < 3) {
				if(timemessage2) {
					timemessage2 = false;
				Hud.hudanimtext("3",BioPirataria.V_WIDTH/2,BioPirataria.V_HEIGHT/2,3);
				}
				}
			else if(tempohudmessage > 3 && tempohudmessage < 4) {
				if(timemessage3) {
					timemessage3 = false;
				Hud.hudanimtext("2",BioPirataria.V_WIDTH/2,BioPirataria.V_HEIGHT/2,3);
				}}
			else if(tempohudmessage > 4 && tempohudmessage < 5) {
				if(timemessage4) {
					timemessage4 = false;
				Hud.hudanimtext("1",BioPirataria.V_WIDTH/2,BioPirataria.V_HEIGHT/2,3);
				}
			}
			else if(tempohudmessage > 5 && tempohudmessage < 6) {
				if(timemessage5) {
					timemessage5 = false;
				Hud.hudanimtext("GOOOOOOOO",280, 250,3);
				}
			}
				
			gamecam.position.set(gamecam.position.x,gamecam.position.y+1,0);
		}
	    
	    
	    if(timePontos > 3) { // acrescenta 1 ponto na HUD a cada 3 segundos
	    	Hud.addPontos(1);
	    	timePontos = 0;
	    }
	    if(tempolava > 1 && heroiLava) { // diminui 1 ponto da vida a cada segundo que se passa sobre a lava
	    	Hud.lostLife(1);
	    	tempolava = 0;
	    }
	    
	    
	 // atualiza a camera
	    
	    gamecam.update();
	    renderer.setView(gamecam);
    }

	@Override
	public void show() 
	{
		//metodo vazio
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
		
		//b2ddr.render(world,gamecam.combined);
		
		
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
