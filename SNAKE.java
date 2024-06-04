import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

public class SNAKE extends JPanel implements ActionListener {
    private static int height;
    private static int width;
    private static int cellsize = 25;
    private static boolean gamestarted = false;
    private static boolean gameover = false;
    private LinkedList<Gamepoint> snakebody = new LinkedList<Gamepoint>();
    private Direction direction= Direction.RIGHT;
    private Direction swdirection;
    private Gamepoint food;
    private Random rand = new Random();
    private static int score = 0;


    public SNAKE(int height, int width) {
        this.height = height;
        this.width = width;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Score: " + score,50,50);


//         drawGrid(g);
        if (!gamestarted && !gameover) {
            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(30F));
            g.drawString("Press Space to start", 220, 200);
        }
        else if(gamestarted && !gameover){
            g.setColor(Color.RED);
            g.fillRect(food.x, food.y, cellsize, cellsize);
            g.setColor(Color.green);
            for(Gamepoint point :snakebody ){
                g.fillRect(point.x, point.y, cellsize, cellsize);
            }

        }
        else{
            g.setColor(Color.RED);

            g.setFont(g.getFont().deriveFont(25F));
            g.drawString("GAMEOVER!!! Press Space to start:", 200, 200);
            g.drawString("HIGHSCORE:"+score, 300, 250);
        }

    }
//    private void drawGrid(Graphics g) {
//        g.setColor(Color.gray);
//        for (int i = 0; i <= height; i += cellsize) {
//            g.drawLine(0, i, height, i);
//        }
//        for (int i = 0; i <= height; i += cellsize) {
//            g.drawLine(i, width, i, 0);
//        }
//    }


    public void startGame(){
        setFocusable(true);
        resetgame();
        addKeyListener(new KeyAdapter(){
           public void keyPressed(KeyEvent e) {
               extractmethod(e);
           }
        });
        new Timer(60,this).start();
    }
    private void extractmethod(KeyEvent e) {
        if (!gamestarted) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {


                gamestarted = true;

            }
        }
        else if (gamestarted && !gameover) {



            switch (e.getKeyCode()) {

                case KeyEvent.VK_UP:
                   if(direction != Direction.DOWN){
                       direction = Direction.UP;
                   }
                    break;

                case KeyEvent.VK_DOWN:
                    if(direction != Direction.UP){
                        direction = Direction.DOWN;
                    }
                    break;

                case KeyEvent.VK_LEFT:
                    if(direction != Direction.RIGHT){
                        direction = Direction.LEFT;
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if(direction != Direction.LEFT){
                        direction = Direction.RIGHT;
                    }

                    break;
            }


        }
        else{
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
               resetgame();
                gameover = false;
                gamestarted = true;

            }
        }
    }



    public void move() {
        swdirection = direction;
    final Gamepoint currentHead = snakebody.getFirst();
        System.out.println(currentHead.x);
        System.out.println(swdirection);
    final Gamepoint newhead = switch(swdirection){
        case UP -> new Gamepoint(currentHead.x,currentHead.y - cellsize);
        case DOWN -> new Gamepoint(currentHead.x,currentHead.y + cellsize);
        case RIGHT -> new Gamepoint(currentHead.x+cellsize,currentHead.y);
        case LEFT -> new Gamepoint(currentHead.x-cellsize,currentHead.y);
    };
//    final Gamepoint nexthead = new Gamepoint(currenthead.x+cellsize, currenthead.y);

        snakebody.addFirst(newhead);



        if (newhead.equals(food)) {
            generatefood();
            score();
        } else {
            snakebody.removeLast();
        }



    if(collisiondetect()){
        gameover = true;
        System.out.println("Collison detected");


    }

    }
    private boolean collisiondetect() {

       final Gamepoint head = snakebody.getFirst();
       final var invalidwidth = (head.x<0) || (head.x>=800);
       final var invalidheidht = (head.y<0) || (head.y>=800);
        for(int i = 1;i<snakebody.size();i++){
            if(head.equals(snakebody.get(i))){
                return true;
            }
        }
       return invalidwidth || invalidheidht;
    }

    private void resetgame(){
        snakebody.clear();
        snakebody.add(new Gamepoint(200,200));
        score = 0;
        generatefood();

    }
    private void generatefood() {
        do {
            food = new Gamepoint(rand.nextInt((width / cellsize)) * cellsize, rand.nextInt((height / cellsize)) * cellsize);
        }
        while (snakebody.contains(food));
    }

    public void score(){
        score+=1;
    }


    @Override
    //timer class bata repaint yeha garni
    public void actionPerformed(ActionEvent e) {
            if(gamestarted && !gameover){
                move();
            }

            repaint();

    }
    private record Gamepoint(int x, int y){}

    private enum Direction{
        UP, DOWN, LEFT, RIGHT;
    }
}


