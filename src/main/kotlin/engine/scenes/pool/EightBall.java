package engine.scenes.pool;

import engine.scenes.Scene;
import engine.scenes.SceneManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EightBall extends Scene {
    private List<Ball> balls;
    private Table table;
    private Ball cueBall;
    private boolean isDragging = false;
    private Point dragStartPoint;
    private Point dragEndPoint;

    public EightBall(SceneManager manager) {
        super(manager);
    }

    @Override
    public void onEnter() {
        table = new Table();
        balls = new ArrayList<>();

        setupTriangle();

        this.manager.getWindow().getFrame().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseReleased(e);
            }
        });

        this.manager.getWindow().getFrame().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e);
            }
        });
    }

    @Override
    public void onUpdate(double deltaTime) {
        for (Ball ball : balls) {
            ball.update(deltaTime);
        }

        for (int i = 0; i < balls.size(); i++) {
            Ball ballA = balls.get(i);
            for (int j = i + 1; j < balls.size(); j++) {
                Ball ballB = balls.get(j);
                ballA.checkCollision(ballB);
            }
        }
    }

    @Override
    public void onDraw(Graphics2D g2d) {
        if (this.table != null) table.draw(g2d);
        if (this.balls != null) {
            for(Ball ball : balls) {
                ball.draw(g2d);
            }
        }

        if (isDragging && cueBall != null && dragEndPoint != null) {
            g2d.setColor(Color.blue);
            g2d.setStroke(new BasicStroke(2)); // Set line thickness
            g2d.drawLine(
                    (int) cueBall.getX(), (int) cueBall.getY(),
                    (int) dragEndPoint.getX(), (int) dragEndPoint.getY() - 25
            );
        }
    }

    @Override
    public void onExit() {

    }

    private void setupTriangle() {
        double startX = table.getRight() -  150;
        double startY = table.getTop() + table.getBottom() / 2;

        List<Integer> ballNumbers = new ArrayList<>();
        for (int i = 1; i <= 15; i++) ballNumbers.add(i);
        Collections.shuffle(ballNumbers);

        int centerIndex = 4;
        ballNumbers.remove((Integer) 8);
        ballNumbers.add(centerIndex, 8);

        int ballIndex = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col <= row; col++) {
                double x = startX + row * (Ball.DIAMETER * -Math.cos(Math.toDegrees(30)));
                double y = startY - row * (Ball.DIAMETER / 2) + col * Ball.DIAMETER;
                balls.add(new Ball(ballNumbers.get(ballIndex++), x, y));
            }
        }

        cueBall = new Ball(0, table.getLeft() + 150, table.getTop() + table.getBottom() / 2);
        balls.add(cueBall);
    }

    private static final int CLICK_RADIUS = 75;
    private void handleMousePressed(MouseEvent e) {
        if (cueBall != null) {
            if (e.getPoint().distance(cueBall.getX() + (Ball.DIAMETER / 2), cueBall.getY() + (Ball.DIAMETER / 2)) <= CLICK_RADIUS) {
                isDragging = true;
                dragStartPoint = e.getPoint();
                dragEndPoint = e.getPoint();
            }
        }

    }

    private void handleMouseReleased(MouseEvent e) {
        if (isDragging) {
            double dx = dragEndPoint.getX() - cueBall.getX();
            double dy = (dragEndPoint.getY() - 25) - cueBall.getY() ;
            double force = Math.sqrt(dx * dx + dy * dy) * 0.05; // Adjust force multiplier as needed

            cueBall.setVelocity(-(dx * force), -(dy * force)); // Set initial velocity based on drag distance
            isDragging = false;
        }
    }

    private void handleMouseDragged(MouseEvent e) {
        if(isDragging) dragEndPoint = e.getPoint();
    }
}
