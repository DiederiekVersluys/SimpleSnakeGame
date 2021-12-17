package com.didericusgames.snake.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;
import com.didericusgames.snake.common.GameManager;
import com.didericusgames.snake.config.GameConfig;
import com.didericusgames.snake.entity.*;

public class GameController {

    //this is where all the game logic can be found


    // == constants ==
    private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);

    // == attributes ==

    private Snake snake;
    private float timer;
    private Coin coin;

    //constructor

    public GameController() {
        snake = new Snake();
        coin = new Coin();
    }

    public Snake getSnake() {
        return snake;

    }

    public Coin getCoin() {
        return coin;
    }

    // == public methods ==
    public void update(float delta) {
        if (GameManager.INSTANCE.isPlaying()) {

            queryInput();

            timer += delta;
            if (timer >= GameConfig.MOVE_TIME) {
                timer = 0;
                snake.move();

                checkOutOfBounds();
                checkCollision();
            }

            spawnCoin();

        }else{
            checkForRestart();
        }
    }




    //private method

    private void queryInput() {
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (leftPressed) {
            snake.setDirection(Direction.LEFT);
        } else if (rightPressed) {
            snake.setDirection(Direction.RIGHT);
        } else if (upPressed) {
            snake.setDirection(Direction.UP);
        } else if (downPressed) {
            snake.setDirection(Direction.DOWN);
        }
    }

    private void checkOutOfBounds() {
        //check x bounds(left/right)

        SnakeHead head = snake.getHead();

        if (head.getX() >= GameConfig.WORLD_WIDTH) {
            head.setX(0);
        } else if (head.getX() < 0) {
            head.setX(GameConfig.WORLD_WIDTH - GameConfig.SNAKE_SPEED);
        }

        //check y bounds(up/down)

        if (head.getY() >= GameConfig.WORLD_HEIGHT) {
            head.setY(0);
        } else if (head.getY() < 0) {
            head.setY(GameConfig.WORLD_HEIGHT - GameConfig.SNAKE_SPEED);
        }
    }

    private void checkCollision() {
        //head <-> coin collision

        SnakeHead head = snake.getHead();
        Rectangle headBounds = head.getBounds();
        Rectangle coinBounds = coin.getBounds();

        boolean overlaps = Intersector.overlaps(headBounds, coinBounds);

        if (coin.isAvailable() && overlaps) {
            snake.insertBodyPart();
            coin.setAvailable(false);
        }

        //head <-> body parts

        for(BodyPart bodyPart : snake.getBodyParts()){
            if(bodyPart.isJustAdded()){
                bodyPart.setJustAdded(false);
                continue;
            }

            Rectangle bodyPartBounds = bodyPart.getBounds();
            if(Intersector.overlaps(bodyPartBounds, headBounds)){

                log.debug("Collision with body part");
                GameManager.INSTANCE.setGameOver();

            }


        }
    }

    private void spawnCoin() {
        if (!coin.isAvailable()) {
            float coinX = MathUtils.random((int) (GameConfig.WORLD_WIDTH - GameConfig.COIN_SIZE));
            float coinY = MathUtils.random((int) (GameConfig.WORLD_HEIGHT - GameConfig.COIN_SIZE));
            coin.setAvailable(true);

            coin.setPosition(coinX, coinY);
        }

    }

    private void checkForRestart() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
        restart();}
    }

    private void restart() {
        GameManager.INSTANCE.setPlaying();
        snake.reset();
        coin.setAvailable(false);
        timer = 0;

    }


}
