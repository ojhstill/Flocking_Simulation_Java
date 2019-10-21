// Y3857872
package bird;

// Import Java Classes:
import java.util.ArrayList;
import java.util.List;

// Import Project Classes:
import frame.Canvas;
import geometry.CartesianCoordinate;
import static tools.Utils.*;

public class IntelligentBird extends DynamicBird {

    // Relative Flocking Parameters:
    private CartesianCoordinate localCentre = new CartesianCoordinate(0,0); // Coordinates of the local centre of mass relative to each bird.
    private double cohesion = 0;    // Universal cohesion parameter (0 -> 100) set via 'birdCohesionSlider'.
    private double separation = 0;  // Universal separation parameter (0 -> 100) set via 'birdSeparaionSlider'.
    private double alignment = 0;   // Universal alignment parameter (0 -> 100) set via 'birdAlignmentSlider'.
    private double dynamics = 0;    // Universal dynamic angular velocity for all birds, set via 'updateCount' in the game loop.
    private int radius = 100;       // Universal radius of local proximity for each bird.
    private int updateCount = 0;    // Counter to periodically randomise the roaming behaviour.

    // Flocking Behaviours:
    private double cohesAng;        // Angular velocity of the cohesion parameter.
    private double separaAng;       // Angular velocity of the separation parameter.
    private double alignAng;        // Angular velocity of the alignment parameter.
    private double roamAng;         // Angular velocity of the roaming parameter.
    private double cohesVel;        // Velocity of the cohesion parameter.
    private double separaVel;       // Velocity of the cohesion parameter.

    private boolean flocking;   // Flag to signify flocking behaviour.
    private boolean evading;    // Flag to signify evasion behaviour.

    // Flocking Analysis:
    private boolean radarVisible = false;       // Draws the local proximity perimeter of the bird.
    private boolean vectorVisible = false;      // Draws the velocity vector of the bird.
    private boolean lCentreVisible = false;     // Draws a line from the bird to the local centre.

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                Constructors
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public IntelligentBird(Canvas canvas) {
        super(canvas);
    }

    public IntelligentBird(Canvas canvas, int xPos, int yPos, int bearing) {
        super(canvas, xPos, yPos, bearing);
    }

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                Getters / Setters
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void setCohesion(double cohesion) {
        this.cohesion = cohesion;
    }

    public void setSeparation(double separation) {
        this.separation = separation;
    }

    public void setAlignment(double alignment) {
        this.alignment = alignment;
    }

    public void setDynamics(double dynamics) {
        this.dynamics = dynamics;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setRadarVisible(boolean radiusVisable) {
        this.radarVisible = radiusVisable;
    }

    public void setVectorVisible(boolean vectorVisible) {
        this.vectorVisible = vectorVisible;
    }

    public void setLocalCentreVisible(boolean lCentreVisable) {
        this.lCentreVisible = lCentreVisable;
    }

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                        Intelligent Bird Class Public Methods
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public void draw() {
        putPenUp();
        move(2);
        putPenDown();
        turn(150);
        move(6);
        turn(120);
        putPenUp();
        move(6);
        putPenDown();
        turn(120);
        move(6);
        turn(-30);
        putPenUp();
        move(-2);

        // Draw proximity radius if set visible.
        if (radarVisible) {
            move(radius);
            turn(90);
            move(-20);
            for (int i = 0; i < 10; i++) {
                move(18 * (2 * Math.PI * radius) / 360);
                turn(18);
                putPenDown();
                move(18 * (2 * Math.PI * radius) / 360);
                turn(18);
                putPenUp();
            }
            move(20);
            turn(-90);
            move(-radius);
        }

        // Draw current angular velocity vector if set visible.
        if (vectorVisible) {
            putPenDown();
            turn(getAngularVel());
            move(getVelocity() / 5);
            putPenUp();
            move(-getVelocity() / 5);
            turn(-getAngularVel());
        }

        // Draw current relative local centre if set visible and flocking.
        if (lCentreVisible && flocking) {
            double displacement = displacement(localCentre.getX(), localCentre.getY(), this.getXPos(), this.getYPos());
            if (displacement <= radius) {
                double xDisplacement = localCentre.getX() - this.getXPos();
                double yDisplacement = this.getYPos() - localCentre.getY();
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

    // Local Centre Method:
    // - Takes a list of intelligent birds and finds the local centre of mass.
    // - Returns of cartesian coordinate.
    public CartesianCoordinate localCentre(List<IntelligentBird> localBirds) {

        CartesianCoordinate localCentre = new CartesianCoordinate(0, 0);

        // Cumulative add each bird's X and Y positions.
        for (IntelligentBird bird : localBirds) {
            localCentre.addX(bird.getXPos());
            localCentre.addY(bird.getYPos());
        }

        // Divide by the number of birds to find the mean average X and Y cartesian.
        localCentre.setX(localCentre.getX() / localBirds.size());
        localCentre.setY(localCentre.getY() / localBirds.size());

        return localCentre;
    }

    // Flocking Method:
    // - Defines the main flocking method.
    // - Updates the bird's state behaviour, velocity, and angular velocity, based on bird and predator positions.
    public void flock(List<IntelligentBird> birds, List<Predator> predators) {

        // Periodically randomise velocity variables to allow birds to roam freely.
        // (These values will be overwritten if bird is flocking or evading)
        if (updateCount == 60) {
            roamAng = randomWithLimits(-60, 60);
            updateCount = 0;
        }
        updateCount++;

        // First check if predator is local.
        int predatorIndex = checkForLocalPredator(predators);

        // If predator is local, ignore flocking behaviour and evade.
        if (evading) {

            Predator localPredator = predators.get(predatorIndex);
            localCentre = new CartesianCoordinate(localPredator.getXPos(), localPredator.getYPos());

            // Set evading angular velocity.
            flockingSeparation(localCentre);
            setAngularVel(separaAng * 1.2);

            // Set evading velocity.
            double evadeVel = radius - displacement(this.getXPos(), this.getYPos(), localPredator.getXPos(), localPredator.getYPos());
            setVelocity(getMaxVelocity() + evadeVel + 10);

            return;
        }

        // Now check if bird is flocking.
        flocking = false;

        // Search radius proximity for other birds.
        List<IntelligentBird> localBirds = searchLocalRadius(birds, radius);

        // If local birds are found and all flocking parameters are not set to zero ...
        if (localBirds.size() > 0 && (cohesion != 0 || separation != 0 || alignment != 0))
            flocking = true;

        // ... start to flock!
        if (flocking) {

            // Find the local centre of neighbouring birds.
            localCentre = localCentre(localBirds);

            // Set flocking parameters based on user controls.
            if (cohesion != 0)
                flockingCohesion(localCentre);
            if (separation != 0)
                flockingSeparation(localCentre);
            if (alignment != 0)
                flockingAlignment(localBirds);

            // CALCULATE ANGULAR VELOCITY:

            // Set the flock angular velocity of the bird dependant on the flocking parameters range [0% -> 100%].
            double flockAng = (cohesAng * (cohesion / 100)) + (separaAng * (separation / 100)) + (alignAng * (alignment / 100));

            // Set turning angle between the flocking angle and the randomised value, based on the largest slider value.
            double largestSliderValue = Math.max(cohesion, Math.max(separation, alignment));
            flockAng = (flockAng * (largestSliderValue / 100)) + (roamAng * (1 - (largestSliderValue/100)));

            // CALCULATE VELOCITY:

            // Find mean average local velocity.
            double averageVel = 0;

            for (IntelligentBird bird : localBirds)
                averageVel += bird.getVelocity();

            averageVel /= localBirds.size();

            // Set the flocking velocity of the bird dependant on the flocking parameters range [0% -> 100%].
            double flockVel = getMaxVelocity() + (cohesVel * (cohesion / 100)) + (separaVel * (separation / 100));

            // Set velocity between the flocking velocity and the average velocity, based on the largest slider value.
            flockVel = (flockVel * (largestSliderValue / 100)) + (averageVel * (1 - (largestSliderValue/100)));

            setAngularVel(flockAng + dynamics);
            setVelocity(flockVel);

            return;
        }

        // If no other birds are local or if all flocking parameters are set to zero ...
        else {
            // ... start to roam freely.
            setAngularVel(roamAng);
            setVelocity(getMaxVelocity());

            return;
        }
    }

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                        Flocking Parameter Private Methods
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    // Search Local Radius Method:
    // - Filters through a list of intelligent birds to return those within the radius given.
    private List<IntelligentBird> searchLocalRadius(List<IntelligentBird> allBirds, int radius) {

        List<IntelligentBird> localBirds = new ArrayList<IntelligentBird>();

        // Scan through all active birds and add to 'local birds' list if within radius proximity.
        for (IntelligentBird bird : allBirds) {
            if (bird != this)
                if (displacement(bird.getXPos(), bird.getYPos(), this.getXPos(), this.getYPos()) < radius)
                    localBirds.add(bird);
        }

        return localBirds;
    }

    // Check For Local Predator Method:
    // - Returns an integer list index if predator is within its radius.
    // - Returns '-1' if no predators are local.
    private int checkForLocalPredator(List<Predator> predators) {

        evading = false;

        // Scan through all predators.
        for (int i = 0; i < predators.size(); i++) {

            // If a predator is within a bird's radius proximity ...
            if (displacement(predators.get(i).getXPos(), predators.get(i).getYPos(), this.getXPos(), this.getYPos()) < radius) {
                // ... set bird to evade and return the predator's index.
                evading = true;
                return i;
            }
        }
        // Return '-1' if no predator indexes are within proximity.
        return -1;
    }

    // Flocking Cohesion Method:
    // - Takes a cartesian coordinate and adjusts the 'cohesAng' and 'cohesVel' variables.
    private void flockingCohesion(CartesianCoordinate localCentre) {

        // 'X' displacement is positive EAST, and 'Y' displacement is positive NORTH.
        double xDisplacement = localCentre.getX() - this.getXPos();
        double yDisplacement = this.getYPos() - localCentre.getY();

        // Find the relative cohesion turning angle TOWARDS local centre.
        cohesAng = relativeTurningAngle(xDisplacement, yDisplacement, this.getBearingAngle());

        // If cohesion turning angle is INSIDE of [-90 -> 90] range, bird is following flock but TRAILING.
            // Increase cohesion velocity a little to bring flock together.
        if (cohesAng > -90 && cohesAng < 90)
            cohesVel = (displacement(xDisplacement, yDisplacement) / radius) * (getMaxVelocity() / 100);
        else cohesVel = 0;

        // If angle is outside of [-90 -> 90] degree range the local centre is behind, therefore bird is LEADING the flock.
            // Continue leading but tilt relative to the local centre's position.
        if (cohesAng >= 90)
            cohesAng = 180 - cohesAng;
        else if (cohesAng <= -90)
            cohesAng = -180 - cohesAng;
    }

    // Flocking Separation Method:
    // - Takes a cartesian coordinate and adjusts the 'separaAng' and 'separaVel' variables.
    private void flockingSeparation(CartesianCoordinate localCentre) {

        // 'X' displacement is positive EAST, and 'Y' displacement is positive NORTH.
        double xDisplacement = localCentre.getX() - this.getXPos();
        double yDisplacement = this.getYPos() - localCentre.getY();

        // Find the relative cohesion turning angle AWAY FROM local centre.
        separaAng = -1 * relativeTurningAngle(xDisplacement, yDisplacement, this.getBearingAngle());

        // If separation turning angle is INSIDE of [-90 -> 90] range, bird is following flock but TRAILING.
            // Decrease separation velocity a little to spread flock apart.
        if (separaAng > -90 && separaAng < 90)
            separaVel = -(displacement(xDisplacement, yDisplacement) / radius) * (getMaxVelocity() / 100);
            // Else bird is LEADING, so increase separation velocity a little to spread flock apart.
        else separaVel = (displacement(xDisplacement, yDisplacement) / radius) * (getMaxVelocity() / 100);

        // If angle is outside of [-90 -> 90] degree range the local centre is behind, therefore bird is leading the flock.
            // Continue leading but tilt relative to the local centre's position.
        if (separaAng >= 90)
            separaAng = 180 - separaAng;
        else if (separaAng <= -90)
            separaAng = -180 - separaAng;
    }

    // Flocking Alignment Method:
    // - Takes a list of local birds and adjusts the 'alignAng' and 'alignVel' variables.
    private void flockingAlignment(List<IntelligentBird> localBirds) {

        alignAng = 0;

        // Cumulative add the turning angle of each local bird from this bird.
        for (IntelligentBird bird : localBirds) {
            if (this.getBearingAngle() > bird.getBearingAngle())
                if (this.getBearingAngle() - bird.getBearingAngle() > 180)
                    alignAng += 360 - bird.getBearingAngle() - this.getBearingAngle();

                else alignAng -= (this.getBearingAngle() - bird.getBearingAngle());

            else if (this.getBearingAngle() < bird.getBearingAngle())
                if (this.getBearingAngle() - bird.getBearingAngle() < -180)
                    alignAng -= 360 - bird.getBearingAngle() - this.getBearingAngle();

                else alignAng += (bird.getBearingAngle() - this.getBearingAngle());
        }

    }
}
