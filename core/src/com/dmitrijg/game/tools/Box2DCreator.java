package com.dmitrijg.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.dmitrijg.game.sprites.Player;

import static com.dmitrijg.game.LonelyHuman.PPM;

public class Box2DCreator {
    public Box2DCreator(World world, TiledMap map) {
        // BodyDef
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // obticles
        for (MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / PPM, (rect.getHeight() / 2) / PPM);

            fdef.shape = shape;
            body.createFixture(fdef);

        }

        // trees
        for (MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / PPM, (rect.getHeight() / 2) / PPM);

            fdef.shape = shape;
            body.createFixture(fdef);

        }

        // homes
        for (MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / PPM, (rect.getHeight() / 2) / PPM);

            fdef.shape = shape;
            body.createFixture(fdef);

        }

        // items
        for (MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / PPM, (rect.getHeight() / 2) / PPM);

            fdef.shape = shape;
            body.createFixture(fdef);

        }



    }
}
