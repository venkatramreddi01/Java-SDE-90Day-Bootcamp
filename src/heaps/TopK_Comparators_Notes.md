# Day 38 — Top-K Patterns: Custom Comparators & 2D Heaps

## 📌 The Golden Rule Summary

| Problem | Goal | Heap Type | Comparator Logic | Eviction Action |
| :--- | :--- | :--- | :--- | :--- |
| **LC 347 (Top K Frequent)** | $K$ Highest Frequencies | **Min-Heap** | `(a, b) -> count.get(a) - count.get(b)` | Drops lowest frequency |
| **LC 973 (K Closest Points)** | $K$ Smallest Distances | **Max-Heap** | `(p1, p2) -> Integer.compare(dist2, dist1)` | Drops largest distance |

---

## ⚡ Java Comparator Tips for FAANG Interviews

### 1. Integer.compare vs Direct Subtraction
Never write `(a, b) -> a - b` when dealing with multiplications or large integers (e.g. coordinates up to $10^4$). Subtraction can overflow:
```java
// SAFE:
Integer.compare(d1, d2); // Handles all sign/overflow ranges correctly
```

### 2. Ascending vs Descending Lambda Rule
- **Min-Heap (Ascending)**: `(a, b) -> Integer.compare(a, b)` $\rightarrow$ smallest sits at root.
- **Max-Heap (Descending)**: `(a, b) -> Integer.compare(b, a)` $\rightarrow$ largest sits at root.

---

## 📊 Complexity Breakdown

- **LC 347 (Top K Frequent)**:
  - Frequency counting: $O(N)$
  - Heap insertions for $D$ distinct numbers: $O(D \log K)$
  - Total Time: **$O(N \log K)$**
  - Space: **$O(N)$**

- **LC 973 (K Closest Points)**:
  - Iterating through $N$ points with heap of size $K$: $O(N \log K)$
  - Total Time: **$O(N \log K)$**
  - Space: **$O(K)$** auxiliary heap space

---
*Created as part of the 90-Day SDE Master Preparation Bootcamp.*
