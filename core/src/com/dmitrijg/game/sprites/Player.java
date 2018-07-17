package com.dmitrijg.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.dmitrijg.game.LonelyHuman;
import com.dmitrijg.game.screens.PlayScreen;

import static com.dmitrijg.game.LonelyHuman.PPM;

public class Player extends Sprite {
    private World world;
    public Body body;

    private TiledMap map;

    private static final int PIXEL_WIDTH = 16;
    private static final int PIXEL_HEIGHT = 32;

    private TextureRegion[] up;
    private TextureRegion[] down;

    private TextureRegion[] left;
    private TextureRegion[] right;

    private TextureRegion[] standing;
    private TextureRegion[][] movements;

    public TextureRegion currentState;

    private boolean isGo = false;
    private boolean goUp = false;
    private boolean goDown = false;
    private boolean goLeft = false;
    private boolean goRight = false;

    private String lastMove = "standing";

    private Animation animation;

    private float stateTime;

    public Player(World world) {
        this.world = world;
        map = new PlayScreen().getMap();
        definePlayer();

        movements = new TextureRegion[5][2];

        up = new TextureRegion[2];
        down = new TextureRegion[2];

        left = new TextureRegion[2];
        right = new TextureRegion[2];

        standing = new TextureRegion[4];

        TextureRegion[][] animationMovements = TextureRegion.split(new Texture("sprites/sprite.png"),
                PIXEL_WIDTH, PIXEL_HEIGHT);

        setBounds(0, 0, PIXEL_WIDTH / PPM, PIXEL_HEIGHT / PPM);

        standing[0] = animationMovements[2][1];
        standing[1] = animationMovements[0][0];
        standing[2] = animationMovements[4][0];
        standing[3] = animationMovements[5][1];
        // previous state
        setRegion(standing[0]);

        animation = new Animation(0.1f, standing[0]);

        up[0] = animationMovements[1][1];
        up[1] = animationMovements[2][0];

        down[0] = animationMovements[0][1];
        down[1] = animationMovements[1][0];

        left[0] = animationMovements[4][1];
        left[1] = animationMovements[5][0];

        right[0] = animationMovements[3][0];
        right[1] = animationMovements[3][1];

        movements[0] = up;
        movements[1] = down;
        movements[2] = left;
        movements[3] = right;


    }

    private void updateAnimation(int index) {
        animation = new Animation(0.1f, movements[index]);
    }

    private void updateAnimation(TextureRegion region) {
        animation = new Animation(0.1f, region);
    }

    // put sprite in box2d
    public void update(float delta) {
        stateTime += delta;
        currentState = (TextureRegion) animation.getKeyFrame(stateTime, true);
        setRegion(currentState);
        setPosition(body.getPosition().x - getWidth() / 2, (body.getPosition().y + getHeight()) - getHeight());
    }


    private void definePlayer() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        PolygonShape ph = new PolygonShape();
        ph.setAsBox(5 / PPM, 5 / PPM);

        for (MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bdef);

            ph.setAsBox((rect.getWidth() / 2) / PPM, (rect.getHeight() / 2) / PPM);

            fdef.shape = ph;
            fdef.filter.categoryBits = LonelyHuman.PLAYER_BIT;
            fdef.filter.maskBits = LonelyHuman.DEFAULT_BIT | LonelyHuman.ITEM_BIT;
            body.createFixture(fdef).setUserData("player");

        }

    }

    public void handleInput(float delta) {
        float velX = 0, velY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velY = 1.0f;
            isGo = true;
            goUp = true;
            lastMove = "up";
            updateAnimation(0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velX = -1.0f;
            isGo = true;
            goLeft = true;
            lastMove = "left";
            updateAnimation(2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velY = -1.0f;
            isGo = true;
            goDown = true;
            lastMove = "down";
            updateAnimation(1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velX = 1.0f;
            isGo = true;
            goRight = true;
            lastMove = "right";
            updateAnimation(3);
        } else {
            isGo = false;
            if(lastMove.equals("right")) updateAnimation(standing[0]);
            if(lastMove.equals("down")) updateAnimation(standing[1]);

            if(lastMove.equals("up")) updateAnimation(standing[3]);

            if(lastMove.equals("left")) updateAnimation(standing[2]);
        }
        body.setLinearVelocity(velX, velY);

    }

    public void dispose() {
        map.dispose();
    }
}
