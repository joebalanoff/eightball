package engine;

import javax.swing.*;
import java.awt.*;

public class MainThread extends JPanel implements Runnable {
    private volatile boolean running = true;
    public Window window;
    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    private long lastLoopTime = System.nanoTime();

    public MainThread(Window window) {
        this.window = window;
    }

    @Override
    public void run(){
        while(running) {
            long now = System.nanoTime();
            long elapsedTime = now - lastLoopTime;
            lastLoopTime = now;

            double deltaTime = elapsedTime / 1000000000.0;

            if(window.getSceneManager().getCurrentScene() != null) {
                window.getSceneManager().getCurrentScene().onUpdate(deltaTime);
            }

            repaint();

            long sleepTime = (OPTIMAL_TIME - (System.nanoTime() - now)) / 1000000;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        if(window.getSceneManager().getCurrentScene() != null) {
            window.getSceneManager().getCurrentScene().onDraw(g2d);
        }
    }

    public void stop() {
        running = false;
    }
}
