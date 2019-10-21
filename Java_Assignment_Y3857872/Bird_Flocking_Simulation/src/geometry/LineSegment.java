// Y3857872
package geometry;

// Line Segment Class:
public class LineSegment {
	private CartesianCoordinate startPoint;
	private CartesianCoordinate endPoint;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                Constructors
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	public LineSegment(CartesianCoordinate start, CartesianCoordinate end) {
		startPoint = start;
		endPoint = end;
	}
	
	public CartesianCoordinate getStartPoint() {
		return startPoint;
	}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                Getters / Setters
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	public void setStartPoint(CartesianCoordinate startPoint) {
		this.startPoint = startPoint;
	}

	public CartesianCoordinate getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(CartesianCoordinate endPoint) {
		this.endPoint = endPoint;
	}
	
	public String toString() {
		return "(" + startPoint.getX() + ", " + startPoint.getY()
		+ ") to (" + endPoint.getX() + ", " + + startPoint.getY() + ")";
	}
	
	public double length() {
		return Math.sqrt(Math.pow(Math.abs(startPoint.getX() + endPoint.getX()), 2)
				+ Math.pow(Math.abs(startPoint.getY() + endPoint.getY()), 2));
	}
}
