package com.simplerpg.game.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.simplerpg.game.collision.CollisionRect;

public class Bullet {
    public static final int SPEED       = 500;
    public static final int WIDTH       = 5;
    public static final int HEIGHT      = 5;
    public static final float MAX_DISTANCE = 400;
    private static Texture textureHorizontal;
    private static Texture textureVertical;
    private static Texture textureEnemyHorizontal;
    private static Texture textureEnemyVertical;
    boolean isEnemy;
    int MAP_HEIGHT, MAP_WIDTH;

    Vector2 position;
    Vector2 startPosition;

    Direction direction;
    CollisionRect rect;
    public boolean remove = false;

    public Bullet (Vector2 position, Direction direction, int MAP_WIDTH, int MAP_HEIGHT, boolean isEnemy) {
        this.position = new Vector2(position.x, position.y);
        this.startPosition = new Vector2(position.x, position.y);
        this.MAP_HEIGHT = MAP_HEIGHT;
        this.MAP_WIDTH = MAP_WIDTH;
        this.direction = direction;
        this.isEnemy = isEnemy;
        this.rect = new CollisionRect(this.position, WIDTH, HEIGHT);

        if (direction == Direction.UP || direction == Direction.DOWN) {
            if (isEnemy) {
                textureEnemyVertical = new Texture("bullets/bullet_enemy_vertical.png");
            } else {
                textureVertical = new Texture("bullets/bullet_vertical.png");
            }

        }

        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            if (isEnemy) {
                textureEnemyHorizontal = new Texture("bullets/bullet_enemy_horizontal.png");
            } else {
                textureHorizontal = new Texture("bullets/bullet_horizontal.png");
            }
        }
    }

    public void update (float deltaTime) {
        switch (direction) {
            case UP:
                position.y += SPEED * deltaTime;
                break;
            case DOWN:
                position.y -= SPEED * deltaTime;
                break;
            case LEFT:
                position.x -= SPEED * deltaTime;
                break;
            case RIGHT:
                position.x += SPEED * deltaTime;
                break;
            case IDLE:
                break;
        }

        if (position.y < 0 || position.y > MAP_HEIGHT
                || position.x < 0 || position.x > MAP_WIDTH
                || position.dst(startPosition) > MAX_DISTANCE) {
            remove = true;
        } else {
            rect.move(position);
        }
    }

    public void draw(SpriteBatch batch) {
        if (remove) {
            return;
        }
        if (direction == Direction.UP || direction == Direction.DOWN) {
            if (isEnemy) {
                batch.draw(textureEnemyVertical, position.x, position.y);
            } else {
                batch.draw(textureVertical, position.x, position.y);
            }
        } else {
            if (isEnemy) {
                batch.draw(textureEnemyHorizontal, position.x, position.y);
            } else {
                batch.draw(textureHorizontal, position.x, position.y);
            }
        }
    }

    public CollisionRect getCollisionRect () {
        return rect;
    }

    public  boolean getIsEnemy() {
        return isEnemy;
    }
}
