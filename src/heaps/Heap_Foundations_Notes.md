# Day 37 — Heap & PriorityQueue Foundations in Java

## 📌 Core Concepts

### 1. What is a Binary Heap?
A Binary Heap is a **Complete Binary Tree** stored as an array that satisfies the **Heap Order Property**:
- **Min-Heap**: `parent <= children` (Root is the absolute minimum).
- **Max-Heap**: `parent >= children` (Root is the absolute maximum).

### 2. Array Representation of Heaps
For any node at 0-based index `i`:
- **Left Child**: `2 * i + 1`
- **Right Child**: `2 * i + 2`
- **Parent**: `(i - 1) / 2`

---

## ☕ Java PriorityQueue Syntax Cheatsheet

```java
// 1. Min-Heap (Default in Java)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// 2. Max-Heap (Reverse Order)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
// or with lambda:
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

// 3. Custom Object Comparator (e.g., pairs {val, freq})
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); // Sort by freq asc
```

---

## ⚡ The Top-K Element Golden Rule

| Goal | Heap Type to Maintain | Heap Size Bound | Time Complexity | Auxiliary Space |
| :--- | :--- | :--- | :--- | :--- |
| **K-th Largest** | **Min-Heap** | **$K$** | $O(N \log K)$ | $O(K)$ |
| **K-th Smallest** | **Max-Heap** | **$K$** | $O(N \log K)$ | $O(K)$ |
| **Full Sort** | Any | $N$ | $O(N \log N)$ | $O(N)$ |

*Why?*
By bounding the heap size to $K$, every insertion/deletion costs only $O(\log K)$ instead of $O(\log N)$. When $K \ll N$, this yields substantial savings in time and memory.

---
*Created as part of the 90-Day SDE Master Preparation Bootcamp.*
