import java.awt.geom.Point2D;

import java.util.Arrays;
import java.util.Stack;

/**
 * Class for computing the convex hull of a given series of points, using Graham's Scan.
 * 
 * @author	Devin Tuchsen
 */
public class GrahamScan {
	private Stack<Point2D> hull = new Stack<Point2D>();
	private Point2D[] points;
	
	/**
	 * Constructs an object that will contain the convex hull of the given points.
	 * Executes Graham's Scan algorithm.
	 * <p>
	 * Based on the algorithm given in "Introduction to Algorithms," 3rd edition, pp. 1031<br>
	 * Thomas H. Cormen et. al.
	 *
	 * @param pts	array of points from which to compute the convex hull
	 */
	public GrahamScan(Point2D[] pts) {
		this.points = pts;
		
		//As of Java SE7, this uses a sorting algorithm called TimSort.
		//TimSort is a hybrid algorithm derived from Merge Sort and Insertion Sort.
		//Its running time is O(nlogn).
		//First sort by position.
		Arrays.sort(points, new PointComparator());
		
		//Minimum y point, or if tie, leftmost point.
		hull.push(points[0]);
		
		//Now, sort by polar angle around p0.
		Arrays.sort(points, new PointComparator(points[0]));
		
		hull.push(points[1]);
		hull.push(points[2]);
		
		for(int i = 3; i < points.length; ++i) {
			Point2D top = hull.peek();
			Point2D nextToTop = hull.get(hull.size() - 2);
		
			while(polarAngle(points[i], nextToTop, top) >= 0 && hull.size() >= 3) {
				hull.pop();
				top = hull.peek();
				nextToTop = hull.get(hull.size() - 2);
			}
			
			hull.push(points[i]);
		}
	}
	
	/**
	 * Returns the computed convex hull points.
	 * 
	 * @return	an array of points on the convex hull
	 */
	public Point2D[] getHull() {
		Point2D[] pointArray = new Point2D[]{};
		return hull.toArray(pointArray);
	}

	/**
	 * Returns the polar angle of p0, p1, and p2.
	 * 
	 * @return	the cross-product polar angle of p0, p1, and p2
	 */
	private double polarAngle(Point2D p0, Point2D p1, Point2D p2) {
		//(p1 - p0) * (p2 - p0) = (x1 - x0)(y2 - y0) - (x2 - x0)(y1 - y0)
		//If this cross product is positive, then p0p1 is clockwise from p0p2; if negative, it is counter-clockwise.
		return (p1.getX() - p0.getX()) * (p2.getY() - p0.getY()) - (p2.getX() - p0.getX()) * (p1.getY() - p0.getY());
	}
}
