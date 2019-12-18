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
    public static final double ANGLE_RANGE = Math.PI / 4;

    public Ball(double x, double y){
        reset(x, y);
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

    public void reset(double xPos, double yPos){
        Random r = new Random();
        double deltaAngle = r.nextDouble() * ANGLE_RANGE;

        this.dx = Math.cos(deltaAngle / 2 + ANGLE_RANGE / 2) * BALL_SPEED;
        this.dy = -Math.sin(deltaAngle / 2 + ANGLE_RANGE / 2) * BALL_SPEED;

        this.xPos = xPos;
        this.yPos = yPos;
    }
}
