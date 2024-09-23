package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import edu.princeton.cs.algs4.Stopwatch;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class ArrayHeapMinPQTest {

    @Test
    public void addTest() {
        ArrayHeapMinPQ<Integer> testPq = new ArrayHeapMinPQ<>();
        testPq.add(2, 2);
        testPq.add(4, 4);
        testPq.add(5,5 );
        testPq.add(3, 3);
        testPq.add(1, 1);
        assertEquals(5, testPq.size());
    }
    @Test
    public void containsTest() {
        ArrayHeapMinPQ<Integer> testPq = new ArrayHeapMinPQ<>();
        testPq.add(2, 2);
        testPq.add(4, 4);
        testPq.add(5,5 );
        testPq.add(3, 3);
        testPq.add(1, 1);

        assertTrue(testPq.contains(1));
        assertTrue(testPq.contains(2));
        assertTrue(testPq.contains(3));
        assertTrue(testPq.contains(4));
        assertTrue(testPq.contains(5));
        assertFalse(testPq.contains(6));
        assertFalse(testPq.contains(7));
        assertFalse(testPq.contains(8));

    }
    @Test
    public void getSmallestTest() {
        ArrayHeapMinPQ<Integer> testPq = new ArrayHeapMinPQ<>();
        testPq.add(4, 4);
        testPq.add(5,5 );
        testPq.add(3, 3);
        testPq.add(2, 2);
        assertTrue(testPq.getSmallest() == 2);
        assertFalse(testPq.getSmallest() == 3);
    }
    @Test
    public void removeSmallestTest() {
        ArrayHeapMinPQ<Integer> testPq = new ArrayHeapMinPQ<>();
        testPq.add(4, 4);
        testPq.add(5,5 );
        testPq.add(3, 3);
        testPq.add(2, 2);
        assertTrue(testPq.getSmallest() == 2);
        assertTrue(testPq.removeSmallest() == 2);
        assertTrue(testPq.getSmallest() == 3);
        assertTrue(testPq.removeSmallest() == 3);
        assertTrue(testPq.getSmallest() == 4);
        assertTrue(testPq.removeSmallest() == 4);
        assertTrue(testPq.getSmallest() == 5);
        assertTrue(testPq.removeSmallest() == 5);
        assertTrue(testPq.size() == 0);

    }
    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<Integer> testPq = new ArrayHeapMinPQ<>();
        testPq.add(4, 4);
        testPq.add(5,5 );
        testPq.add(3, 3);
        testPq.add(2, 2);

        testPq.changePriority(2, 6);
        testPq.changePriority(5, 2);
        assertTrue(testPq.getSmallest() == 5);
    }

    @Test
    public void timeTest() {
        Stopwatch record = new Stopwatch();
        ArrayHeapMinPQ<Integer> testPq = new ArrayHeapMinPQ<>();
        int iter = 0;
        while (iter < 10000) {
            int priority = 10000 - iter;
            testPq.add(iter, priority);
            iter++;
        }
        double rectime = record.elapsedTime();
        System.out.println("add time:" + rectime);

        Stopwatch middleRecord = new Stopwatch();
        int middleIter = 0;
        while (middleIter < 10000) {
            int incre = middleIter + 1;
            testPq.changePriority(middleIter, incre);
            middleIter++;
        }
        double middleRectime = middleRecord.elapsedTime();
        System.out.println("changePriority time:" + middleRectime);

        Stopwatch lastRecord = new Stopwatch();
        NaiveMinPQ<Integer> testNpq = new NaiveMinPQ<>();
        int lastIter = 0;
        while (lastIter < 10000) {
            int priority = 10000 - lastIter;
            testNpq.add(lastIter, priority);
            lastIter++;
        }
        double lastRectime = lastRecord.elapsedTime();
        System.out.println("add time:" + lastRectime);
    }
}
