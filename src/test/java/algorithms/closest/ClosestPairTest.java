package algorithms.closest;

import algorithms.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ClosestPairTest {

    @Test
    void testSimpleCase() {
        ClosestPair.Point2D[] pts = {
                new ClosestPair.Point2D(0, 0),
                new ClosestPair.Point2D(3, 4),
                new ClosestPair.Point2D(7, 1),
                new ClosestPair.Point2D(2, 2)
        };
        Metrics m = new Metrics("closest-pair");
        double res = ClosestPair.findClosest(pts, m);
        assertEquals(Math.sqrt(8), res, 1e-9);
        assertTrue(m.getComparisons() > 0);
        assertTrue(m.getMaxDepth() > 0);
    }

    @Test
    void testTwoPoints() {
        ClosestPair.Point2D[] pts = {
                new ClosestPair.Point2D(1, 1),
                new ClosestPair.Point2D(4, 5)
        };
        Metrics m = new Metrics("closest-pair");
        double res = ClosestPair.findClosest(pts, m);
        assertEquals(5.0, res, 1e-9);
    }

    @Test
    void testRandomVsNaive() {
        Random rnd = new Random(42);
        for (int t = 0; t < 10; t++) {
            int n = 50;
            ClosestPair.Point2D[] pts = new ClosestPair.Point2D[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point2D(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
            }

            Metrics m = new Metrics("closest-pair");
            double fast = ClosestPair.findClosest(pts, m);
            double slow = bruteForce(pts);
            assertEquals(slow, fast, 1e-9);
        }
    }

    private double bruteForce(ClosestPair.Point2D[] pts) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                double d = Math.sqrt(dx * dx + dy * dy);
                if (d < min) min = d;
            }
        }
        return min;
    }
}
