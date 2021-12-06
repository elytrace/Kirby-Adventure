package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

import Main.GamePanel;
import TileMap.TileMap;

public class Diamond {
    private double x;
    private double y;

    private int width;
    private int height;

    private TileMap tileMap;

    private BufferedImage[] diamond;

    private ArrayList<Integer> diamondX;
    private ArrayList<Integer> diamondY;

    private Animation animation;

    private int numberDiamondEarn = 0;

    public Diamond(TileMap tm) {
        tileMap = tm;
        width = 22; 
        height = 22;

        diamondX = new ArrayList<>();
        diamondY = new ArrayList<>();

        try {
            diamond = new BufferedImage[1];
            diamond[0] = ImageIO.read(new File("Graphics/diamond.gif"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
    }
    public Rectangle getRectangle() { 
        return new Rectangle((int)x, (int)y, 10, 10); 
    }

    public void setX(int i) { x = i; }
    public void setY(int i) { y = i; }

    public void addLocation(int x, int y) {
        diamondX.add(x);
        diamondY.add(y);
    }

    public void setLocation() {
        Random rd = new Random();
        int index = rd.nextInt(diamondX.size());
        while(x == diamondX.get(index)) 
            index = rd.nextInt(5);
        x = diamondX.get(index);
        y = diamondY.get(index);
        numberDiamondEarn++;
    }

    public int getNumberDiamondEarn() { return numberDiamondEarn; }

    public double getX() { return x; }
    public double getY() { return y; }

    public void update() {

        tileMap.setX((int)(GamePanel.WIDTH / 2 - x));
        tileMap.setY((int)(GamePanel.HEIGHT / 2 - y));

        animation.setFrame(diamond);
        animation.setDelay(-1);
        animation.update();
    }


    public void draw(Graphics2D g) {
        int tx = tileMap.getX();
        int ty = tileMap.getY();

        g.drawImage(
            animation.getImage(),
            (int) (tx + x - width / 2),
            (int) (ty + y - height / 2),
            null
        );
    } 
    
}
