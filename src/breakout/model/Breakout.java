package breakout.model;


import breakout.event.ModelEvent;
import breakout.event.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 *  Overall all logic for the Breakout Game
 *  Model class representing the full game
 *  This class should use other objects delegate work.
 *
 *  NOTE: Nothing visual here
 *
 */
public class Breakout {

    public static final double GAME_WIDTH = 400;
    public static final double GAME_HEIGHT = 400;
    public static final double BALL_SPEED_FACTOR = 1.05; // Increase ball speed
    public static final long SEC = 1_000_000_000;  // Nano seconds used by JavaFX
    public static final Ball ball = new Ball(GAME_WIDTH / 2 - (Ball.BALL_WIDTH / 2), GAME_HEIGHT - 100);
    public static final Paddle paddle = new Paddle(GAME_WIDTH / 2 - (Paddle.PADDLE_WIDTH / 2), ball.getY() + ball.getWidth());


    private int ballsLeft = 5;
    int points;

    // Objects needed for the model
    private List<Wall> walls;
    private List<Brick> bricks;

    // Variables needed for paddle movement
    private int dx;

    // Constructor that accepts all objects needed for the model
    public Breakout(List<Wall> walls, List<Brick> bricks){
        this.walls = walls;
        this.bricks = bricks;

        Random r = new Random();
        //ball.setX(r.nextInt((int)GAME_WIDTH));
        ball.setAngle(Math.PI / 4); // TEMP
    }


    // --------  Game Logic -------------

    private long timeForLastHit;         // To avoid multiple collisions

    public void update(long now) {
        // Hax
        paddle.setX(ball.getX() - paddle.getWidth() / 2);

        // Move Paddle
        movePaddle(dx);

        // Move Ball
        moveBall();

        // Collisions
        if (timeForLastHit <= now - (SEC / 1000)){
            handleCollisions(now);
        }

        // Game Over
        if (ball.getY() >= GAME_HEIGHT){
            gameOver();
        }
    }

    // ----- Helper methods--------------

    // Used for functional decomposition
    private void gameOver(){
        if (getBallsLeft() > 0){
            // Spawns a new ball
            ball.setX(GAME_WIDTH / 2 - (Ball.BALL_WIDTH / 2));
            ball.setY(GAME_HEIGHT - 100);
            ball.setAngle(Math.PI / 4);

            ballsLeft -= 1;
        }
        else{
            // TODO
        }
    }

    private void handleCollisions(long now){
        double xPos = ball.getX();
        double yPos = ball.getY();

        //System.out.println(ball.getAngle());

        // TODO: Collision with walls: Actually use the walls
        // TODO: Improve collision with paddle
        // TODO: Fix: Ball disappears when it hits a corner
        // TODO: Collision with bricks from all sides
        // TODO: Collision bounce angle for multiple directions is wrong depending on the entry angle

        // Walls (Left/Right/Top)
        if (xPos + ball.getWidth() >= GAME_WIDTH){ // Right Wall
            timeForLastHit = now;
            ball.setAngle(Math.PI - ball.getAngle());
        }
        else if (xPos <= 0){ // Left Wall
            ball.setAngle(Math.PI - ball.getAngle());
            timeForLastHit = now;
        }
        else if (yPos <= 0){
            //ball.setAngle(Math.PI + ball.getAngle());
            ball.setAngle(ball.getAngle() + Math.PI / 2);
            //ball.setAngle(Math.PI - ball.getAngle());
            timeForLastHit = now;
        }
        // Collision with paddle
        else if (yPos >= paddle.getY() - paddle.getHeight() / 2){
            if (yPos <= paddle.getY() + paddle.getHeight() / 2){
                if (xPos >= paddle.getX() && xPos <= paddle.getX() + paddle.getWidth()){
                    ball.setAngle(ball.getAngle() + Math.PI / 2);
                    timeForLastHit = now;
                }
            }
        }

        // Collision with bricks
        for (int i = 0; i < bricks.size(); i++){
            Brick b = bricks.get(i);
            if ((yPos >= b.getY() && yPos <= b.getY() + b.getHeight()) && (xPos >= b.getX() && xPos <= b.getX() + b.getWidth())){
                ball.setAngle(ball.getAngle() + Math.PI / 2);
                bricks.remove(i);
                i--;
                points++;
            }
        }
    }

    private void moveBall(){
        double cxPos = ball.getX();
        double cyPos = ball.getY();
        double angle = ball.getAngle();

        double nxPos = cxPos + Math.cos(angle) * Ball.BALL_SPEED;
        double nyPos = cyPos - Math.sin(angle) * Ball.BALL_SPEED;

        ball.setX(nxPos);
        ball.setY(nyPos);
    }

    public void movePaddle(int dx){
        double cxPos = paddle.getX();
        double nxPos = cxPos + (dx * Paddle.PADDLE_SPEED);

        // Normalizes the positions
        if (nxPos < 0){
            nxPos = 0;
        }
        else if (nxPos + paddle.getWidth() > GAME_WIDTH){
            nxPos = GAME_WIDTH - paddle.getWidth();
        }

        paddle.setX(nxPos);
    }


    // --- Used by GUI  ------------------------

    public void setDx(int dx){
        this.dx = dx;
    }

    public List<IPositionable> getPositionables() {
        List<IPositionable> posis = new ArrayList<>();
        for (Wall w : walls){
            posis.add(w);
        }
        for (Brick b : bricks){
            posis.add(b);
        }
        posis.add(ball);
        posis.add(paddle);
        return posis;
    }

    public int getPoints() {
        return points;
    }

    public int getBallsLeft() {
        return ballsLeft;
    }




}
