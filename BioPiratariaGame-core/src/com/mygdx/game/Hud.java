package com.mygdx.game;

import com.badlogic.gdx.Game;
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

public class Hud implements Disposable{

public Stage stage;
private Viewport viewport;

//variaveis da HUD (tabela de pontos , vida etc.)//
private Integer worldTimer;
private float timeCount;
private static Integer pontos;
private static Integer vida;
private Integer vidaInimigo;
private Game game;
private boolean timeUp;

Label timeCountLabel;
static Label pontosLabel;
static Label  vidaLabel;
static Label vidaInimigoLabel;
Label pontosTextLabel;
Label timeCountTextLabel;
Label vidatextLabel;
Label vidaInimigoTextLabel;

public Hud(SpriteBatch sb) {
	
	//contadores da HUD//
	worldTimer = 90;
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
    timeCountLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
    pontosLabel = new Label(String.format("%05d",pontos), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
    vidaLabel = new Label(String.format("%02d",vida), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
    vidaInimigoLabel = new Label(String.format("%02d",vidaInimigo), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
    pontosTextLabel = new Label(String.format("Pontos",pontosTextLabel), new Label.LabelStyle(new BitmapFont(),Color.BLUE));
    timeCountTextLabel = new Label(String.format("Tempo",timeCountTextLabel), new Label.LabelStyle(new BitmapFont(),Color.GREEN));
    vidatextLabel = new Label(String.format("Vida",vidatextLabel), new Label.LabelStyle(new BitmapFont(),Color.RED));
    vidaInimigoTextLabel = new Label(String.format("Vida Inimigo",vidatextLabel), new Label.LabelStyle(new BitmapFont(),Color.YELLOW));
    
    

    table.add(vidatextLabel).expandX().padTop(10);
    table.add(pontosTextLabel).expandX().padTop(10);
    table.add(timeCountTextLabel).expandX().padTop(10);
    table.add(vidaInimigoTextLabel).expandX().padTop(10);
    table.row();
    table.add(vidaLabel).expandX();
    table.add(pontosLabel).expandX();
    table.add(timeCountLabel).expandX();
    table.add(vidaInimigoLabel).expandX();
//
//			countdownLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
//			scoreLabel = new Label(String.format("%05d",score), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
//			TimeLabel = new Label("TIMER", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
//			LevelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
//			worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
//			marioLabel = new Label("Mario", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
//	table.add(marioLabel).expandX().padTop(10);
//	table.add(worldLabel).expandX().padTop(10);
//	table.add(TimeLabel).expandX().padTop(10);
//	table.row();
//	table.add(scoreLabel).expandX();
//	table.add(LevelLabel).expandX();
//	table.add(countdownLabel).expandX();
	
	stage.addActor(table);
}
public void update(float dt){
    timeCount += dt;
    if(timeCount >= 1){
        if (worldTimer > 0) {
            worldTimer--;
        } else {
            timeUp = true;
        }
        timeCountLabel.setText(String.format("%03d", worldTimer));
        timeCount = 0;
    }
}

public static void setvidaInimigo(int vida) {
	vidaInimigoLabel.setText(String.format("%02d",vida));
}
public static void lostLife(int life) {
	vida -= life;
	vidaLabel.setText(String.format("%02d",vida));
}
public static void addPontos(int ponto) {
	pontos += ponto;
	pontosLabel.setText(String.format("%02d",pontos));
}

public static Integer getLife() {
	return vida;
}
@Override
public void dispose() {
	// TODO Auto-generated method stub
	stage.dispose();
}
public boolean isTimeUp() { 
	return timeUp; 
	}

}
