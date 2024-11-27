package org.github.makonahi.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    protected double worldX,worldY;
    protected double speed;

    protected BufferedImage sprite;
    protected int direction;
    protected int spriteNum;
    protected int spriteFrameCounter;

    public static final int DIRECTION_DOWN=0;
    public static final int DIRECTION_UP=1;
    public static final int DIRECTION_LEFT=2;
    public static final int DIRECTION_RIGHT=3;


    protected Rectangle solidArea;

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    protected boolean collisionOn = false;

    public double getWorldY() {
        return worldY;
    }

    public double getWorldX() {
        return worldX;
    }

    public Rectangle getSolidArea(){
        return solidArea;
    }

    public int getDirection(){
        return direction;
    }

    public double getSpeed() {
        return speed;
    }
}
