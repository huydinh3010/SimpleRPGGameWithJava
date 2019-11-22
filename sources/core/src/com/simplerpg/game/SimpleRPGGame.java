package com.simplerpg.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.simplerpg.game.character.Difficulty;
import com.simplerpg.game.tilemap.TileMap;

public class SimpleRPGGame extends Game {

    private LoadingScreen loadingScreen;
    private GameScreen mainScreen;
    private MenuScreen menuScreen;
    private EndScreen endScreen;
    private IntroductionScreen introductionScreen;
    private LevelScreen levelScreen;
    private PauseScreen pauseScreen;
    private Difficulty currentLevel;
    private int currentMap = 0;

    public final static int MENU = 0;
    public final static int INTRODUCTION = 1;
    public final static int GAME_EASY = 2;
    public final static int GAME_HARD = 5;
    public final static int ENDGAME = 3;
    public final static int LEVEL = 4;
    public final static int RESUME = 6;
    public final static int PAUSE = 7;
    /**
     * Called when the {@link Application} is first created.
     */
    @Override
    public void create() {
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }

    public void changeScreen(int screen) {
        switch (screen) {
            case MENU:
                if (menuScreen == null) {
                    menuScreen = new MenuScreen(this);
                }
                this.setScreen(menuScreen);
                break;
            case INTRODUCTION:
                if (introductionScreen == null) {
                    introductionScreen = new IntroductionScreen(this);
                }
                this.setScreen(introductionScreen);
                break;
            case GAME_EASY:
                currentLevel = Difficulty.EASY;
                changeMap();
                break;
            case GAME_HARD:
                currentLevel = Difficulty.MEDIUM;
                changeMap();
                break;
            case ENDGAME:
                if (endScreen == null) {
                    endScreen = new EndScreen(this);
                }
                this.setScreen(endScreen);
                break;
            case LEVEL:
                if (levelScreen == null) {
                    levelScreen = new LevelScreen(this);
                }
                this.setScreen(levelScreen);
                break;
            case RESUME:
                this.setScreen(mainScreen);
                break;
            case PAUSE:
                if (pauseScreen == null) {
                    pauseScreen = new PauseScreen(this);
                }
                this.setScreen(pauseScreen);
                break;
        }
    }

    public void changeMap (){
        if(currentMap == 2){
            changeScreen(MENU);
            currentMap = 0;
            return;
        }

        if(currentMap == 0){
            mainScreen = new GameScreen(this, currentLevel, TileMap.map1);
        }
        if(currentMap == 1) {
            mainScreen = new GameScreen(this, currentLevel, TileMap.map2);
        }

        this.setScreen(mainScreen);
        currentMap++;
    }
}
