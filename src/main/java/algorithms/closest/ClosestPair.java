package algorithms.closest;

import algorithms.metrics.Metrics;
import java.util.Arrays;

public class ClosestPair {

    public static class Point2D {
        public final double x, y;
        public Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static double findClosest(Point2D[] pts, Metrics m) {
        long start = System.nanoTime();
        m.reset();

        Arrays.sort(pts, (a, b) -> Double.compare(a.x, b.x));
        double result = closestRec(pts, 0, pts.length - 1, m);

        long end = System.nanoTime();
        long elapsed = end - start;
        System.out.println(m.toCsvLine(pts.length, elapsed));

        return result;
    }

    private static double closestRec(Point2D[] pts, int left, int right, Metrics m) {
        if (right - left <= 3) {
            return bruteForce(pts, left, right, m);
        }

        m.enterRecursion();
        try {
            int mid = (left + right) / 2;
            double d1 = closestRec(pts, left, mid, m);
            double d2 = closestRec(pts, mid + 1, right, m);
            double d = Math.min(d1, d2);

            double midX = pts[mid].x;
            final double delta = d;

            Point2D[] strip = Arrays.stream(pts, left, right + 1)
                    .filter(p -> Math.abs(p.x - midX) < delta)
                    .sorted((a, b) -> {
                        m.incComparisons();
                        return Double.compare(a.y, b.y);
                    })
                    .toArray(Point2D[]::new);
            m.incAllocations();

            for (int i = 0; i < strip.length; i++) {
                for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < d; j++) {
                    m.incComparisons();
                    d = Math.min(d, dist(strip[i], strip[j]));
                }
            }
            return d;
        } finally {
            m.exitRecursion();
        }
    }

    private static double bruteForce(Point2D[] pts, int l, int r, Metrics m) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = l; i <= r; i++) {
            for (int j = i + 1; j <= r; j++) {
                m.incComparisons();
                min = Math.min(min, dist(pts[i], pts[j]));
            }
        }
        return min;
    }

    private static double dist(Point2D a, Point2D b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
