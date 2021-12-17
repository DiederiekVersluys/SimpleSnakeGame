package com.didericusgames.snake.screen.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.didericusgames.snake.config.GameConfig;
import com.didericusgames.snake.entity.BodyPart;
import com.didericusgames.snake.entity.Coin;
import com.didericusgames.snake.entity.Snake;
import com.didericusgames.snake.entity.SnakeHead;
import com.didericusgames.snake.util.GdxUtils;
import com.didericusgames.snake.util.ViewportUtils;
import com.didericusgames.snake.util.debug.DebugCameraController;

public class GameRenderer implements Disposable {

    //everything rendered on screen is found here


    //attributes

    private final GameController controller;
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private DebugCameraController debugCameraController;


    //constructor
    public GameRenderer(GameController controller) {
        this.controller = controller;
        init();
    }

    //init
    private void init() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);


    }

    //public methods

    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        GdxUtils.clearScreen();


        renderDebug();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        ViewportUtils.debugPixelsPerUnit(viewport);
    }


    @Override
    public void dispose() {
        renderer.dispose();
    }

    //private methods

    private void renderDebug() {

        ViewportUtils.drawGrid(viewport, renderer);
        viewport.apply();

        Color oldColor = new Color(renderer.getColor());
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();
        renderer.end();
        renderer.setColor(oldColor);
    }

    private void drawDebug() {

        Snake snake = controller.getSnake();

        //render body before the head. Otherwise the body will be rendered on top of the head (since the new body parts are spawned in head position)
        renderer.setColor(Color.YELLOW);
        for (BodyPart bodyPart : snake.getBodyParts()) {
            Rectangle bodyPartBounds = bodyPart.getBounds();
            renderer.rect(bodyPartBounds.x, bodyPartBounds.y, bodyPartBounds.width, bodyPartBounds.height);

        }

        //here the head is rendered in green
        renderer.setColor(Color.GREEN);

        SnakeHead head = snake.getHead();
        Rectangle headBounds = head.getBounds();
        renderer.rect(headBounds.x, headBounds.y, headBounds.width, headBounds.height);


        //this is where the coin is rendered


        Coin coin = controller.getCoin();
        if(coin.isAvailable()) {
            renderer.setColor(Color.BLUE);
            Rectangle coinBounds = coin.getBounds();
            renderer.rect(coinBounds.x, coinBounds.y, coinBounds.width, coinBounds.height);
        }
    }
}
