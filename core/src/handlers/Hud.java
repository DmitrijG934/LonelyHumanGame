package handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dmitrijg.game.LonelyHuman;

public class Hud implements Disposable {
    private Viewport viewport;
    public Stage stage;

    private Label fpsManager;

    public Hud(SpriteBatch batch) {
        viewport = new FitViewport(LonelyHuman.V_WIDTH, LonelyHuman.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, batch);

        fpsManager = new Label(String.format("FPS: %d", Gdx.graphics.getFramesPerSecond()), new Label.LabelStyle(
                new BitmapFont(), Color.WHITE
        ));

        Table table = new Table();
        table.top();

        table.setFillParent(true);

        table.add(fpsManager).expandX().align(Align.left);

        stage.addActor(table);

    }

    public void update() {
        fpsManager.setText(String.format("FPS: %d", Gdx.graphics.getFramesPerSecond()));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
