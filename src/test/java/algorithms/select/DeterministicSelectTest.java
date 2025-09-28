package algorithms.select;

import algorithms.metrics.Metrics;
import algorithms.selection.DeterministicSelect;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DeterministicSelectTest {

    @Test
    void testRandomTrials100() {
        Random rnd = new Random(12345);
        for (int t = 0; t < 100; t++) {
            int n = 1 + rnd.nextInt(200);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(1000) - 500;
            int k = rnd.nextInt(n);

            int[] copy = Arrays.copyOf(a, a.length);
            Arrays.sort(copy);
            int expected = copy[k];

            Metrics m = new Metrics("deterministic-select");
            int got = DeterministicSelect.select(a, k, m);
            assertEquals(expected, got);
        }
    }

    @Test
    void testDuplicatesAndEdges() {
        int[] a = {5, 1, 5, 5, 2, 5, 3};
        int[] sorted = Arrays.copyOf(a, a.length);
        Arrays.sort(sorted);

        Metrics m1 = new Metrics("deterministic-select");
        assertEquals(sorted[0], DeterministicSelect.select(Arrays.copyOf(a, a.length), 0, m1));

        Metrics m2 = new Metrics("deterministic-select");
        assertEquals(sorted[1], DeterministicSelect.select(Arrays.copyOf(a, a.length), 1, m2));

        int[] b = {9, 7, 2, 4};
        Arrays.sort(b);
        Metrics m3 = new Metrics("deterministic-select");
        assertEquals(b[b.length - 1], DeterministicSelect.select(Arrays.copyOf(b, b.length), b.length - 1, m3));
    }

    @Test
    void testInvalidK() {
        Metrics m = new Metrics("deterministic-select");
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{1, 2, 3}, -1, m));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{1, 2, 3}, 3, m));
    }
}
