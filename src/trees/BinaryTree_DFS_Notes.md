# 🌲 Binary Tree Foundations & DFS Traversals — Interview Notes

> High-yield topic for Google, Microsoft, Amazon, and Meta interviews.

---

## 1. What is a Binary Tree?

A **Binary Tree** is a hierarchical non-linear data structure where each node has at most two children (`left` and `right`).

### Node Definition in Java
```java
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) { this.val = val; }
}
```

### Core Terminology
- **Root:** The topmost node with no parent.
- **Leaf Node:** A node with both `left == null` and `right == null`.
- **Subtree:** A tree consisting of a node and all of its descendants.
- **Height / Max Depth:** The maximum number of nodes from the root down to the farthest leaf node.

---

## 2. The 3 Classic DFS Traversals

The name of the traversal dictates **when the ROOT is processed**:

| Traversal | Sequence | Nature / Work Timing | Use Case |
| :--- | :--- | :--- | :--- |
| **Pre-Order** | **Root** $\rightarrow$ Left $\rightarrow$ Right | Winding / Top-Down | Tree cloning, serialization, prefix expressions. |
| **In-Order** | Left $\rightarrow$ **Root** $\rightarrow$ Right | In-between / Symmetry | **Sorted order in Binary Search Trees (BST)!** |
| **Post-Order** | Left $\rightarrow$ Right $\rightarrow$ **Root** | Unwinding / Bottom-Up | Deleting trees, calculating height, size, diameter. |

---

## 3. Visualizing Traversal on Sample Tree

```
         [ 1 ]
        /     \
     [ 2 ]   [ 3 ]
     /   \
  [ 4 ] [ 5 ]
```

- **Pre-Order:** `1, 2, 4, 5, 3`
- **In-Order:** `4, 2, 5, 1, 3`
- **Post-Order:** `4, 5, 2, 3, 1`

---

## 4. Maximum Depth of Binary Tree (LeetCode 104)

### Recurrence Relation (Divide & Conquer / Post-Order)
$$\text{depth}(\text{root}) = 1 + \max(\text{depth}(\text{root.left}), \text{depth}(\text{root.right}))$$

### Base Case
$$\text{if } \text{root} == \text{null} \implies 0$$

### Complexity Analysis
- **Time Complexity:** $O(N)$ because every node in the tree is visited exactly once.
- **Space Complexity:** $O(H)$ auxiliary space on the JVM call stack:
  - **Balanced Tree:** $H = O(\log N)$
  - **Skewed Tree (Worst-case linked list):** $H = O(N)$
