package com.dmitrijg.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dmitrijg.game.screens.MenuScreen;
import com.dmitrijg.game.screens.PlayScreen;

public class LonelyHuman extends Game {
	public SpriteBatch batch;

	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MenuScreen(this));
	}

	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		System.out.println("game disposed");
		super.dispose();
		batch.dispose();
	}
}
