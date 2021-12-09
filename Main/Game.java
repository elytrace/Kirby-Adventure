package Main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game {
    public static void main(String[] args) {
        JFrame window = new JFrame("Kirby Adventure");
        window.setIconImage(new ImageIcon("Resources/Graphics/Player/kirbyidle.gif").getImage());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new GamePanel());
        
        window.pack();

        window.setVisible(true);
        window.setResizable(false);
        
    }
}