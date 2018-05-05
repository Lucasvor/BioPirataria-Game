package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BioPirataria extends Game {
	public static SpriteBatch batch;
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 600;
	public static final float PPM =100;

	@Override
	public void create () {
        batch = new SpriteBatch();
        //player = new Sprite(new Texture("Human.jpg"));
        //setScreen(new PlayScreen(this));
        setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}
