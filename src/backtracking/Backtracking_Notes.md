# 🌲 Backtracking Core Patterns — Interview Notes

> High-yield pattern for FAANG/Product interviews (LeetCode 78, 46, 39, N-Queens, Sudoku).

---

## 1. What is Backtracking?

Backtracking is a systematic brute-force technique using **Depth-First Search (DFS)** that explores all candidate paths and **reverts ("undos") decisions** when a path fails or reaches a solution.

### Analogy: Maze Exploration
- Walk forward through the maze.
- Reach a dead end or exit $\rightarrow$ step backward to the previous junction.
- Try the next unexplored branch.

---

## 2. The Universal 3-Step Backtracking Blueprint

Every backtracking algorithm follows this core structure:

```java
void backtrack(state, parameters) {
    if (baseCaseCondition) {
        result.add(new ArrayList<>(current)); // Deep copy snapshot
        return;
    }

    for (choice : availableChoices) {
        if (!isValid(choice)) continue;

        // 1. CHOOSE
        current.add(choice);

        // 2. EXPLORE
        backtrack(nextState);

        // 3. UNCHOOSE (BACKTRACK)
        current.remove(current.size() - 1);
    }
}
```

### ⚠️ The Java Deep Copy Pitfall
- In Java, objects like `ArrayList` are passed by **reference value**.
- Writing `result.add(current)` stores the reference to the active list.
- When the recursion unwinds and removes elements, all references inside `result` point to the modified (eventually empty) list.
- **Rule:** Always capture an immutable snapshot: `result.add(new ArrayList<>(current));`

---

## 3. Comparison of Core Patterns

| Pattern | Problem | Order Matters? | Loop Start | Recursive Call | Pruning / Early Stop |
| :--- | :--- | :---: | :---: | :---: | :--- |
| **Subsets** | LeetCode 78 | No | `int i = start` | `backtrack(i + 1)` | Every node is a subset |
| **Permutations** | LeetCode 46 | **Yes** | `int i = 0` | `backtrack(used)` | `if (used[i]) continue;` |
| **Combination Sum** | LeetCode 39 | No | `int i = start` | `backtrack(i)` | `if (candidate > remain) break;` |

---

## 4. Pruning (Branch Cutting)

**Pruning** is the technique of cutting off exploration paths early when they are guaranteed not to lead to a valid solution.

In **Combination Sum**:
```java
Arrays.sort(candidates);
for (int i = start; i < candidates.length; i++) {
    if (candidates[i] > remain) break; // Cuts off all larger elements!
    ...
}
```
If `candidates = [2, 3, 6, 7]` and `remain = 4`:
When `i` hits `6`, because the array is sorted, we know `7` will also exceed `remain`.
Using `break` cuts off entire subtrees, dramatically reducing execution time from exponential worst-case to sub-millisecond speeds.

---

## 5. Complexity Summary

| Problem | Time Complexity | Auxiliary Space (Call Stack) | Space for Output |
| :--- | :--- | :--- | :--- |
| **Subsets** | $O(N \cdot 2^N)$ | $O(N)$ | $O(N \cdot 2^N)$ |
| **Permutations** | $O(N \cdot N!)$ | $O(N)$ | $O(N \cdot N!)$ |
| **Combination Sum** | $O(2^T)$ where $T = \text{target} / \min(\text{candidates})$ | $O(T)$ | Variable |
