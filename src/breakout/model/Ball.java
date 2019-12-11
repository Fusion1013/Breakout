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

    public Ball(double x, double y){
        this.xPos = x;
        this.yPos = y;
    }

    private double xPos;
    private double yPos;
    private double angle;

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

    public void setAngle(double angle){
        this.angle = angle;
    }

    public double getAngle(){
        return angle;
    }
}
