// Y3857872
package geometry;

public class CartesianCoordinate {
	private double xPosition;
	private double yPosition;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                Constructors
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	public CartesianCoordinate(double x, double y) {
		xPosition = x;
		yPosition = y;
	}

	public double getX() {
		return xPosition;
	}

	public void setX(double newX) {
		this.xPosition = newX;
	}

	public double getY() {
		return yPosition;
	}

	public void setY(double newY) {
		this.yPosition = newY;
	}

	public void addX(double addX) {
		this.xPosition = getX() + addX;
	}

	public void addY(double addY) {
		this.yPosition = getY() + addY;
	}
}
