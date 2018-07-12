package com.dmitrijg.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dmitrijg.game.LonelyHuman;

public class PlayScreen implements Screen {

    private LonelyHuman game;

    // create cameras and viewports
    private OrthographicCamera gamecam;
    private Viewport gameport;


    public PlayScreen(LonelyHuman lonelyHuman) {
        this.game = lonelyHuman;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(LonelyHuman.V_WIDTH, LonelyHuman.V_HEIGHT, gamecam);

        gamecam.position.set(new Vector2(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2), 0);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
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

    @Override
    public void dispose() {

    }
}
