package com.dmitrijg.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dmitrijg.game.LonelyHuman;

public class MenuScreen implements Screen {

    private Label.LabelStyle activeStyle;
    private Label.LabelStyle inactiveStyle;

    private Viewport viewport;
    private Viewport camView;
    private Stage stage;
    private Stage camStage;

    private int mapPixelHeight;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TmxMapLoader mapLoader;

    private OrthographicCamera menuCam;


    private LonelyHuman game;

    private Label startLabel;
    private Label exitLabel;
    private Label gameLabel;

    private String activeLabel = "start";

    public MenuScreen(LonelyHuman game) {
        this.game = game;

        menuCam = new OrthographicCamera();
        // set viewports and cameras
        viewport = new FitViewport(LonelyHuman.V_WIDTH, LonelyHuman.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        camView = new FitViewport(LonelyHuman.V_WIDTH, LonelyHuman.V_HEIGHT, menuCam);
        camStage = new Stage(camView, game.batch);

        // labels
        activeStyle = new Label.LabelStyle(new BitmapFont(), Color.RED);
        inactiveStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        startLabel = new Label("START", activeStyle);
        exitLabel = new Label("EXIT", inactiveStyle);

        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        style.font.getData().scale(2.5f);

        gameLabel = new Label("LONELY HUMAN", style);

        // setting table
        Table tableCenter = new Table();
        tableCenter.setFillParent(true);

        tableCenter.top();
        tableCenter.add(gameLabel).align(Align.center).expandX();

        tableCenter.row();

        tableCenter.center();

        tableCenter.add(startLabel).expandX().padTop(50);
        tableCenter.row();
        tableCenter.add(exitLabel).expandX().padTop(20);

        stage.addActor(tableCenter);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("menu_map.tmx");

        mapRenderer = new OrthogonalTiledMapRenderer(map);
        menuCam.position.set(new Vector2(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2), 0);

        MapProperties prop = map.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        int mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        handleInput();
        if(menuCam.position.y > (mapPixelHeight - menuCam.viewportHeight / 2)) {
            menuCam.position.y = mapPixelHeight / 2;
        } else {
            menuCam.position.y += 50 * delta;
        }
        menuCam.update();

    }

    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            exitLabel.setStyle(activeStyle);
            startLabel.setStyle(inactiveStyle);
            activeLabel = "exit";
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            exitLabel.setStyle(inactiveStyle);
            startLabel.setStyle(activeStyle);
            activeLabel = "start";

        }
        if(activeLabel.equals("start") && Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            dispose();
            game.setScreen(new PlayScreen(game));
        }if(activeLabel.equals("exit") && Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            Gdx.app.exit();
        }

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(menuCam);
        mapRenderer.render();

        game.batch.setProjectionMatrix(camStage.getCamera().combined);
        camStage.draw();

        game.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        System.out.println("MenuState dispose");
        stage.dispose();
        map.dispose();
        mapRenderer.dispose();
    }
}
