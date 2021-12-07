package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.Diamond;
import Entity.Player;
import Manager.GameStateManager;
import Manager.Keys;
import TileMap.TileMap;

public class PlayState extends GameState {
    
    private TileMap tileMap;
    private Player player;
    private Diamond diamond;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        tileMap = new TileMap("Resources/Maps/mapTrang.txt", 32);
        tileMap.loadTiles("Resources/Maps/tileset.gif");

        player = new Player(tileMap);

        player.setX(50);
        player.setY(50);

        // player.setX(560);
        // player.setY(113);

        diamond = new Diamond(tileMap);

        diamond.addLocation(17 * 32 + 15, 3 * 32 + 15);
        diamond.addLocation(17 * 32 + 15, 11 * 32 + 15);
        diamond.addLocation(9 * 32 + 15, 10 * 32 + 15);
        diamond.addLocation(9 * 32 + 15, 3 * 32 + 15);
        diamond.addLocation(3 * 32 + 15, 5 * 32 + 15);
        diamond.addLocation(1 * 32 + 15, 13 * 32 + 15);

        diamond.setLocation();
        
    }

    @Override
    public void update() {
        handleInput();

        // tileMap.draw(g);
        // drawScore(g);

        diamond.update();
        player.update();

        earnDiamond();
        if(diamond.getNumberDiamondEarn() > 10) {
            gsm.setState(GameStateManager.GAMEOVER);
        }
    }

    // update Diamond locations
    private void earnDiamond() {
        if(player.getRectangle().intersects(diamond.getRectangle())) 
            diamond.setLocation();
    }
    
    @Override
    public void draw(Graphics2D g) {
        
        // draw blocks, player, diamond and bottom bar
        tileMap.draw(g);
        player.draw(g);
        diamond.draw(g);
        // drawScore(g);
        
    }

    private void drawScore(Graphics2D g) {
        // draw bottom bar
        // g.setColor(new Color(51, 153, 255));
        g.setColor(Color.BLACK);
        g.fillRect(0, 479, 32 * 20, 33);

        // draw Diamond counting bar
        g.setColor(new Color(51, 204, 255));
        g.fillRect(535, 479, 110, 33);

        // draw Diamond earned
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        String s = (diamond.getNumberDiamondEarn() > 10 ? "10" : diamond.getNumberDiamondEarn() - 1) + " / 10";
        g.drawString(s, (diamond.getNumberDiamondEarn() >= 10 ? 574 : 579), 503);
        try {
            g.drawImage(ImageIO.read(new File("Resources/Graphics/Diamond/diamond.gif")), 540, 485, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleInput() {
        if(Keys.isDown(Keys.ESCAPE)) {
            gsm.setPaused(true);
        }
        if(Keys.isDown(Keys.LEFT)) {
            player.setLeft(true);
        }
        if(Keys.isReleased(Keys.LEFT)) {
            player.setLeft(false);
        }
		if(Keys.isDown(Keys.RIGHT)) {
            player.setRight(true);
        }
        if(Keys.isReleased(Keys.RIGHT)) {
            player.setRight(false);
        }
		if(Keys.isPressed(Keys.UP)) player.setJumping(true);
        
    }

    
}
