package com.dmitrijg.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dmitrijg.game.LonelyHuman;
import com.dmitrijg.game.sprites.Player;
import com.dmitrijg.game.tools.Box2DCreator;
import handlers.Hud;
import static com.dmitrijg.game.LonelyHuman.PPM;

public class PlayScreen implements Screen {

    private LonelyHuman game;

    // create cameras and viewports
    private OrthographicCamera gamecam;
    private Viewport gameport;

    // load tiled map
    private TiledMap map;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private TmxMapLoader mapLoader;

    // load box2d vars
    private World world;
    private Box2DDebugRenderer b2dr;

    // map properties
    private int mapWidth;
    private int mapHeight;
    private int tilePixelWidth;
    private int tilePixelHeight;

    // Hud
    private Hud hudCam;

    // Create player
    private Player player;

    public PlayScreen(LonelyHuman lonelyHuman) {
        this.game = lonelyHuman;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(LonelyHuman.V_WIDTH / PPM, LonelyHuman.V_HEIGHT / PPM, gamecam);

        // set tiled map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level2.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1f/ PPM);

        // Hud cam
        hudCam = new Hud(game.batch);

        // make box2d world
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

        new Box2DCreator(world, map);

        MapProperties prop = map.getProperties();
        mapWidth = prop.get("width", Integer.class);
        mapHeight = prop.get("height", Integer.class);
        tilePixelWidth = prop.get("tilewidth", Integer.class);
        tilePixelHeight = prop.get("tileheight", Integer.class);

        // player
        player = new Player(world);

        gamecam.position.set(new Vector2(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2), 0);

    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        handleInput();

        hudCam.update();

        player.handleInput();
        gamecam.update();

        //gamecam.position.x = player.body.getPosition().x;
        //gamecam.position.y = player.body.getPosition().y;
        if(player.body.getPosition().x > gameport.getWorldWidth() / 2) {
            gamecam.position.x = player.body.getPosition().x;
            if(gamecam.position.x > 7.8f) {
                gamecam.position.x = 7.8f;
            }
        }
        if(player.body.getPosition().y > gameport.getWorldHeight() / 2) {
            gamecam.position.y = player.body.getPosition().y;
            if(gamecam.position.y > 9.0f) {
                gamecam.position.y = 9.0f;
            }
        }

        world.step(1f/60f, 6, 2);

        tiledMapRenderer.setView(gamecam);
    }

    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            game.dispose();
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.render();
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(hudCam.stage.getCamera().combined);
        hudCam.stage.draw();


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
        System.out.println("PlayState dispose");
        map.dispose();
        tiledMapRenderer.dispose();
        b2dr.dispose();
    }
}
