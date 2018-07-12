package com.dmitrijg.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dmitrijg.game.screens.PlayScreen;

public class LonelyHuman extends Game {
	public SpriteBatch batch;

	public static final int V_WIDTH = 600;
	public static final int V_HEIGHT = 340;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	public void render() {
		super.render();
	}

}
