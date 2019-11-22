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
    private Sprite ground = new TileSet(ItemTileSetType.GROUND_GRASS).getSprite(); // nen cua tile map
    public static final int[][] map1 = {
//            {2,2,2,6,6,6,6,6,6,6,6,6,6,6,6,6},
//            {2,2,2,6,2,6,0,0,0,2,2,2,2,4,2,6},
//            {6,2,2,2,2,6,0,0,2,2,2,2,2,4,2,6},
//            {6,6,6,2,2,6,2,2,2,2,1,2,2,4,2,6},
//            {6,2,2,2,2,6,2,2,2,2,1,2,2,4,2,6},
//            {6,2,4,4,4,2,2,2,6,6,1,6,2,4,2,6},
//            {6,2,4,2,2,2,2,2,2,6,2,6,2,2,2,6},
//            {6,2,4,2,2,2,2,2,2,6,2,6,2,2,2,6},
//            {6,2,6,6,6,6,2,2,2,6,2,6,2,6,2,6},
//            {6,2,6,2,2,2,2,2,2,6,2,2,2,6,2,6},
//            {6,2,2,2,6,6,6,6,6,6,2,6,6,6,2,6},
//            {6,6,6,6,6,2,4,4,2,2,2,6,2,2,2,6},
//            {6,2,2,2,2,2,4,2,2,2,2,6,2,2,2,6},
//            {6,2,2,2,2,2,4,2,2,2,2,6,2,2,0,6},
//            {6,2,2,2,2,2,2,2,2,2,5,5,6,2,2,6},
//            {6,0,6,6,0,0,2,4,5,2,5,5,5,6,6,6},
//            {6,5,5,0,2,2,2,4,2,2,2,2,2,2,2,6},
//            {6,5,2,0,2,2,2,4,2,2,2,2,1,5,2,6},
//            {6,2,2,5,2,2,2,4,2,2,1,1,1,5,2,6},
//            {6,2,2,5,2,2,2,4,2,2,2,2,1,5,2,6},
//            {6,2,2,5,2,2,2,6,2,2,2,2,1,2,2,6},
//            {6,2,2,2,2,2,2,6,2,2,2,2,1,2,2,6},
//            {6,2,2,2,2,6,6,6,6,6,6,2,2,2,2,6},
//            {6,2,2,6,2,2,2,6,0,6,2,2,2,2,2,6},
//            {6,2,2,6,2,2,2,6,2,6,2,2,2,2,2,6},
//            {6,2,2,6,6,6,2,2,2,6,2,6,6,6,6,6},
//            {6,2,2,2,6,1,2,2,2,6,2,2,2,4,6,2},
//            {6,1,2,2,6,1,2,2,2,6,2,4,2,2,6,2},
//            {6,1,2,2,6,6,1,6,6,6,2,4,6,6,6,2},
//            {6,1,1,2,2,2,1,5,5,2,2,2,2,2,2,2},
//            {6,6,6,6,6,6,6,6,6,6,6,6,6,6,2,2}

            {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,2,2,2,2,2},
            {6,2,2,2,2,2,2,2,2,2,2,2,2,0,2,6,2,2,2,2,2,2,2,2,2,6,6,6,6,2,2},
            {6,4,4,4,4,4,2,2,6,6,6,2,2,2,2,6,2,5,5,5,2,2,2,2,2,6,4,2,6,2,6},
            {6,2,2,2,2,2,2,2,2,2,6,2,2,2,6,5,2,1,1,1,1,1,2,2,2,6,2,2,6,2,6},
            {6,2,2,2,2,6,6,6,6,2,6,6,6,6,5,5,2,2,1,2,2,2,2,2,2,6,2,4,4,2,6},
            {6,2,2,1,1,1,2,2,2,2,2,2,2,2,5,5,2,2,1,2,2,2,6,2,2,2,2,2,2,2,6},
            {6,2,2,2,2,6,6,6,6,6,6,2,2,2,2,2,2,2,2,2,2,2,6,6,6,6,6,6,6,2,6},
            {6,0,2,2,2,6,2,2,2,2,6,2,2,2,2,5,2,2,2,2,2,2,6,0,2,2,2,2,6,5,6},
            {6,0,0,2,2,2,2,2,2,2,6,4,2,2,2,4,4,4,4,4,6,6,6,6,6,2,2,2,6,5,6},
            {6,0,0,2,2,2,2,2,2,2,6,4,4,4,2,2,2,2,2,2,2,2,6,2,2,2,2,2,1,1,6},
            {6,6,6,6,6,2,2,2,6,2,6,2,2,2,2,0,2,2,2,2,2,2,6,2,2,6,1,1,6,2,6},
            {6,2,2,2,2,4,2,2,6,2,6,6,2,2,2,0,2,2,2,2,2,2,2,2,2,6,6,6,6,2,6},
            {6,6,2,2,2,4,2,2,6,2,2,6,2,2,2,6,0,0,5,5,5,2,2,6,6,6,2,2,2,2,6},
            {2,2,2,6,2,4,4,4,6,6,2,6,2,2,2,6,5,2,2,2,2,2,2,2,2,2,2,2,2,1,6},
            {2,2,2,6,2,2,2,2,2,2,2,6,2,2,2,0,5,5,2,2,2,2,2,2,2,2,2,1,1,1,6},
            {2,2,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6}

    };
    public static final int[][] map2 = {
            {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,1,3},
            {6,16,18,10,12,12,6,39,40,6,35,15,13,34,2,2,2,2,6,45,41,42,1,1},
            {6,16,18,1,12,12,6,1,1,6,34,15,15,35,2,2,2,2,2,45,45,45,45,6},
            {6,17,1,4,1,1,1,2,2,2,36,15,15,37,2,2,2,2,6,6,6,6,45,6},
            {6,1,8,6,3,14,1,2,2,2,36,37,38,36,2,2,2,2,2,2,2,6,45,6},
            {6,1,7,6,1,1,2,2,2,2,2,2,2,2,2,31,31,31,31,31,2,6,45,6},
            {6,1,9,3,1,6,6,2,2,2,2,2,2,2,2,30,28,24,27,30,2,6,45,6},
            {6,1,1,1,1,6,2,2,2,2,2,2,2,2,2,30,23,23,24,30,2,6,45,6},
            {6,5,1,1,19,6,2,2,2,2,2,2,2,2,2,30,25,24,26,30,6,6,45,6},
            {6,44,43,1,20,6,6,6,6,6,6,6,6,6,6,30,29,29,29,30,2,45,45,6},
            {6,1,1,1,2,2,2,2,2,2,2,2,2,2,6,6,6,6,2,2,2,1,22,6},
            {6,1,6,0,6,6,6,2,2,2,2,2,2,2,6,2,2,2,2,2,2,1,21,6},
            {6,1,6,45,6,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,11,6},
            {6,6,6,45,6,47,48,1,1,1,2,2,6,6,6,6,6,2,2,1,11,11,11,6},
            {1,1,45,45,6,46,46,47,48,48,1,2,2,2,6,2,2,2,1,11,11,11,11,6},
            {32,1,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6}
    };
    private int start_i;

    public void loadMapFromFile(String mapFile){

    }

//    public void loadMapFromArray(int[][] items){
//        this.mapArray = items;
//        ItemTileSetType[][] itemTypes = new ItemTileSetType[items.length][items[0].length];
//        ItemTileSetType[] mapping = {
//                ItemTileSetType.BUSH_BANANA,
//                ItemTileSetType.CACTUSES,
//                ItemTileSetType.GROUND,
//                ItemTileSetType.GROUND_GRASS,
//                ItemTileSetType.RIDGE,
//                ItemTileSetType.TREE,
//                ItemTileSetType.WALL};
//        for(int i = 0; i < itemTypes.length; i++){
//            for (int j = 0; j < itemTypes[0].length; j++){
//                itemTypes[i][j] = mapping[items[i][j]];
//            }
//        }
//        loadMapFromArray(itemTypes);
//    }

    int[][] rotatematrix(int[][] items) {
        int cols = items[0].length;
        int rows = items.length;
        int[][] newArray = new int[cols][rows];
        for(int i=0; i<cols; i++) {
            for(int j=0; j<rows; j++) {
                newArray[i][j] = items[rows - 1 - j][i];
            }
        }
        return newArray;
    }

    public void loadMapFromArray(int[][] items){
        this.mapArray = rotatematrix(items);
        int rows = items.length, cols = items[0].length;
        ItemTileSetType[][] itemTypes = new ItemTileSetType[cols][rows];
        ItemTileSetType[] mapping = {
                ItemTileSetType.GATE,
                ItemTileSetType.GROUND_GRASS,
                ItemTileSetType.GROUND,
                ItemTileSetType.WALL_1,
                ItemTileSetType.WALL_2,
                ItemTileSetType.WALL_3,
                ItemTileSetType.WALL_4,
                ItemTileSetType.WALL_5,
                ItemTileSetType.WALL_6,
                ItemTileSetType.WALL_7,
                ItemTileSetType.TREE_DUMMY,
                ItemTileSetType.TREE_CROWNS_1,
                ItemTileSetType.TREE_CROWNS_2,
                ItemTileSetType.TREE_CROWNS_3,
                ItemTileSetType.TREE_1,
                ItemTileSetType.TREE_2,
                ItemTileSetType.TREE_3,
                ItemTileSetType.TREE_4,
                ItemTileSetType.TREE_5,
                ItemTileSetType.TREE_STUBS_1,
                ItemTileSetType.TREE_STUBS_2,
                ItemTileSetType.TREE_STUBS_3,
                ItemTileSetType.TREE_STUBS_4,
                ItemTileSetType.BUSHES_BANANAS,
                ItemTileSetType.BUSHES_BANANAS_2,
                ItemTileSetType.BUSHES_BANANAS_3,
                ItemTileSetType.BUSHES_BIG_1,
                ItemTileSetType.BUSHES_BIG_2,
                ItemTileSetType.BUSHES_BIG_3,
                ItemTileSetType.RIDGE,
                ItemTileSetType.RIDGE_2,
                ItemTileSetType.RIDGE_3,
                ItemTileSetType.HOUSE_BOTTOM,
                ItemTileSetType.HOUSE_TOP,
                ItemTileSetType.CACTUSES,
                ItemTileSetType.CACTUSES_2,
                ItemTileSetType.CACTUSES_3,
                ItemTileSetType.CACTUSES_4,
                ItemTileSetType.CACTUSES_BIG,
                ItemTileSetType.WELL,
                ItemTileSetType.WELL_BAIL,
                ItemTileSetType.TELEGA_BRICKS_1,
                ItemTileSetType.TELEGA_BRICKS_2,
                ItemTileSetType.TELEGA_BRICKS_3,
                ItemTileSetType.TELEGA_BRICKS_4,
                ItemTileSetType.TRAILS,
                ItemTileSetType.WOOD_WALLS_1,
                ItemTileSetType.WOOD_WALLS_2,
                ItemTileSetType.WOOD_WALLS_3
        };
        for(int i = 0; i < cols; i++){
            for (int j = 0; j < rows; j++){
                itemTypes[i][j] = mapping[items[rows - 1 - j][i]];
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
        for(int i = 0; i < items.length; i++){
            for (int j = 0; j < items[0].length; j++){
                // luon ve GROUND truoc o duoi moi tile
                ground.setPosition(i * cellSize, j * cellSize);
                ground.draw(batch);
                if (this.mapArray[i][j] != 1 ) { // neu khong phai GROUND moi can ve
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
                if (i < 0 || j < 0 || i >= this.mapArray.length || j >= this.mapArray[0].length){
                    return true;
                }
                if (this.mapArray[i][j] != 2
                        && this.mapArray[i][j] !=0
                        && this.mapArray[i][j] !=1
                        && this.mapArray[i][j] !=10
                        && this.mapArray[i][j] !=11
                        && this.mapArray[i][j] !=12
                        && this.mapArray[i][j] !=13
                        && this.mapArray[i][j] !=14
                        && this.mapArray[i][j] !=15
                        && this.mapArray[i][j] !=32
                        && this.mapArray[i][j] !=33
                        && this.mapArray[i][j] !=45){ // khong di duoc vao tile khac voi GROUND
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkChangeMap(float x, float y, int radius, int reduceR){
        float end_x = x + 2 * radius;
        float end_y = y + 2 * radius;
        int start_i = (int) ((x+reduceR) / this.cellSize);
        int end_i = (int) ((end_x-reduceR) / this.cellSize);
        int start_j = (int) ((y+reduceR) / this.cellSize);
        int end_j = (int) ((end_y-reduceR) / this.cellSize);
        for (int i = start_i; i <= end_i; i++){
            for (int j = start_j; j <= end_j; j++) {
                if (i < 0 || j < 0 || i >= this.mapArray.length || j >= this.mapArray[0].length) {
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
