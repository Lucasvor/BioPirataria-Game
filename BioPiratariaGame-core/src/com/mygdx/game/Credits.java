package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Credits implements Screen {
	private Viewport viewport;
	private Stage stage;
	private Game game;
	
	// cria testura com nome dos participantes do grupo e os dados do curso
	private Texture unip;
	private Texture semestres;
	private Texture annyg;
	private Texture christianf;
	private Texture karinal;
	private Texture loand;
	private Texture lucasr;
	private Texture luize;
	private Texture phelipet;
	
	
	// 
	public Credits(Game game) {
		this.game = game;
		viewport = new FitViewport(BioPirataria.V_WIDTH, BioPirataria.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, ((BioPirataria)game).batch);
		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

		
		//adiciona a imagem a textura 
		
		unip = new Texture("creditsImages/unip.png");
		semestres = new Texture("creditsImages/semestres.png");
		annyg = new Texture("creditsImages/annyg.png");
		christianf = new Texture("creditsImages/christianf.png");
		karinal = new Texture("creditsImages/karinal.png");
		loand = new Texture("creditsImages/loan.png");
		lucasr = new Texture("creditsImages/lucasr.png");
		luize = new Texture("creditsImages/luize.png");
		phelipet = new Texture("creditsImages/phelipet.png");
		
		// retorna para tela inicial
		Table table = new Table();
		table.center();
		table.setFillParent(true);
		Label clickToReturn = new Label("Click para Retornar ao Menu ", font);
		table.add(clickToReturn).expandX().padTop(500f);
		table.row();
		stage.addActor(table);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void render(float dt) {
		if (Gdx.input.justTouched()) { 
			game.setScreen(new MenuScreenRetorno((BioPirataria)game));
			dispose();
		}
	
		
		//posição de cada textura da tela
		
		Gdx.gl.glClearColor(0, 0, 0, 1); //COR DA TELA = PRETO
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		BioPirataria.batch.begin();
		BioPirataria.batch.draw(unip, 310, 450);
		BioPirataria.batch.draw(semestres, 202, 400);
		BioPirataria.batch.draw(annyg, 230, 330);
		BioPirataria.batch.draw(christianf, 230, 290);
		BioPirataria.batch.draw(karinal, 230, 250);
		BioPirataria.batch.draw(loand, 230, 210);
		BioPirataria.batch.draw(lucasr, 230, 170);
		BioPirataria.batch.draw(luize, 230, 130);
		BioPirataria.batch.draw(phelipet, 230, 90);
		BioPirataria.batch.end();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void resume() {}

	@Override
	public void show() {}
	
}
