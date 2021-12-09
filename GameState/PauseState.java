package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// import javax.imageio.ImageIO;

import Main.GamePanel;
import Manager.GameStateManager;
import Manager.Keys;


public class PauseState extends GameState {
	
    // protected BufferedImage choice;
    private String[] options = {
		"RESUME",
        "RESTART",
		"MAIN MENU"
	};
    private int currentOption;
    private BufferedImage[][] diamond;

	public PauseState(GameStateManager gsm) {
		super(gsm);
        int width = 22;
        int height = 22;
        try {
            BufferedImage image = ImageIO.read(new File("Resources/Graphics/Diamond/diamond1.gif"));
            int w = image.getWidth() / width;
            int h = image.getHeight() / height;

        diamond = new BufferedImage[h][w];
            for(int i = 0; i < h; i++)
                for(int j = 0; j < w; j++) {
                    diamond[i][j] = image.getSubimage(j * width, i * height, width, height);
                }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void init() {
        
    }
	
	public void update() {
        handleInput();
	}
	
	public void draw(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        g.setColor(Color.BLACK);
        g.fillRect(GamePanel.WIDTH / 10 * 4, GamePanel.HEIGHT / 10 * 4 - 35, GamePanel.WIDTH / 10 * 2, GamePanel.HEIGHT / 10);
        g.fillRect(GamePanel.WIDTH / 10 * 4, GamePanel.HEIGHT / 10 * 6 - 35, GamePanel.WIDTH / 10 * 2, GamePanel.HEIGHT / 10);
        g.fillRect(GamePanel.WIDTH / 10 * 4, GamePanel.HEIGHT / 10 * 8 - 35, GamePanel.WIDTH / 10 * 2, GamePanel.HEIGHT / 10);
        
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        g.drawString(options[0], GamePanel.WIDTH / 10 * 4 + 25, GamePanel.HEIGHT / 10 * 4);
        g.drawString(options[1], GamePanel.WIDTH / 10 * 4 + 21, GamePanel.HEIGHT / 10 * 6);
        g.drawString(options[2], GamePanel.WIDTH / 10 * 4 + 14, GamePanel.HEIGHT / 10 * 8);

        if(currentOption == 0) {
            // g.setColor(Color.BLUE);
            // g.fillRect(GamePanel.WIDTH / 10 * 4 - 20, GamePanel.HEIGHT / 10 * 4 - 35, GamePanel.WIDTH / 10 * 3, GamePanel.HEIGHT / 10);
            // g.setColor(Color.WHITE);
            // g.drawString(options[0], GamePanel.WIDTH / 10 * 4 + 30, GamePanel.HEIGHT / 10 * 4);
            
            g.drawImage(diamond[0][1], GamePanel.WIDTH / 10 * 4 - 25, GamePanel.HEIGHT / 10 * 4 - 20, null);
        }
        else if(currentOption == 1) {
            // g.setColor(Color.BLUE);
            // g.fillRect(GamePanel.WIDTH / 10 * 4 - 20, GamePanel.HEIGHT / 10 * 6 - 35, GamePanel.WIDTH / 10 * 3, GamePanel.HEIGHT / 10);
            // g.setColor(Color.WHITE);
            // g.drawString(options[1], GamePanel.WIDTH / 10 * 4 + 20, GamePanel.HEIGHT / 10 * 6);
        
            g.drawImage(diamond[0][1], GamePanel.WIDTH / 10 * 4 - 25, GamePanel.HEIGHT / 10 * 6 - 20, null);
        }
        else if(currentOption == 2) {
            // g.setColor(Color.BLUE);
            // g.fillRect(GamePanel.WIDTH / 10 * 4 - 20, GamePanel.HEIGHT / 10 * 8 - 35, GamePanel.WIDTH / 10 * 3, GamePanel.HEIGHT / 10);
            // g.setColor(Color.WHITE);
            // g.drawString(options[2], GamePanel.WIDTH / 10 * 4 + 15, GamePanel.HEIGHT / 10 * 8);
        
            g.drawImage(diamond[0][1], GamePanel.WIDTH / 10 * 4 - 25, GamePanel.HEIGHT / 10 * 8 - 20, null);
        }
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
            gsm.setPaused(false);
		}
		if(currentOption == 1) {
            gsm.setPaused(false);
			gsm.setState(GameStateManager.PLAY);
		}
        if(currentOption == 2) {
            gsm.setPaused(false);
            gsm.setState(GameStateManager.MENU);
        }

	}
}

