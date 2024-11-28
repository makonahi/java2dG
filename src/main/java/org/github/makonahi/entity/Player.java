package org.github.makonahi.entity;

import org.github.makonahi.main.GamePanel;
import org.github.makonahi.main.KeyHandler;
import org.github.makonahi.world.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    private GamePanel gp;
    private KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    private final int originalTileSize;
    private Rectangle playerModel;

    public Player(GamePanel gp, KeyHandler keyHandler, TileManager tileManager){

        this.gp=gp;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerSprite();

        this.originalTileSize=gp.getOriginalTileSize();

        solidArea = new Rectangle(
                4*gp.getScale(),
                8*gp.getScale(),
                8*gp.getScale(),
                8*gp.getScale());

        screenX=gp.getScreenWidth()/2 - (gp.getTileSize())/2;
        screenY=gp.getScreenHeight()/2 - (gp.getTileSize())/2;

    }

    public void setDefaultValues(){
        worldX=gp.getTileSize()*1;
        worldY=gp.getTileSize()*1;
        speed=4;
        direction = DIRECTION_DOWN;
        spriteNum=0;
    }

    public void rescale(){
        solidArea = new Rectangle(
                4*gp.getScale(),
                8*gp.getScale(),
                8*gp.getScale(),
                8*gp.getScale());
    }

    public void getPlayerSprite(){
        try{
            sprite=ImageIO.read(getClass().getResourceAsStream("/player/16bitplayer.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){

        int deltaX=0, deltaY=0;

        if (keyHandler.isUpPressed()) {
            deltaY -= speed;
            direction=DIRECTION_UP;
        }
        if (keyHandler.isDownPressed()) {
            deltaY += speed;
            direction=DIRECTION_DOWN;
        }
        if (keyHandler.isLeftPressed()) {
            deltaX -= speed;
            direction=DIRECTION_LEFT;
        }
        if (keyHandler.isRightPressed()) {
            deltaX += speed;
            direction=DIRECTION_RIGHT;
        }

        collisionOn=false;
        if (deltaX==0&&deltaY==0){
            spriteNum=0;
        }
        else {
            spriteFrameCounter++;
            worldX+=gp.collisionChecker.checkHorizontalVector(this, deltaX);
            worldY+=gp.collisionChecker.checkVerticalVector(this, deltaY);
        }

        if(spriteFrameCounter>=30){
            spriteNum++;
            if (spriteNum==2)
                spriteNum=0;
            spriteFrameCounter=0;
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        switch (direction) {
            case (DIRECTION_LEFT):
                image=sprite.getSubimage(spriteNum*(originalTileSize+1),(originalTileSize+1)*3,
                        originalTileSize,originalTileSize);
                break;
            case (DIRECTION_RIGHT):
                image=sprite.getSubimage(spriteNum*(originalTileSize+1),0,
                        originalTileSize,originalTileSize);
                break;
            case (DIRECTION_UP):
                image=sprite.getSubimage(spriteNum*(originalTileSize+1),(originalTileSize+1),
                        originalTileSize,originalTileSize);
                break;
            case (DIRECTION_DOWN):
                image=sprite.getSubimage(spriteNum*(originalTileSize+1),(originalTileSize+1)*2,
                        originalTileSize,originalTileSize);
                break;
        }
        //draw a circle shadow
        g2.setColor(new Color(0,0,0, 0.5f));
        g2.fillOval(screenX + gp.getTileSize()/4, (int) (screenY + (gp.getTileSize()*0.80f)),(int)(gp.getTileSize()*0.60f),
                (int) (gp.getTileSize() / 3.5f));
        //draw sprite
        g2.drawImage(image,screenX,screenY, gp.getTileSize(), gp.getTileSize(), null);

        if (gp.keyHandler.getShowDebug()) {
            g2.setColor(Color.red);
            g2.drawRect(solidArea.x + screenX, solidArea.y + screenY, solidArea.width, solidArea.height);
        }
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setWorldX(double newPlayerWorldX) {
        this.worldX = newPlayerWorldX;
    }

    public void setWorldY(double newPlayerWorldY) {
        this.worldY = newPlayerWorldY;
    }

    public void setSpeed(double v) {
        this.speed = v;
    }
}
