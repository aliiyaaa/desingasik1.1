# DAA_Assignment_1

## Overview
Repository with implementations and experiments for:

- **MergeSort** (D&C, Master Theorem Case 2)
- **QuickSort** (random pivot, smaller-first recursion)
- **Deterministic Select** (Median-of-Medians, O(n))
- **Closest Pair of Points** (2D, O(n log n))

## Learning Goals
- Implement classic divide-and-conquer algorithms (MergeSort, QuickSort, Deterministic Select, Closest Pair).
- Analyse running-time recurrences using **Master Theorem** and **Akra–Bazzi**.
- Collect metrics: execution time, comparisons, swaps, allocations, recursion depth.
- Communicate results via this README report and clean Git history.


## Recurrence Analysis

### MergeSort
- **Recurrence:** T(n) = 2T(n/2) + Θ(n)
- **Master Theorem:** a = 2, b = 2, f(n) = Θ(n) → Case 2
- **Solution:** T(n) = Θ(n log n)

### QuickSort (randomized, smaller-first recursion)
- **Expected recurrence:** T(n) ≈ 2T(n/2) + Θ(n)
- **Expected complexity:** Θ(n log n)
- **Worst-case:** Θ(n²) (rare with random pivot)
- **Recursion depth:** bounded by O(log n) due to smaller-first recursion

### Deterministic Select (Median-of-Medians)
- **Recurrence:** T(n) = T(n/5) + T(7n/10) + Θ(n)
- **Akra–Bazzi intuition:** solves to Θ(n)
- **Guarantee:** true linear-time selection

### Closest Pair of Points
- **Recurrence:** T(n) = 2T(n/2) + Θ(n)
- **Master Theorem:** Case 2
- **Solution:** Θ(n log n)

## Architecture Notes
- **Metrics Tracking:**  
  Implemented in `Metrics` class. Tracks comparisons, swaps, allocations, execution time, and recursion depth for fair algorithm comparisons.

- **MergeSort:**  
  Uses a reusable auxiliary buffer to reduce allocations. Falls back to InsertionSort for small subarrays (cutoff ≈ 16).

- **QuickSort:**  
  Picks a pivot randomly. Always recurses into the smaller partition first and tail-recurses on the larger, guaranteeing O(log n) stack depth.

- **Deterministic Select:**  
  Groups elements into fives, computes median-of-medians as pivot, partitions in-place, and only recurses into the side containing the target. Depth controlled with smaller-first recursion.

- **Closest Pair:**  
  Standard divide-and-conquer. Sorts by x, divides, checks strip near the middle. Optimized with y-sorting and 7–8 neighbor comparisons.

## How to Run
```bash
./gradlew build
./gradlew test
java -jar build/libs/algos.jar --algo mergesort --n 10000 --out results.csv
