package breakout.model;

import java.util.List;

public class Base {
    public enum Side{
        NONE, TOP, BOTTOM, LEFT, RIGHT;
    }


    public int ballsLeft = 5;
    int points;

    // Objects needed for the model
    public List<Wall> walls;
    public List<Brick> bricks;

    // Variables needed for paddle movement
    public int paddleDX;
}