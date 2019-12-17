package breakout.model;

import java.util.Random;

import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;

/*
 *    A Ball for the Breakout game
 */

public class Ball implements IPositionable {
    public static final double BALL_WIDTH = 8.0;
    public static final double BALL_SPEED = 4.0;
    public static final double START_ANGLE = Math.PI / 4;

    public Ball(double x, double y){
        this.xPos = x;
        this.yPos = y;

        this.dx = Math.cos(START_ANGLE) * BALL_SPEED;
        this.dy = -Math.sin(START_ANGLE) * BALL_SPEED;
    }

    private double xPos;
    private double yPos;

    private double dx;
    private double dy;

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

    public void setX(double xPos){
        this.xPos = xPos;
    }

    public void setY(double yPos){
        this.yPos = yPos;
    }

    public double getDX() {
        return dx;
    }

    public double getDY() {
        return dy;
    }

    public void setDX(double dx) {
        this.dx = dx;
    }

    public void setDY(double dy) {
        this.dy = dy;
    }
}
