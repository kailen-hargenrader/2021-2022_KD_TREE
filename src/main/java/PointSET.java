/******************************************************************************
 *  Name:    J.D. DeVaughn-Brown
 *  NetID:   jddevaug
 *  Precept: P05
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *  
 *  Compilation:  javac-algs4 PointSET.java
 *  Execution:    java-algs4 PointSET
 *  Dependencies: Point2D.java RectHV.java 
 * 
 *  Description: Represents a set of points in the unit square 
 *  (all points have x- and y-coordinates between 0 and 1) 
 *  using a red-black BST to support range search 
 *  (find all of the points contained in a query rectangle) 
 *  and nearest-neighbor search (find a closest point to a query point).
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {
	TreeSet<Point2D> points;
	// construct an empty set of points
	public PointSET() {
		TreeSet<Point2D> points = new TreeSet<Point2D>();
	}
	// is the set empty? 
	public boolean isEmpty() {
		return points.size() == 0;
	}
	// number of points in the set 
	public int size() {
		return points.size();
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if(p == null) throw new IllegalArgumentException("New point cannot be null");
			points.add(p);
	}

	// does the set contain point p? 
	public boolean contains(Point2D p) {
		if(p == null) throw new IllegalArgumentException("Cannot check for null point");
		return points.contains(p);
	}

	// draw all points to standard draw 
	public void draw() {
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		for(Point2D p : points) {
			p.draw();
		}
	}

	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> queue = new Queue<Point2D>();
		for(Point2D p : points) {
			if(rect.contains(p)) queue.enqueue(p);
		}
		return queue;
	}

	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p) {
		if(p == null) throw new IllegalArgumentException("Point cannot be null.");
		if(isEmpty()) return null;
		Point2D nearest = null;
		for (Point2D P : points) {
			if(nearest == null || P.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
				nearest = P;
			}
			
		}
		return nearest;
	}

	// unit testing of the methods (optional) 
	public static void main(String[] args) {

	}
}


