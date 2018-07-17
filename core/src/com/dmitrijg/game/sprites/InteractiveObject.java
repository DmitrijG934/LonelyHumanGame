package com.dmitrijg.game.sprites;

import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

import static com.dmitrijg.game.LonelyHuman.PPM;


public abstract class InteractiveObject implements Disposable {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;

    protected Fixture fixture;

    public InteractiveObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / PPM, (bounds.getY() + bounds.getHeight() / 2) / PPM);

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / PPM, (bounds.getHeight() / 2) / PPM);

        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    public abstract void onCollision();

    public void setCategoryFilter(short category) {
        Filter filter = new Filter();
        filter.categoryBits = category;
        fixture.setFilterData(filter);
    }

    public void removeCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(9);
        TiledMapTileLayer.Cell cell = layer.getCell((int) (body.getPosition().x * PPM) / 32,
                (int) (body.getPosition().y * PPM) / 32);
        cell.setTile(null);
    }

    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(9);
        return layer.getCell((int) (body.getPosition().x * PPM) / 32,
                (int) (body.getPosition().y * PPM) / 32);
    }


    public void dispose() {
        world.dispose();
        world.dispose();
    }

}
