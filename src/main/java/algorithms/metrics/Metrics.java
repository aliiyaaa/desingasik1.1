package algorithms.metrics;

public class Metrics {
    private final String algorithm;

    private long comparisons = 0;
    private long swaps = 0;
    private long allocations = 0;

    private int currentDepth = 0;
    private int maxDepth = 0;

    public Metrics(String algorithm) {
        this.algorithm = algorithm;
    }

    public void reset() {
        comparisons = 0;
        swaps = 0;
        allocations = 0;
        currentDepth = 0;
        maxDepth = 0;
    }

    // --- counters ---
    public void incComparisons() { comparisons++; }
    public void incComparisons(long delta) { comparisons += delta; }
    public long getComparisons() { return comparisons; }

    public void incSwaps() { swaps++; }
    public void incSwaps(long delta) { swaps += delta; }
    public long getSwaps() { return swaps; }

    public void incAllocations() { allocations++; }
    public void incAllocations(long delta) { allocations += delta; }
    public long getAllocations() { return allocations; }

    // --- recursion depth tracking ---
    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        if (currentDepth > 0) currentDepth--;
    }

    public int getCurrentDepth() { return currentDepth; }
    public int getMaxDepth() { return maxDepth; }

    public String getAlgorithm() { return algorithm; }

    // CSV helpers
    public static String csvHeader() {
        return "algorithm,n,time_ns,comparisons,swaps,allocations,max_depth";
    }

    public String toCsvLine(int n, long timeNs) {
        return String.format("%s,%d,%d,%d,%d,%d,%d",
                escapeCsv(algorithm),
                n,
                timeNs,
                comparisons,
                swaps,
                allocations,
                maxDepth
        );
    }

    private static String escapeCsv(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }
}