package com.dmitrijg.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dmitrijg.game.LonelyHuman;
import com.dmitrijg.game.sprites.Player;
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

        // BodyDef
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / PPM, (rect.getHeight() / 2) / PPM);

            fdef.shape = shape;
            body.createFixture(fdef);

        }

        // player
        player = new Player(world);

        gamecam.position.set(new Vector2(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2), 0);

    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        handleInput();
        player.handleInput();
        gamecam.update();

        gamecam.position.x = player.body.getPosition().x;
        gamecam.position.y = player.body.getPosition().y;
        world.step(1f/60f, 6, 2);

        tiledMapRenderer.setView(gamecam);
    }

    private void handleInput() {

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

    }
}
