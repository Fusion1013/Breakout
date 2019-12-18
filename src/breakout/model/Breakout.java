package breakout.model;


import breakout.event.EventBus;
import breakout.event.IEventHandler;
import breakout.event.ModelEvent;
import breakout.view.BreakoutGUI;
import org.w3c.dom.css.Rect;

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
public class Breakout extends Base {

    public static final double GAME_WIDTH = 400;
    public static final double GAME_HEIGHT = 400;
    public static final double BALL_SPEED_FACTOR = 1.05; // Increase ball speed
    public static final long SEC = 1_000_000_000;  // Nano seconds used by JavaFX
    public static final Ball ball = new Ball(GAME_WIDTH / 2 - (Ball.BALL_WIDTH / 2), GAME_HEIGHT - 100);
    public static final Paddle paddle = new Paddle(GAME_WIDTH / 2 - (Paddle.PADDLE_WIDTH / 2), ball.getY() + ball.getWidth());

    // Constructor that accepts all objects needed for the model
    public Breakout(List<Wall> walls, List<Brick> bricks){
        this.walls = walls;
        this.bricks = bricks;
    }


    // --------  Game Logic -------------

    public void update(long now) {
        // Hax
        paddle.setX(ball.getX() - paddle.getWidth() / 2);

        // Move Paddle
        movePaddle(paddleDX);

        // Move Ball
        moveBall(ball.getDX(), ball.getDY());

        // Collisions
        handleCollisions();

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
            ball.reset(GAME_WIDTH / 2 - (Ball.BALL_WIDTH / 2), GAME_HEIGHT - 100);

            ballsLeft -= 1;
        }
        else{
            // TODO
        }
    }

    private void handleCollisions(){

        // Collision with walls
        for (Wall w : walls){
            handleBallDirection(circleRectangleCollision(w));
        }

        // Collision with bricks
        for (int i = 0; i < bricks.size(); i++){
            Side side = circleRectangleCollision(bricks.get(i));

            // If collision, play sound and remove brick
            if (side != Side.NONE){
                bricks.remove(i);
                i--;
                points++;

                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_BRICK));
            }

            // Edit direction of the ball
            handleBallDirection(side);
        }

        // Collision with paddle
        Side side = circleRectangleCollision(paddle);

        // If collision, play sound
        if (side != Side.NONE){
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_PADDLE));
        }

        // Edit direction of the ball
        handleBallDirection(side);
    }

    private void handleBallDirection(Side side){
        if (side == Side.TOP || side == Side.BOTTOM){
            ball.setDY(-ball.getDY());
        }
        else if(side == Side.LEFT || side == Side.RIGHT){
            ball.setDX(-ball.getDX());
        }
    }

    private Side circleRectangleCollision(IPositionable rect){
        // Variables needed for calculation
        double cx = ball.getX() + ball.getWidth() / 2;
        double cy = ball.getY() + ball.getWidth() / 2;

        double rx = rect.getX();
        double ry = rect.getY();

        double testX = cx;
        double testY = cy;

        double rw = rect.getWidth();
        double rh = rect.getHeight();

        Side side = Side.NONE;

        // Checks which edge of the rectangle the circle is closest to
        if (cx < rx){ // left edge
            testX = rx;
            side = Side.LEFT;
        }
        else if (cx > rx+rw){ // right edge
            testX = rx+rw;
            side = Side.RIGHT;
        }

        if (cy < ry){ // top edge
            testY = ry;
            side = Side.TOP;
        }
        else if (cy > ry+rh){ // bottom edge
            testY = ry+rh;
            side = Side.BOTTOM;
        }

        // Calculates the distance from the circle to the rect
        double distX = cx-testX;
        double distY = cy-testY;
        double distance = Math.sqrt( (distX*distX) + (distY*distY) );

        // Returns the result
        if (distance <= ball.getWidth() / 2) {
            return side;
        }
        else {
            return Side.NONE;
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

class Circle{
    public Circle(double x, double y, double r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    private double x;
    private double y;
    private double r;

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getRadius(){
        return r;
    }
}

class Rectangle{
    public Rectangle(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    private double x;
    private double y;
    private double width;
    private double height;

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }
}