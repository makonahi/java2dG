package org.github.makonahi.main;

import org.github.makonahi.entity.Entity;
import org.github.makonahi.entity.Player;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public double checkHorizontalVector(Entity entity, int deltaX) {

        int entityLeftWorldX = (int) (entity.getWorldX() + entity.getSolidArea().x);
        int entityRightWorldX = entityLeftWorldX + entity.getSolidArea().width;
        int entityTopWorldY = (int) (entity.getWorldY() + entity.getSolidArea().y);
        int entityBottomWorldY = entityTopWorldY + entity.getSolidArea().height;

        int entityLeftCol;
        int entityRightCol;
        int entityTopRow = entityTopWorldY/gp.getTileSize();
        int entityBottomRow = entityBottomWorldY/gp.getTileSize();

        int tileNum1, tileNum2;

        if (deltaX < 0){
            entityLeftCol = (int) ((entityLeftWorldX - entity.getSpeed())/gp.getTileSize());
            tileNum1=gp.tileManager.getMapTileNum()[entityLeftCol][entityTopRow];
            tileNum2=gp.tileManager.getMapTileNum()[entityLeftCol][entityBottomRow];
            if (gp.tileManager.getTiles()[tileNum1].getCollision() ||
                    gp.tileManager.getTiles()[tileNum2].getCollision())
                deltaX=0;
        }

        else if (deltaX > 0){
            entityRightCol = (int) ((entityRightWorldX + entity.getSpeed())/gp.getTileSize());
            tileNum1=gp.tileManager.getMapTileNum()[entityRightCol][entityTopRow];
            tileNum2=gp.tileManager.getMapTileNum()[entityRightCol][entityBottomRow];
            if (gp.tileManager.getTiles()[tileNum1].getCollision() ||
                    gp.tileManager.getTiles()[tileNum2].getCollision())
                deltaX=0;
        }
        return deltaX;
    }

    public double checkVerticalVector(Entity entity, int deltaY){

        int entityLeftWorldX = (int) (entity.getWorldX() + entity.getSolidArea().x);
        int entityRightWorldX = entityLeftWorldX + entity.getSolidArea().width;
        int entityTopWorldY = (int) (entity.getWorldY() + entity.getSolidArea().y);
        int entityBottomWorldY = entityTopWorldY + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX/gp.getTileSize();
        int entityRightCol = entityRightWorldX/gp.getTileSize();
        int entityTopRow;
        int entityBottomRow;

        int tileNum1, tileNum2;

        if (deltaY < 0) {
            entityTopRow = (int) ((entityTopWorldY - entity.getSpeed()) / gp.getTileSize());
            tileNum1 = gp.tileManager.getMapTileNum()[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileManager.getMapTileNum()[entityRightCol][entityTopRow];
            if (gp.tileManager.getTiles()[tileNum1].getCollision() ||
                    gp.tileManager.getTiles()[tileNum2].getCollision())
                deltaY=0;
        }
        else if (deltaY > 0) {
            entityBottomRow = (int) ((entityBottomWorldY + entity.getSpeed())/gp.getTileSize());
            tileNum1=gp.tileManager.getMapTileNum()[entityLeftCol][entityBottomRow];
            tileNum2=gp.tileManager.getMapTileNum()[entityRightCol][entityBottomRow];
            if (gp.tileManager.getTiles()[tileNum1].getCollision() ||
                    gp.tileManager.getTiles()[tileNum2].getCollision())
                deltaY=0;
        }

        return deltaY;
    }
}
