package com.simplerpg.game.character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.simplerpg.game.GameObject;
import com.simplerpg.game.tilemap.TileMap;
import com.simplerpg.game.animation.AnimationController;
import com.simplerpg.game.character.Difficulty;

public class Characters extends GameObject {
    Direction direction = Direction.DOWN;
    Vector2 velocity = new Vector2(0, 0);
    int speed;
    TileMap tileMap;
    int hp;
    int damage;
    Difficulty difficulty; // do kho

    public Characters() {

    }

    public Characters(String name, Vector2 position, float rotation, Vector2 scale, Sprite sprite,
                      AnimationController animationController, TileMap tileMap, Difficulty difficulty) {
        super(name, position, rotation, scale, sprite, animationController);
        this.tileMap = tileMap;
        this.difficulty = difficulty;
    }

    public void move(Direction newDirection){
        String animName = "";
        direction = newDirection;
        switch (direction){
            case UP:
                velocity.x = 0;
                velocity.y = this.speed;
                animName = "move_up";
                break;
            case RIGHT:
                velocity.x = this.speed;
                velocity.y = 0;
                animName = "move_right";
                break;
            case DOWN:
                velocity.x = 0;
                velocity.y = -this.speed;
                animName = "move_down";
                break;
            case LEFT:
                velocity.x = -this.speed;
                velocity.y = 0;
                animName = "move_left";
                break;
            case IDLE:
                velocity.x = 0;
                velocity.y = 0;
                animName = "idle";
                break;
        }
        animationController.play(animName);
    }

    public void idle(){
        String animName = "";
        switch (direction){
            case UP:
                animName = "idle_up";
                break;
            case RIGHT:
                animName = "idle_right";
                break;
            case DOWN:
                animName = "idle_down";
                break;
            case LEFT:
                animName = "idle_left";
                break;
        }
        velocity.x = 0;
        velocity.y = 0;
        animationController.play(animName);
    }

    @Override
    public void update() {
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
