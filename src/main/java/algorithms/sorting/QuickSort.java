package algorithms.sorting;

import algorithms.metrics.Metrics;

import java.util.Random;

public class QuickSort {
    private static final Random RAND = new Random();

    public static long sort(int[] array, Metrics metrics) {
        metrics.reset();
        long start = System.nanoTime();

        quickSort(array, 0, array.length - 1, metrics);

        long end = System.nanoTime();
        return end - start;
    }

    private static void quickSort(int[] array, int left, int right, Metrics metrics) {
        while (left < right) {
            metrics.enterRecursion();

            int pivotIndex = left + RAND.nextInt(right - left + 1);
            int pivot = array[pivotIndex];


            swap(array, pivotIndex, right, metrics);

            int partitionIndex = partition(array, left, right, pivot, metrics);

            if (partitionIndex - left < right - partitionIndex) {
                quickSort(array, left, partitionIndex - 1, metrics);
                left = partitionIndex + 1;
            } else {
                quickSort(array, partitionIndex + 1, right, metrics);
                right = partitionIndex - 1;
            }

            metrics.exitRecursion();
        }
    }

    private static int partition(int[] array, int left, int right, int pivot, Metrics metrics) {
        int i = left;
        for (int j = left; j < right; j++) {
            metrics.incComparisons();
            if (array[j] <= pivot) {
                swap(array, i, j, metrics);
                i++;
            }
        }
        swap(array, i, right, metrics);
        return i;
    }

    private static void swap(int[] array, int i, int j, Metrics metrics) {
        if (i != j) {
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
            metrics.incSwaps();
        }
    }
}