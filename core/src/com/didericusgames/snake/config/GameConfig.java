package com.didericusgames.snake.config;

public class GameConfig {

    // == constants ==
    public static final float WIDTH = 800f; // pixels
    public static final float HEIGHT = 480f; // pixels

    public static final float WORLD_WIDTH = 25f; // world units
    public static final float WORLD_HEIGHT = 15f; // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f; // world units

    public static final float SNAKE_SIZE = 1;

    public static final float MOVE_TIME = 0.2f; //sec
    public static final float SNAKE_SPEED = 1f; //world units

    public static final float COIN_SIZE = 1; //world units

    private GameConfig() {}
}
