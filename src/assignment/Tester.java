package assignment;

import static org.junit.Assert.assertTrue;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import assignment.KdTree.DPoint;

public class Tester {

	 public static void main(String[] args) {
	      Result result = JUnitCore.runClasses(Tester.class);
			
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println("Unit Test Result:"+result.wasSuccessful());
	   }
	 

    @Test
    public void testKdTree() {
        List<DPoint> points = new ArrayList<DPoint>();
        DPoint p1 = new DPoint(new double[] {5.2, 9.5});
        points.add(p1);
        DPoint p2 = new DPoint(new double[] {7.3, 4.8});
        points.add(p2);
        DPoint p3 = new DPoint(new double[] {6, 6});
        points.add(p3);
        KdTree<DPoint> kdTree = new KdTree<DPoint>(points);

        Collection<DPoint> result = kdTree.nearestNeighbourSearch(1, p3);
        assertTrue("K-D Tree query error. query=(k=1, p=(9, 6)) returned="+result, result.contains(p3));

        DPoint search = new DPoint(new double[] {1, 4});
        result = kdTree.nearestNeighbourSearch(4, search);
        assertTrue("K-D Tree query error. query=(k=4, p=(1, 4)) returned="+result, (result.contains(p1) &&
                                                                                    result.contains(p2) )
        );

        kdTree.remove(p3);
        kdTree.remove(p1);
        kdTree.remove(p2);
    }

    @Test
    public void testKdTree_as_iterable() {
        List<DPoint> points = new ArrayList<DPoint>();
        DPoint p1 = new DPoint(new double[] {7.6, 3.5});
        points.add(p1);
        DPoint p2 = new DPoint(new double[] {5.9, 4.1});
        points.add(p2);
        DPoint p3 = new DPoint(new double[] {9.4, 6.8});
        points.add(p3);
        DPoint p4 = new DPoint(new double[] {4.1, 7.1});
        points.add(p4);
        DPoint p5 = new DPoint(new double[] {8.1, 1.9});
        points.add(p5);
        DPoint p6 = new DPoint(new double[] {7.2, 0.1});
        points.add(p6);
        KdTree<DPoint> kdTree = new KdTree<DPoint>(points);

        for (final DPoint p : kdTree)
           assertTrue(kdTree.contains(p));
    }

}
