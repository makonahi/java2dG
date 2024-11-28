package org.github.makonahi.main;

import java.awt.*;
import java.util.ArrayList;

public class Debugger {

    private GamePanel gp;
    boolean showDebug=true;

    public Debugger(GamePanel gp){
        this.gp = gp;
    }


    public void showDebugInfo(Graphics2D g2){

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Verdana", Font.BOLD, 12));


        ArrayList<String> dataList = new ArrayList<>();
        dataList.add("FPS: " + gp.getCurrentFPS());
        dataList.add(" ");
        int count = 0;

        if (gp.keyHandler.getShowDebug()) {
            int entityLeftWorldX = (int) (gp.player.getWorldX() + gp.player.getSolidArea().x);
            int entityRightWorldX = entityLeftWorldX + gp.player.getSolidArea().width;
            int entityTopWorldY = (int) (gp.player.getWorldY() + gp.player.getSolidArea().y);
            int entityBottomWorldY = entityTopWorldY + gp.player.getSolidArea().height;

            int entityLeftCol = entityLeftWorldX / gp.getTileSize();
            int entityRightCol = entityRightWorldX / gp.getTileSize();
            int entityTopRow = entityTopWorldY / gp.getTileSize();
            int entityBottomRow = entityBottomWorldY / gp.getTileSize();

            dataList.add("ScreenX: " + gp.player.getScreenX());
            dataList.add("ScreenY: " + gp.player.getScreenY());
            dataList.add("WorldX: " + gp.player.getWorldX());
            dataList.add("WorldY: " + gp.player.getWorldY());

            dataList.add("LC: " + entityLeftCol +
                    " RC: " + entityRightCol +
                    " TR: " + entityTopRow +
                    " BT: " + entityBottomRow);

            dataList.add("Direction: " + gp.player.getDirection());
            dataList.add("Speed: " + gp.player.getSpeed());
            dataList.add("WorldWidth: " + gp.getWorldWidth());
            dataList.add("WorldHeight: " + gp.getWorldHeight());
            dataList.add("ts: " + gp.getTileSize() + " o: " + gp.getOriginalTileSize() + " s: " + gp.getScale());
        }
        for (String str : dataList) {
            g2.drawChars(str.toCharArray(), 0, str.length(), 5, 15 * count + 15);
            count++;
        }
        ArrayList<String> controlList = new ArrayList<>();
        controlList.add("WASD - Move");
        controlList.add("F1 - Show debug info");
        controlList.add("MWheel - Zoom In/Out");
        count=0;
        for (String str : controlList){
            g2.drawChars(str.toCharArray(),0, str.length(), 5, gp.getScreenHeight()-(15*count + 5));
            count++;
        }
    }
}
