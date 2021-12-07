package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Main.GamePanel;
import Manager.GameStateManager;
import Manager.Keys;

public class MenuState extends GameState {
	
	// private BufferedImage bg;
	private BufferedImage[] diamond;
	
	private int currentOption = 0;
	private String[] options = {
		"START",
		"QUIT"
	};
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		diamond = new BufferedImage[1];
		try {
            diamond[0] = ImageIO.read(new File("Resources/Graphics/Diamond/diamond.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void update() {
        handleInput();
	}
	
	public void draw(Graphics2D g) {
		
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.setColor(Color.BLACK);

        g.fillRect(GamePanel.WIDTH / 10 * 4, GamePanel.HEIGHT / 10 * 4, GamePanel.WIDTH / 10 * 2, GamePanel.HEIGHT / 10);
        g.fillRect(GamePanel.WIDTH / 10 * 4, GamePanel.HEIGHT / 10 * 6, GamePanel.WIDTH / 10 * 2, GamePanel.HEIGHT / 10);

        g.setColor(Color.WHITE);
        g.drawString(options[0], GamePanel.WIDTH / 10 * 4 + 25, GamePanel.HEIGHT / 10 * 4 + 35);
        g.drawString(options[1], GamePanel.WIDTH / 10 * 4 + 35, GamePanel.HEIGHT / 10 * 6 + 35);
		
		if(currentOption == 0) g.drawImage(diamond[0], GamePanel.WIDTH / 10 * 4 - 25, GamePanel.HEIGHT / 10 * 4 + 15, null);
		else if(currentOption == 1) g.drawImage(diamond[0], GamePanel.WIDTH / 10 * 4 - 25, GamePanel.HEIGHT / 10 * 6 + 15, null);
		
	}
	
    @Override
	public void handleInput() {
		if(Keys.isPressed(Keys.DOWN) && currentOption < options.length - 1) {
			currentOption++;
		}
		if(Keys.isPressed(Keys.UP) && currentOption > 0) {
			currentOption--;
		}
		if(Keys.isPressed(Keys.ENTER)) {
			selectOption();
		}
	}
	
	private void selectOption() {
		if(currentOption == 0) {
			gsm.setState(GameStateManager.PLAY);
		}
		if(currentOption == 1) {
			System.exit(0);
		}
	}
	
}
