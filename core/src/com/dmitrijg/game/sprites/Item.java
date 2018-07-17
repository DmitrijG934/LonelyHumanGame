package com.dmitrijg.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.dmitrijg.game.LonelyHuman;
import handlers.Hud;


public class Item extends InteractiveObject {
    public Item(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(LonelyHuman.ITEM_BIT);
    }

    @Override
    public void onCollision() {
        setCategoryFilter(LonelyHuman.DISAPPEAR_ITEM_BIT);
        Hud.scoreCount++;
        Hud.amountItems--;
        removeCell();
    }
}
