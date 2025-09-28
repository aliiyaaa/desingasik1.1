package algorithms.sorting;

import algorithms.metrics.Metrics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void testSortSmallArray() {
        int[] arr = {5, 2, 9, 1, 5, 6};
        Metrics metrics = new Metrics("MergeSort");
        long time = MergeSort.sort(arr, metrics);

        assertArrayEquals(new int[]{1, 2, 5, 5, 6, 9}, arr);
        assertTrue(time > 0, "Время должно быть положительным");
        assertTrue(metrics.getComparisons() > 0, "Должны быть сравнения");
        assertTrue(metrics.getAllocations() > 0, "Должны быть аллокации");
        assertTrue(metrics.getMaxDepth() > 0, "Должна быть глубина рекурсии");
    }

    @Test
    void testSortEmptyArray() {
        int[] arr = {};
        Metrics metrics = new Metrics("MergeSort");
        long time = MergeSort.sort(arr, metrics);

        assertArrayEquals(new int[]{}, arr);
        assertTrue(time >= 0);
        assertEquals(0, metrics.getComparisons());
    }

    @Test
    void testSortSingleElement() {
        int[] arr = {42};
        Metrics metrics = new Metrics("MergeSort");
        long time = MergeSort.sort(arr, metrics);

        assertArrayEquals(new int[]{42}, arr);
        assertTrue(time >= 0);
        assertEquals(0, metrics.getComparisons());
    }

    @Test
    void testSortAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        Metrics metrics = new Metrics("MergeSort");
        long time = MergeSort.sort(arr, metrics);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
        assertTrue(metrics.getComparisons() > 0);
    }
}
