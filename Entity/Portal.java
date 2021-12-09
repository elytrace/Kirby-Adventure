package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import Main.GamePanel;
import TileMap.TileMap;

public class Portal {
    private int x;
    private int y;

    private int width;
    private int height;

    private TileMap tileMap;

    private BufferedImage[][] portal;

    private Animation animation;

    public Portal(TileMap tm) {
        tileMap = tm;
        width = 32; 
        height = 32;

        try {
            BufferedImage image = ImageIO.read(new File("Resources/Graphics/Portal/portal.gif"));
            int w = image.getWidth() / width;
            int h = image.getHeight() / height;

            portal = new BufferedImage[h][w];
            for(int i = 0; i < h; i++)
                for(int j = 0; j < w; j++) {
                    portal[i][j] = image.getSubimage(j * width, i * height, width, height);
                }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrame(portal[0]);
        animation.setDelay(100);
        tileMap.setX((int)(GamePanel.WIDTH / 2 - x));
        tileMap.setY((int)(GamePanel.HEIGHT / 2 - y));

    }
    public Rectangle getRectangle() { 
        return new Rectangle((int)x, (int)y, 10, 10); 
    }

    public void setX(int i) { x = i; }
    public void setY(int i) { y = i; }

    public void setLocation(int x, int y) {
        setX(x);
        setY(y);
    }

    public void update() {

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
