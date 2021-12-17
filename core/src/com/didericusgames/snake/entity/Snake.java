package com.didericusgames.snake.entity;

import com.badlogic.gdx.utils.Array;
import com.didericusgames.snake.config.GameConfig;

public class Snake {

    // attributes

    private float xBeforeUpdate;
    private float yBeforeUpdate;

    private SnakeHead head;

    private Direction direction = Direction.RIGHT;

    private final Array<BodyPart> bodyParts = new Array<>();

    public Snake() {
        head = new SnakeHead();
    }


    //public methods

    public void move() {
        xBeforeUpdate = head.getX();
        yBeforeUpdate = head.getY();
        ;

        if (direction.isRight()) {
            head.updateX(GameConfig.SNAKE_SPEED);
        } else if (direction.isLeft()) {
            head.updateX(-GameConfig.SNAKE_SPEED);
        } else if (direction.isUp()) {
            head.updateY(GameConfig.SNAKE_SPEED);
        } else if (direction.isDown()) {
            head.updateY(-GameConfig.SNAKE_SPEED);
        }

        updateBodyParts();
    }


    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public SnakeHead getHead() {
        return head;
    }

    public Array<BodyPart> getBodyParts() {
        return bodyParts;
    }

    public void insertBodyPart() {
        BodyPart bodypart = new BodyPart();
        bodypart.setPosition(head.getX(), head.getY());
        bodyParts.insert(0, bodypart);
    }

    //private methods


    private void updateBodyParts() {
        if (bodyParts.size > 0) {
            BodyPart tail = bodyParts.removeIndex(0);
            tail.setPosition(xBeforeUpdate, yBeforeUpdate);
            bodyParts.add(tail);
        }
    }
}
