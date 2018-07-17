package com.dmitrijg.game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.dmitrijg.game.sprites.InteractiveObject;

public class CustomContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture f = contact.getFixtureA();
        Fixture f1 = contact.getFixtureB();
        if((f.getUserData().equals("player") || f1.getUserData().equals("player"))){

            Fixture player = f.getUserData().equals("player") ? f : f1;
            Fixture object = player == f ? f1 : f;

            if(object.getUserData() != null && InteractiveObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveObject) object.getUserData()).onCollision();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
