package com.simplerpg.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
    private EndScreen loseScreen;

    public Difficulty currentLevel;
    public int currentMap = 0;

    public int point = 0;
//    private final Sound painPlayerSound   = Gdx.audio.newSound(Gdx.files.internal("pain1.wav"));
//    private final Sound painEnemySound    = Gdx.audio.newSound(Gdx.files.internal("sounds/pain2.wav"));
    public final static int MENU            = 0;
    public final static int INTRODUCTION    = 1;
    public final static int GAME_EASY       = 2;
    public final static int GAME_HARD       = 5;
    public final static int ENDGAME         = 3;
    public final static int LEVEL           = 4;
    public final static int RESUME          = 6;
    public final static int PAUSE           = 7;
    public final static int WIN             = 1000;
    /**
     * Called when the {@link Application} is first created.
     */
    @Override
    public void create() {
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }
    public void endGame(int point){
        EndScreen endScreen = new EndScreen(this, point);
        this.setScreen(endScreen);
    }
    public void changeScreen(int screen) {
        switch (screen) {
            case MENU:
                if (menuScreen == null) {
                    menuScreen = new MenuScreen(this);
                }
                currentMap = 0;
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
            endGame(WIN);
            currentMap = 0;
            return;
        }

        if(currentMap == 0){
            point = 0;
            mainScreen = new GameScreen(this, currentLevel, TileMap.map1);
        }
        if(currentMap == 1) {
            mainScreen = new GameScreen(this, currentLevel, TileMap.map2);
        }

        this.setScreen(mainScreen);
        currentMap++;
    }

//    public void playPainPlayerSound(){
//        painPlayerSound.play();
//    }
//
//    public void playPainEnemySound() {
//        painEnemySound.play();
//    }
}
