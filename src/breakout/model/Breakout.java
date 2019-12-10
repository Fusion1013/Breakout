package breakout.model;


import breakout.event.ModelEvent;
import breakout.event.EventBus;

import java.util.ArrayList;
import java.util.List;

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
    public static final Paddle paddle = new Paddle(GAME_WIDTH / 2 - (Paddle.PADDLE_WIDTH / 2), ball.yPos + ball.getWidth());


    private int ballsLeft = 5;
    int points;

    // Objects needed for the model
    private List<Wall> walls;
    private List<Brick> bricks;

    // Constructor that accepts all objects needed for the model
    public Breakout(List<Wall> walls, List<Brick> bricks){
        this.walls = walls;
        this.bricks = bricks;
    }


    // --------  Game Logic -------------

    private long timeForLastHit;         // To avoid multiple collisions

    public void update(long now) {
        // TODO  Main game loop, start functional decomposition from here
        // MOVE PADDLE
        // MOVE BALL
    }

    // ----- Helper methods--------------

    // Used for functional decomposition



    // --- Used by GUI  ------------------------

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
