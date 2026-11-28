# Day 39 — Advanced Heap Patterns: Multi-Way Merge & Greedy CPU Scheduling

## 📌 Problem 1: Merge k Sorted Lists (LC 23 - Hard)

### 1. Intuition: The Multi-Way Merge
- With $k$ sorted lists, the global minimum is always one of the $k$ current head nodes.
- Maintain a **Min-Heap of size at most $k$**.
- Each step:
  1. `poll()` the smallest node.
  2. Connect it to the merged result.
  3. Push `smallest.next` into the heap (if non-null).

### 2. Complexity Analysis
- Total nodes: $N$.
- Number of lists: $k$.
- Heap size: never exceeds $k$.
- Each node is offered and polled exactly once:
  $$\text{Time Complexity} = O(N \log k)$$
  $$\text{Space Complexity} = O(k)$$
- Compare this with naive merging: $O(N \cdot k)$ or sorting all nodes: $O(N \log N)$. The heap multi-way merge is optimal!

---

## 📌 Problem 2: Task Scheduler (LC 621)

### 1. Greedy Choice Property
- The task with the highest frequency (`maxFreq`) is the bottleneck.
- Tasks are arranged into frames of size `n + 1`:
  $$\text{Frames Count} = \text{maxFreq} - 1$$
  $$\text{Frame Width} = n + 1$$
  $$\text{Total Base Slots} = (\text{maxFreq} - 1) \times (n + 1) + \text{maxCount}$$
- If there are enough other tasks to fill all idle gaps with zero idle time, the answer is simply `tasks.length`.
- Therefore:
  $$\text{Result} = \max(\text{tasks.length}, (\text{maxFreq} - 1) \times (n + 1) + \text{maxCount})$$

---
*Created as part of the 90-Day SDE Master Preparation Bootcamp.*
