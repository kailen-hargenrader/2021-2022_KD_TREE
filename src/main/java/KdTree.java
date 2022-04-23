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
		if(isEmpty()) {
			root = new TreeNode(p, true);
			size++;
		}
		else {
			TreeNode t = root;
			while(!p.equals(t.getValue()) && (t.getLeft() != null || t.getRight() != null)) {
				if(t.getVert()) {
					if(p.x() > t.getValue().x()) {
						if(t.getRight() != null) t=t.getRight();
						else {
							t.SetRight(new TreeNode(p, !t.getVert()));
							t=t.getRight();
							size++;
						}
					}
					else {
						if(t.getLeft() != null) t=t.getLeft();
						else {
							t.SetLeft(new TreeNode(p, !t.getVert()));
							t=t.getLeft();
							size++;
						}
					}
				}
				else {
					if(p.y() > t.getValue().y()) {
						if(t.getRight() != null) t=t.getRight();
						else {
							t.SetRight(new TreeNode(p, !t.getVert()));
							t=t.getRight();
							size++;
						}
					}
					else {
						if(t.getLeft() != null) t=t.getLeft();
						else {
							t.SetLeft(new TreeNode(p, !t.getVert()));
							t=t.getLeft();
							size++;
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
		TreeNode t = root;
		return containsNode(t, p);
	}
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

	// draw all points to standard draw 
	public void draw() {
		if(!isEmpty()) {
			StdDraw.clear();
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(.01);
			drawNode(root);
		}
	}
	private void drawNode(TreeNode n) {
		n.getValue().draw();
		if(n.getRight() != null) drawNode(n.getRight());
		if(n.getLeft() != null) drawNode(n.getLeft());
	}

	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		if(rect == null) throw new IllegalArgumentException("Rectangle cannot be null.");
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		if(isEmpty()) return points;
		range(root, rect, points);
		return points;
	}

	private void range(TreeNode t, RectHV rect, ArrayList<Point2D> points){
		if(rect.contains(t.getValue())) points.add(t.getValue());
		if(t.getVert()) {
			if(t.getValue().x() < rect.xmin()) {
				if(t.getLeft() != null) range(t.getLeft(), rect, points);
			}
			else if(t.getValue().x() > rect.xmax()) {
				if(t.getRight() != null) range(t.getRight(), rect, points);
			}
			else {
				if(t.getLeft() != null) range(t.getLeft(), rect, points);
				if(t.getRight() != null) range(t.getRight(), rect, points);
			}
		}
		else {
			if(t.getValue().y() < rect.ymin()) {
				if(t.getLeft() != null) range(t.getLeft(), rect, points);
			}
			else if(t.getValue().y() > rect.ymax()) {
				if(t.getRight() != null) range(t.getRight(), rect, points);
			}
			else {
				if(t.getLeft() != null) range(t.getLeft(), rect, points);
				if(t.getRight() != null) range(t.getRight(), rect, points);
			}
		}
	}

	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p) {
		if(p == null) throw new IllegalArgumentException("Point cannot be null.");
		if(isEmpty()) return null;
		Double dist = p.distanceSquaredTo(root.getValue());
		TreeNode minNode = root;
		nearestNode(p, root, dist, minNode);
		return minNode.getValue();
	}
	private void nearestNode(Point2D p, TreeNode t, Double d, TreeNode min) {
		if(t.getVert()) {
			if(p.x() <= t.getValue().x()) {
				if(t.getLeft() != null) {
					if(p.distanceSquaredTo(t.getLeft().getValue()) < d) {
						min = t.getLeft();
						d = p.distanceSquaredTo(t.getLeft().getValue());
					}
					nearestNode(p, t.getLeft(), d, min);
				}
				if(t.getRight() != null && (t.getValue().x() - p.x()) * (t.getValue().x() - p.x()) < d) {
					if(p.distanceSquaredTo(t.getRight().getValue()) < d) {
						min = t.getRight();
						d = p.distanceSquaredTo(t.getRight().getValue());
					}
					nearestNode(p, t.getRight(), d, min);
				}
			}
			else {
				if(t.getRight() != null) {
					if(p.distanceSquaredTo(t.getRight().getValue()) < d) {
						min = t.getRight();
						d = p.distanceSquaredTo(t.getRight().getValue());
					}
					nearestNode(p, t.getRight(), d, min);
				}
				if(t.getLeft() != null && (t.getValue().x() - p.x()) * (t.getValue().x() - p.x()) < d) {
					if(p.distanceSquaredTo(t.getLeft().getValue()) < d) {
						min = t.getLeft();
						d = p.distanceSquaredTo(t.getLeft().getValue());
					}
					nearestNode(p, t.getLeft(), d, min);
				}
			}
		}
		else {
			if(p.y() <= t.getValue().y()) {
				if(t.getLeft() != null) {
					if(p.distanceSquaredTo(t.getLeft().getValue()) < d) {
						min = t.getLeft();
						d = p.distanceSquaredTo(t.getLeft().getValue());
					}
					nearestNode(p, t.getLeft(), d, min);
				}
				if(t.getRight() != null && (t.getValue().y() - p.y()) * (t.getValue().y() - p.y()) < d) {
					if(p.distanceSquaredTo(t.getRight().getValue()) < d) {
						min = t.getRight();
						d = p.distanceSquaredTo(t.getRight().getValue());
					}
					nearestNode(p, t.getRight(), d, min);
				}
			}
			else {
				if(t.getRight() != null) {
					if(p.distanceSquaredTo(t.getRight().getValue()) < d) {
						min = t.getRight();
						d = p.distanceSquaredTo(t.getRight().getValue());
					}
					nearestNode(p, t.getRight(), d, min);
				}
				if(t.getLeft() != null && (t.getValue().y() - p.y()) * (t.getValue().y() - p.y()) < d) {
					if(p.distanceSquaredTo(t.getLeft().getValue()) < d) {
						min = t.getLeft();
						d = p.distanceSquaredTo(t.getLeft().getValue());
					}
					nearestNode(p, t.getLeft(), d, min);
				}
			}
		}
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



