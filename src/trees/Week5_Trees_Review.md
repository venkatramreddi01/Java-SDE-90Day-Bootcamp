# 🌳 Week 5 Master Review & Interview Cheatsheet: Trees & BSTs

> **Week 5 Milestone Complete** | 100% Score on Mock Assessment (5/5)
> **Key Patterns Mastered**: DFS (Pre/In/Post), BFS (Level Order, Zigzag, Views), Tree Properties, BST Navigation & Invariants, Tree Backtracking, and Binary Tree Reconstruction.

---

## 📌 Pattern Summary & Decision Matrix

| Problem Type / Need | Optimal Approach | Core Data Structure / Technique |
| :--- | :--- | :--- |
| **Level-by-level processing / Shortest path in unweighted tree** | **BFS** | `Queue<TreeNode>` with `levelSize = queue.size()` snapshot |
| **Max Depth, Subtree Height, Bottom-Up Checks** | **DFS (Post-Order)** | Left subtree $\rightarrow$ Right subtree $\rightarrow$ Compute height `Math.max(L, R) + 1` |
| **Path between any two nodes / Tree Diameter** | **DFS (Post-Order)** | Global max: `max = Math.max(max, leftHeight + rightHeight)` |
| **Early termination on violation (Balanced Tree)** | **DFS Sentinel** | Return `-1` as sentinel when `Math.abs(L - R) > 1` |
| **Symmetry / Mirror checking** | **Dual-DFS Helper** | Compare outer pair (`left.left, right.right`) & inner pair (`left.right, right.left`) |
| **Generic Tree LCA (LC 236)** | **DFS (Post-Order)** | If both subtrees return non-null $\rightarrow$ current node is LCA |
| **BST LCA (LC 235)** | **Top-Down Navigation** | If both $< \text{root}$, go left; if both $> \text{root}$, go right; else split node is LCA |
| **BST Search / Insert** | **BST Pruning** | Compare with `node.val` to eliminate half the tree ($O(\log N)$ average) |
| **Validate BST (LC 98)** | **Range Propagation** | Validate within `(min, max)` bounds using `Long.MIN_VALUE` and `Long.MAX_VALUE` |
| **Root-to-Leaf All Paths (LC 113)** | **Tree Backtracking** | Choose (`add`) $\rightarrow$ Explore (`dfs`) $\rightarrow$ Unchoose (`remove(size - 1)`) + Snapshot at leaf |
| **Construct Tree from Traversals (LC 105)** | **Divide & Conquer** | `preorder[0]` is root, locate in `inorderMap`, split left/right with `leftSize` |

---

## 🧠 Critical Traps & Interview Secrets

### 1. BFS Snapshot Rule
```java
int levelSize = queue.size(); // MUST BE CAPTURED BEFORE INNER LOOP
for (int i = 0; i < levelSize; i++) {
    TreeNode node = queue.poll();
    if (i == levelSize - 1) rightView.add(node.val); // Rightmost visible node
    if (node.left != null) queue.offer(node.left);
    if (node.right != null) queue.offer(node.right);
}
```
*Why:* The queue dynamically grows as children are offered. Without `levelSize`, level boundaries are lost.

### 2. The BST Local Check Trap
Checking `node.left.val < node.val && node.right.val > node.val` only validates direct parent-child relationships. It completely misses violations of distant ancestors.
*Solution:* Pass down explicit boundaries `(long min, long max)`:
- Going left: upper bound becomes `node.val`.
- Going right: lower bound becomes `node.val`.

### 3. Tree Backtracking Snapshot Contract
- In Java, lists are passed by reference.
- Adding `result.add(current)` stores the reference, which turns into empty lists `[]` when recursion unwinds.
- Always snapshot valid paths: `result.add(new ArrayList<>(current))`.

---

## 📊 Complexity Quick Reference

| Operation / Problem | Time Complexity | Auxiliary Space (Balanced) | Auxiliary Space (Skewed) |
| :--- | :--- | :--- | :--- |
| **BFS Level Order** | $O(N)$ | $O(N)$ (widest level $\approx N/2$) | $O(1)$ |
| **DFS Traversals** | $O(N)$ | $O(\log N)$ (recursion call stack) | $O(N)$ |
| **BST Search / Insert / Validate** | $O(H)$ | $O(\log N)$ | $O(N)$ |
| **Construct BT (LC 105)** | $O(N)$ (with HashMap) | $O(N)$ (map) + $O(H)$ (stack) | $O(N)$ |

---
*Created as part of the 90-Day SDE Master Preparation Bootcamp.*
