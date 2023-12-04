import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame() {
       this.add(new GamePanel());
       this.setTitle("Snake Game");
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.pack();
       this.setResizable(false);
       this.setVisible(true);
       this.setLocationRelativeTo(null);
    }
}
