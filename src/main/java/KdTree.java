/******************************************************************************
 *  Name:    J.D. DeVaughn-Brown
 *  NetID:   jddevaug
 *  Precept: P05
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *  
 *  Compilation:  javac-algs4 KdTree.java
 *  Execution:    java-algs4 KdTree
 *  Dependencies: Point2D.java RectHV.java 
 * 
 *  Description: Represents a set of points in the unit square 
 *  (all points have x- and y-coordinates between 0 and 1) 
 *  using a 2d-tree to support efficient range search 
 *  (find all of the points contained in a query rectangle) 
 *  and nearest-neighbor search (find a closest point to a query point).
 ******************************************************************************/

import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Faster implementation of range and nearest neighbor methods using red black BST
 * @author monke
 *
 */
public class KdTree {
	// construct an empty set of points
	private TreeNode root;
	private int size;

	/**
	 * Initialize class with nothing
	 */
	public KdTree() {

	}
	
	/**
	 * @return Empty?
	 */
	// is the set empty? 
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * @return number of points in BST
	 */
	// number of points in the set 
	public int size() {
		return size;
	}

	/**
	 * inserts point at correct node in BST
	 * @param p point
	 */
	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if(p == null) throw new IllegalArgumentException("New point cannot be null");
		if(isEmpty()) {
			root = new TreeNode(p, true);
			size++;
		}
		if(!contains(p)) {
			insertNode(root, p);
		}
	}
	
	/**
	 * help the insertion through recursion
	 * @param t Node in BST
	 * @param p point
	 */
	private void insertNode(TreeNode t, Point2D p) {
		if(t.getVert()) {
			if(t.getValue().x() >= p.x()) {
				if(t.getLeft() != null) insertNode(t.getLeft(), p);
				else {
					t.SetLeft(new TreeNode(p, !t.getVert()));
					size++;
				}
			}
			else {
				if(t.getRight() != null) insertNode(t.getRight(), p);
				else {
					t.SetRight(new TreeNode(p, !t.getVert()));
					size++;
				}
			}
		}
		else {
			if(t.getValue().y() >= p.y()) {
				if(t.getLeft() != null) insertNode(t.getLeft(), p);
				else {
					t.SetLeft(new TreeNode(p, !t.getVert()));
					size++;
				}
			}
			else {
				if(t.getRight() != null) insertNode(t.getRight(), p);
				else {
					t.SetRight(new TreeNode(p, !t.getVert()));
					size++;
				}
			}
		}
	}

	/**
	 * Does the BST contain point p
	 * @param p point
	 * @return p in BST?
	 */
	// does the set contain point p? 
	public boolean contains(Point2D p) {
		if(p == null) throw new IllegalArgumentException("Cannot check for null point");
		if(isEmpty()) return false;
		TreeNode t = root;
		return containsNode(t, p);
	}
	
	/**
	 * recursive helper to contains
	 * @param t Node in BST
	 * @param p point
	 * @return point in BST?
	 */
	private boolean containsNode(TreeNode t, Point2D p) {
		if(p.equals(t.getValue())) return true;
		if(t.getVert()) {
			if(p.x() <= t.getValue().x()) {
				if(t.getLeft() != null) return containsNode(t.getLeft(), p);
				else return false;
			}
			else {
				if(t.getRight() != null) return containsNode(t.getRight(), p);
				else return false;
			}
		}
		else {
			if(p.y() <= t.getValue().y()) {
				if(t.getLeft() != null) return containsNode(t.getLeft(), p);
				else return false;
			}
			else {
				if(t.getRight() != null) return containsNode(t.getRight(), p);
				else return false;
			}
		}
	}

	/**
	 * Draws bst to standard output
	 */
	// draw all points to standard draw 
	public void draw() {
		if(!isEmpty()) {
			StdDraw.clear();
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(.01);
			drawNode(root);
		}
	}
	
	/**
	 * recursively helps draw
	 * @param n node in bst
	 */
	private void drawNode(TreeNode n) {
		n.getValue().draw();
		if(n.getRight() != null) drawNode(n.getRight());
		if(n.getLeft() != null) drawNode(n.getLeft());
	}

	/**
	 * Finds points in rectangle 
	 * @param rect rectangle
	 * @return the points in the BST within rect
	 */
	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		if(rect == null) throw new IllegalArgumentException("Rectangle cannot be null.");
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		if(isEmpty()) return points;
		range(root, rect, points);
		return points;
	}

	/**
	 * recursive helper for range
	 * @param t node in BST
	 * @param rect rectangle
	 * @param points list of points
	 */
	private void range(TreeNode t, RectHV rect, ArrayList<Point2D> points){
		if(rect.contains(t.getValue())) {
			points.add(t.getValue());
			if(t.getLeft() != null) range(t.getLeft(), rect, points);
			if(t.getRight() != null) range(t.getRight(), rect, points);
		}
		else {
			if(t.getVert()) {
				if(t.getValue().x() < rect.xmin()) {
					if(t.getRight() != null) range(t.getRight(), rect, points);
				}
				else if(t.getValue().x() > rect.xmax()) {
					if(t.getLeft() != null) range(t.getLeft(), rect, points);
				}
				else {
					if(t.getLeft() != null) range(t.getLeft(), rect, points);
					if(t.getRight() != null) range(t.getRight(), rect, points);
				}
			}
			else {
				if(t.getValue().y() < rect.ymin()) {
					if(t.getRight() != null) range(t.getRight(), rect, points);
				}
				else if(t.getValue().y() > rect.ymax()) {
					if(t.getLeft() != null) range(t.getLeft(), rect, points);
				}
				else {
					if(t.getLeft() != null) range(t.getLeft(), rect, points);
					if(t.getRight() != null) range(t.getRight(), rect, points);
				}
			}
		}
		
	}

	/**
	 * Finds nearest point to p
	 * @param p point
	 * @return nearest point to p
	 */
	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p) {
		if(p == null) throw new IllegalArgumentException("Point cannot be null.");
		if(isEmpty()) return null;
		TreeNode min = root;
		doubleWithValue dist = new doubleWithValue(p.distanceSquaredTo(root.getValue()));
		return nearestNode(p, root, dist, min).getValue();
		
	}
	/**
	 * recursive helper to nearest
	 * @param p point
	 * @param t node in BST
	 * @param d minimum squared distance so far
	 * @param min node with minimum distance
	 * @return a new minimum node
	 */
	private TreeNode nearestNode(Point2D p, TreeNode t, doubleWithValue d, TreeNode min) {
		if(t.getVert()) {
			if(p.x() <= t.getValue().x()) {
				if(t.getLeft() != null) {
					if(p.distanceSquaredTo(t.getLeft().getValue()) < d.getValue()) {
						min = t.getLeft();
						d.setValue(p.distanceSquaredTo(t.getLeft().getValue()));
					}
					min = nearestNode(p, t.getLeft(), d, min);
				}
				if(t.getRight() != null && (t.getValue().x() - p.x()) * (t.getValue().x() - p.x()) < d.getValue()) {
					if(p.distanceSquaredTo(t.getRight().getValue()) < d.getValue()) {
						min = t.getRight();
						d.setValue(p.distanceSquaredTo(t.getRight().getValue()));
					}
					min = nearestNode(p, t.getRight(), d, min);
				}
			}
			else {
				if(t.getRight() != null) {
					if(p.distanceSquaredTo(t.getRight().getValue()) < d.getValue()) {
						min = t.getRight();
						d.setValue(p.distanceSquaredTo(t.getRight().getValue()));
					}
					min = nearestNode(p, t.getRight(), d, min);
				}
				if(t.getLeft() != null && (t.getValue().x() - p.x()) * (t.getValue().x() - p.x()) < d.getValue()) {
					if(p.distanceSquaredTo(t.getLeft().getValue()) < d.getValue()) {
						min = t.getLeft();
						d.setValue(p.distanceSquaredTo(t.getLeft().getValue()));
					}
					min = nearestNode(p, t.getLeft(), d, min);
				}
			}
		}
		else {
			if(p.y() <= t.getValue().y()) {
				if(t.getLeft() != null) {
					if(p.distanceSquaredTo(t.getLeft().getValue()) < d.getValue()) {
						min = t.getLeft();
						d.setValue(p.distanceSquaredTo(t.getLeft().getValue()));
					}
					min = nearestNode(p, t.getLeft(), d, min);
				}
				if(t.getRight() != null && (t.getValue().y() - p.y()) * (t.getValue().y() - p.y()) < d.getValue()) {
					if(p.distanceSquaredTo(t.getRight().getValue()) < d.getValue()) {
						min = t.getRight();
						d.setValue(p.distanceSquaredTo(t.getRight().getValue()));
					}
					min = nearestNode(p, t.getRight(), d, min);
				}
			}
			else {
				if(t.getRight() != null) {
					if(p.distanceSquaredTo(t.getRight().getValue()) < d.getValue()) {
						min = t.getRight();
						d.setValue(p.distanceSquaredTo(t.getRight().getValue()));
					}
					min = nearestNode(p, t.getRight(), d, min);
				}
				if(t.getLeft() != null && (t.getValue().y() - p.y()) * (t.getValue().y() - p.y()) < d.getValue()) {
					if(p.distanceSquaredTo(t.getLeft().getValue()) < d.getValue()) {
						min = t.getLeft();
						d.setValue(p.distanceSquaredTo(t.getLeft().getValue()));
					}
					min = nearestNode(p, t.getLeft(), d, min);
				}
			}
		}
		return min;
	}


	// unit testing of the methods (optional) 
	public static void main(String[] args) {

	}
}

/**
 * Node for BST
 * @author monke
 *
 */
class TreeNode {
	private Point2D value;
	private TreeNode left;
	private TreeNode right;
	private Boolean vert;

	/**
	 * Initialize TreeNode with a point and a orientation
	 * @param p point
	 * @param b orientation (true is vertical and false is horizontal)
	 */
	public TreeNode(Point2D p, Boolean b) {
		value = p;
		vert = b;
		left = null;
		right = null;
	}

	/**
	 * Set the right child
	 * @param r
	 */
	public void SetRight(TreeNode r) {
		right = r;
	}

	/**
	 * Set the left child
	 * @param l
	 */
	public void SetLeft(TreeNode l) {
		left = l;
	}

	/**
	 * Return the Right Child
	 * @return
	 */
	public TreeNode getRight() {
		return right;
	}
	
	/**
	 * Return the Left Child
	 * @return
	 */
	public TreeNode getLeft() {
		return left;
	}

	/**
	 * Return the orientation
	 * @return
	 */
	public Boolean getVert() {
		return vert;
	}

	/**
	 * return the point value
	 * @return
	 */
	public Point2D getValue() {
		return value;
	}
}

/**
 * Double that is mutable when passed as parameter and stored globally
 * @author monke
 *
 */
class doubleWithValue{
	private double d;
	
	/**
	 * Initialize class
	 * @param number
	 */
	public doubleWithValue(double number){
		d = number;
	}
	
	/**
	 * Modify value of double
	 * @param number
	 */
	public void setValue(double number) {
		d = number;
	}
	
	/**
	 * return value of double
	 * @return
	 */
	public double getValue() {
		return d;
	}
}


