package com.dmitrijg.game.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.dmitrijg.game.LonelyHuman;
import handlers.Hud;


public class Item extends InteractiveObject {

    private static TiledMapTileSet tileSet;
    private static final int ID = 169;

    private Sound sound;

    public Item(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        tileSet = map.getTileSets().getTileSet("tiles2");
        fixture.setUserData(this);
        setCategoryFilter(LonelyHuman.ITEM_BIT);
        getCell().setTile(tileSet.getTile(ID));

        sound = LonelyHuman.manager.get("ogg/sounds/get_item.ogg");

    }

    @Override
    public void onCollision() {
        setCategoryFilter(LonelyHuman.DISAPPEAR_ITEM_BIT);
        Hud.scoreCount++;
        sound.play();
        Hud.amountItems--;
        removeCell();
    }

    @Override
    public void dispose() {
        sound.dispose();
        super.dispose();
    }
}
