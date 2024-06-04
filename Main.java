import javax.swing.*;

public class Main {
    private static final int height = 800;
    private static final int width = 800;
    public static void main(String[] args) {

        SNAKE snake = new SNAKE(height, width);
        
        JFrame frame = new JFrame("Snake Game" );
        snake.startGame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(snake);
        frame.pack();



    }
}


