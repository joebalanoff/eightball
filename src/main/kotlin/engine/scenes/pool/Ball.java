package engine.scenes.pool;

import java.awt.*;

public class Ball {
    public static final double DIAMETER = 20;
    private static final double MASS = 1.0;

    private int number;
    private double x, y;
    private double radius = DIAMETER / 2;
    private double vx = 0, vy = 0;
    private Color color;

    public Ball(int number, double x, double y) {
        this.number = number;
        this.x = x;
        this.y = y;

        if(number == 0) color = Color.white;
        else if (number < 8) color = Color.yellow;
        else if (number == 8) color = Color.black;
        else color = Color.red;
    }

    public void update(double deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;

        vx *= 0.98;
        vy *= 0.98;

        checkWallCollision();
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillOval((int)(x-radius), (int)(y - radius), (int)(radius * 2), (int)(radius * 2));
    }

    public void setVelocity(double vx, double vy){
        this.vx = vx;
        this.vy = vy;
    }

    public void checkCollision(Ball other) {
        if (other == this) return; // Ignore self-collision

        double dx = other.x - this.x;
        double dy = other.y - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double overlap = this.radius + other.radius - distance;

        if (distance < this.radius + other.radius) {
            // Normalize the collision vector
            double nx = dx / distance;
            double ny = dy / distance;

            // Calculate the overlap vector and adjust positions
            double moveDistance = overlap / 2;
            this.x -= moveDistance * nx;
            this.y -= moveDistance * ny;
            other.x += moveDistance * nx;
            other.y += moveDistance * ny;

            // Calculate relative velocity
            double tx = -ny;
            double ty = nx;

            double v1n = this.vx * nx + this.vy * ny;
            double v1t = this.vx * tx + this.vy * ty;
            double v2n = other.vx * nx + other.vy * ny;
            double v2t = other.vx * tx + other.vy * ty;

            double m1 = MASS;
            double m2 = MASS;

            // Exchange momentum
            double v1nFinal = (v1n * (m1 - m2) + 2 * m2 * v2n) / (m1 + m2);
            double v2nFinal = (v2n * (m2 - m1) + 2 * m1 * v1n) / (m1 + m2);

            this.vx = v1nFinal * nx + v1t * tx;
            this.vy = v1nFinal * ny + v1t * ty;
            other.vx = v2nFinal * nx + v2t * tx;
            other.vy = v2nFinal * ny + v2t * ty;
        }
    }

    private void checkWallCollision() {
        if (x - radius < 50 || x + radius > 740) vx = -vx;
        if (y - radius < 40 || y + radius > 420) vy = -vy;
    }

    public double getX() { return x; }
    public double getY() { return y; }
}
