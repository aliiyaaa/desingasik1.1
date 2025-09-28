package algorithms.utils;

import algorithms.metrics.Metrics;

import java.util.Arrays;
import java.util.Random;

public class SortUtils {

    private static final Random RANDOM = new Random();

    public static int[] randomArray(int n, int bound) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = RANDOM.nextInt(bound);
        }
        return arr;
    }

    public static int[] sortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i;
        return arr;
    }

    public static int[] reversedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = n - 1 - i;
        return arr;
    }

    public static int[] copy(int[] arr) {
        return Arrays.copyOf(arr, arr.length);
    }

    public static void runAndMeasure(String algorithmName, int[] input, SortAlgorithm algorithm, Metrics metrics) {
        metrics.reset();
        long start = System.nanoTime();
        algorithm.sort(input, metrics);
        long end = System.nanoTime();
        System.out.println(metrics.toCsvLine(input.length, end - start));
    }


    @FunctionalInterface
    public interface SortAlgorithm {
        void sort(int[] arr, Metrics metrics);
    }
}

