package org.github.makonahi.main;

import org.github.makonahi.entity.Player;
import org.github.makonahi.world.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //screen settings
    private final int originalTileSize=16;
    private int scale = 4;

    private int tileSize = originalTileSize * scale;
    private int maxScreenCol = 16;
    private int maxScreenRow = 12;
    private int screenWidth = tileSize * maxScreenCol;
    private int screenHeight = tileSize * maxScreenRow;

    //world set
    private final int maxWorldCol = 16;
    private final int maxWorldRow = 16;
    private final int worldWidth = tileSize * maxWorldCol; //2304 576 4
    private final int worldHeight = tileSize * maxWorldRow;

    //FPS
    private int FPS = 120;

    public int getCurrentFPS() {
        return currentFPS;
    }

    private int currentFPS;

    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    Debugger debugger = new Debugger(this);
    Thread gameThread;
    public Player player = new Player(this,keyHandler, tileManager);



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.addMouseWheelListener(mouseHandler);
        this.setFocusable(true);
    }

    public void zoomInOut(int zoomDelta){

        int oldWorldWidth = tileSize * maxWorldCol;

        scale=Math.min(Math.max(scale+zoomDelta,1), 8);
        tileSize=originalTileSize*scale;

        int newWorldWidth = tileSize * maxWorldCol;

        player.setSpeed((double) newWorldWidth/256);

        double multiplier = (double) newWorldWidth/oldWorldWidth;

        double newPlayerWorldX = player.getWorldX() * multiplier;
        double newPlayerWorldY = player.getWorldY() * multiplier;

        player.setWorldX(newPlayerWorldX);
        player.setWorldY(newPlayerWorldY);
        player.rescale();
    }

    public void strartGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1_000_000_000 / FPS; //0.016(6) sec
        double delta=0;
        long lastTime=System.nanoTime();
        long currentTime;
        long timer=0;
        int drawCount=0;

        while(gameThread!=null){

            currentTime = System.nanoTime();
            delta+=(currentTime - lastTime) / drawInterval;
            timer+=(currentTime-lastTime);
            lastTime=currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000){
                currentFPS = drawCount;
                drawCount=0;
                timer=0;
            }
        }
    }

    public void update(){
        player.update();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        debugger.showDebugInfo(g2);
        player.draw(g2);

        g2.dispose();
    }

    public int getTileSize(){
        return tileSize;
    }

    public int getOriginalTileSize(){
        return originalTileSize;
    }

    public int getScale() {
        return scale;
    }

    public int getMaxScreenCol(){
        return maxScreenCol;
    }

    public int getMaxScreenRow(){
        return maxScreenRow;
    }

    public int getScreenWidth() {
        return screenWidth;
    }
    public int getScreenHeight() {
        return screenHeight;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }
}
