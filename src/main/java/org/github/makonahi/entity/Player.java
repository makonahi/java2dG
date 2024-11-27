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
                5*gp.getScale(),
                21*gp.getScale(),
                13*gp.getScale(),
                26*gp.getScale());

        screenX=gp.getScreenWidth()/2 - (gp.getTileSize()/2)/2;
        screenY=gp.getScreenHeight()/2 - (gp.getTileSize())/2;

    }

    public void setDefaultValues(){
        worldX=gp.getTileSize()*6;
        worldY=gp.getTileSize()*6;
        speed=4;
        speed = gp.getWorldWidth()/576;
        direction = DIRECTION_DOWN;
        spriteNum=0;
    }

    public void getPlayerSprite(){
        try{
            sprite=ImageIO.read(getClass().getResourceAsStream("/player/Player_Sprite_SheetV2.png"));
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
            if (spriteNum==4)
                spriteNum=0;
            spriteFrameCounter=0;
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        switch (direction) {
            case (DIRECTION_UP):
                image=sprite.getSubimage(spriteNum*(originalTileSize/2+1),147,
                        originalTileSize/2,originalTileSize);
                break;
            case (DIRECTION_DOWN):
                image=sprite.getSubimage(spriteNum*(originalTileSize/2+1),0,
                        originalTileSize/2,originalTileSize);
                break;
            case (DIRECTION_LEFT):
                image=sprite.getSubimage(spriteNum*(originalTileSize/2+1),49,
                        originalTileSize/2,originalTileSize);
                break;
            case (DIRECTION_RIGHT):
                image=sprite.getSubimage(spriteNum*(originalTileSize/2+1),98,
                        originalTileSize/2,originalTileSize);
                break;
        }
        /*g2.setColor(new Color(0,0,0, 0.5f));
        g2.fillOval(screenX, (int) (screenY + (playerHitbox.height*0.85f) * gp.getScale()),playerHitbox.width * gp.getScale(), (int) (playerHitbox.width * gp.getScale() / 2.5f));
        g2.setColor(new Color(255,0,0));
        g2.drawRect(screenX,screenY, playerHitbox.width*gp.getScale(), playerHitbox.height*gp.getScale());*/
        g2.drawImage(image,screenX,screenY, gp.getTileSize()/2, gp.getTileSize(), null);
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
