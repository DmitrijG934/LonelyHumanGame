package com.dmitrijg.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.dmitrijg.game.screens.PlayScreen;

import static com.dmitrijg.game.LonelyHuman.PPM;

public class Player extends Sprite {
    private World world;
    public Body body;

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

    private Animation animation;

    private float stateTime;

    public Player(World world) {
        this.world = world;
        definePlayer();

        movements = new TextureRegion[5][2];

        up = new TextureRegion[2];
        down = new TextureRegion[2];

        left = new TextureRegion[2];
        right = new TextureRegion[2];

        standing = new TextureRegion[1];

        TextureRegion[][] animationMovements = TextureRegion.split(new Texture("sprites/player-sprite.png"),
                PIXEL_WIDTH, PIXEL_HEIGHT);

        setBounds(0, 0, PIXEL_WIDTH / PPM, PIXEL_HEIGHT / PPM);
        standing[0] = animationMovements[2][1];
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
        movements[4] = standing;

    }

    public void updateAnimation(int index) {
        animation = new Animation(0.1f, movements[index]);
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

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(32 / PPM,32 / PPM);
        body = world.createBody(bdef);

        fdef.shape = ph;
        body.createFixture(fdef);

    }

    public void handleInput(float delta) {
        float velX = 0, velY = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velY = 1.0f;
            isGo = true;
            updateAnimation(0);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velX = -1.0f;
            isGo = true;
            updateAnimation(2);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velY = -1.0f;
            isGo = true;
            updateAnimation(1);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velX = 1.0f;
            isGo = true;
            updateAnimation(3);
        }

        System.out.println(isGo);

        body.setLinearVelocity(velX, velY);

    }
}
