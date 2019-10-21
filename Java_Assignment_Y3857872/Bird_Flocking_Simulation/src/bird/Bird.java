// Y3857872
package bird;

// Import Project Classes:
import frame.Canvas;
import geometry.CartesianCoordinate;

// Bird Class:
public class Bird {

    // Respective 'Bird' Class Variables:
    private Canvas canvas;
    private CartesianCoordinate currentPosition;
    private double bearingAngle;
    private boolean penDown;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                Constructors
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    // 'Bird' Class Constructor:
    protected Bird(Canvas canvas) {
        this.canvas = canvas;

        // Randomise bird's position and bearing.
        int randXPos = (int) (Math.random() * canvas.getWidth());
        int randYPos = (int) (Math.random() * canvas.getHeight());
        int randBearing = (int) (Math.random() * 359);

        // Set variables.
        currentPosition = new CartesianCoordinate(randXPos, randYPos);
        bearingAngle = randBearing;
        penDown = false;
    }

    protected Bird(Canvas canvas, int xPos, int yPos, int bearing) {
        this.canvas = canvas;

        // Set variables.
        currentPosition = new CartesianCoordinate(xPos, yPos);
        bearingAngle = bearing;
        penDown = false;
    }

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                Getters / Setters
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public int getXPos() {
        return (int) currentPosition.getX();
    }

    public int getYPos() {
        return (int) currentPosition.getY();
    }

    public double getBearingAngle() {
        return bearingAngle;
    }

    public void setBearingAngle(double newAngle) {
        this.bearingAngle = newAngle;
    }

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                    Bird Class Public / Protected Methods
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * The bird is moved in its current direction for the given number of pixels.
     * If the pen is down when the robot moves, a line will be drawn on the floor.
     */
    protected void move(double distance) {

        CartesianCoordinate newPosition = new CartesianCoordinate(0, 0);

        newPosition.setX(currentPosition.getX() + distance * Math.sin(Math.toRadians(bearingAngle)));
        newPosition.setY(currentPosition.getY() - distance * Math.cos(Math.toRadians(bearingAngle)));

        if (penDown)
            canvas.drawLineBetweenPoints(currentPosition, newPosition);

        // Set new calculated position to the current position.
        currentPosition = newPosition;
    }

    /**
     * Rotates the bird clockwise by the specified angle in degrees.
     */
    protected void turn(double clockWise) {
        bearingAngle = bearingAngle + clockWise;

        // Ensure bearing angle remains within [0 -> 359] degree range.
        if (bearingAngle < 0)
            bearingAngle += 360;
        else if (bearingAngle >= 360)
            bearingAngle -= 360;
    }

    /**
     * Moves the pen off the canvas so that the bird's route isn't drawn for any subsequent movements.
     */
    protected void putPenUp() {
        penDown = false;
    }

    /**
     * Lowers the pen onto the canvas so that the bird's route is drawn.
     */
    protected void putPenDown() {
        penDown = true;
    }

    // Draw generic bird to canvas:
    // - This method is overrided by extended classes to distinguish between birds and predators.
    protected void draw() {
        putPenUp();
        move(4);
        putPenDown();
        turn(150);
        move(10);
        turn(120);
        move(10);
        turn(120);
        move(10);
        turn(-30);
        putPenUp();
        move(-4);
    }

    // Undraw generic bird from canvas:
    // - Not used within the game loop, canvas.clear() is used to increased performance.
    protected void undraw() {
        canvas.removeMostRecentLine();
        canvas.removeMostRecentLine();
        canvas.removeMostRecentLine();
        canvas.repaint();
    }

    /**
     * Updates the bird's position to the other side of the screen if bird is outside the canvas boundary.
     */
    protected void wrapPosition() {

        // If x-position is out of bounds of canvas width, flip to other side of canvas.
        if (getXPos() <= 0)
            currentPosition.setX(canvas.getWidth() - 2);
        else if (getXPos() >= canvas.getWidth())
            currentPosition.setX(2);

        // If y-position is out of bounds of canvas height, flip to other side of canvas.
        if (getYPos() <= 0)
            currentPosition.setY(canvas.getHeight() - 2);
        else if (getYPos() >= canvas.getHeight())
            currentPosition.setY(2);
    }
}
