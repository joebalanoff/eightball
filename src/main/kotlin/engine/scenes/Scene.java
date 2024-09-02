package engine.scenes;

import engine.Window;

import java.awt.*;

public abstract class Scene {
    private final Window window;
    protected final SceneManager manager;
    public Scene(SceneManager manager) {
        this.manager = manager;
        this.window = this.manager.getWindow();
    }

    public abstract void onEnter();
    public abstract void onUpdate(double deltaTime);
    public abstract void onDraw(Graphics2D g2d);
    public abstract void onExit();

    protected int getWidth() { return this.window.getWidth(); }
    protected int getHeight() { return this.window.getHeight(); }
}
