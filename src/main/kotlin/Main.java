import engine.Window;
import engine.scenes.pool.EightBall;

public class Main {
    public Main() {
        Window window = new Window(800, 500);
        window.getSceneManager().setScene(new EightBall(window.getSceneManager()));
    }

    public static void main(String[] args){
        new Main();
    }
}
