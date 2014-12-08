import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

/**
 * A JFrame that is specialized for displaying points and their convex hull.
 * Used when graphics are enabled in the application.
 *
 * @author	Devin Tuchsen
 */
public class HullFrame extends JFrame {
	private Point2D[] points;
	private Point2D[] hullPoints;
	
	/**
	 * Constructs a new HullFrame object with no points or hull points.
	 */
	public HullFrame() {
		super("Convex Hull");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.points = new Point2D[]{};
		this.hullPoints = new Point2D[]{};
	}
	
	/**
	 * Constructs a new HullFrame object with given points and hull points.
	 *
	 * @param points		array of points around which the convex hull is centred
	 * @param hullPoints	array of points that comprise the convex hull
	 */
	public HullFrame(Point2D[] points, Point2D[] hullPoints) {
		super("Convex Hull");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.points = points;
		this.hullPoints = hullPoints;
	}
	
	//Since AWT uses multiple threads, only one of these should be running at any time (synchronized)
	//Otherwise, points may change during painting, causing out-of-bounds errors
	/**
	 * Sets the points around which the convex hull is centred.
	 *
	 * @param points	array of points around which the convex hull is centred
	 */
	public synchronized void setPoints(Point2D[] points) {
		this.points = points;
	}
	
	/**
	 * Sets the points that comprise the convex hull.
	 *
	 * @param hullPoints	array of points that comprise the convex hull
	 */
	public synchronized void setHullPoints(Point2D[] hullPoints) {
		this.hullPoints = hullPoints;
	}
	
	/**
	 * Paints the container. Will render the points around which the convex hull is centered,
	 * as well as a polygon representing the convex hull.
	 *
	 * @param g	the specified Graphics window
	 */
	@Override
	public synchronized void paint(Graphics g) {
		//Overwrite the background with solid white
		g.setColor(Color.WHITE);
		g.fillRect(0,0,800,600);
		
		//Draw the points
		g.setColor(Color.BLACK);
		for(Point2D point : points) {
			int x = (int) point.getX();
			int y = (int) point.getY();
			g.fillOval(x - 2, y - 2, 4, 4);
		}
		
		//Draw the convex hull
		int xPoints[] = new int[hullPoints.length];
		int yPoints[] = new int[hullPoints.length];
		for(int i = 0; i < hullPoints.length; ++i) {
			xPoints[i] = (int) hullPoints[i].getX();
			yPoints[i] = (int) hullPoints[i].getY();
		}
		g.drawPolygon(xPoints, yPoints, hullPoints.length);
	}
}
