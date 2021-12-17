package com.didericusgames.snake.entity;

public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    // public methods

    public boolean isUp(){
        return this == UP;
    }
    public boolean isRight(){
        return this == RIGHT;
    }
    public boolean isDown(){
        return this == DOWN;
    }
    public boolean isLeft(){
        return this == LEFT;
    }

    public Direction getOpposite(){
        if(isLeft()){
            return RIGHT;
        }else if(isRight()){
            return LEFT;
        }else if(isDown()){
            return UP;
        }else if(isUp()) {
            return DOWN;
        }

        //this will never happen
        throw new IllegalStateException("Can't find opposite of direction " +this);
    }
    public boolean isOpposite(Direction direction){
        return getOpposite() == direction;
    }
}
