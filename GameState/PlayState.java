package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Diamond;
import Entity.Player;
import Entity.Portal;
import Manager.GameStateManager;
import Manager.Keys;
import TileMap.TileMap;

public class PlayState extends GameState {
    
    private TileMap tileMap;
    private Player player;

    private Diamond diamond;
    private ArrayList<Diamond> diamonds;
    private int diamondEarned;

    private Portal portal;

    private BufferedImage background;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        tileMap = new TileMap("Resources/Maps/map1.txt", 32);
        tileMap.loadTiles("Resources/Maps/tileset.gif");

        player = new Player(tileMap);

        // player.setX(40 * 32 + 15);
        // player.setY(2 * 32 + 15);
        player.setX(50);
        player.setY(50);
        
        diamond = new Diamond(tileMap);
        diamonds = new ArrayList<>();
        diamond.addLocation(6 * 32 + 15, 5 * 32 + 15);
        diamond.addLocation(15 * 32 + 15, 8 * 32 + 15);
        diamond.addLocation(29 * 32 + 15, 9 * 32 + 15);
        diamond.addLocation(39 * 32 + 15, 18 * 32 + 15);
        diamond.addLocation(17 * 32 + 15, 18 * 32 + 15);
        diamond.addLocation(19 * 32 + 15, 18 * 32 + 15);
        diamond.addLocation(10 * 32 + 15, 14 * 32 + 15);
        diamond.addLocation(48 * 32 + 15, 14 * 32 + 15);
        diamond.addLocation(32 * 32 + 15, 24 * 32 + 15);
        diamond.addLocation(1 * 32 + 15, 14 * 32 + 15);
        
        for(int i = 0; i < diamond.getNumberDiamond(); i++) {
            Diamond temp = new Diamond(tileMap);
            temp.setLocation(diamond.getX(i), diamond.getY(i));
            diamonds.add(temp);
        }
        
        portal = new Portal(tileMap);

    }

    @Override
    public void update() {
        handleInput();

        // tileMap.draw(g);
        // drawScore(g);

        diamond.update();
        for(Diamond diamond : diamonds) {
            diamond.update();
        }
        portal.update();
        player.update();

        earnDiamond();
        if(player.getRectangle().intersects(portal.getRectangle())) {
            gsm.setState(GameStateManager.GAMEOVER);
        }
    }

    // update Diamond locations
    private void earnDiamond() {
        for(int i = 0; i < diamonds.size(); i++) {
            if(player.getRectangle().intersects(diamonds.get(i).getRectangle())) {
                diamonds.get(i).setLocation(0, 0);
                diamondEarned++;
            }
        }
        // if(player.getRectangle().intersects(diamond.getRectangle()))
        //     diamond.remove();   
    }
    
    public static BufferedImage convertToARGB(BufferedImage image)
{
    BufferedImage newImage = new BufferedImage(
        image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = newImage.createGraphics();
    g.drawImage(image, 0, 0, null);
    g.dispose();
    return newImage;
}

    @Override
    public void draw(Graphics2D g) {
        
        // g.setColor(Color.BLACK);
        // g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        
        // try {
        //     background = ImageIO.read(new File("Resources/Maps/background1.gif"));
        //     BufferedImage toPaint = convertToARGB(background);
        //     g.drawImage(toPaint, 0, 0, null);
        // }
        // catch(Exception e) {
        //     e.printStackTrace();
        // }

        // draw blocks, player, diamond and bottom bar
        tileMap.draw(g);
        player.draw(g);
        // diamond.draw(g);
        for(Diamond dm : diamonds) {
            dm.draw(g);
        }
        drawScore(g);
        if(diamondEarned >= diamonds.size()) {
            portal.setLocation(47 * 32 + 15, 2 * 32 + 15);
            portal.draw(g);
        }
    }

    private void drawScore(Graphics2D g) {
        // draw bottom bar
        // g.setColor(new Color(51, 153, 255));
        g.setColor(Color.BLACK);
        g.fillRect(0, 479, 32 * 20, 33);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        
        if(diamondEarned < 2) 
            g.drawString("Earn all Diamonds to open the portal !", 100, 500);

        if(diamondEarned >= diamonds.size()) 
            g.drawString("The portal has appeared on the top right of the map !", 35, 500);

        // draw Diamond counting bar
        g.setColor(new Color(51, 204, 255));
        g.fillRect(535, 479, 110, 33);

        // draw Diamond earned
        g.setColor(Color.BLACK);
        String s = diamondEarned + " / " + diamonds.size();
        g.drawString(s, (diamondEarned >= 10 ? 574 : 579), 503);
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
