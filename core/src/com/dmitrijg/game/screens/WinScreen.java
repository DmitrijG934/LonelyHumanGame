package com.dmitrijg.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dmitrijg.game.LonelyHuman;

public class WinScreen implements Disposable, Screen {

    private LonelyHuman game;

    private Stage stage;
    private Viewport gameport;

    private Sound music;

    private Label winGameLabel;
    private Label playAgain;
    private Label exitLabel;

    private Label.LabelStyle activeStyle;
    private Label.LabelStyle inactiveStyle;

    private String status = "play_again";

    public WinScreen(SpriteBatch batch, LonelyHuman game) {
        this.game = game;

        GameOverScreen.sound.stop();
        music = LonelyHuman.manager.get("ogg/sounds/win_sound.ogg", Sound.class);

        gameport = new FitViewport(LonelyHuman.V_WIDTH, LonelyHuman.V_HEIGHT);
        stage = new Stage(gameport, batch);

        Table table = new Table();
        table.center();

        table.setFillParent(true);

        Label.LabelStyle winLabelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        inactiveStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        activeStyle = new Label.LabelStyle(new BitmapFont(), Color.RED);

        inactiveStyle.font.getData().setScale(1.5f);
        activeStyle.font.getData().setScale(1.5f);
        winLabelStyle.font.getData().setScale(2.5f);
        winGameLabel = new Label("YOU WIN!", winLabelStyle);

        playAgain = new Label("Play again", activeStyle);
        exitLabel = new Label("Exit", inactiveStyle);

        table.add(winGameLabel).expandX().align(Align.center);

        table.row();
        table.add(playAgain).expandX().align(Align.center).padTop(20);
        table.row();
        table.add(exitLabel).expandX().align(Align.center).padTop(10);

        stage.addActor(table);
        music.play(.1f);

    }

    public void update(float delta) {
        handleInput();
    }

    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            status = "exit";
            playAgain.setStyle(inactiveStyle);
            exitLabel.setStyle(activeStyle);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            status = "play_again";
            playAgain.setStyle(activeStyle);
            exitLabel.setStyle(inactiveStyle);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && status.equals("play_again")) {
            game.setScreen(new ChooseDifficultyMenu(game));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && status.equals("exit")) {
            Gdx.app.exit();
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
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
        music.dispose();
        stage.dispose();
    }
}
