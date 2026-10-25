# 📘 CS Core & SDE Revision Notes

## 1. Sorting Algorithms: QuickSort vs. MergeSort

| Feature | MergeSort | QuickSort |
| :--- | :--- | :--- |
| **Strategy** | Divide & Conquer (Split in half, sort halves, merge) | Partitioning around a Pivot element |
| **Best / Avg Time** | $O(N \log N)$ | $O(N \log N)$ |
| **Worst Time** | $O(N \log N)$ (Guaranteed!) | $O(N^2)$ (When pivot is smallest/largest element) |
| **Auxiliary Space** | $O(N)$ (Requires temporary array to merge) | $O(1)$ extra space ($O(\log N)$ call stack) |
| **Stability** | **Stable** (Preserves relative order of duplicates) | **Unstable** (Can reorder duplicate keys) |

### **Java Standard Library Implementation Details**
* `Arrays.sort(int[] a)`: Uses **Dual-Pivot QuickSort**. Fast cache performance, in-place, memory efficient ($O(1)$ extra space).
* `Collections.sort(List<T>)` / `Arrays.sort(Object[] a)`: Uses **TimSort** (Hybrid MergeSort + InsertionSort). Guaranteed stable sorting so equal objects don't switch order.

---

## 2. Core Pattern Triggers (DSA)

* **Two Pointers (Inward)**: Sorted array + Find pair summing to target ($O(N)$ time, $O(1)$ space).
* **Two Pointers (Fast & Slow)**: In-place array partitioning (e.g. Move Zeroes) or Linked List cycle detection.
* **Sliding Window (Fixed)**: Contiguous subarray of fixed size $K$ (e.g. Max average of size $K$).
* **Sliding Window (Dynamic)**: Longest/shortest contiguous substring satisfying condition (Expand right, shrink left).
* **Prefix Sum + HashMap**: Subarray sum equals $K$ with negative numbers allowed ($O(N)$ time).
* **Monotonic Stack**: Next/Previous Greater or Smaller element, Daily Temperatures, Histogram rectangle.
* **Monotonic Deque**: Sliding Window Maximum ($O(N)$ time using `ArrayDeque`).
