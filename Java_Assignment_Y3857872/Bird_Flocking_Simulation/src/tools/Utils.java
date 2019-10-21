// Y3857872
package tools;

public class Utils {
	
	public static void pause(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// We are happy with interruptions, so do not report exception
		}
	}

	public static int randomWithLimits(int min, int max) {

		return (int) (Math.random() * (max - min)) + min;
	}

	public static double randomWithLimits(double min, double max) {

		return (Math.random() * (max - min)) + min;
	}

	public static double displacement(double x1, double y1, double x2, double y2) {

		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public static double displacement(double xDisplacement, double yDisplacement) {

		return Math.sqrt(Math.pow(xDisplacement, 2) + Math.pow(yDisplacement, 2));
	}

	// Finds relative turning angle within -180 -> 180 degree range.
	public static double relativeTurningAngle(double xDisplacement, double yDisplacement, double originBearing) {

		double targetBearing = 0;

		// Calculate relative bearing angle of local centre from bird's position.
		if (xDisplacement > 0 && yDisplacement > 0)
			targetBearing = 90 - Math.toDegrees(Math.atan2(yDisplacement, xDisplacement));

		else if (xDisplacement > 0 && yDisplacement < 0)
			targetBearing = 90 - Math.toDegrees(Math.atan2(yDisplacement, xDisplacement));

		else if (xDisplacement < 0 && yDisplacement > 0)
			targetBearing = 450 - Math.toDegrees(Math.atan2(yDisplacement, xDisplacement));

		else if (xDisplacement < 0 && yDisplacement < 0)
			targetBearing = 90 - Math.toDegrees(Math.atan2(yDisplacement, xDisplacement));

		double turningAngle = targetBearing - originBearing;

		if (turningAngle < -180)
			turningAngle += 360;
		else if (turningAngle >= 180)
			turningAngle -= 360;

		return turningAngle;
	}
}