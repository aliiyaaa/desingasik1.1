package algorithms.sorting;

import algorithms.metrics.Metrics;

public class MergeSort {

    public static long sort(int[] array, Metrics metrics) {
        metrics.reset();
        long start = System.nanoTime();

        mergeSort(array,0, array.length - 1, metrics);

        long end = System.nanoTime();
        return end - start;
    }

    private static void mergeSort(int[] array, int left, int right, Metrics metrics) {
        metrics.enterRecursion();

        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(array, left, mid, metrics);
            mergeSort(array, mid + 1, right, metrics);

            merge(array, left, mid, right, metrics);
        }

        metrics.exitRecursion();
    }

    private static void merge(int[] array, int left, int mid, int right, Metrics metrics) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];
        metrics.incAllocations(2);

        for (int i = 0; i < n1; i++) {
            L[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            metrics.incComparisons();
            if (L[i] <= R[j]) {
                array[k++] = L[i++];
            } else {
                array[k++] = R[j++];
            }
        }

        while (i < n1) {
            array[k++] = L[i++];
        }
        while (j < n2) {
            array[k++] = R[j++];
        }
    }
}
