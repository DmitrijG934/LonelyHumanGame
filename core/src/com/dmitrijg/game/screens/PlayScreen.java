package com.dmitrijg.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.dmitrijg.game.tools.CustomContactListener;
import handlers.Hud;
import static com.dmitrijg.game.LonelyHuman.PPM;
import static com.dmitrijg.game.LonelyHuman.manager;

public class PlayScreen implements Screen {

    private LonelyHuman game;

    // create cameras and viewports
    private OrthographicCamera gamecam;
    private Viewport gameport;

    public PlayScreen() {

    }

    public TiledMap getMap() {
        return map;
    }

    // load tiled map
    private static TiledMap map;
    private static OrthogonalTiledMapRenderer tiledMapRenderer;
    private static TmxMapLoader mapLoader;


    // load box2d vars
    private World world;
    private Box2DDebugRenderer b2dr;

    // layers
    private int[] backgroundLayers;
    private int[] foregroundLayers;

    // Hud
    private Hud hudCam;

    // Create player
    private Player player;

    // create music
    public static Music music;

    static {
        // set tiled map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/level.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1f/ PPM);

    }


    public PlayScreen(LonelyHuman lonelyHuman) {
        this.game = lonelyHuman;

        gamecam = new OrthographicCamera();
        gameport = new FitViewport(LonelyHuman.V_WIDTH / PPM, LonelyHuman.V_HEIGHT / PPM, gamecam);

        // Hud cam
        hudCam = new Hud(game.batch, game);

        // layers
        backgroundLayers = new int[]{ 0, 6,7, 8, 9, 11 }; // не выделяйте память при каждом кадре!
        foregroundLayers = new int[]{ 10, 12 };    // не выделяйте память при каждом кадре!

        // make box2d world
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

        Box2DCreator box2DCreator = new Box2DCreator(world, map);
        Hud.amountItems = box2DCreator.items;

        // player
        player = new Player(world);

        gamecam.position.set(new Vector2(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2), 0);

        world.setContactListener(new CustomContactListener());

        music = manager.get("ogg/music/game_theme.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(.1f);
        music.play();

    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        // update input
        handleInput();

        // update hud cam
        hudCam.update(delta);

        // update player input
        player.handleInput(delta);
        gamecam.update();

        // define player borders
        if(player.body.getPosition().x > gameport.getWorldWidth() / 2) {

            // fix bug with black lines between tiles
            gamecam.position.x =  (float) Math.round(player.body.getPosition().x * 100f) / 100f;
            //gamecam.position.x = player.body.getPosition().x;
            if(gamecam.position.x > 7.8f)
                gamecam.position.x = 7.8f;
        }
        if(player.body.getPosition().y > gameport.getWorldHeight() / 2) {
            gamecam.position.y = player.body.getPosition().y;
            if(gamecam.position.y > 9.2f) gamecam.position.y = 9.2f;
        }

        world.step(1f/60f, 6, 2);
        // set view to gamecam (tiled map)
        tiledMapRenderer.setView(gamecam);
    }

    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            game.setScreen(new MenuScreen(game));
            Hud.scoreCount = 0;
            music.stop();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        player.update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render map
        //tiledMapRenderer.render();

        tiledMapRenderer.render(backgroundLayers);

        // render box2d world
        //b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
        tiledMapRenderer.render(foregroundLayers);

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
        System.out.println("playstate disposed");
        map.dispose();
        player.dispose();
        tiledMapRenderer.dispose();
        b2dr.dispose();
        music.dispose();
    }
}
