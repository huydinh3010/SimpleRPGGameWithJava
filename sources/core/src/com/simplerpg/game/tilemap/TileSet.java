package com.simplerpg.game.tilemap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import sun.nio.cs.ext.TIS_620;

public class TileSet {
    private Texture texture;
    private int spriteIndex;
    private int spriteWidth;
    private int spriteHeight;
    private Sprite sprite;

    public TileSet(){

    }

    public TileSet(ItemTileSetType itemType){
        texture = TileSet.getTextureFromType(itemType);
        spriteHeight = TileSet.getSpriteHeightFromType(itemType);
        spriteWidth = TileSet.getSpriteWidthFromType(itemType);
        sprite = new Sprite(texture, spriteWidth, spriteHeight);
    }

    public static Texture getTextureFromType(ItemTileSetType type){
        Texture texture = null;
        switch (type){
            case TREE:
                texture = new Texture("tilesets/tree_dummy.png");
                break;
            case WALL:
                texture = new Texture("tilesets/walls.png");
                break;
            case RIDGE:
                texture = new Texture("tilesets/ridge.png");
                break;
            case GROUND:
                texture = new Texture("tilesets/ground.png");
                break;
            case CACTUSES:
                texture = new Texture("tilesets/cactuses.png");
                break;
            case BUSH_BANANA:
                texture = new Texture("tilesets/bushes_bananas.png");
                break;
            case GROUND_GRASS:
                texture = new Texture("tilesets/ground_grass.png");
                break;
        }
        return texture;
    }

    public static int getSpriteWidthFromType(ItemTileSetType type){
        int width = 0;
        switch (type){
            case TREE:
                width = 105;
                break;
            case WALL:
                width = 45;
                break;
            case RIDGE:
                width = 70;
                break;
            case GROUND:
                width = 30;
                break;
            case CACTUSES:
                width = 50;
                break;
            case BUSH_BANANA:
                width = 60;
                break;
            case GROUND_GRASS:
                width = 30;
                break;
        }
        return width;
    }

    public static int getSpriteHeightFromType(ItemTileSetType type){
        int height = 0;
        switch (type){
            case TREE:
                height = 89;
                break;
            case WALL:
                height = 45;
                break;
            case RIDGE:
                height = 70;
                break;
            case GROUND:
                height = 30;
                break;
            case CACTUSES:
                height = 50;
                break;
            case BUSH_BANANA:
                height = 60;
                break;
            case GROUND_GRASS:
                height = 30;
                break;
        }
        return height;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getSpriteIndex() {
        return spriteIndex;
    }

    public void setSpriteIndex(int spriteIndex) {
        this.spriteIndex = spriteIndex;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}

