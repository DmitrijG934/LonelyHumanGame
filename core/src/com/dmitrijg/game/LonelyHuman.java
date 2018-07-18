package com.dmitrijg.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dmitrijg.game.screens.MenuScreen;

public class LonelyHuman extends Game {
	public SpriteBatch batch;

	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short ITEM_BIT = 4;
	public static final short DISAPPEAR_ITEM_BIT = 8;

	// create asset manager
	public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MenuScreen(this));
		manager = new AssetManager();

		// load resources
		manager.load("ogg/music/game_theme.ogg", Music.class);
		manager.load("ogg/sounds/get_item.ogg", Sound.class);
		manager.finishLoading();
	}

	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		System.out.println("game disposed");
		super.dispose();
		batch.dispose();
		manager.dispose();
	}
}
