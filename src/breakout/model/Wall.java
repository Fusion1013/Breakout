package breakout.model;

/*
        A wall for the ball to bounce
 */
public class Wall implements IPositionable {

    public Wall(Dir dir, double xPos, double yPos, double wallHeight){
        this.dir = dir;
        this.xPos = xPos;
        this.yPos = yPos;
        this.wallHeight = wallHeight;
    }

    public static final double WALL_WIDTH = 1;

    public Dir dir;
    public double xPos;
    public double yPos;
    public double wallHeight;


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
        return WALL_WIDTH;
    }

    @Override
    public double getHeight() {
        return wallHeight;
    }


    public enum Dir {
        HORIZONTAL, VERTICAL;
    }

}
