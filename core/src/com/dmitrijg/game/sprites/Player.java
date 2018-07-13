package com.dmitrijg.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import static com.dmitrijg.game.LonelyHuman.PPM;

public class Player extends Sprite {
    private World world;
    public Body body;

    public Player(World world) {
        this.world = world;
        definePlayer();
    }

    private void definePlayer() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        PolygonShape ph = new PolygonShape();
        ph.setAsBox(10 / PPM, 10 / PPM);

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(32 / PPM,32 / PPM);
        body = world.createBody(bdef);

        fdef.shape = ph;
        body.createFixture(fdef);

    }

    public void handleInput() {
        float velX = 0, velY = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velY = 1.0f;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velX = -1.0f;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velY = -1.0f;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velX = 1.0f;
        }
        body.setLinearVelocity(velX, velY);

    }
}
