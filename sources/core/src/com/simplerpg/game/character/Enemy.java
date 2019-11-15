package com.simplerpg.game.character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.simplerpg.game.GameObject;
import com.simplerpg.game.animation.AnimationController;
import com.badlogic.gdx.Gdx;
import java.util.Random;
import static java.lang.Math.abs;

public class Enemy extends GameObject {
    public static final String TAG = Enemy.class.getName();

    private Direction direction = Direction.DOWN;
    private Vector2 velocity = new Vector2(0, 0);
    private int spawnTime; // thoi gian spawn
    private int hp;
    private int damage;
    private Difficulty difficulty; // do kho
    private Characters player; // dung de xac dinh vi tri player

    public Enemy() {

    }

    public Enemy(String name, Vector2 position, float rotation, Vector2 scale, Sprite sprite,
                 AnimationController animationController, Difficulty difficulty, Characters player) {
        super(name, position, rotation, scale, sprite, animationController);
        this.player = player;
        this.difficulty = difficulty;
        if (difficulty == Difficulty.EASY){
            this.spawnTime = 7;
            this.hp = 3;
            this.damage = 1;
        } else if (difficulty == Difficulty.MEDIUM){
            this.spawnTime = 5;
            this.hp = 5;
            this.damage = 2;
        } else { // Difficulty.HARD
            this.spawnTime = 3;
            this.hp = 7;
            this.damage = 3;
        }
    }

    public void move(Direction newDirection){
        // khi gap tuong thi khong move
        String animName = "";
        direction = newDirection;
        switch (direction){
            case UP:
                velocity = new Vector2(0, 1);
                animName = "move_up";
                break;
            case RIGHT:
                velocity = new Vector2(1, 0);
                animName = "move_right";
                break;
            case DOWN:
                velocity = new Vector2(0, -1);
                animName = "move_down";
                break;
            case LEFT:
                velocity = new Vector2(-1, 0);
                animName = "move_left";
                break;
        }
        animationController.play(animName);
    }

    @Override
    public void update() {
        Direction nextMove = this.nextMove();
        if (nextMove != this.direction){
            move(nextMove);
        }
        position.x += velocity.x;
        position.y += velocity.y;
    }

    @Override
    public void draw(SpriteBatch batch) {
        Sprite sprite = animationController.getKeyFrame();
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
        sprite.setScale(scale.x, scale.y);
        sprite.draw(batch);
    }

    private Direction nextMove() {
        if (difficulty == Difficulty.EASY) {
            // trung binh 1 s doi huong 1 lan:
            Random rd = new Random();
            int rand_int = rd.nextInt(60);
            if (rand_int == 1) {
                return Direction.getRandom();
            }
            return this.direction;
        } else if(difficulty == Difficulty.MEDIUM) {
            Direction next = Direction.RIGHT;
            float min = manhattan_dist(this.position.x + 1, this.position.y);
            if (manhattan_dist(this.position.x - 1, this.position.y) < min){
                min = manhattan_dist(this.position.x - 1, this.position.y);
                next = Direction.LEFT;
            } else if (manhattan_dist(this.position.x, this.position.y + 1) < min){
                min = manhattan_dist(this.position.x, this.position.y + 1);
                next = Direction.UP;
            } else if (manhattan_dist(this.position.x, this.position.y - 1) < min) { // LEFT
                min = manhattan_dist(this.position.x, this.position.y - 1);
                next = Direction.DOWN;
            }
            return next;
        } else { // Difficulty.HARD
            Direction next = Direction.RIGHT;
            float min = bfs_dist(this.position.x + 1, this.position.y);
            if (bfs_dist(this.position.x - 1, this.position.y) < min){
                min = bfs_dist(this.position.x - 1, this.position.y);
                next = Direction.LEFT;
            } else if (bfs_dist(this.position.x, this.position.y + 1) < min){
                min = bfs_dist(this.position.x, this.position.y + 1);
                next = Direction.UP;
            } else if (bfs_dist(this.position.x, this.position.y - 1) < min) { // LEFT
                min = bfs_dist(this.position.x, this.position.y - 1);
                next = Direction.DOWN;
            }
            return next;
        }
    }

    private float manhattan_dist(float x, float y){
        // khoang cach manhattan tu (x, y) toi player
        return abs(x - this.player.getPosition().x) + abs(y - this.player.getPosition().y);
    }

    private float bfs_dist(float x, float y){
        // khoang cach nho nhat tu (x, y) toi player (dung bfs)
        // se them vao sau khi xu ly va cham voi tuong
        return manhattan_dist(x, y);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
}
