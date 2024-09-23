package bearmaps;

import org.junit.Test;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.Stopwatch;
import static org.junit.Assert.assertEquals;
//@Josh Hug video support
public class KDTreeTest {
    private static Random r = new Random(500);

    private static KDTree buildLectureTree() {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        return kd;
    }
    private static void buildTreeWithDoubles() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(2, 3);

        KDTree kd = new KDTree(List.of(p1, p2));
    }
    @Test
    public void testNearestDemoSlide() {
        KDTree kd = buildLectureTree();
        Point actual = kd.nearest(0, 7);
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }
    private Point randomPoints() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);
    }

    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i += 1) {
            points.add(randomPoints());
        }
        return points;
    }
    @Test
    public void testWith1000Points() {
        List<Point> points1000 = randomPoints(1000);
        NaivePointSet nps = new NaivePointSet(points1000);
        KDTree kd = new KDTree(points1000);

        List<Point> queries200 = randomPoints(200);
        for (Point p : queries200) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(p.distance(expected, p), p.distance(actual, p), 0.001);
        }
    }
    public void pointAndQuerieTest(int pointamount, int queryamount) {
        List<Point> points = randomPoints(pointamount);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = randomPoints(queryamount);
        for (Point p : queries) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(p.distance(expected, p), p.distance(actual, p), 0.001);
        }
    }
    @Test
    public void testWith1000PointsAnd200Queries() {
        int pointCount = 1000;
        int queryCount = 200;
        pointAndQuerieTest(pointCount, queryCount);
    }
    @Test
    public void testWith10000PointsAnd2000Queries() {
        int pointCount = 10000;
        int queryCount = 2000;
        pointAndQuerieTest(pointCount, queryCount);
    }

   /* public class TimeAList {
        private void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
            System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
            System.out.printf("------------------------------------------------------------\n");
            for (int i = 0; i < Ns.size(); i += 1) {
                int N = Ns.get(i);
                double time = times.get(i);
                int opCount = opCounts.get(i);
                double timePerOp = time / opCount * 1e6;
                System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
            }
        }
    }*/
    @Test
    public void timingTest() {
        List<Point> Npoints = randomPoints(100000);
        List<Point> quer = randomPoints(10000);
        KDTree kd = new KDTree(Npoints);
        NaivePointSet naiveSet =  new NaivePointSet(Npoints);


        Stopwatch record = new Stopwatch();
        for (Point p : quer) {
            naiveSet.nearest(p.getX(), p.getY());
        }
        double rectime = record.elapsedTime();
        System.out.println("Naive time:" + rectime);

        record = new Stopwatch();
        for (Point p : quer) {
            kd.nearest(p.getX(), p.getY());
        }
        rectime = record.elapsedTime();
        System.out.println("KDTree time:" + rectime);
    }
}
