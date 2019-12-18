package breakout.model;

/*
        A wall for the ball to bounce
 */
public class Wall implements IPositionable {

    public Wall(double xPos, double yPos, double width, double height){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    private double xPos;
    private double yPos;
    private double width;
    private double height;


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
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }
}
