package benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class SelectVsSortBenchmark {

    @Param({"100", "1000", "10000", "100000"})
    private int n;

    private int[] data;
    private int k;

    @Setup(Level.Invocation)
    public void setup() {
        Random rnd = new Random(42);
        data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = rnd.nextInt();
        }
        k = n / 2;
    }

    @Benchmark
    public int quickSelect() {
        int[] copy = Arrays.copyOf(data, data.length);
        return quickSelect(copy, 0, copy.length - 1, k);
    }

    @Benchmark
    public int fullSort() {
        int[] copy = Arrays.copyOf(data, data.length);
        Arrays.sort(copy);
        return copy[k];
    }

    private int quickSelect(int[] arr, int left, int right, int k) {
        while (true) {
            if (left == right) return arr[left];
            int pivotIndex = partition(arr, left, right);
            if (k == pivotIndex) {
                return arr[k];
            } else if (k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left;
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right);
        return i;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
