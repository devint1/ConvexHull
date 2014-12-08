import java.awt.geom.Point2D;

import java.util.Date;
import java.util.Random;
 
/**
 * Application that calculates the convex hull using either Graham's Scan or Jarvis'
 * March. It calculates the convex hull given <code>n</code> randomly generated 
 * points a total of <code>i</code> iterations. If graphics are enabled, each result
 * is printed to the screen. 
 * 
 * @author Devin Tuchsen
 */
public class ConvexHull {
	public static void main(String[] args) {
		//Arguments
		int numPoints = 0;
		int numIters = 0;
		boolean graphics = false;
		boolean graham = false;
		
		//Parse the arguments
		try {
			numPoints = Integer.parseInt(args[0]);
			numIters = Integer.parseInt(args[1]);
			if(args[2].equals("y"))
				graphics = true;
			else if(args[2].equals("n"))
				graphics = false;
			else
				throw new Exception();
			if(args[3].equals("g"))
				graham = true;
			else if(args[3].equals("j"))
				graham = false;
			else
				throw new Exception();
		} catch(Exception e) {
			//If we get here, the user did not enter something correctly
			//Display the usage information
			System.out.println("Usage: java ConvexHull\t<points> <iterations> <display> <algorithm>");
			System.out.println("\t\t\t<points>: number of points to include in each iteration");
			System.out.println("\t\t\t<iterations>: number of iterations to compute");
			System.out.println("\t\t\t<graphics>: y: graphically display the result of each iteration; n: do not display");
			System.out.println("\t\t\t<algorithm>: g: Graham's Scan; j: Jarvis' March");
			return;
		}
		
		//Used for creating random points
		Random rand = new Random();
		
		//Set up the window
		HullFrame frame = new HullFrame();
		if(graphics) {
			frame.setSize(800,600);
			frame.setVisible(true);
		}
		
		//Begin computations
		long startTime = new Date().getTime();
		long totalHullPoints = 0;
		for(int i = 0; i < numIters; ++i) {
			//Randomly generate all of the points
			Point2D[] points = new Point2D[numPoints];
			for(int j = 0; j < numPoints; ++j) {
				//Tries to account for borders rendered by the OS
				//Using doubles reduces the likelihood that duplicate points will exist
				//Jarvis' March will break if duplicate points exist
				double x = rand.nextDouble() * 780;
				double y = rand.nextDouble() * 555;
				points[j] = new Point2D.Double(x + 10, y + 35);
			}
			
			//Compute the convex hull
			Point2D[] hullPoints = new Point2D[]{};
			
			if(graham) {
				GrahamScan grahamScan = new GrahamScan(points);
				hullPoints = grahamScan.getHull();
			} else {
				JarvisMarch jarvisMarch = new JarvisMarch(points);
				hullPoints = jarvisMarch.getHull();
			}
			
			totalHullPoints += hullPoints.length;
			
			if(graphics) {
				//Display the result
				frame.setPoints(points);
				frame.setHullPoints(hullPoints);
				frame.repaint();
			}
		}
		long endTime = new Date().getTime();
		
		//Calculate statistics
		long duration = endTime - startTime;
		double averageTime = ((double)duration)/((double)numIters);
		double averageHullPoints = ((double)totalHullPoints)/((double)numIters);
		
		//Print statistics to screen
		System.out.println("Total time elapsed: " + duration + "ms");
		System.out.println("Average time per iteration: " + averageTime + "ms");
		System.out.println("Average number of hull points: " + averageHullPoints);
	}
}
