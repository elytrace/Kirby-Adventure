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
    private int x;
    private int y;

    private int width;
    private int height;

    private TileMap tileMap;

    private BufferedImage[][] diamond;

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

    public int getNumberDiamond() { return diamondX.size(); }

    public void setLocation(int x, int y) {
        setX(x);
        setY(y);
        if(x == 0 && y == 0) numberDiamondEarn++;
    }

    // public void setLocation() {
    //     Random rd = new Random();
    //     diamondX.remove(x);
    //     diamondY.remove(y);
    //     int index = rd.nextInt(diamondX.size());
    //     x = diamondX.get(index);
    //     y = diamondY.get(index);
    //     numberDiamondEarn++;
    // }

    public int getX(int i) {
        return diamondX.get(i);
    }
    public int getY(int i) {
        return diamondY.get(i);
    }

    public int getNumberDiamondEarn() { return numberDiamondEarn; }

    public void update() {

        tileMap.setX((int)(GamePanel.WIDTH / 2 - x));
        tileMap.setY((int)(GamePanel.HEIGHT / 2 - y));

        animation.setFrame(diamond[0]);
        animation.setDelay(500);
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
