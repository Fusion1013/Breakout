package breakout.model;

/*
 *   A brick for the rows of bricks
 */

public class Brick implements IPositionable {
    public Brick(double xPos, double yPos, int points){
        this.xPos = xPos;
        this.yPos = yPos;
        this.points = points;
    }

    public static final double BRICK_WIDTH = 20;
    public static final double BRICK_HEIGHT = 10;

    public double xPos;
    public double yPos;
    public int points;

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
        return BRICK_WIDTH;
    }

    @Override
    public double getHeight() {
        return BRICK_HEIGHT;
    }
}

