package com.didericusgames.snake.screen.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.didericusgames.snake.SimpleSnakeGame;
import com.didericusgames.snake.assets.AssetDescriptors;
import com.didericusgames.snake.collision.CollisionListener;
import com.didericusgames.snake.common.GameManager;
import com.didericusgames.snake.common.GameState;
import com.didericusgames.snake.screen.menu.MenuScreen;

public class GameScreen extends ScreenAdapter {

    // == attributes ==
    private final SimpleSnakeGame game;
    private final AssetManager assetManager;
    private final CollisionListener listener;

    private GameController controller;
    private GameRenderer renderer;

    private Sound coinSound;
    private Music gameMusic;

    // == constructors ==
    public GameScreen(SimpleSnakeGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        listener = new CollisionListener() {
            @Override
            public void hitCoin() {
                coinSound.play();

            }
        };
    }

    // == public methods ==
    @Override
    public void show() {
        coinSound = assetManager.get(AssetDescriptors.COIN_SOUND);
        gameMusic = assetManager.get(AssetDescriptors.GAME_MUSIC);

        controller = new GameController(listener);
        renderer = new GameRenderer(game.getBatch(), assetManager, controller);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);
        gameMusic.play();
        gameMusic.setVolume(0.2f);
        gameMusic.setLooping(true);

        if (GameManager.INSTANCE.isGameOver()) {
            gameMusic.stop();
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        gameMusic.dispose();
    }
}
