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
import java.util.ArrayList;

public class GameScreen implements Screen, InputProcessor {
	SpriteBatch batch;
	TileMap tileMap = new TileMap();
	Player player;
	FitViewport viewport; // viewport giup map vua voi man hinh
	Difficulty difficulty;
	boolean isPause = false;
	private SimpleRPGGame parent;
	private Stage stage;

	ArrayList<Bullet> bullets;
	ArrayList<Enemy> enemies;

	float countDownNewEnemy = 1;
	int   maxEnemy 			= 10;
	int countEnemy 			= 0;

	public GameScreen(SimpleRPGGame game, Difficulty difficulty, int[][] map){
		parent = game;
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		this.difficulty = difficulty;
		stage = new Stage(new ScreenViewport());
		batch = new SpriteBatch();
		tileMap.loadMapFromArray(map);
		Gdx.input.setInputProcessor(this);
		// fit viewport voi tilemap
		viewport = new FitViewport(tileMap.getMapWidth(), tileMap.getMapHeight());
		try {
			player = new Player("pipyaka", new Vector2(90, 90), 0.0f, new Vector2(1,1), null,
					new AnimationController("anims/player.anim"), tileMap, difficulty);
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	public int getScreenWidth() {
		return viewport.getScreenWidth();
	}

	public int getScreenHeight() {
		return viewport.getScreenHeight();
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
		if(player.isChangeMap){
			parent.changeMap();
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.apply();
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		tileMap.draw(batch);

		countDownNewEnemy -= delta;
		if (countDownNewEnemy <= 0 && countEnemy < maxEnemy) {
			countDownNewEnemy = 5;
			try {
				Enemy spider = new Enemy("Spider", new Vector2 (150, 150), 0.0f, new Vector2(1,1), null,
						new AnimationController("anims/spider.anim"), difficulty, player, tileMap);
				enemies.add(spider);
				countEnemy += 1;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			bullets.add(new Bullet(player.position, player.getDirection(), tileMap.getMapWidth(), tileMap.getMapHeight(), false));
		}
//		System.out.println("Player: " + player.getHp() + "; Spider: " + spider.getHp());

		for (Enemy enemy: enemies) {
			enemy.countdownToAttack -= delta;
			if (enemy.countdownToAttack <= 0 && enemy.getHp() > 0) {
				enemy.countdownToAttack = 1;
				bullets.add(new Bullet(enemy.position, enemy.getDirectionBullet(), tileMap.getMapWidth(), tileMap.getMapHeight(), true));
			}
		}

		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		ArrayList<Enemy> enemiesToRemove = new ArrayList<Enemy>();
		for (Bullet bullet : bullets) {
			bullet.update(delta);
			if (bullet.remove) {
				bulletsToRemove.add(bullet);
			}
		}
		for (Bullet bullet : bullets) {
			for (Enemy enemy: enemies) {
				if (bullet.getIsEnemy() == false && bullet.getCollisionRect().collidesWith(enemy.getCollisionRect())) {
					bulletsToRemove.add(bullet);
					enemy.hitShot(player);
					if (enemy.getHp() <= 0) {
						enemiesToRemove.add(enemy);
					} else {
						enemy.update();
						enemy.draw(batch);
					}
				}
				if (bullet.getIsEnemy() == true && bullet.getCollisionRect().collidesWith(player.getCollisionRect())) {
					bulletsToRemove.add(bullet);
					player.hitShot(enemy);
				}
			}
		}

		bullets.removeAll(bulletsToRemove);
		enemies.removeAll(enemiesToRemove);

		for (Enemy enemy: enemies) {
			enemy.update();
			enemy.draw(batch);
		}

		if (player.getHp() > 0) {
			player.update();
			player.draw(batch);
		} else {
			parent.changeScreen(SimpleRPGGame.MENU);
		}

		for (Bullet bullet: bullets) {
			bullet.draw(batch);
		}

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
		} else if(keycode == Input.Keys.LEFT){
			player.move(Direction.LEFT);
			return true;
		} else if(keycode == Input.Keys.DOWN){
			player.move(Direction.DOWN);
			return true;
		} else if(keycode == Input.Keys.RIGHT){
			player.move(Direction.RIGHT);
			return true;
		} else if (keycode == Input.Keys.ESCAPE) {
			isPause = true;
			parent.changeScreen(SimpleRPGGame.PAUSE);
			return true;
		} else {
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
