import java.awt.geom.Point2D;

import java.util.ArrayList;

/**
 * Class for computing the convex hull of a given series of points, using Jarvis' March.
 * 
 * @author	Devin Tuchsen
 */
public class JarvisMarch {
	private static final int TURN_LEFT = 1;
	private static final int TURN_RIGHT = -1;
	private static final int TURN_NONE = 0;
	
	private ArrayList<Point2D> hull = new ArrayList<Point2D>();;
	private Point2D[] points;
	
	/**
	 * Constructs an object that will contain the convex hull of the given points.
	 * Executes Jarvis' March algorithm.
	 * <p>
	 * Based on pseudocode written by Tom Switzer. See
	 * http://tomswitzer.net/2009/12/jarvis-march/
	 *
	 * @param pts	array of points from which to compute the convex hull
	 */
	public JarvisMarch(Point2D[] pts) {
		this.points = pts;
		
		hull.add(pts[0]);
	
		//Find the leftmost point
		for(Point2D p : pts) {
			if(p.getX() < hull.get(0).getX()) {
				hull.clear();
				hull.add(p);
			}
		}
		
		for(int i = 0; i < hull.size(); ++i) {
			Point2D p = hull.get(i);
			Point2D q = nextHullPoint(p);
			if(!q.equals(hull.get(0)))
				hull.add(q);
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
	 * Determines if p, 1, and r form a right, left, or no turn.
	 *
	 * @return	JarvisMarch.TURN_RIGHT, JarvisMarch.TURN_NONE, or JarvisMarch.TURN_LEFT
	 */
	private int turn(Point2D p, Point2D q, Point2D r) {
		double angle = (q.getX() - p.getX())*(r.getY() - p.getY()) - (r.getX() - p.getX())*(q.getY() - p.getY());
		if(angle < 0) return TURN_RIGHT;
		else if(angle == 0) return TURN_NONE;
		else return TURN_LEFT;
	}
	
	/**
	 * Returns the next point in the convex hull, given point p.
	 *
	 * @return the next point in the convex hull
	 */
	private Point2D nextHullPoint(Point2D p) {
		Point2D q = p;
		for(Point2D r : points) {
			int t = turn(p, q, r);
			if(t == TURN_RIGHT || t == TURN_NONE && p.distance(r) > p.distance(q)) {
				q = r;
			}
		}
		return q;
	}
}