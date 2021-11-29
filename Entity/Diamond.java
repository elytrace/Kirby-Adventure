/* 
    Hiển thị viên kim cương
*/

package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

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

    private Animation animation;

    public Diamond(TileMap tm) {
        tileMap = tm;
        width = 22; 
        height = 22;

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
        return new Rectangle((int)x + 15, (int)y, 22, 22); 
    }

    public void setX(int i) { x = i; }
    public void setY(int i) { y = i; }

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
