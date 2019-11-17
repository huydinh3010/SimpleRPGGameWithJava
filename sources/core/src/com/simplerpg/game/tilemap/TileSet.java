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
        switch (itemType){
            case TREE:
                texture = new Texture("tilesets/tree_dummy.png");
                sprite = new Sprite(texture, 0, 0, 105, 89);
                // (0, 0) la vi tri cua tile trong file tree_dummy.org
                // (105, 89) la chieu dai, chieu rong cua tile.
                break;
            case WALL:
                texture = new Texture("tilesets/walls.png");
                sprite = new Sprite(texture, 9, 15, 28, 28);
                break;
            case RIDGE:
                texture = new Texture("tilesets/ridge.png");
                sprite = new Sprite(texture, 71, 8, 69, 57);
                break;
            case GROUND:
                texture = new Texture("tilesets/ground.png");
                sprite = new Sprite(texture, 0, 0, 30, 30);
                break;
            case CACTUSES:
                texture = new Texture("tilesets/cactuses.png");
                sprite = new Sprite(texture, 3, 1, 38, 38);
                break;
            case BUSH_BANANA:
                texture = new Texture("tilesets/bushes_bananas.png");
                sprite = new Sprite(texture, 5, 3, 54, 56);
                break;
            case GROUND_GRASS:
                texture = new Texture("tilesets/ground_grass.png");
                sprite = new Sprite(texture, 0, 0, 30, 30);
                break;
        }
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

