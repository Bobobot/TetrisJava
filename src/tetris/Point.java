package tetris;

/**
 * Represents a simple point with an x and a y coordinate.
 */
public class Point {
	int x, y;

	/**
	 * The constructor. Creates a new point with the coordinates given.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * The constructor. Creates a new point from another point
	 * @param point The point that is to be copied
	 */
	public Point(Point point) {
		x = point.x;
		y = point.y;
	}

	/**
	 * A standard equality check
	 * @param obj The object we check equality against
	 * @return true if the two points are equal, otherwise false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Point other = (Point) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	/**
	 * Adds two points together
	 * @param p The point that is to be added to this object
	 * @return returns the sum of the two points
	 */
	public Point add(Point p) {
		return new Point(x + p.x, y + p.y);
	}

	/**
	 * Returns the point below this one
	 * @return The point below this one
	 */
	public Point below() {
		return new Point(x, y + 1);
	}

	/**
	 * Returns the point to the right of this one
	 * @return The point to the right of this one
	 */
	public Point rightTo() {
		return new Point(x + 1, y);
	}

	/**
	 * Returns the point to the left of this one
	 * @return The point to the left of this one
	 */
	public Point leftTo() {
		return new Point (x - 1, y);
	}

	/**
	 * A standard toString method.
	 * @return The string of the point.
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
		
	}
}