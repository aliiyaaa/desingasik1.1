package algorithms.selection;

import algorithms.metrics.Metrics;
import java.util.Objects;

public final class DeterministicSelect {

    private DeterministicSelect() {}

    public static int select(int[] array, int k, Metrics metrics) {
        Objects.requireNonNull(array, "array == null");
        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("k out of range: " + k);
        }
        metrics.reset();
        int idx = selectIndex(array, 0, array.length - 1, k, metrics);
        return array[idx];
    }

    private static int selectIndex(int[] a, int left, int right, int kIndex, Metrics m) {
        if (left == right) return left;

        m.enterRecursion();
        try {
            int pivotIndex = medianOfMedians(a, left, right, m);
            pivotIndex = partition(a, left, right, pivotIndex, m);

            if (kIndex == pivotIndex) {
                return pivotIndex;
            } else if (kIndex < pivotIndex) {
                return selectIndex(a, left, pivotIndex - 1, kIndex, m);
            } else {
                return selectIndex(a, pivotIndex + 1, right, kIndex, m);
            }
        } finally {
            m.exitRecursion();
        }
    }

    private static int partition(int[] a, int left, int right, int pivotIndex, Metrics m) {
        int pivotValue = a[pivotIndex];
        swap(a, pivotIndex, right, m);
        int store = left;
        for (int j = left; j < right; j++) {
            m.incComparisons();
            if (a[j] < pivotValue) {
                swap(a, store, j, m);
                store++;
            }
        }
        swap(a, store, right, m);
        return store;
    }

    private static int medianOfMedians(int[] a, int left, int right, Metrics m) {
        int n = right - left + 1;
        if (n <= 5) {
            insertionSort(a, left, right, m);
            return left + n / 2;
        }

        int mediansWrite = left;
        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            insertionSort(a, i, subRight, m);
            int median = i + (subRight - i) / 2;
            swap(a, mediansWrite, median, m);
            mediansWrite++;
        }

        int numMedians = mediansWrite - left;
        int mid = left + (numMedians - 1) / 2;
        return selectIndex(a, left, left + numMedians - 1, mid, m);
    }

    private static void insertionSort(int[] a, int left, int right, Metrics m) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left) {
                m.incComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    m.incSwaps();
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
            m.incSwaps();
        }
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        if (i == j) return;
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        m.incSwaps();
    }
}
