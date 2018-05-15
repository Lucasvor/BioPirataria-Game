package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Tools.GifDecoder;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MenuScreen implements Screen{
	
	//tela Menu Principal//
	//ALTURA E LARGURA DOS BOTÕES DO MENU
	Animation<TextureRegion> animation;;
	
	float stateTime;
	private static final int PLAY_BUTTOM_WIDTH = 92;
	private static final int PLAY_BUTTOM_HEIGHT = 30;
	
	private static final int CREDITS_BUTTOM_WIDTH = 130;
	private static final int CREDITS_BUTTOM_HEIGHT = 30;
	
	private static final int QUIT_BUTTOM_WIDTH = 95;
	private static final int QUIT_BUTTOM_HEIGHT = 26;
	
	//VARIÁVEIS PARA AS IMAGENS DOS BOTÕES 
	
	private Viewport viewport;
	private Texture title;
	private Texture playbtnactive;
	private Texture playbtninactive;
	private Texture creditsbtnactive;
	private Texture creditsbtninactive;
	private Texture quitbtnactive;
	private Texture quitbtninactive;
	
	private Game game;
	
	
	public MenuScreen (Game game) {
		this.game = game;
		viewport = new FitViewport(BioPirataria.V_WIDTH, BioPirataria.V_HEIGHT, new OrthographicCamera());
		
		//Música do menu
		
		BioPirataria.manager.get("Songs/Mercury.wav", Sound.class).play();
		
		title = new Texture("biopiratariatitle.png");
		playbtnactive = new Texture("playbtnactv.png"); //JOGAR ATIVO
		playbtninactive = new Texture("playbtninactv.png"); //JOGAR INATIVO
		creditsbtnactive = new Texture("creditsbtnactv.png"); //CRÉDITOS ATIVO
		creditsbtninactive = new Texture("creditsbtninactv.png"); //CRÉDITOS INATIVO
		quitbtnactive = new Texture("quitbtnactv.png"); //SAIR ATIVO
		quitbtninactive = new Texture("quitbtninactv.png"); // SAIR INATIVO
		//animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("menugif.gif").read()); //GIF da tela inicial
	}
	
	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void render(float dt) {
		//Gdx.gl.glClearColor(0, 0, 0, 1); //COR DA TELA = PRETO
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		BioPirataria.batch.begin();
		stateTime += dt;
		//BioPirataria.batch.draw(animation.getKeyFrame(stateTime),-30,-40);
		
		BioPirataria.batch.draw(title, 110, 345); //POSIÇÃO DA IMAGEM TITULO na TELA DO JOGO
		
		int x = BioPirataria.V_WIDTH/2 - CREDITS_BUTTOM_WIDTH - 35; //posição x para tornar as imagens selecionaveis
		
		//se o botão JOGAR for ativado, passará para tela PlayScreen
		
		if(Gdx.input.getX() < x + PLAY_BUTTOM_WIDTH && Gdx.input.getX() > x &&
				 BioPirataria.V_HEIGHT - Gdx.input.getY() < 340 + PLAY_BUTTOM_HEIGHT && BioPirataria.V_HEIGHT - Gdx.input.getY() > 325) {
		BioPirataria.batch.draw(playbtnactive, BioPirataria.V_WIDTH/2 - QUIT_BUTTOM_WIDTH - 50, 210 , PLAY_BUTTOM_WIDTH, PLAY_BUTTOM_HEIGHT);
		if(Gdx.input.isTouched()) {
			BioPirataria.manager.get("Songs/Mercury.wav", Sound.class).pause();
			game.setScreen(new PlayScreen(null,game));
		}
		}else {
			BioPirataria.batch.draw(playbtninactive, BioPirataria.V_WIDTH/2 - QUIT_BUTTOM_WIDTH - 50, 210, PLAY_BUTTOM_WIDTH, PLAY_BUTTOM_HEIGHT);
		}
		
		//Se a tela de créditos for ativada , passará para CreditsScreen
		
		 if(Gdx.input.getX() < x + CREDITS_BUTTOM_WIDTH && Gdx.input.getX() > x &&
				 BioPirataria.V_HEIGHT - Gdx.input.getY() < 288 + CREDITS_BUTTOM_HEIGHT && BioPirataria.V_HEIGHT - Gdx.input.getY() > 280) {
			BioPirataria.batch.draw(creditsbtnactive, x, 160, CREDITS_BUTTOM_WIDTH, CREDITS_BUTTOM_HEIGHT);
			if(Gdx.input.isTouched()) {
			game.setScreen(new Credits(game));
			}
		}else {
			BioPirataria.batch.draw(creditsbtninactive, BioPirataria.V_WIDTH/2 - CREDITS_BUTTOM_WIDTH - 35, 160, CREDITS_BUTTOM_WIDTH, CREDITS_BUTTOM_HEIGHT);
		}
		
		//sair
		if(Gdx.input.getX() < x + QUIT_BUTTOM_WIDTH && Gdx.input.getX() > x &&
				 BioPirataria.V_HEIGHT - Gdx.input.getY() < 230 + QUIT_BUTTOM_HEIGHT && BioPirataria.V_HEIGHT - Gdx.input.getY() > 227) {
			BioPirataria.batch.draw(quitbtnactive, BioPirataria.V_WIDTH/2 - QUIT_BUTTOM_WIDTH - 50, 110, QUIT_BUTTOM_WIDTH, QUIT_BUTTOM_HEIGHT);
			if(Gdx.input.isTouched()) {
				Gdx.app.exit();
			}
		}else {
			BioPirataria.batch.draw(quitbtninactive, BioPirataria.V_WIDTH/2 - QUIT_BUTTOM_WIDTH - 50, 110, QUIT_BUTTOM_WIDTH, QUIT_BUTTOM_HEIGHT);
		}
	
		BioPirataria.batch.end();
	}

	@Override
	public void resize(int width, int heigth) {}

	@Override
	public void resume() {}

	@Override
	public void show() {}
	
	@Override
	public void dispose() {}

}
