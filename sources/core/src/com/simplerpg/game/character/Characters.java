package com.simplerpg.game.character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.simplerpg.game.GameObject;
import com.simplerpg.game.tilemap.TileMap;
import com.simplerpg.game.animation.AnimationController;

public class Characters extends GameObject {
    private Direction direction = Direction.DOWN;
    private Vector2 velocity = new Vector2(0, 0);
    private int speed;
    TileMap tileMap;

    public Characters() {

    }

    public Characters(String name, Vector2 position, float rotation, Vector2 scale, Sprite sprite,
                      AnimationController animationController, int speed, TileMap tileMap) {
        super(name, position, rotation, scale, sprite, animationController);
        this.speed = speed;
        this.tileMap = tileMap;
    }



    public void rotate(int degree){
        String animName = "";
        switch (direction){
            case UP:
                direction = degree >= 0 ? Direction.RIGHT : Direction.LEFT;
                animName = degree >= 0 ? "rotate_up_to_right" : "rotate_up_to_left";
                break;
            case DOWN:
                direction = degree >= 0 ? Direction.LEFT : Direction.RIGHT;
                animName = degree >= 0 ? "rotate_down_to_left" : "rotate_down_to_right";
                break;
            case LEFT:
                direction = degree >= 0 ? Direction.UP : Direction.DOWN;
                animName = degree >= 0 ? "rotate_left_to_up" : "rotate_left_to_down";
                break;
            case RIGHT:
                direction = degree >= 0 ? Direction.DOWN : Direction.UP;
                animName = degree >= 0 ? "rotate_right_to_down" : "rotate_right_to_up";
                break;
        }
        animationController.play(animName);
    }

    public void move(Direction newDirection){
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
        velocity = new Vector2(0,0);
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
