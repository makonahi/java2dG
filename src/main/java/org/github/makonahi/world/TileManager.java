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

    private int waterFrame=0;

    private final int COLUMN_COUNT=4;

    private BufferedImage sprites=null;


    public TileManager(GamePanel gp){
        this.gp = gp;

        tiles = new Tile[32];
        mapTileNum=new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];


        getTileTexture();

        loadMap("/maps/devRoom.txt");
    }

    private void getTileTexture(){
        try{
            sprites=ImageIO.read(getClass().getResourceAsStream("/tiles/tiles16bit.png"));
            for (int i=0;i<4;i++)
                for (int j=0;j<8;j++){
                    tiles[j*4+i] = new Tile(sprites.getSubimage(i*(gp.getOriginalTileSize()+1),
                            j*(gp.getOriginalTileSize()+1),gp.getOriginalTileSize(),gp.getOriginalTileSize()));
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
        waterFrame++;

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
                if (tileNum==28){
                    if(waterFrame<=60)
                        g2.drawImage(tiles[28].getTexture(),
                                (int)screenX, (int)screenY, gp.getTileSize(), gp.getTileSize(), null);
                    else if (waterFrame <= 120) {
                        g2.drawImage(tiles[29].getTexture(),
                                (int) screenX, (int) screenY, gp.getTileSize(), gp.getTileSize(), null);
                    }
                    else if (waterFrame <= 180) {
                        g2.drawImage(tiles[30].getTexture(),
                                (int) screenX, (int) screenY, gp.getTileSize(), gp.getTileSize(), null);
                    }
                    else if (waterFrame <= 240) {
                        g2.drawImage(tiles[31].getTexture(),
                                (int) screenX, (int) screenY, gp.getTileSize(), gp.getTileSize(), null);
                        if (waterFrame == 240)
                            waterFrame=0;
                    }
                }
                else
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
