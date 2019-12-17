package breakout.model;


import breakout.event.EventBus;
import breakout.event.IEventHandler;
import breakout.event.ModelEvent;
import breakout.view.BreakoutGUI;

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
    private int paddleDX;

    // Constructor that accepts all objects needed for the model
    public Breakout(List<Wall> walls, List<Brick> bricks){
        this.walls = walls;
        this.bricks = bricks;

        Random r = new Random();
    }


    // --------  Game Logic -------------

    private long timeForLastHit;         // To avoid multiple collisions

    public void update(long now) {
        // Hax
        paddle.setX(ball.getX() - paddle.getWidth() / 2);

        // Move Paddle
        movePaddle(paddleDX);

        // Move Ball
        //moveBall(ball.getDX(), ball.getDY());

        for (int i = 0; i < Ball.BALL_SPEED * 10; i++){
            moveBall(ball.getDX() / (Ball.BALL_SPEED * 10), ball.getDY() / (Ball.BALL_SPEED * 10));
            if (timeForLastHit <= now - (SEC / 30)){
                handleCollisions(now);
            }
        }

        // Collisions
        if (timeForLastHit <= now - (SEC / 30)){
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

            ballsLeft -= 1;
        }
        else{
            // TODO
        }
    }

    private void handleCollisions(long now){
        double xPos = ball.getX();
        double yPos = ball.getY();

        if (xPos + ball.getWidth() >= GAME_WIDTH || xPos <= 0){ // Left/Right Wall
            ball.setDX(-ball.getDX());
            timeForLastHit = now;
        }
        else if (yPos <= 0){ // Top Wall
            ball.setDY(-ball.getDY());
            timeForLastHit = now;
        }
        // Collision with paddle
        else if (yPos >= paddle.getY() - paddle.getHeight() / 2){
            if (yPos <= paddle.getY() + paddle.getHeight() / 2){
                if (xPos >= paddle.getX() && xPos <= paddle.getX() + paddle.getWidth()){
                    ball.setDY(-ball.getDY());
                    timeForLastHit = now;

                    // Call Sound
                    EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_PADDLE));
                }
            }
        }

        // Collision with bricks
        for (int i = 0; i < bricks.size(); i++){
            Brick b = bricks.get(i);
            if ((yPos >= b.getY() - 1 && yPos <= b.getY() + b.getHeight() + 2) && (xPos >= b.getX() - 1 && xPos <= b.getX() + b.getWidth() + 2)){
                ball.setDY(-ball.getDY());
                timeForLastHit = now;
                bricks.remove(i);
                i--;
                points++;

                // Call Sound
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_BRICK));
            }
        }
    }

    private void moveBall(double dx, double dy){
        double cxPos = ball.getX();
        double cyPos = ball.getY();

        double nxPos = cxPos + dx;
        double nyPos = cyPos + dy;

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

    public void setPaddleDX(int paddleDX){
        this.paddleDX = paddleDX;
    }

    public List<IPositionable> getPositionables() {
        List<IPositionable> posables = new ArrayList<>();
        for (Wall w : walls){
            posables.add(w);
        }
        for (Brick b : bricks){
            posables.add(b);
        }
        posables.add(ball);
        posables.add(paddle);
        return posables;
    }

    public int getPoints() {
        return points;
    }

    public int getBallsLeft() {
        return ballsLeft;
    }




}
