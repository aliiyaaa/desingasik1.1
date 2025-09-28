package algorithms.closest;

import algorithms.metrics.Metrics;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static double findClosest(Point2D[] points, Metrics metrics) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("Need at least 2 points");
        }
        metrics.reset();

        Point2D[] sortedByX = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedByX, Comparator.comparingDouble(p -> p.x));
        Point2D[] aux = new Point2D[points.length]; // for merging by y
        metrics.incAllocations();

        return closest(sortedByX, aux, 0, points.length - 1, metrics);
    }

    private static double closest(Point2D[] pts, Point2D[] aux, int left, int right, Metrics m) {
        if (left >= right) return Double.POSITIVE_INFINITY;

        m.enterRecursion();
        try {
            int mid = (left + right) / 2;
            double deltaLeft = closest(pts, aux, left, mid, m);
            double deltaRight = closest(pts, aux, mid + 1, right, m);
            double delta = Math.min(deltaLeft, deltaRight);

            mergeByY(pts, aux, left, mid, right);

            int stripSize = 0;
            for (int i = left; i <= right; i++) {
                if (Math.abs(pts[i].x - pts[mid].x) < delta) {
                    aux[stripSize++] = pts[i];
                }
            }

            for (int i = 0; i < stripSize; i++) {
                for (int j = i + 1; j < stripSize && (aux[j].y - aux[i].y) < delta; j++) {
                    m.incComparisons();
                    delta = Math.min(delta, dist(aux[i], aux[j]));
                }
            }

            return delta;
        } finally {
            m.exitRecursion();
        }
    }

    private static void mergeByY(Point2D[] pts, Point2D[] aux, int left, int mid, int right) {
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if (pts[i].y <= pts[j].y) {
                aux[k++] = pts[i++];
            } else {
                aux[k++] = pts[j++];
            }
        }
        while (i <= mid) aux[k++] = pts[i++];
        while (j <= right) aux[k++] = pts[j++];

        System.arraycopy(aux, 0, pts, left, k);
    }

    private static double dist(Point2D a, Point2D b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static class Point2D {
        public final double x, y;
        public Point2D(double x, double y) {
            this.x = x; this.y = y;
        }
    }
}
