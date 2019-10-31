package com.simplerpg.game.tilemap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileMap {
    private TileSet items[][];
    private int cellSize = 30;

    public static final int[][] test = {
            {6,6,6,6,6,6,6,6,6,6},
            {6,2,2,2,2,2,2,2,2,6},
            {6,2,2,2,2,2,2,2,2,6},
            {6,2,2,2,2,2,2,2,2,6},
            {6,2,2,2,2,2,2,2,2,6},
            {6,2,2,2,2,2,2,2,2,6},
            {6,2,2,2,2,2,2,2,2,6},
            {6,2,2,2,2,2,2,2,2,6},
            {6,2,2,2,2,2,2,2,2,6},
            {6,6,6,6,6,6,6,6,6,6},
    };

    public void loadMapFromFile(String mapFile){

    }

    public void loadMapFromArray(int[][] items){
        ItemTileSetType[][] itemTypes = new ItemTileSetType[items.length][items[0].length];
        ItemTileSetType[] mapping = {
                ItemTileSetType.BUSH_BANANA,
                ItemTileSetType.CACTUSES,
                ItemTileSetType.GROUND,
                ItemTileSetType.GROUND_GRASS,
                ItemTileSetType.RIDGE,
                ItemTileSetType.TREE,
                ItemTileSetType.WALL};
        for(int i = 0; i < itemTypes.length; i++){
            for (int j = 0; j < itemTypes[0].length; j++){
                itemTypes[i][j] = mapping[items[i][j]];
            }
        }
        loadMapFromArray(itemTypes);
    }

    public void loadMapFromArray(ItemTileSetType[][] itemTypes){
        items = new TileSet[itemTypes.length][itemTypes[0].length];
        for(int i = 0; i < itemTypes.length; i++){
            for (int j = 0; j < itemTypes[0].length; j++){
                items[i][j] = new TileSet(itemTypes[i][j]);
            }
        }
    }

    public void draw(SpriteBatch batch){
        for(int i = 0; i < items.length; i++){
            for (int j = 0; j < items[0].length; j++){
                Sprite sprite = items[i][j].getSprite();
                sprite.setPosition(i * cellSize, j * cellSize);
                sprite.draw(batch);
            }
        }
    }

    public TileSet[][] getItems() {
        return items;
    }

    public void setItems(TileSet[][] items) {
        this.items = items;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }
}
