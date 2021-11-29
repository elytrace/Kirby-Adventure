package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GameOver {

    public GameOver() {
    }

    public void draw(Graphics2D g) {
        g.drawString("GAME OVER", 200, 200);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
    }
}
