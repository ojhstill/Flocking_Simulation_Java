// Y3857872
package bird;

// Import Java Classes:
import java.util.List;

// Import Project Classes:
import frame.Canvas;
import geometry.CartesianCoordinate;
import static tools.Utils.*;

// Predator Bird Class:
public class Predator extends DynamicBird {

    // Relative hunting parameters:
    private CartesianCoordinate preyPos = new CartesianCoordinate(0,0);    // Coordinates of the chasing bird.
    private final int radius = 150;         // Universal radius of local proximity for each predator.
    private int updateCount = 0;            // Counter to periodically randomise the roaming behaviour.
    private double roamAng = 0;             // Angular velocity of roaming behaviour.
    private int huntedBirdIndex;

    private boolean chasing = false;        // Flag to signify chasing behaviour.
    private boolean hungry = true;          // Flag to signify hungry behaviour.

    // Hunting Analysis:
    private boolean radarVisible;       // Draws the local proximity perimeter of the bird.
    private boolean vectorVisible;      // Draws the velocity vector of the bird.
    private boolean lCentreVisible;     // Draws a line from the bird to the local centre.

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                Constructors
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public Predator(Canvas canvas) {
        super(canvas);
    }

    public Predator(Canvas canvas, int xPos, int yPos, int bearing) {
        super(canvas, xPos, yPos, bearing);
    }

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                Getters / Setters
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void setRadarVisible(boolean radarVisible) {
        this.radarVisible = radarVisible;
    }

    public void setVectorVisible(boolean vectorVisible) {
        this.vectorVisible = vectorVisible;
    }

    public void setLocalCentreVisible(boolean lCentreVisible) {
        this.lCentreVisible = lCentreVisible;
    }

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                        Hunting Parameter Public Methods
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public void draw() {
        putPenUp();
        move(10);
        putPenDown();
        turn(150);
        move(20);
        turn(120);
        //putPenUp();
        move(20);
        //putPenDown();
        turn(120);
        move(20);
        turn(-30);
        putPenUp();
        move(-10);

        if (radarVisible) {
            move(radius);
            turn(90);
            move(-15);
            for (int i = 0; i < 10; i++) {
                move(18 * (2 * Math.PI * radius) / 360);
                turn(18);
                putPenDown();
                move(18 * (2 * Math.PI * radius) / 360);
                turn(18);
                putPenUp();
            }
            move(15);
            turn(-90);
            move(-radius);
        }

        if (vectorVisible) {
            putPenDown();
            turn(getAngularVel());
            move(getVelocity() / 5);
            putPenUp();
            move(-getVelocity() / 5);
            turn(-getAngularVel());
        }

        if (lCentreVisible && chasing) {
            double xDisplacement = preyPos.getX() - this.getXPos();
            double yDisplacement = this.getYPos() - preyPos.getY();
            double displacement = displacement(xDisplacement, yDisplacement);
            if (displacement < radius) {
                double turningAngle = relativeTurningAngle(xDisplacement, yDisplacement, this.getBearingAngle());
                turn(turningAngle);
                putPenDown();
                move(displacement);
                putPenUp();
                move(-displacement);
                turn(-turningAngle);
            }
        }
    }

    // Hunt Method:
    // - Defines the main hunting method.
    // - Updates the predator's state behaviour, velocity, and angular velocity, based only on bird positions.
    public void hunt(List<IntelligentBird> birds) {

        // Periodically randomise roaming angular velocity to allow birds to roam freely.
            // (These values will be overwritten if predator is hunting)
        if (updateCount == 120) {
            roamAng = randomWithLimits(-60, 60);
            hungry = true;
            updateCount = 0;
        }
        updateCount++;

        if (hungry) {

            chasing = false;

            for (int i = 0; i < birds.size() - 1; i++) {

                if (displacement(birds.get(i).getXPos(), birds.get(i).getYPos(), this.getXPos(), this.getYPos()) < radius) {
                    // If localPrey size is greater than 0, start to chase.
                    chasing = true;

                    double huntedBirdDisplacement = radius;
                    double birdDisplacement = displacement(birds.get(i).getXPos(), birds.get(i).getYPos(), this.getXPos(), this.getYPos());

                    // If a closer bird has been found, set to new hunted bird.
                    if (birdDisplacement < huntedBirdDisplacement) {
                        huntedBirdDisplacement = birdDisplacement;
                        huntedBirdIndex = i;
                    }

                    boolean birdHunted = false;

                    // Check if bird is within eating distance of predator.
                    if (displacement(birds.get(i).getXPos(), birds.get(i).getYPos(), this.getXPos(), this.getYPos()) < 8) {
                        huntedBirdIndex = i;
                        birdHunted = true;
                    }

                    // If bird is within distance and predator is hungry, remove bird.
                    if (birdHunted) {
                        birds.remove(huntedBirdIndex);
                        chasing = false;
                        hungry = false;
                    }
                }
            }

            // If bird is local, ignore roaming behaviour and chase.
            if (chasing) {

                preyPos = new CartesianCoordinate(birds.get(huntedBirdIndex).getXPos(), birds.get(huntedBirdIndex).getYPos());

                // 'X' displacement is positive EAST, and 'Y' displacement is positive NORTH.
                double xDisplacement = preyPos.getX() - this.getXPos();
                double yDisplacement = this.getYPos() - preyPos.getY();

                double huntAng = relativeTurningAngle(xDisplacement, yDisplacement, this.getBearingAngle());
                setAngularVel(huntAng * 1.4); // Increased turning ability.

                double huntVel = radius - displacement(xDisplacement, yDisplacement);
                setVelocity(getMaxVelocity() + huntVel);

                return;
            }
        }

        // If no birds are local to hunt start to roam freely.
        setAngularVel(roamAng);
        setVelocity(getMaxVelocity());
    }
}
