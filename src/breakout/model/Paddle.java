package breakout.model;

import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;

/*
 * A Paddle for the Breakout game
 *
 */
public class Paddle implements IPositionable {

    public static final double PADDLE_WIDTH = 60;
    public static final double PADDLE_HEIGHT = 10;
    public static final double PADDLE_SPEED = 5;

    public Paddle(double xPos, double yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public double xPos;
    public double yPos;

    public void setX(double xPos){
        this.xPos = xPos;
    }

    @Override
    public double getX() {
        return xPos;
    }

    @Override
    public double getY() {
        return yPos;
    }

    @Override
    public double getWidth() {
        return PADDLE_WIDTH;
    }

    @Override
    public double getHeight() {
        return PADDLE_HEIGHT;
    }
}
