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

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;


public class KdTree {
	// construct an empty set of points
	private TreeNode root;
	private int size;

	public KdTree() {

	}
	// is the set empty? 
	public boolean isEmpty() {
		return root == null;
	}
	// number of points in the set 
	public int size() {
		return size;
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if(p == null) throw new IllegalArgumentException("New point cannot be null");
		if(isEmpty()) root = new TreeNode(p, true);
		else {
			TreeNode t = root;
			while(t.getLeft() != null || t.getRight() != null) {
				if(t.getVert()) {
					if(p.x() > t.getValue().x()) {
						if(t.getRight() != null) t=t.getRight();
						else {
							t.SetRight(new TreeNode(p, !t.getVert()));
							t=t.getRight();
						}
					}
					else {
						if(t.getLeft() != null) t=t.getLeft();
						else {
							t.SetLeft(new TreeNode(p, !t.getVert()));
							t=t.getLeft();
						}
					}
				}
				else {
					if(p.y() > t.getValue().y()) {
						if(t.getRight() != null) t=t.getRight();
						else {
							t.SetRight(new TreeNode(p, !t.getVert()));
							t=t.getRight();
						}
					}
					else {
						if(t.getLeft() != null) t=t.getLeft();
						else {
							t.SetLeft(new TreeNode(p, !t.getVert()));
							t=t.getLeft();
						}
					}
				}
			}
		}
	}

	// does the set contain point p? 
	public boolean contains(Point2D p) {
		if(p == null) throw new IllegalArgumentException("Cannot check for null point");
		if(isEmpty()) return false;
		if(p.equals(root.getValue())) return true;
		TreeNode t = root;
		while(t.getLeft() != null || t.getRight() != null) {
			if(t.getVert()) {
				if(p.x() > t.getValue().x()) {
					if(t.getRight() != null) {
						if(p == t.getRight().getValue()) return true;
						t=t.getRight();
					}
				}
				else {
					if(t.getLeft() != null) {
						if(p == t.getLeft().getValue()) return true;
						t=t.getLeft();
					}
				}
			}
			else {
				if(p.y() > t.getValue().y()) {
					if(t.getRight() != null) {
						if(p == t.getRight().getValue()) return true;
						t=t.getRight();
					}
				}
				else {
					if(t.getLeft() != null) {
						if(p == t.getLeft().getValue()) return true;
						t=t.getLeft();
					}
				}
			}
		}
		return false;
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
		for()
	}

	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p) {
		return null;
	}

	// unit testing of the methods (optional) 
	public static void main(String[] args) {

	}
}

class TreeNode {
	private Point2D value;
	private TreeNode left;
	private TreeNode right;
	private Boolean vert;

	public TreeNode(Point2D p, Boolean b) {
		value = p;
		vert = b;
		left = null;
		right = null;
	}

	public void SetRight(TreeNode r) {
		right = r;
	}

	public void SetLeft(TreeNode l) {
		left = l;
	}

	public TreeNode getRight() {
		return right;
	}

	public TreeNode getLeft() {
		return left;
	}

	public Boolean getVert() {
		return vert;
	}
	
	public Point2D getValue() {
		return value;
	}
}



