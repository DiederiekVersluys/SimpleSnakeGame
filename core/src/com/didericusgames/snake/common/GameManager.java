package com.didericusgames.snake.common;

public class GameManager {

    // constants
    public static final GameManager INSTANCE = new GameManager();

    //attributes
    private GameState state = GameState.READY;

    private int score;
    private int displayScore;
    private int highScore;
    private int displayHighScore;


    //constructor

    private GameManager() {
    }

    //public methods
    public boolean isReady() {
        return state.isReady();
    }

    public boolean isPlaying() {
        return state.isPlaying();
    }

    public boolean isGameOver() {
        return state.isGameOver();
    }

    public void setPlaying() {
        state = GameState.PLAYING;
    }

    public void setGameOver() {
        state = GameState.GAME_OVER;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public int getDisplayHighScore() {
        return displayHighScore;
    }

    public void incrementScore(int amount){
        score+=amount;

        if(score>highScore){
            highScore = score;
        }
    }

    public void reset(){
        setPlaying();
        score = 0;
        displayScore = 0;
    }

    public void updateDisplayScore(float delta){
        if (displayScore < score){
            displayScore = Math.min(score, displayScore + (int)(100*delta));
        }

        if(displayScore < highScore){
            displayHighScore = Math.min(highScore, displayHighScore + (int)(100*delta));
        }
    }
}
