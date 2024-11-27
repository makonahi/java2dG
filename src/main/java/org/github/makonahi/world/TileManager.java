package org.github.makonahi.world;

import org.github.makonahi.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    private GamePanel gp;
    private Tile[] tiles;
    private int[][] mapTileNum;

    private final int COLUMN_COUNT=4;

    private final int tileSize;
    private final int originalTileSize;

    private BufferedImage sprites=null;


    public TileManager(GamePanel gp){
        this.gp = gp;

        tiles = new Tile[16];
        mapTileNum=new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];

        tileSize = gp.getTileSize();
        originalTileSize=gp.getOriginalTileSize();


        getTileTexture();

        loadMap("/maps/devRoom.txt");
    }

    private void getTileTexture(){
        try{
            sprites=ImageIO.read(getClass().getResourceAsStream("/tiles/tiles.png"));
            for (int i=0;i<4;i++)
                for (int j=0;j<4;j++){
                    tiles[j*4+i] = new Tile(sprites.getSubimage(i*49,j*49,48,48));
                    if (j*4+i>3)
                        tiles[j*4+i].setCollision(true);
                }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
                String line = br.readLine();

                while(col<gp.getMaxWorldCol()){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col==gp.getMaxWorldCol()){
                    col=0;
                    row++;
                }
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2){
        int worldCol=0;
        int worldRow=0;

        while (worldCol<gp.getMaxWorldCol()&&worldRow<gp.getMaxWorldRow()){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();
            double screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
            double screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();
            //FIXME temporarily 100 pixel are drawn out of default boundary
            if (worldX + gp.getTileSize()>gp.player.getWorldX()-gp.player.getScreenX()-100 &&
                worldX - gp.getTileSize()< gp.player.getWorldX()+gp.player.getScreenX()+100 &&
                worldY + gp.getTileSize()>gp.player.getWorldY()-gp.player.getScreenY()-100 &&
                worldY - gp.getTileSize()< gp.player.getWorldY()+gp.player.getScreenY()+100){
                g2.drawImage(tiles[tileNum].getTexture(),
                        (int)screenX, (int)screenY, gp.getTileSize(), gp.getTileSize(), null);
            }
            worldCol++;

            if(worldCol == gp.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }
    public Tile[] getTiles(){
        return tiles;
    }
}
