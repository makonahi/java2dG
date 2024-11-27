package org.github.makonahi.main;

import org.github.makonahi.entity.Player;
import org.github.makonahi.world.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //screen settings
    private final int originalTileSize=48;
    private int scale = 3;

    private int tileSize = originalTileSize * scale;
    private int maxScreenCol = 8;
    private int maxScreenRow = 6;
    private int screenWidth = tileSize * maxScreenCol;
    private int screenHeight = tileSize * maxScreenRow;

    //world set
    private final int maxWorldCol = 16;
    private final int maxWorldRow = 16;
    private final int worldWidth = tileSize * maxWorldCol; //2304 576 4
    private final int worldHeight = tileSize * maxWorldRow;

    //FPS
    private int FPS = 120;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
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

        /*int oldWorldWidth = tileSize * maxWorldCol;

        tileSize=Math.min(Math.max(tileSize+zoomDelta*2, originalTileSize), originalTileSize*4);

        int newWorldWidth = tileSize * maxWorldCol;

        player.setSpeed((double) newWorldWidth/576);

        double multiplier = (double) newWorldWidth/oldWorldWidth;

        double newPlayerWorldX = player.getWorldX() * multiplier;
        double newPlayerWorldY = player.getWorldY() * multiplier;

        player.setWorldX(newPlayerWorldX);
        player.setWorldY(newPlayerWorldY);*/
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
                System.out.println("FPS " + drawCount);
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
