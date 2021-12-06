package Entity;
import java.awt.image.*;
import javax.imageio.ImageIO;

import Main.GamePanel;
import TileMap.TileMap;

import java.io.File;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player {

    // location của Kirby
    private double x;
    private double y;

    // speed của Kirby
    private double dx;
    private double dy;

    // hitbox của Kỉbry
    private int width;
    private int height;

    // status của Kirby
    private boolean left;
    private boolean right;
    private boolean jumping;
    private boolean falling;

    // thông số di chuyển của Kirby
    private double moveSpeed;
    private double maxSpeed;
    private double maxFallingSpeed;
    private double stopSpeed;
    private double jumpStart;
    private double gravity;

    // 
    private TileMap tileMap;

    // 4 corners
    private boolean topLeft;
    private boolean topRight;
    private boolean bottomLeft;
    private boolean bottomRight;

    // update chuyển động cho Kirby
    private Animation animation;

    // các frames hiển thị trạng thái của Kirby
    private BufferedImage[] idleSprite;
    private BufferedImage[] walkingSprites;
    private BufferedImage[] jumpingSprites;
    private BufferedImage[] fallingSprites;
    private boolean facingLeft;

    public Player(TileMap tm) {
        tileMap = tm;
        width = 22; 
        height = 22;

        moveSpeed = 0.6;
        maxSpeed = 4.2;
        maxFallingSpeed = 12;
        stopSpeed = 0.3;
        jumpStart = -11.0;
        gravity = 0.67;

        try {

            // lấy hình ảnh từ sources 
            idleSprite = new BufferedImage[1];
            jumpingSprites = new BufferedImage[1];
            fallingSprites = new BufferedImage[1];
            walkingSprites = new BufferedImage[6];

            idleSprite[0] = ImageIO.read(new File("Graphics/Player/kirbyidle.gif"));
            jumpingSprites[0] = ImageIO.read(new File("Graphics/Player/kirbyjump.gif"));
            fallingSprites[0] = ImageIO.read(new File("Graphics/Player/kirbyfall.gif"));

            BufferedImage image = ImageIO.read(new File("graphics/player/kirbywalk.gif"));
            for(int i = 0; i < walkingSprites.length; i++) {
                walkingSprites[i] = image.getSubimage(
                    i * width + i,
                    0,
                    width,
                    height
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        facingLeft = left;
    }

    public Rectangle getRectangle() { 
        return new Rectangle((int)x, (int)y, width, height); 
    }

    public void setX(int i) { x = i; }
    public void setY(int i) { y = i; }

    public double getX() { return x; }
    public double getY() { return y; }

    public void setLeft(boolean b) { left = b; }
    public void setRight(boolean b) { right = b; }
    public void setJumping(boolean b) {
        if(!falling) {
            jumping = true;
        }
    }

    // kiểm tra xem các góc di chuyển có bị chặn ko
    private void calculateConrners(double x, double y) {

        // tính toán hitbox của Kirby
        int leftTile = tileMap.getColTile((int)(x - width / 2));
        int rightTile = tileMap.getColTile((int)(x + width / 2) - 1);
        int topTile = tileMap.getRowTile((int)(y - height / 2));
        int bottomTile = tileMap.getRowTile((int)(y + height / 2) - 1);
        

        topLeft = tileMap.isBlocked(topTile, leftTile);
        topRight = tileMap.isBlocked(topTile, rightTile);
        bottomLeft = tileMap.isBlocked(bottomTile, leftTile);
        bottomRight = tileMap.isBlocked(bottomTile, rightTile);
    }

    public void update() {
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed)
                dx = -maxSpeed;
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) 
                dx = maxSpeed;
        }
        else {
            if(dx > 0) {
                dx -= stopSpeed;
                if(dx < 0) dx = 0;
            }
            else if(dx < 0) {
                dx += stopSpeed;
                if(dx > 0) dx = 0;
            }
        }
        if(jumping) {
            dy = jumpStart;
            falling = true;
            jumping = false;
        }
        if(falling) {
            dy += gravity;
            if(dy > maxFallingSpeed) {
                dy = maxFallingSpeed;
            }
        }
        else {
            dy = 0;
        }

        int currCol = tileMap.getColTile((int) x);
        int currRow = tileMap.getRowTile((int) y);

        double toX = x + dx;
        double toY = y + dy;

        double tempX = x;
        double tempY = y;

        calculateConrners(x, toY);
        if(dy < 0) {
            if(topLeft || topRight) {
                dy = 0;
                tempY = currRow * tileMap.getTileSize() + height / 2;
            }
            else {
                tempY += dy;
            }
        }
        if(dy > 0) {
            if(bottomLeft || bottomRight) {
                dy = 0;
                falling = false;
                tempY = (currRow + 1) * tileMap.getTileSize() - height / 2;
            }
            else {
                tempY += dy;
            }
        }

        calculateConrners(toX, y);
        if(dx < 0) {
            if(topLeft || bottomLeft) {
                dx = 0;
                tempX = currCol * tileMap.getTileSize() + width / 2;
            }
            else {
                tempX += dx;
            }
        }
        if(dx > 0) {
            if(topRight || bottomRight) {
                dx = 0;
                tempX = (currCol + 1) * tileMap.getTileSize() - width / 2;
            }
            else {
                tempX += dx;
            }
        }

        if(!falling) {
            calculateConrners(x, y+1);
            if(!bottomLeft && !bottomRight) {
                falling = true;
            }
        }

        x = tempX;
        y = tempY;

        tileMap.setX((int)(GamePanel.WIDTH / 2 - x));
        tileMap.setY((int)(GamePanel.HEIGHT / 2 - y));

        if(left || right) {
            animation.setFrame(walkingSprites);
            animation.setDelay(100);
        }
        else {
            animation.setFrame(idleSprite);
            animation.setDelay(-1);
        }
        if(dy < 0) {
            animation.setFrame(jumpingSprites);
            animation.setDelay(-1);
        }
        if(dy > 0) {
            animation.setFrame(fallingSprites);
            animation.setDelay(-1);
        }
        animation.update();

        if(dx < 0) {
            facingLeft = true;
        }
        if(dx > 0) {
            facingLeft = false;
        }
    }

    public void draw(Graphics2D g) {
        int tx = tileMap.getX();
        int ty = tileMap.getY();

        if(facingLeft) {
            g.drawImage(
                animation.getImage(),
                (int) (tx + x - width / 2),
                (int) (ty + y - height / 2),
                null
            );
        }
        else {
            g.drawImage(
                animation.getImage(),
                (int) (tx + x - width / 2 + width),
                (int) (ty + y - height / 2),
                -width,
                height,
                null
            );
        }
    } 
}
