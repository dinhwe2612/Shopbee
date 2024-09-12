package com.example.shopbee.ui.common.dialogs.gameoverdialog;

public class GameOverEvent {
    static public enum GameOver {
        HOME,
        PLAYAGAIN
    }
    private GameOver gameOver;
    public GameOverEvent(GameOver gameOver) {
        this.gameOver = gameOver;
    }
    public GameOver getGameOver() {
        return gameOver;
    }
}
