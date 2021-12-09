package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Manager.GameStateManager;
import Manager.Keys;


public class GamePanel extends JPanel implements Runnable, KeyListener {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 512;
    
    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g;

    private GameStateManager gsm;

    private int FPS = 30;
    private int targetTime = 1000 / FPS;

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
            drawToScreen();

            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;
            if(waitTime < 0) waitTime = targetTime;

            try {
                Thread.sleep(waitTime);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        gsm = new GameStateManager();
    }

    private void update() {
        gsm.update();
        Keys.update();
    }

    private void draw() {
        gsm.draw(g);
    }


    private void render() {

        // draw background
        g.setColor(new Color(224, 255, 255));
        // g.setColor(new Color(255, 204, 229));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // g.setColor(new Color(0, 191, 255));
        // g.fillRect(0, 385, WIDTH, HEIGHT / 2);

    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Keys.keySet(e.getKeyCode(), true);
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Keys.keySet(e.getKeyCode(), false);
        
    }
    
}
