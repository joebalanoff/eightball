package engine;

import engine.scenes.Scene;
import engine.scenes.SceneManager;

import javax.swing.*;

public class Window {
    private final int width, height;
    private final JFrame frame;

    private final MainThread mainThread;
    private final SceneManager sceneManager;

    public Window(int width, int height) {
        this.width = width;
        this.height = height;

        this.frame = new JFrame("Window");
        this.frame.setSize(width, height);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);

        // Add other listeners and panels
        sceneManager = new SceneManager(this);

        mainThread = new MainThread(this);
        new Thread(mainThread).start();

        this.frame.add(mainThread);

        this.frame.setVisible(true);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public SceneManager getSceneManager() { return this.sceneManager; }

    public JFrame getFrame() { return this.frame; }
}
