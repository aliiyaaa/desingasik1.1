package algorithms.sorting;

import algorithms.metrics.Metrics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void testSortSmallArray() {
        int[] arr = {9, 3, 7, 1, 5, 2};
        Metrics metrics = new Metrics("QuickSort");
        long time = QuickSort.sort(arr, metrics);

        assertArrayEquals(new int[]{1, 2, 3, 5, 7, 9}, arr);
        assertTrue(time > 0, "Время должно быть положительным");
        assertTrue(metrics.getComparisons() > 0, "Должны быть сравнения");
        assertTrue(metrics.getSwaps() > 0, "Должны быть свапы");
        assertTrue(metrics.getMaxDepth() > 0, "Должна быть глубина рекурсии");
    }

    @Test
    void testSortEmptyArray() {
        int[] arr = {};
        Metrics metrics = new Metrics("QuickSort");
        long time = QuickSort.sort(arr, metrics);

        assertArrayEquals(new int[]{}, arr);
        assertTrue(time >= 0);
        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getSwaps());
    }

    @Test
    void testSortSingleElement() {
        int[] arr = {42};
        Metrics metrics = new Metrics("QuickSort");
        long time = QuickSort.sort(arr, metrics);

        assertArrayEquals(new int[]{42}, arr);
        assertTrue(time >= 0);
        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getSwaps());
    }

    @Test
    void testSortAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        Metrics metrics = new Metrics("QuickSort");
        long time = QuickSort.sort(arr, metrics);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
        assertTrue(metrics.getComparisons() > 0);
    }

    @Test
    void testSortReversedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        Metrics metrics = new Metrics("QuickSort");
        long time = QuickSort.sort(arr, metrics);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
        assertTrue(metrics.getComparisons() > 0);
        assertTrue(metrics.getSwaps() > 0);
    }
}