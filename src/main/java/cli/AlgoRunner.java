package cli;

import algorithms.sorting.MergeSort;
import algorithms.sorting.QuickSort;
import algorithms.selection.DeterministicSelect;
import algorithms.closest.ClosestPair;
import algorithms.metrics.Metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class AlgoRunner {
    public static void main(String[] args) throws Exception {
        String algo = "mergesort";
        int n = 1000;
        int trials = 1;
        String outputFile = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--algo": algo = args[++i]; break;
                case "--n": n = Integer.parseInt(args[++i]); break;
                case "--trials": trials = Integer.parseInt(args[++i]); break;
                case "--output": outputFile = args[++i]; break;
            }
        }

        PrintWriter out;
        if (outputFile != null) {
            File f = new File(outputFile);
            boolean exists = f.exists();
            out = new PrintWriter(new FileWriter(f, true));
            if (!exists) {
                out.println(Metrics.csvHeader());
            }
        } else {
            out = new PrintWriter(System.out, true);
            out.println(Metrics.csvHeader());
        }

        Random rnd = new Random();
        for (int t = 0; t < trials; t++) {
            int[] arr = rnd.ints(n, 0, 1_000_000).toArray();

            Metrics metrics = new Metrics(algo);
            long start = System.nanoTime();

            switch (algo.toLowerCase()) {
                case "mergesort":
                    MergeSort.sort(Arrays.copyOf(arr, arr.length), metrics);
                    break;
                case "quicksort":
                    QuickSort.sort(Arrays.copyOf(arr, arr.length), metrics);
                    break;
                case "deterministicselect":
                    int k = n / 2;
                    DeterministicSelect.select(Arrays.copyOf(arr, arr.length), k, metrics);
                    break;
                case "closestpair":
                    ClosestPair.Point[] pts = ClosestPair.randomPoints(n, rnd);
                    ClosestPair.closestPair(pts, metrics);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown algo: " + algo);
            }

            long time = System.nanoTime() - start;
            out.println(metrics.toCsvLine(n, time));
        }

        out.flush();
        if (outputFile != null) out.close();
    }
}
