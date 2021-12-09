package Entity;
import java.awt.image.*;

public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    
    public Animation() {
        frames = new BufferedImage[6];
    }

    public void setFrame(BufferedImage[] images) {
        frames = images;
        if(currentFrame >= frames.length) currentFrame = 0;
    }

    public void setDelay(long d) {
        delay = d;
    }

    public void update() {
        if(delay == -1) return;

        // thời gian trôi qua = delay thì update frames của Kirby
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length) {
            currentFrame = 0;
        }
    }

    public BufferedImage getImage() {
        return frames[currentFrame];
    }
}
