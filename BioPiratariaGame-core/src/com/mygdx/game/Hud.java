package com.mygdx.game;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Tools.FloatingText;

public class Hud implements Disposable{

public static Stage stage;
private Viewport viewport;

//variaveis da HUD (tabela de pontos , vida etc.)//

private Integer worldTimer;
private static float timeCount;
private static Integer pontos;
private static Integer vida; 
private Integer vidaInimigo;
private static Game game;
private boolean timeUp;

static Label timeCountLabel; // contagem Jogo
static Label pontosLabel; // pontos Heroi = score
static Label  vidaLabel; // vida Heroi
static Label vidaInimigoLabel; // vida Inimigo
Label timeCountTextLabel; // contagem Jogo
Label pontosTextLabel; // pontos Heroi = score
Label vidatextLabel; // vida Heroi
Label vidaInimigoTextLabel; // vida Inimigo
static FloatingText floatingText;

public static float getTimeCount() {
	return timeCount;
}

public Hud(SpriteBatch sb, Game gm) {
	this.game = gm;
	
	//contadores da HUD//

	worldTimer = 1;

	timeCount = 0;
	pontos = 0;
	vida = 100;
	vidaInimigo = 1000;
	
	
	//configurações de tamanho , cor do contador e da fonte da HUD //
	
	
	viewport = new FitViewport(BioPirataria.V_WIDTH,BioPirataria.V_HEIGHT, new OrthographicCamera());
	stage = new Stage(viewport,sb);
	
	Table table = new Table();
	table.top();
	table.setFillParent(true);
	
    timeCountLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(),Color.WHITE)); //numeros do tempo da HUD na cor branca //
    pontosLabel = new Label(String.format("%05d",pontos), new Label.LabelStyle(new BitmapFont(),Color.WHITE));//numeros de pontos da HUD na cor branca //
    vidaLabel = new Label(String.format("%02d",vida), new Label.LabelStyle(new BitmapFont(),Color.WHITE));//numeros da vida do heroi HUD na cor branca //
    vidaInimigoLabel = new Label(String.format("%02d",vidaInimigo), new Label.LabelStyle(new BitmapFont(),Color.WHITE));//numeros da vida do inimigo  HUD na cor branca //
    pontosTextLabel = new Label(String.format("Pontos",pontosTextLabel), new Label.LabelStyle(new BitmapFont(),Color.BLUE)); //palavra (pontos) na cor azul//
    timeCountTextLabel = new Label(String.format("Tempo",timeCountTextLabel), new Label.LabelStyle(new BitmapFont(),Color.GREEN));//palavra (tempo) na cor verde//
    vidatextLabel = new Label(String.format("Vida",vidatextLabel), new Label.LabelStyle(new BitmapFont(),Color.RED));//palavra (vida) na cor vermelha//
    vidaInimigoTextLabel = new Label(String.format("Vida Inimigo",vidatextLabel), new Label.LabelStyle(new BitmapFont(),Color.YELLOW));//palavra (inimigo) na cor amarela//
    
    

    // tamanhos da fonte //
    
    table.add(vidatextLabel).expandX().padTop(20);
    table.add(pontosTextLabel).expandX().padTop(20);
    table.add(timeCountTextLabel).expandX().padTop(20);
    table.add(vidaInimigoTextLabel).expandX().padTop(20);
    table.row();
    table.add(vidaLabel).expandX();
    table.add(pontosLabel).expandX();
    table.add(timeCountLabel).expandX();
    table.add(vidaInimigoLabel).expandX();
    
//    floatingText = new FloatingText("SALVE OS ANIMAIS DA FAZENDA!!!!", TimeUnit.SECONDS.toMillis(150));
//    floatingText.setPosition(280, 250);
//    floatingText.setDeltaY(100);
     
    //stage.addActor(floatingText);
	
	stage.addActor(table);
}
public void update(float dt){
    timeCount += dt;
    if(timeCount >= 1){
        if (worldTimer > 0) {
            worldTimer++;
        } else {
            timeUp = true;
        }
        timeCountLabel.setText(String.format("%03d", worldTimer));
        timeCount = 0;
    }  
    if(worldTimer == 0) {
    	Gdx.app.log("Seu tempo acabou, gameover!", "");
    	game.setScreen(new GameOverScreen(game));
    }
} 
public static void setvidaHeroi(int vidaHeroi) {
	vidaLabel.setText(String.format("%02d", vidaHeroi));
}
public static void setvidaInimigo(int vida) {
	vidaInimigoLabel.setText(String.format("%02d",vida));
}
public static void lostLife(int life) {
	vida -= life;
	vidaLabel.setText(String.format("%02d",vida));
	if(getVidaHeroi() <= 0) {
		Gdx.app.log("Acabaram suas vidas, gameover!", "");
		game.setScreen(new GameOverScreen(game));
	}
}
public static int getVidaHeroi() {
	return vida;
}
public static void addPontos(int ponto) {
	pontos += ponto;
	pontosLabel.setText(String.format("%02d",pontos));
}

public static void hudanimtext(String text,float x,float y) {
	floatingText = new FloatingText(text, TimeUnit.SECONDS.toMillis(150));
    floatingText.setPosition(x,y);
    floatingText.setDeltaY(100);
    stage.addActor(floatingText);
	if(!floatingText.isAnimated())
		floatingText.animate();
	
}
public static Integer getLife() {
	return vida;
}
@Override
public void dispose() {
	
	stage.dispose();
}
public boolean isTimeUp() { 
	return timeUp; 
	}

}
