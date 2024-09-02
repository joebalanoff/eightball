package engine.scenes;

import engine.Window;

public class SceneManager {
    private final Window window;

    private Scene currentScene;

    public SceneManager(Window window) {
        this.window = window;
    }

    public void setScene(Scene newScene) {
        if (currentScene != null) {
            currentScene.onExit();
        }
        currentScene = newScene;
        currentScene.onEnter();
    }

    public Scene getCurrentScene() { return this.currentScene; }

    public Window getWindow() { return this.window; }
}
