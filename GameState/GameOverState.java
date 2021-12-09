package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Manager.GameStateManager;
import Manager.Keys;

public class GameOverState extends GameState {

    public GameOverState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {
    
    }

    public void update() {
        handleInput();
    }

    public void draw(Graphics2D g) {
        // g.setColor(Color.CYAN);
        // g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 22));
        g.drawString("Thanks for playing the demo game !", 155, 135);
        g.drawString("The extended version is available at", 155, 185);
        g.drawString("TFI season 3", 250, 235);

        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("Press ENTER to return to Main menu", 190, 300);
    }

    @Override
    public void handleInput() {
        if(Keys.isPressed(Keys.ENTER)) {
            gsm.setState(GameStateManager.MENU);
        }
        
    }
}
