package com.simplerpg.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.simplerpg.game.animation.AnimationController;
import com.simplerpg.game.character.*;
import com.simplerpg.game.tilemap.*;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.IOException;

public class SimpleRPG implements ApplicationListener, InputProcessor {
	SpriteBatch batch;
	TileMap tileMap = new TileMap();
	Characters character;
	Enemy spider;

	@Override
	public void create () {
		batch = new SpriteBatch();
		tileMap.loadMapFromArray(TileMap.test);
		Gdx.input.setInputProcessor(this);
		try {
			character = new Characters("pipyaka", new Vector2(0, 0), 0.0f, new Vector2(1,1), null,
					new AnimationController("anims/player.anim"));
			spider = new Enemy("Spider", new Vector2 (100, 100), 0.0f, new Vector2(1,1), null,
					new AnimationController("anims/spider.anim"), Difficulty.MEDIUM, character);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		tileMap.draw(batch);
		character.update();
		spider.update();
		character.draw(batch);
		spider.draw(batch);
		batch.end();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	public void dispose(){
		batch.dispose();
	}


	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.UP){
			character.move(Direction.UP);
			return true;
		}
		else if(keycode == Input.Keys.LEFT){
			character.move(Direction.LEFT);
			return true;
		}
		else if(keycode == Input.Keys.DOWN){
			character.move(Direction.DOWN);
			return true;
		}
		else if(keycode == Input.Keys.RIGHT){
			character.move(Direction.RIGHT);
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.UP){
			character.idle();
			return true;
		}
		else if(keycode == Input.Keys.LEFT){
			character.idle();
			return true;
		}
		else if(keycode == Input.Keys.DOWN){
			character.idle();
			return true;
		}
		else if(keycode == Input.Keys.RIGHT){
			character.idle();
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
