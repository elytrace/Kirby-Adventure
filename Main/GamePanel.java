package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Entity.Diamond;
import Entity.Player;
import TileMap.TileMap;


public class GamePanel extends JPanel implements Runnable, KeyListener {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 512;
    
    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g;

    private BufferedImage bg;

    private int FPS = 30;
    private int targetTime = 1000 / FPS;

    private TileMap tileMap;
    private Player player;
    private Diamond diamond;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    public void run() {
        init();

        long startTime;
        long urdTime;
        long waitTime;

        while(running) {
            startTime = System.nanoTime();
            update();
            render();
            draw();

            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;

            try {
                Thread.sleep(waitTime);
            }
            catch(Exception e) {
            }
        }
    }

    private void init() {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        tileMap = new TileMap("Maps/map2.txt", 32);
        tileMap.loadTiles("Graphics/tileset.gif");

        player = new Player(tileMap);

        player.setX(50);
        player.setY(50);

        // player.setX(560);
        // player.setY(113);

        diamond = new Diamond(tileMap);

        diamond.addLocation(585, 116);
        diamond.addLocation(555, 371);
        diamond.addLocation(303, 111);
        diamond.addLocation(47, 431);
        diamond.addLocation(303, 335);
        diamond.addLocation(111, 175);
        diamond.setLocation();
    }

    private void update() {
        tileMap.draw(g);
        drawScore(g);

        diamond.update();
        player.update();

        earnDiamond();
    }

    // update Diamond locations
    private void earnDiamond() {
        if(player.getRectangle().intersects(diamond.getRectangle())) 
            diamond.setLocation();
    }

    private void render() {

        // draw background
        g.setColor(new Color(224, 255, 255));
        g.fillRect(0, 0, WIDTH, 400);

        g.setColor(new Color(0, 191, 255));
        g.fillRect(0, 385, WIDTH, HEIGHT / 2);

        // draw blocks, player, diamond and bottom bar
        tileMap.draw(g);
        player.draw(g);
        diamond.draw(g);
        drawScore(g);
    }

    private void draw() {

        Image doubleBuffer = createImage(WIDTH, HEIGHT);
        Graphics g2 = doubleBuffer.getGraphics();
        
        g2.drawImage(image, 0, 0, null);
        if(diamond.getNumberDiamondEarn() > 10) 
            drawEnding(g2);  
        
        g2.dispose();

        Graphics g3 = getGraphics();
        g3.drawImage(doubleBuffer, 0, 0, null);
   
    }

    private void drawEnding(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 22));
        g.drawString("Thanks for playing the demo game !", 155, 135);
        g.drawString("The extended version is available at", 155, 185);
        g.drawString("TFI season 3", 250, 235);
    }

    private void drawScore(Graphics2D g) {
        // draw bottom bar
        g.setColor(new Color(51, 153, 255));
        g.fillRect(0, 481, 32 * 20, 32);

        // draw Diamond counting bar
        g.setColor(new Color(51, 204, 255));
        g.fillRect(535, 481, 110, 32);

        // draw Diamond earned
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        String s = (diamond.getNumberDiamondEarn() > 10 ? "10" : diamond.getNumberDiamondEarn() - 1) + " / 10";
        g.drawString(s, (diamond.getNumberDiamondEarn() >= 10 ? 574 : 579), 503);
        try {
            g.drawImage(ImageIO.read(new File("Graphics/diamond.gif")), 540, 485, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/* KEY EVENTS */
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_A) {
            player.setLeft(true);
        }
        if(code == KeyEvent.VK_D) {
            player.setRight(true);
        }
        if(code == KeyEvent.VK_W) {
            player.setJumping(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_A) {
            player.setLeft(false);
        }
        if(code == KeyEvent.VK_D) {
            player.setRight(false);
        }
    }

}
