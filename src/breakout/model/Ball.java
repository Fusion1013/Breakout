package breakout.model;

import java.util.Random;

import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;

/*
 *    A Ball for the Breakout game
 */

public class Ball implements IPositionable {
    public static final double BALL_WIDTH = 8.0;
    public static final double BALL_SPEED = 10.0;

    public Ball(double x, double y){
        this.xPos = x;
        this.yPos = y;
    }

    public double xPos;
    public double yPos;

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
        return BALL_WIDTH;
    }

    @Override
    public double getHeight() {
        return BALL_WIDTH;
    }
}
