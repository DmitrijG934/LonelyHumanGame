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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dmitrijg.game.LonelyHuman;
import handlers.Hud;

public class ChooseDifficultyMenu implements Screen {

    private LonelyHuman game;

    private Label.LabelStyle activeStyle;
    private Label.LabelStyle inactiveStyle;

    private long timer;

    private Viewport viewport;
    private Viewport camView;
    private Stage stage;
    private Stage camStage;

    private int mapPixelHeight;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TmxMapLoader mapLoader;

    private OrthographicCamera menuCam;

    private int counter = 0;
    private Label[] labels;

    private Label easyLabel;
    private Label normalLabel;
    private Label hardLabel;
    private Label insaneLabel;

    private String activeLabel = "easy";

    public ChooseDifficultyMenu(LonelyHuman game) {
        this.game = game;

        if(PlayScreen.music != null) PlayScreen.music.stop();

        labels = new Label[4];

        menuCam = new OrthographicCamera();
        // set viewports and cameras
        viewport = new FitViewport(LonelyHuman.V_WIDTH, LonelyHuman.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        camView = new FitViewport(LonelyHuman.V_WIDTH, LonelyHuman.V_HEIGHT, menuCam);
        camStage = new Stage(camView, game.batch);

        // labels
        activeStyle = new Label.LabelStyle(new BitmapFont(), Color.RED);
        inactiveStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        activeStyle.font.getData().setScale(1.5f);
        inactiveStyle.font.getData().setScale(1.5f);

        easyLabel = new Label("EASY", activeStyle);
        normalLabel = new Label("NORMAL", inactiveStyle);
        hardLabel = new Label("HARD", inactiveStyle);
        insaneLabel = new Label("INSANE", inactiveStyle);

        easyLabel.setName("easy");
        normalLabel.setName("normal");
        hardLabel.setName("hard");
        insaneLabel.setName("insane");

        labels[0] = easyLabel;
        labels[1] = normalLabel;
        labels[2] = hardLabel;
        labels[3] = insaneLabel;


        // setting table
        Table tableCenter = new Table();
        tableCenter.setFillParent(true);

        tableCenter.center();

        tableCenter.add(easyLabel).expandX().padTop(10f);
        tableCenter.row();
        tableCenter.add(normalLabel).expandX().padTop(10f);
        tableCenter.row();
        tableCenter.add(hardLabel).expandX().padTop(10f);
        tableCenter.row();
        tableCenter.add(insaneLabel).expandX().padTop(10f);

        stage.addActor(tableCenter);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("menu_map.tmx");

        mapRenderer = new OrthogonalTiledMapRenderer(map);
        menuCam.position.set(new Vector2(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2), 0);

        MapProperties prop = map.getProperties();

        int mapHeight = prop.get("height", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        mapPixelHeight = mapHeight * tilePixelHeight;

    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        if(menuCam.position.y > (mapPixelHeight - menuCam.viewportHeight / 2)) {
            menuCam.position.y = mapPixelHeight / 2;
        } else {
            menuCam.position.y += 50 * delta;
        }
        menuCam.update();
    }

    public void handleInput() {
       if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            try {
                counter++;
                labels[counter].setStyle(activeStyle);
                activeLabel = labels[counter].getName();
                labels[--counter].setStyle(inactiveStyle);
                counter++;
            } catch (ArrayIndexOutOfBoundsException ex) {
                counter = 3;
                System.out.println(labels[counter]);
            }
       }
       if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            try {
                counter--;
                labels[counter].setStyle(activeStyle);
                activeLabel = labels[counter].getName();
                labels[++counter].setStyle(inactiveStyle);
                counter--;
            } catch (ArrayIndexOutOfBoundsException ex) {
                counter = 0;
                System.out.println(labels[counter]);
            }
       }
       if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
           dispose();
           game.setScreen(new MenuScreen(game));
       }
       if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
           if (activeLabel.equals("easy")) {
               Hud.setPreviousTime(40);

           } else if (activeLabel.equals("normal")) {
               Hud.setPreviousTime(30);
           } else if (activeLabel.equals("hard")) {
               Hud.setPreviousTime(15);
           } else if (activeLabel.equals("insane")) {
               Hud.setPreviousTime(10);
           }
           game.setScreen(new PlayScreen(game));
       }

    }

    @Override
    public void render(float delta) {
        update(delta);
        handleInput();
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

    public long getTimer() {
        return timer;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        System.out.println("diff menu disposed");
        stage.dispose();
        map.dispose();
        mapRenderer.dispose();
        stage.dispose();
        camStage.dispose();
    }
}
