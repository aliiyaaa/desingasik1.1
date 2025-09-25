# Assignment 1 – Divide and Conquer Algorithms

**Author:** Amanzhol Madiyar (SE-2407)  
**Course:** Algorithms & Data Structures  
**Repository:** [daa-assignment1.1](https://github.com/KasperSaint07/daa-assignment1.1)

---

##  Learning Goals
- Implement classic divide-and-conquer algorithms with **safe recursion patterns**.
- Analyse running-time recurrences using **Master Theorem (3 cases)** and **Akra–Bazzi intuition**.
- Collect metrics (time, recursion depth, comparisons/allocations).
- Validate theoretical analysis with **empirical measurements**.
- Practice **GitHub workflow** with feature branches and structured commits.

---

##  Project Structure
src/
├── main/java
│ ├── mergesort/ # MergeSort implementation with cutoff + buffer
│ ├── quicksort/ # QuickSort with randomized pivot & safe recursion
│ ├── select/ # Deterministic Select (Median-of-Medians)
│ ├── closestpair/ # Closest Pair of Points (O(n log n) D&C)
│ ├── metrics/ # Metrics collection (time, comparisons, depth, ops)
│ ├── cli/ # CLI runner, CSV output
│ └── bench/ # JMH benchmarks
│ └── utils/ # utils
├── test/java
│ ├── mergesort/ # MergeSort tests
│ ├── quicksort/ # QuickSort tests (depth boundedness)
│ ├── select/ # Deterministic Select tests
│ ├── closestpair/ # Validation vs O(n²) implementation
│ └── metrics/ # Metrics unit tests


---

## ️ Architecture Notes
- **Recursion Depth Control**
  - QuickSort always recurses on the **smaller partition** and iterates on the larger one. This bounds the recursion depth to `O(log n)` with high probability.
  - MergeSort includes a **cutoff**: for very small subarrays, it switches to InsertionSort to reduce overhead.
- **Buffer Reuse**
  - MergeSort reuses a single buffer array to avoid repeated allocations.
- **Metrics**
  - Each algorithm tracks:
    - `comparisons`
    - `operations` (assignments/swaps)
    - `recursionDepth`
    - `timeNs` (execution time)

---

##  Recurrence Analysis

### 1. MergeSort
- Recurrence:  
  `T(n) = 2T(n/2) + Θ(n)`
- By **Master Theorem, Case 2**:  
  `T(n) = Θ(n log n)`
- Notes: cutoff to insertion sort reduces constant factors.

### 2. QuickSort (randomized pivot, recurse smaller side)
- Recurrence (average):  
  `T(n) = T(n/2) + T(n/2) + Θ(n)` → `Θ(n log n)`
- Worst case: `Θ(n²)` (rare due to randomization).
- Recursion depth: `O(log n)` in expectation.

### 3. Deterministic Select (Median-of-Medians)
- Recurrence:  
  `T(n) = T(n/5) + T(7n/10) + Θ(n)`
- By **Akra–Bazzi intuition**:  
  `T(n) = Θ(n)`
- Guarantees linear time selection.

### 4. Closest Pair of Points (2D)
- Recurrence:  
  `T(n) = 2T(n/2) + Θ(n)`
- By **Master Theorem, Case 2**:  
  `T(n) = Θ(n log n)`
- “Strip check” uses ≤ 7–8 neighbors, constant work per point.

---

##  Empirical Results

**Plots (from `results.csv`):**
- Time vs n (log-log scale).
- Depth vs n (QuickSort depth bounded by ~2 log₂ n).
- Comparisons/operations vs n.

👉 Insert your generated plots here (Excel, Google Sheets, matplotlib).

**Discussion:**
- Measured times align with theoretical expectations (`n log n` for Merge/Quick/Closest, linear for Select).
- QuickSort shows higher variance due to random pivoting.
- MergeSort benefits from cutoff (InsertionSort on n ≤ 16).
- Closest Pair has higher constant factors but scales as `n log n`.

---

##  Testing
- **Sorting correctness**: verified on random, sorted, and reverse arrays.
- **QuickSort depth**: max depth checked ≤ ~2·⌊log₂ n⌋ + O(1).
- **Deterministic Select**: compared against `Arrays.sort()[k]` on 100 random trials.
- **Closest Pair**: validated vs O(n²) implementation for n ≤ 2000.

---

##  GitHub Workflow
- Branches used:
  - `1-add-metrics-system`
  - `3-add-mergesort-with-buffer`
  - `5-add-quicksort`
  - `7-deterministic-select-median-of-medians`
  - `13-implement-closest-pair-of-points`
  - `17-benchjmh-harness`
  - `19-docsreport`
- Commit storyline followed **Conventional Commits** style:
  - `init: maven, junit5, readme`
  - `feat(metrics): counters, depth tracker, CSV writer`
  - `feat(mergesort): baseline + reuse buffer + cutoff + tests`
  - `feat(quicksort): randomized pivot, smaller-side recursion + tests`
  - `feat(select): deterministic MoM5 + tests`
  - `feat(closest): divide-and-conquer + strip check`
  - `bench(jmh): harness for select vs sort`
  - `docs(report): master theorem + plots`
  - `release: v1.0`

---

##  Summary
- All four required algorithms implemented with correct complexities.
- Safe recursion patterns ensured bounded depth.
- Metrics collection + CSV output enabled analysis.
- Tests confirm correctness against known results.
- GitHub workflow followed with feature branches and structured commits.

 Next steps: extend report with richer plots, explore cache effects and GC influence.
