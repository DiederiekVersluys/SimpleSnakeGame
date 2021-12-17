package com.didericusgames.snake.entity;

import com.didericusgames.snake.config.GameConfig;

public class Coin extends EntityBase{

    private boolean available;

    public Coin(){
        setSize(GameConfig.COIN_SIZE, GameConfig.COIN_SIZE);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
