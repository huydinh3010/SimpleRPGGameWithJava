package com.simplerpg.game.tilemap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileMap {
    private TileSet items[][];
    private int mapArray[][]; // luu lai mang truyen vao
    private int cellSize = 30;
    // kich thuoc map theo pixels
    private int mapWidth;
    private int mapHeight;

    public static final int[][] test = {
            {6,6,6,6,6,6,6,6,6,6},
            {6,5,2,2,0,0,0,2,2,6},
            {6,2,2,2,2,2,2,2,2,6},
            {6,2,2,2,1,2,2,5,2,6},
            {6,2,2,2,1,2,2,5,2,6},
            {6,2,2,2,1,2,2,5,2,6},
            {6,2,2,2,2,2,2,4,2,6},
            {6,2,5,5,5,2,2,4,2,6},
            {6,2,2,2,2,2,2,2,2,6},
            {6,6,6,6,6,6,6,6,6,6}
    };

    public void loadMapFromFile(String mapFile){

    }

    public void loadMapFromArray(int[][] items){
        this.mapArray = items;
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
        this.mapWidth = itemTypes.length * this.cellSize;
        this.mapHeight = itemTypes[0].length * this.cellSize;
    }

    public void draw(SpriteBatch batch){
        Sprite ground = new TileSet(ItemTileSetType.GROUND).getSprite();
        for(int i = 0; i < items.length; i++){
            for (int j = 0; j < items[0].length; j++){
                // luon ve GROUND truoc o duoi moi tile
                ground.setPosition(i * cellSize, j * cellSize);
                ground.draw(batch);
                if (this.mapArray[i][j] != 2) { // neu khong phai GROUND moi can ve
                    Sprite sprite = items[i][j].getSprite();
                    sprite.setPosition(i * cellSize, j * cellSize);
                    sprite.setSize(this.cellSize, this.cellSize);
                    sprite.draw(batch);
                }
            }
        }
    }

    public boolean hitAWall(float x, float y, int radius, int reduceR){
        // kiem tra nhan vat tai vi tri (x, y), co ban kinh radius co cham vao tuong hay khong.
        // reduceR la phan ban kinh giam di de nhan vat co the lot qua cac khoang hep.
        float end_x = x + 2 * radius;
        float end_y = y + 2 * radius;
        int start_i = (int) ((x+reduceR) / this.cellSize);
        int end_i = (int) ((end_x-reduceR) / this.cellSize);
        int start_j = (int) ((y+reduceR) / this.cellSize);
        int end_j = (int) ((end_y-reduceR) / this.cellSize);
        for (int i = start_i; i <= end_i; i++){
            for (int j = start_j; j <= end_j; j++){
                if (this.mapArray[i][j] != 2){ // khong di duoc vao tile khac voi GROUND
                    return true;
                }
            }
        }
        return false;
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

    public int getMapWidth() { return mapWidth; }

    public int getMapHeight() { return mapHeight; }
}
