import java.awt.geom.Point2D;

import java.util.Comparator;

/**
 * Comparator for points. Can compare based on position or polar angle.
 *
 * @author	Devin Tuchsen
 */
public class PointComparator implements Comparator<Point2D> {
	private Point2D origin;

	/**
	 * Constructs a new PointComparator that will compare based on position.
	 */
	public PointComparator() {
	}
	
	/**
	 * Constructs a new PointComparator that will compare based on polar angle.
	 *
	 * @param origin	origin point around which to compute polar angles
	 */
	public PointComparator(Point2D origin) {
		this.origin = origin;
	}

	/**
	 * Compares its two arguments for order. Returns a negative integer, zero, or a 
	 * positive integer as the first argument is less than, equal to, or greater than
	 * the second. If an origin was specified, comparison will be based on polar 
	 * angle. Otherwise, it is based upon position.
	 * 
	 * @param p1	the first point to be compared
	 * @param p2	the second point to be compared
	 * @return		a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
	 */
	@Override
	public int compare(Point2D p1, Point2D p2) {
		double angle;
		if(origin != null)
			angle = (p1.getX() - origin.getX()) * (p2.getY() - origin.getY()) - (p2.getX() - origin.getX()) * (p1.getY() - origin.getY());
		else
			angle = p1.getX()*p2.getY() - p1.getY()*p2.getX();
		return (int) angle;
	}
}
