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

import javax.swing.JPanel;

import Entity.Diamond;
import Entity.Player;
import TileMap.TileMap;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    
    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g;

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

        tileMap = new TileMap("Maps/testmap2.txt", 32);
        tileMap.loadTiles("Graphics/tileset.gif");

        player = new Player(tileMap);

        player.setX(50);
        player.setY(50);

        // player.setX(560);
        // player.setY(113);

        diamond = new Diamond(tileMap);
        diamond.setX(585);
        diamond.setY(116);
    }

    private void update() {
        tileMap.draw(g);
        
        diamond.update();
        player.update();

        earnDiamond();
    }

    public void earnDiamond() {
        if(player.getRectangle().intersects(diamond.getRectangle())) {  
            diamond.setX(0);
            diamond.setY(0);
        
        }
    }

    private void render() {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        tileMap.draw(g);
        player.draw(g);
        diamond.draw(g);
        
    }

    private void draw() {

        Image doubleBuffer = createImage(WIDTH, HEIGHT);
        Graphics g2 = doubleBuffer.getGraphics();
        
        g2.drawImage(image, 0, 0, null);
        if(diamond.getX() == 0 && diamond.getY() == 0) {

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("Thanks for playing the demo game !", 38, 135);
            g2.drawString("The extended version is available at", 30, 185);
            g2.drawString("TFI season 3", 140, 235);
            
        }
        g2.dispose();

        Graphics g3 = getGraphics();
        g3.drawImage(doubleBuffer, 0, 0, null);

        
    }

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
