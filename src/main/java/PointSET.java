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

/**
 * A brute force implementation of range and nearest neighbor search.
 * @author monke
 *
 */
public class PointSET {
	private TreeSet<Point2D> points;
	
	/**
	 * Initialize TreeSet of points
	 */
	// construct an empty set of points
	public PointSET() {
		points = new TreeSet<Point2D>();
	}
	
	/**
	 * Is the set empty
	 * @return empty?
	 */
	// is the set empty? 
	public boolean isEmpty() {
		return points.size() == 0;
	}
	
	/**
	 * returns number of points
	 * @return number of points
	 */
	// number of points in the set 
	public int size() {
		return points.size();
	}

	/**
	 * insert a point in the treeSet
	 */
	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if(p == null) throw new IllegalArgumentException("New point cannot be null");
		points.add(p);
	}
	
	/**
	 * does the treeset contain point p
	 * @param p point
	 * @return is p in treeset
	 */
	// does the set contain point p? 
	public boolean contains(Point2D p) {
		if(p == null) throw new IllegalArgumentException("Cannot check for null point");
		return points.contains(p);
	}

	/**
	 * Draws treeset to standard output
	 */
	// draw all points to standard draw
	public void draw() {
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		for(Point2D p : points) {
			p.draw();
		}
	}

	/**
	 * Finds points contained within a rectangle
	 * @param rect rectangle
	 * @return set of points in rectangle
	 */
	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		if(rect == null) throw new IllegalArgumentException("Rectangle cannot be null.");
		Queue<Point2D> queue = new Queue<Point2D>();
		for(Point2D p : points) {
			if(rect.contains(p)) queue.enqueue(p);
		}
		return queue;
	}

	/**
	 * Finds nearest point to point p
	 * @param p point
	 * @return nearest point to p
	 */
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


