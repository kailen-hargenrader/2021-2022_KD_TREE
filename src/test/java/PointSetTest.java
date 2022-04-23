import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSetTest {
	PointSET pointset1;
  @Before
  public void setUp() throws Exception {
	  pointset1 = generatePointSET("circle10.txt");
  }

  public PointSET generatePointSET(String s) {
	  In in = new In("kdtree-test-files/" + s);
	  PointSET p = new PointSET();
	  while(in.hasNextLine()) {
		  p.insert(new Point2D(in.readDouble(), in.readDouble()));
	  }
	  return p;
  }
  @Test
  public void testIsEmpty() {
    assertFalse(pointset1.isEmpty());
  }

  @Test
  public void testSize() {
    assertTrue(pointset1.size() == 10);
  }

  @Test
  public void testInsert() {
    pointset1.insert(new Point2D(1,1));
  }

  @Test
  public void testContains() {
    pointset1.contains(new Point2D(1,1));
  }

  @Test
  public void testRange() {
    Iterable<Point2D> iterable = pointset1.range(new RectHV(-100,100,-100,100));
    int count = 0;
    for(Point2D p : iterable) {
    	count++;
    }
    assertTrue(count == 11);
  }

  @Test
  public void testNearest() {
    assertTrue(pointset1.nearest(new Point2D(2,2)).equals(new Point2D(1,1)));
  }

}
