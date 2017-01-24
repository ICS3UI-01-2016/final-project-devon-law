
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author lawd8918
 */
public class FinalGame extends JComponent implements KeyListener {

    int player = 250;
    int playerSpeed = 5;
    int skySpeed = 2;
    int score = 0;
    int scoreRelative = 0;
    Font scoreFont = new Font("Arial", Font.BOLD, 42);
    Font deadFont = new Font("Arial", Font.ITALIC, 90);
    Font title = new Font("Stencil", Font.BOLD, 60);
    Rectangle[] skyBit = new Rectangle[15];
    Rectangle player1 = new Rectangle(player, 550, 55, 55);
    Rectangle[] bonus = new Rectangle[1];
    boolean left = false;
    boolean right = false;
    boolean space = false;
    boolean levelUp = false;
    boolean gameOver = false;
    boolean playerRight = false;
    boolean playerLeft = false;
    boolean playerStill = true;
    boolean start = false;
    boolean easy = false;
    boolean medium = false;
    boolean hard = false;
    boolean instructions = false;
    double frame = 0;
    BufferedImage[] runRightAnim = {loadImage("animate1.png"), loadImage("animate2.png"), loadImage("animate3.png"), loadImage("animate4.png"), loadImage("animate5.png"), loadImage("animate6.png"), loadImage("animate7.png"), loadImage("animate8.png")};
    BufferedImage[] runLeftAnim = {loadImage("animate11.png"), loadImage("animate22.png"), loadImage("animate33.png"), loadImage("animate44.png"), loadImage("animate55.png"), loadImage("animate66.png"), loadImage("animate77.png"), loadImage("animate88.png")};
    BufferedImage runRight = loadImage("manRight3.png");
    BufferedImage runLeft = loadImage("manLeft3.png");
    BufferedImage manStill = loadImage("manStill3.png");
    BufferedImage background = loadImage("cityDrawing.png");
    //BufferedImage manDead = loadImage("manDead.png");
    BufferedImage brick = loadImage("brickGame.png");
    BufferedImage menuBackground = loadImage("nuclearExplosion.jpg");
    BufferedImage supplyDrop = loadImage("supplyDrop.png");
    BufferedImage standingStill = loadImage("standing1.png");
    BufferedImage manDead = loadImage("dead.png");
    // Height and Width of our game
    static final int WIDTH = 600;
    static final int HEIGHT = 800;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {

        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 
        //Wait for game to start



        if (start == false) {
            g.setColor(Color.black);
            g.drawImage(menuBackground, 0, 0, 600, 800, null);
            g.setColor(Color.red);
            g.setFont(title);
            g.drawString("the apocalypse", 40, 200);
            g.setFont(scoreFont);
            g.setColor(Color.white);
            g.drawString("Press 1 for Easy", 75, 300);
            g.drawString("Press 2 for Medium", 75, 350);
            g.drawString("Press 3 for Hard", 75, 400);
            g.drawString("Hold 'i' for instructions.", 75, 700);

        }
        if (instructions == true) {
            g.setColor(Color.black);
            g.drawImage(menuBackground, 0, 0, 600, 800, null);
            g.setFont(scoreFont);
            g.setColor(Color.white);
            g.drawString("LEFT arrow to move left", 25, 100);
            g.drawString("RIGHT arrow to move right", 25, 150);
            g.drawString("Avoid -> ", 25, 200);
            g.drawImage(brick, 250, 175, 70, 30, null);
            g.drawString("Collect -> ", 25, 300);
            g.drawImage(supplyDrop, 250, 250, 50, 50, null);
        }
        //once game has started
        if (start == true && instructions == false) {
            g.drawImage(background, 0, 0, 600, 800, null);
            g.setColor(Color.red);
            //if not dead
            if (gameOver == false) {
                if (playerStill == true) {
                    g.drawImage(standingStill, player1.x, player1.y, player1.width, player1.height, null);
                }
                if (playerRight == true) {
                    g.drawImage(runRightAnim[(int) frame], player1.x, player1.y, player1.width, player1.height, null);
                }
                if (playerLeft == true) {
                    g.drawImage(runLeftAnim[(int) frame], player1.x, player1.y, player1.width, player1.height, null);
                }
            }
            g.setColor(Color.green);
            for (int i = 0; i < bonus.length; i++) {
                g.drawImage(supplyDrop, bonus[i].x, bonus[i].y, bonus[i].width, bonus[i].height, null);
            }
            for (int i = 0; i < skyBit.length; i++) {
                g.drawImage(brick, skyBit[i].x, skyBit[i].y, skyBit[i].width, skyBit[i].height, null);
            }
            g.setColor(Color.WHITE);
            g.setFont(scoreFont);
            g.drawString("" + score, WIDTH / 2 - 50, 50);
            //if player hits +1000 score
            if (levelUp == true) {
                g.setColor(Color.WHITE);
                g.setFont(scoreFont);
                g.drawString("SPEED UP!!", WIDTH / 2 - 50, 200);
            }
            //player dies
            if (gameOver == true) {

                g.drawImage(manDead, player1.x, player1.y, player1.width, player1.height, null);
                g.setColor(Color.red);
                g.setFont(deadFont);
                g.drawString("GAME OVER", 10, 300);
                g.setColor(Color.white);
                g.setFont(scoreFont);
                g.drawString("Press Space To Restart", 50, 400);
            }
        }
    }
    // GAME DRAWING ENDS HERE

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            File file = new File(filename);
            img = ImageIO.read(file);
        } catch (Exception e) {
            //if there is an error print it
            e.printStackTrace();
        }
        return img;
    }

    public void reset() {
        //reset score
        scoreRelative = 0;
        score = 0;
        gameOver = false;
        levelUp = false;
        start = false;

        //reset player position
        Rectangle player1 = new Rectangle(player, 550, 50, 50);
        int skyY = -30;
        Random randGen = new Random();

        for (int i = 0; i < skyBit.length; i++) {
            //reset sky pieces 
            int skyX = randGen.nextInt(WIDTH - 50);
            skyBit[i] = new Rectangle(skyX, skyY, 70, 30);
            skyY = skyY - 100;
            skySpeed = 2;
        }
        for (int i = 0; i < bonus.length; i++) {
            bonus[i].y = -50;
        }
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        //set up sky pieces 
        int skyY = -30;
        //randomize X-value
        Random randGen = new Random();
        for (int i = 0; i < skyBit.length; i++) {
            int skyX = randGen.nextInt(WIDTH - 50);
            skyBit[i] = new Rectangle(skyX, skyY, 70, 30);
            skyY = skyY - 100;
        }
        for (int i = 0; i < bonus.length; i++) {
            int skyX = randGen.nextInt(WIDTH - 50);
            bonus[i] = new Rectangle(skyX, 100, 50, 50);
        }

        // the main game loop section
        // game will end if you set done = false;

        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();


            frame = frame + 0.25;
            if (frame == 8) {
                frame = 0;
            }

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            if (start == true) {
                if (gameOver == false) {
                    score++;
                    if (score >= scoreRelative + 1000) {
                        skySpeed = skySpeed + 1;
                        scoreRelative = score;
                        levelUp = true;
                    }
                    if (score == scoreRelative + 150) {
                        levelUp = false;
                    }
                    for (int i = 0; i < skyBit.length; i++) {
                        skyBit[i].y = skyBit[i].y + skySpeed;
                        if (skyBit[i].y >= 800) {
                            skyBit[i].y = -30;
                            int skyX = randGen.nextInt(WIDTH - 50);
                            skyBit[i].x = skyX;
                        }
                        if (player1.intersects(skyBit[i])) {
                            gameOver = true;
                        }
                    }
                    for (int i = 0; i < bonus.length; i++) {
                        bonus[i].y = bonus[i].y + 1;
                        if (bonus[i].y >= 800) {
                            bonus[i].y = -50;
                            int skyX = randGen.nextInt(WIDTH);
                            bonus[i].x = skyX;
                        }
                        //supply drop collected
                        if (player1.intersects(bonus[i])) {
                            score = score + 250;
                            bonus[i].y = -50;
                            int skyX = randGen.nextInt(WIDTH);
                            bonus[i].x = skyX;

                        }
                    }


                    if (left == true && player1.x != 0) {
                        player1.x = player1.x - playerSpeed;
                    }
                    if (right == true && player1.x != 550) {
                        player1.x = player1.x + playerSpeed;
                    }
                }
            }
            if (space == true) {
                reset();
            }
            // GAME LOGIC ENDS HERE 

            // update the drawing (calls paintComponent)
            repaint();



            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
            }


        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        FinalGame game = new FinalGame();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        //add the key listener
        frame.addKeyListener(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            playerStill = false;
            playerLeft = true;
            left = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            playerStill = false;
            playerRight = true;
            right = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            space = true;
        }

        if (key == KeyEvent.VK_1) {
            easy = true;
            skySpeed = 1;
            start = true;
        }
        if (key == KeyEvent.VK_2) {
            medium = true;
            skySpeed = 2;
            start = true;
        }
        if (key == KeyEvent.VK_3) {
            hard = true;
            skySpeed = 4;
            start = true;
        }
        if (key == KeyEvent.VK_I) {
            instructions = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            playerStill = true;
            playerLeft = false;
            left = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            playerStill = true;
            playerRight = false;
            right = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            space = false;
        }
        if (key == KeyEvent.VK_I) {
            instructions = false;
        }

    }
}