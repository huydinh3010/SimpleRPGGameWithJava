package com.simplerpg.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.simplerpg.game.character.Difficulty;

public class SimpleRPGGame extends Game {

    private LoadingScreen loadingScreen;
    private GameScreen mainScreenEasy;
    private GameScreen mainScreenHard;
    private MenuScreen menuScreen;
    private EndScreen endScreen;
    private IntroductionScreen introductionScreen;
    private LevelScreen levelScreen;

    public final static int MENU = 0;
    public final static int INTRODUCTION = 1;
    public final static int GAME_EASY = 2;
    public final static int GAME_HARD = 5;
    public final static int ENDGAME = 3;
    public final static int LEVEL = 4;

    /**
     * Called when the {@link Application} is first created.
     */
    @Override
    public void create() {
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }

    public  void changeScreen(int screen) {
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
                if (mainScreenEasy == null) {
                    mainScreenEasy = new GameScreen(this, Difficulty.EASY);
                }
                this.setScreen(mainScreenEasy);
                break;
            case GAME_HARD:
                if (mainScreenHard == null) {
                    mainScreenHard = new GameScreen(this, Difficulty.MEDIUM);
                }
                this.setScreen(mainScreenHard);
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
        }
    }

}
