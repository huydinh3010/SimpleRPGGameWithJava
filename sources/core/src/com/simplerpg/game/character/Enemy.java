package com.simplerpg.game.character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.simplerpg.game.GameObject;
import com.simplerpg.game.animation.AnimationController;
import com.simplerpg.game.tilemap.TileMap;
import com.simplerpg.game.character.Difficulty;
import com.badlogic.gdx.Gdx;
import java.util.Random;

import static java.lang.Math.abs;

public class Enemy extends Characters {
    public static final String TAG = Enemy.class.getName();

    private Player player; // dung de xac dinh vi tri player
    private Random rd = new Random();
    private boolean isAbleToShoot;
    public float countdownToAttack = 1;
    private Direction directionBullet = Direction.DOWN;
    private boolean meleeAttacking = false; // quai co dang tan cong can chien nguoi choi khong?

    public Enemy(String name, Vector2 position, float rotation, Vector2 scale, Sprite sprite,
                 AnimationController animationController, Difficulty difficulty, Player player, TileMap tileMap,
                 boolean isAbleToShoot) {
        super(name, position, rotation, scale, sprite, animationController, tileMap, difficulty);
        this.player = player;
        this.isAbleToShoot = isAbleToShoot;

        if (difficulty == Difficulty.EASY){
            this.hp = 10;
            this.rangedDamage = 1;
            this.meleeDamage = 2;
            this.speed = 1;
        } else if (difficulty == Difficulty.MEDIUM){
            this.hp = 10;
            this.rangedDamage = 1;
            this.meleeDamage = 3;
            this.speed = 2;
        } else { // Difficulty.HARD
            this.hp = 20;
            this.rangedDamage = 3;
            this.meleeDamage = 5;
            this.speed = 3;
        }
        if (!isAbleToShoot){
            this.hp += 10;
            this.meleeDamage += 2;
            this.speed += 1;
        }

    }

    @Override
    public void update() {
        Direction nextMove = this.nextMove();
        if (nextMove != this.direction){
            if (nextMove != Direction.IDLE) {
                this.directionBullet = nextMove;
            }
            move(nextMove);
        }
        if (!tileMap.hitAWall(this.position.x + velocity.x, this.position.y + velocity.y, 15, 6)) {
            position.x += velocity.x;
            position.y += velocity.y;
        }
    }

    private Direction nextMove() {
        if (difficulty == Difficulty.EASY) {
            // che do EASY, bot di chuyen ngau nhien
            // trung binh 1 s doi huong 1 lan:
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

    public Direction getDirectionBullet() {
        return directionBullet;
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

    public boolean isAbleToShoot() {
        return isAbleToShoot;
    }

    public boolean getMeleeAttacking(){
        return meleeAttacking;
    }

    public void setMeleeAttacking(boolean meleeAttacking){
        this.meleeAttacking = meleeAttacking;
    }
}
