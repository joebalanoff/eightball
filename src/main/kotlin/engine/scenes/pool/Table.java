package engine.scenes.pool;

import java.awt.*;

public class Table {
    private int left = 50, right = 740, top = 40, bottom = 420;
    private Color tableColor = new Color(34, 139, 34);

    private Color railingColor = new Color(126, 89, 67); // Black railing
    private Color pocketColor = new Color(58, 55, 55); // Black pockets
    private int pocketRadius = 20; // Radius of the pockets
    private int railingThickness = 30; // Thickness of the railings

    public int getLeft() { return left; }
    public int getRight() { return right; }
    public int getTop() { return top; }
    public int getBottom() { return bottom; }

    public void draw(Graphics2D g2d) {
        g2d.setColor(tableColor);
        g2d.fillRect(left, top, right - left, bottom - top);

        g2d.setColor(railingColor);
        g2d.fillRect(left - railingThickness, top - railingThickness, right - left + 2 * railingThickness, railingThickness); // Top railing
        g2d.fillRect(left - railingThickness, top - railingThickness, railingThickness, bottom - top + 2 * railingThickness); // Left railing
        g2d.fillRect(left - railingThickness, bottom, right - left + 2 * railingThickness, railingThickness); // Bottom railing
        g2d.fillRect(right, top - railingThickness, railingThickness, bottom - top + 2 * railingThickness); // Right railing

        g2d.setColor(pocketColor);
        // Corners
        g2d.fillOval(left - pocketRadius, top - pocketRadius, 2 * pocketRadius, 2 * pocketRadius); // Top-left
        g2d.fillOval(right - left + left - pocketRadius, top - pocketRadius, 2 * pocketRadius, 2 * pocketRadius); // Top-right
        g2d.fillOval(left - pocketRadius, bottom - pocketRadius, 2 * pocketRadius, 2 * pocketRadius); // Bottom-left
        g2d.fillOval(right - left + left - pocketRadius, bottom - pocketRadius, 2 * pocketRadius, 2 * pocketRadius); // Bottom-right
        // Mid-sides
        g2d.fillOval((left + right) / 2 - pocketRadius, top - pocketRadius, 2 * pocketRadius, 2 * pocketRadius); // Top-center
        g2d.fillOval((left + right) / 2 - pocketRadius, bottom - pocketRadius, 2 * pocketRadius, 2 * pocketRadius); // Bottom-center
    }
}
