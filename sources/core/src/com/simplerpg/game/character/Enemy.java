package com.simplerpg.game.character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.simplerpg.game.GameObject;
import com.simplerpg.game.animation.AnimationController;
import com.simplerpg.game.tilemap.TileMap;
import com.badlogic.gdx.Gdx;
import java.util.Random;
import static java.lang.Math.abs;

public class Enemy extends GameObject {
    public static final String TAG = Enemy.class.getName();

    private Direction direction = Direction.DOWN;
    private Vector2 velocity = new Vector2(0, 0);
    // private int spawnTime; // thoi gian spawn, de ra ngoai class nay thi hop ly hon
    private int hp;
    private int damage;
    private Difficulty difficulty; // do kho
    private Characters player; // dung de xac dinh vi tri player
    private int speed; // so pixel ma positon thay doi trong moi frame
    private TileMap tileMap;

    public Enemy() {

    }

    public Enemy(String name, Vector2 position, float rotation, Vector2 scale, Sprite sprite,
                 AnimationController animationController, Difficulty difficulty, Characters player,
                 int speed, TileMap tileMap) {
        super(name, position, rotation, scale, sprite, animationController);
        this.speed = speed;
        this.tileMap = tileMap;
        this.player = player;
        this.difficulty = difficulty;
        if (difficulty == Difficulty.EASY){
            this.hp = 3;
            this.damage = 1;
        } else if (difficulty == Difficulty.MEDIUM){
            this.hp = 5;
            this.damage = 2;
        } else { // Difficulty.HARD
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
                velocity = new Vector2(0, this.speed);
                animName = "move_up";
                break;
            case RIGHT:
                velocity = new Vector2(this.speed, 0);
                animName = "move_right";
                break;
            case DOWN:
                velocity = new Vector2(0, -this.speed);
                animName = "move_down";
                break;
            case LEFT:
                velocity = new Vector2(-this.speed, 0);
                animName = "move_left";
                break;
            case IDLE:
                velocity = new Vector2(0, 0);
                animName = "idle";
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
        if (!tileMap.hitAWall(this.position.x + velocity.x, this.position.y + velocity.y, 15, 6)) {
            position.x += velocity.x;
            position.y += velocity.y;
        }
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
            // che do EASY, bot di chuyen ngau nhien
            // trung binh 1 s doi huong 1 lan:
            Random rd = new Random();
            int rand_int = rd.nextInt(15);
            if (rand_int == 0) {
                return Direction.getRandom();
            }
            return this.direction;
        } else if(difficulty == Difficulty.MEDIUM) {
            // che do MEDIUM, khi nap sau tuong bot se khong thay
            // heuristic A* dung khoang cach manhattan
            Direction next = Direction.IDLE;
            float min = Integer.MAX_VALUE;
            if (manhattan_dist(this.position.x + this.speed, this.position.y) < min &&
                    !tileMap.hitAWall(this.position.x + this.speed, this.position.y, 15, 6)){
                min = manhattan_dist(this.position.x + this.speed, this.position.y);
                next = Direction.RIGHT;
            }
            if (manhattan_dist(this.position.x - this.speed, this.position.y) < min &&
                    !tileMap.hitAWall(this.position.x - this.speed, this.position.y, 15, 6)){
                min = manhattan_dist(this.position.x - this.speed, this.position.y);
                next = Direction.LEFT;
            }
            if (manhattan_dist(this.position.x, this.position.y + this.speed) < min &&
                    !tileMap.hitAWall(this.position.x, this.position.y + this.speed, 15, 6)){
                min = manhattan_dist(this.position.x, this.position.y + this.speed);
                next = Direction.UP;
            }
            if (manhattan_dist(this.position.x, this.position.y - this.speed) < min &&
                    !tileMap.hitAWall(this.position.x, this.position.y - this.speed, 15, 6)){
                min = manhattan_dist(this.position.x, this.position.y - this.speed);
                next = Direction.DOWN;
            }
            return next;
        } else { // Difficulty.HARD
            // che do HARD, bot luon tim duoc duong di ngan nhat toi nguoi choi
            // heuristic A* dung khoang cach ngan nhat (duyet BFS)
            // (ma BFS phai duyet qua nhieu lan nen khong kha thi)
            Direction next = Direction.IDLE;
            float min = Integer.MAX_VALUE;
            if (bfs_dist(this.position.x + this.speed, this.position.y) < min &&
                    !tileMap.hitAWall(this.position.x + this.speed, this.position.y, 15, 6)){
                min = bfs_dist(this.position.x + this.speed, this.position.y);
                next = Direction.RIGHT;
            }
            if (bfs_dist(this.position.x - this.speed, this.position.y) < min &&
                    !tileMap.hitAWall(this.position.x - this.speed, this.position.y, 15, 6)){
                min = bfs_dist(this.position.x - this.speed, this.position.y);
                next = Direction.LEFT;
            }
            if (bfs_dist(this.position.x, this.position.y + this.speed) < min &&
                    !tileMap.hitAWall(this.position.x, this.position.y + this.speed, 15, 6)){
                min = bfs_dist(this.position.x, this.position.y + this.speed);
                next = Direction.UP;
            }
            if (bfs_dist(this.position.x, this.position.y - this.speed) < min &&
                    !tileMap.hitAWall(this.position.x, this.position.y - this.speed, 15, 6)){
                min = bfs_dist(this.position.x, this.position.y - this.speed);
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
