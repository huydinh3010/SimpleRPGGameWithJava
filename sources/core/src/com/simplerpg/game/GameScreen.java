package com.simplerpg.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.simplerpg.game.animation.AnimationController;
import com.simplerpg.game.character.*;
import com.simplerpg.game.character.Difficulty;
import com.simplerpg.game.tilemap.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.io.IOException;

public class GameScreen implements Screen, InputProcessor {
	SpriteBatch batch;
	TileMap tileMap = new TileMap();
	Player player;
	Enemy spider;
	FitViewport viewport; // viewport giup map vua voi man hinh
	Difficulty difficulty;
	boolean isPause = false;
	private SimpleRPGGame parent;
	private Stage stage;

	public GameScreen(SimpleRPGGame game, Difficulty difficulty){
		parent = game;
		this.difficulty = difficulty;
		stage = new Stage(new ScreenViewport());
		batch = new SpriteBatch();
		tileMap.loadMapFromArray(TileMap.test);
		Gdx.input.setInputProcessor(this);
		// fit viewport voi tilemap
		viewport = new FitViewport(tileMap.getMapWidth(), tileMap.getMapHeight());
		try {
			player = new Player("pipyaka", new Vector2(90, 90), 0.0f, new Vector2(1,1), null,
					new AnimationController("anims/player.anim"), tileMap, difficulty);
			spider = new Enemy("Spider", new Vector2 (150, 150), 0.0f, new Vector2(1,1), null,
					new AnimationController("anims/spider.anim"), difficulty, player, tileMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	/**
	 * Called when this screen becomes the current screen for a {@link Game}.
	 */
	@Override
	public void show() {
		isPause = false;
		Gdx.input.setInputProcessor(this);
	}

	/**
	 * Called when the screen should render itself.
	 *
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public void render(float delta) {
		if(isPause == true){
			return;
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.apply();
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		tileMap.draw(batch);
		player.update();
		spider.update();
		player.draw(batch);
		spider.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	/**
	 * Called when this screen is no longer the current screen for a {@link Game}.
	 */
	@Override
	public void hide() {

	}

	public void dispose(){
		batch.dispose();
	}


	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.UP){
			player.move(Direction.UP);
			return true;
		}
		else if(keycode == Input.Keys.LEFT){
			player.move(Direction.LEFT);
			return true;
		}
		else if(keycode == Input.Keys.DOWN){
			player.move(Direction.DOWN);
			return true;
		}
		else if(keycode == Input.Keys.RIGHT){
			player.move(Direction.RIGHT);
			return true;
		}
		else if (keycode == Input.Keys.ESCAPE) {
			isPause = true;
			parent.changeScreen(SimpleRPGGame.PAUSE);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.UP){
			player.idle();
			return true;
		}
		else if(keycode == Input.Keys.LEFT){
			player.idle();
			return true;
		}
		else if(keycode == Input.Keys.DOWN){
			player.idle();
			return true;
		}
		else if(keycode == Input.Keys.RIGHT){
			player.idle();
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
