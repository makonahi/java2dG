package org.github.makonahi.world;

import java.awt.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage texture;
    private boolean collision=false;

    public Tile(BufferedImage texture) {
        this.texture=texture;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public void setCollision(boolean b) {
        this.collision=b;
    }

    public boolean getCollision() {
        return collision;
    }
}
