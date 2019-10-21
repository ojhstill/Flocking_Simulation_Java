// Y3857872
package bird;

// Import Project Classes:
import frame.Canvas;

// Dynamic Bird Class:
public class DynamicBird extends Bird {

    // Respective 'Dynamic Bird' Class Variables:
    private double velocity = 100;
    private double maxVelocity = 100;
    private double angularVel = 0;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                Constructors
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected DynamicBird(Canvas canvas) {
        super(canvas);
    }

    protected DynamicBird(Canvas canvas, int xPos, int yPos, int bearing) {
        super(canvas, xPos, yPos, bearing);
    }

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                Getters / Setters
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double newVelocity) {
        this.velocity = newVelocity;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public double getAngularVel() {
        return angularVel;
    }

    public void setAngularVel(double newAngularVel) {
        this.angularVel = newAngularVel;
    }

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                        Dynamic Bird Class Public Methods
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    // Update DynamicBird Method:
    // - Changes bird's position and bearing angle based on the velocity parameters.
    // - Updates parameters from deltaTime.
    public void update(long deltaTime) {

        // Calculate turning angle from angular velocity and 'delta time' (ms).
        turn(angularVel * (deltaTime / (float) 1000));

        // Normalise velocity value.
        if (velocity < maxVelocity / 10)
            velocity = maxVelocity / 10;
        else if (velocity > maxVelocity * 3)
            velocity = maxVelocity;

        // Calculate distance from velocity and 'delta time' (ms).
        move(velocity * (deltaTime / (float) 1000));

        // Wrap bird around the canvas if its current position is off screen.
        wrapPosition();
    }
}