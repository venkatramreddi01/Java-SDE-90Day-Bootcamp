# Day 30 — BFS & Level Order Traversal (Binary Trees)
## Interview Notes

---

## 🔑 Core Pattern (Memorize This Template)

```java
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);

while (!queue.isEmpty()) {
    int levelSize = queue.size();          // ← snapshot of current level size
    List<Integer> level = new ArrayList<>();

    for (int i = 0; i < levelSize; i++) {
        TreeNode node = queue.poll();
        level.add(node.val);
        if (node.left  != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }
    result.add(level);
}
```

**Why `queue.size()` before the inner loop?**
Because by the time the inner loop runs, the queue is growing (children being added).
Capturing `size` FIRST freezes the count of nodes at the current level only.

---

## 📊 Complexity

| Metric | Value | Reason |
|--------|-------|--------|
| Time   | O(N)  | Every node is enqueued and dequeued exactly once |
| Space  | O(N)  | Queue can hold up to N/2 nodes (last/widest level of perfect tree) |

---

## ⚡ LC 102 vs LC 103 — Only One Difference

| | LC 102 Level Order | LC 103 Zigzag |
|---|---|---|
| Data structure for level | `ArrayList` | `LinkedList` |
| Insert direction | always `addLast()` | alternate `addFirst()` / `addLast()` |
| Extra flag needed? | ❌ | ✅ `boolean leftToRight` |

---

## 🔄 BFS vs DFS — When to Use Which

| Scenario | Use |
|----------|-----|
| Level-by-level output needed | **BFS** |
| Find shortest path in unweighted graph/tree | **BFS** |
| Max/Min Depth, Path Sum | **DFS** |
| Serialize/Deserialize a tree | **BFS** (LeetCode style) |
| Diameter, Lowest Common Ancestor | **DFS** |

---

## 🎯 Common Mistakes in BFS Tree Problems

1. **Forgetting the `levelSize` snapshot** — leads to processing nodes from the NEXT level in current iteration.
2. **Adding null children** — always check `node.left != null` before `queue.offer()`.
3. **Using Stack instead of Queue** — Stack gives DFS order, not BFS order.
4. **Zigzag: forgetting to flip the flag** — `leftToRight = !leftToRight` must come AFTER adding to result.

---

## 🏢 Real Interview Usage

| Company | Problem Type | Pattern |
|---------|-------------|---------|
| Microsoft | Level Order → find right-most node per level | BFS + track last in each level |
| Amazon | Connect nodes at same level (LC 116) | BFS |
| Google | Minimum depth of binary tree | BFS (finds level of first leaf) |
| Meta | Vertical Order Traversal | BFS + HashMap |
