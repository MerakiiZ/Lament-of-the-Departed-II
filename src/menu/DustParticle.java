package menu;

import java.awt.*;
import java.util.Random;

public class DustParticle {
    private int x, y, size;
    private float alpha; // Transparency (fade effect)
    private float speedX, speedY;
    private Random rand;

    public DustParticle(int startX, int startY) {
        this.rand = new Random();
        this.x = startX;
        this.y = startY;
        this.size = rand.nextInt(8) + 3; // Random size (3-10 pixels)
        this.alpha = rand.nextFloat() * 0.5f + 0.5f; // Random transparency (0.5 to 1.0)

        // Slow random movement in any direction
        this.speedX = (rand.nextFloat() - 0.5f) * 1.2f; // Moves left or right randomly
        this.speedY = (rand.nextFloat() - 0.5f) * 0.5f; // Moves slightly up/down
    }

    public void update() {
        x += speedX; // Move horizontally
        y += speedY; // Move slightly up/down

        alpha -= 0.002f; // Slowly fade out

        // Reset when faded completely
        if (alpha <= 0) {
            x = rand.nextInt(800); // Reset to random X position
            y = rand.nextInt(600); // Reset to random Y position
            alpha = rand.nextFloat() * 0.5f + 0.5f; // Reset transparency
        }
    }

    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // Apply transparency
        g.setColor(new Color(200, 200, 200, (int) (alpha * 255))); // Soft gray dust
        g.fillOval(x, y, size, size); // Blurry dust effect
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // Reset transparency
    }
}
