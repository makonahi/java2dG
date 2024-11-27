package org.github.makonahi.main;

import java.awt.event.*;

public class MouseHandler implements MouseListener, MouseWheelListener {

    GamePanel gp;

    public MouseHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0)
        {
            gp.zoomInOut(1);
        }
        else
        {
            gp.zoomInOut(-1);
        }
    }
}
