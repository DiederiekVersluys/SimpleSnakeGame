package com.didericusgames.snake.screen.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.didericusgames.snake.SimpleSnakeGame;
import com.didericusgames.snake.assets.AssetDescriptors;
import com.didericusgames.snake.config.GameConfig;
import com.didericusgames.snake.screen.game.GameScreen;
import com.didericusgames.snake.screen.menu.MenuScreen;
import com.didericusgames.snake.util.GdxUtils;

public class LoadingScreen extends ScreenAdapter {


    //constants

    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2;
    private static final float PROGRESS_BAR_HEIGHT = 50f;

    //attributes
    private final SimpleSnakeGame game;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;

    //constructor


    public LoadingScreen(SimpleSnakeGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        assetManager.load(AssetDescriptors.UI_FONT);
        assetManager.load(AssetDescriptors.GAME_PLAY);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.COIN_SOUND);
        assetManager.load(AssetDescriptors.GAME_MUSIC);
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        update(delta);
        draw();
        if (changeScreen) {
            game.setScreen(new MenuScreen(game));
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    @Override
    public void hide() {
        dispose();
    }

    //private methods

    private void update(float delta) {
        progress = assetManager.getProgress();

        if (assetManager.update()) {
            waitTime -= delta;
            if (waitTime <= delta) {
                changeScreen = true;
            }
        }

    }

    private void draw() {
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.rect((GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f,
                (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2,

                //this is how you fill the progress bar

                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);

        renderer.end();
    }
}


