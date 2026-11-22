package trees;

import java.util.*;

/**
 * ============================================================
 *  DAY 33 — Tree Problems: Invert, Symmetric, LCA, Path Sum II
 * ============================================================
 *
 *  Problems:
 *   1. LC 226 — Invert Binary Tree          (DFS Post-Order)
 *   2. LC 101 — Symmetric Tree              (DFS Mirror Check)
 *   3. LC 236 — Lowest Common Ancestor      (DFS Post-Order)
 *   4. LC 113 — Path Sum II                 (DFS + Backtracking)
 *
 *  Time  : O(N) for all — every node visited exactly once
 *  Space : O(H) — recursion stack height
 *                 Balanced tree: O(log N) | Skewed: O(N)
 * ============================================================
 */
public class BinaryTreeProblems {

    // --------------------------------------------------------
    //  PROBLEM 1: LC 226 — Invert Binary Tree
    //
    //  At every node: swap left and right children.
    //  Then recursively invert both subtrees.
    //
    //  Base case: node == null → nothing to invert, return null
    //
    //  Example:
    //      4              4
    //     / \    →       / \
    //    2   7          7   2
    //   / \ / \        / \ / \
    //  1  3 6  9      9  6 3  1
    // --------------------------------------------------------
    public TreeNode invertTree(TreeNode root) {
        // Base case: empty node — nothing to do
        if (root == null) return null;

        // POST-ORDER: invert children first
        TreeNode left  = invertTree(root.left);
        TreeNode right = invertTree(root.right);

        // Then swap at this node
        root.left  = right;
        root.right = left;

        return root;
    }

    // --------------------------------------------------------
    //  PROBLEM 2: LC 101 — Symmetric Tree
    //
    //  A tree is symmetric if it's a mirror of itself.
    //  Use a helper that checks TWO nodes at mirror positions:
    //    - left.left  mirrors with right.right
    //    - left.right mirrors with right.left
    //
    //  Base cases:
    //    - Both null       → true  (empty mirrors empty)
    //    - Only one null   → false (mismatch in structure)
    //    - Values differ   → false (mismatch in value)
    // --------------------------------------------------------
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    // Checks if two subtrees are mirrors of each other
    private boolean isMirror(TreeNode left, TreeNode right) {
        // Both null → symmetric (two empty subtrees match)
        if (left == null && right == null) return true;

        // Only one is null → not symmetric (structural mismatch)
        if (left == null || right == null) return false;

        // Values must match AND children must be cross-mirrors
        return (left.val == right.val)
            && isMirror(left.left,  right.right)   // outer pair
            && isMirror(left.right, right.left);   // inner pair
    }

    // --------------------------------------------------------
    //  PROBLEM 3: LC 236 — Lowest Common Ancestor (LCA)
    //
    //  Find the deepest node that has BOTH p and q as descendants.
    //  (A node is a descendant of itself)
    //
    //  Post-Order Logic:
    //   - If current node == p or == q → return current node
    //   - If BOTH left and right return non-null → current IS the LCA
    //   - If only one side returns non-null → pass it upward
    //   - If both return null → return null (p and q not in subtree)
    //
    //  Example:
    //        3
    //       / \
    //      5   1
    //     / \ / \
    //    6  2 0  8
    //      / \
    //     7   4
    //
    //  LCA(5, 1) = 3   ← found in different subtrees of 3
    //  LCA(5, 4) = 5   ← 5 is returned immediately, 4 is below it
    // --------------------------------------------------------
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base case: null node or found p or q
        if (root == null) return null;
        if (root == p || root == q) return root; // found one of them!

        // POST-ORDER: ask both subtrees
        TreeNode left  = lowestCommonAncestor(root.left,  p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // Both sides found something → THIS node is the LCA!
        if (left != null && right != null) return root;

        // Only one side found something → pass it upward
        return left != null ? left : right;
    }

    // --------------------------------------------------------
    //  PROBLEM 4: LC 113 — Path Sum II
    //
    //  Find ALL root-to-leaf paths where node values sum to target.
    //
    //  Pattern: Backtracking on a Tree (3-Step Blueprint)
    //   1. CHOOSE   → add node to path, subtract val from target
    //   2. EXPLORE  → recurse left and right
    //   3. UNCHOOSE → remove node from path (backtrack!)
    //
    //  Collect path only at a LEAF node when remainingTarget == 0
    //
    //  Example: target = 22
    //       5
    //      / \
    //     4   8
    //    /   / \
    //   11  13   4
    //  /  \       \
    // 7    2       1
    //
    //  Result: [[5,4,11,2], [5,8,4,1]]
    //  5+4+11+2=22 ✅  |  5+8+4+1=22 ✅
    // --------------------------------------------------------
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        dfsPathSum(root, targetSum, current, result);
        return result;
    }

    private void dfsPathSum(TreeNode node, int remaining,
                            List<Integer> current,
                            List<List<Integer>> result) {
        // Base case: null node — nothing to do
        if (node == null) return;

        // CHOOSE: add this node to current path
        current.add(node.val);
        remaining -= node.val;

        // Check: are we at a LEAF with remaining == 0?
        if (node.left == null && node.right == null && remaining == 0) {
            result.add(new ArrayList<>(current)); // snapshot! (same as backtracking Day 27)
        }

        // EXPLORE: go deeper into both subtrees
        dfsPathSum(node.left,  remaining, current, result);
        dfsPathSum(node.right, remaining, current, result);

        // UNCHOOSE: remove this node before returning to parent
        // Without this, previous nodes stay in list → wrong paths!
        current.remove(current.size() - 1);
    }

    // --------------------------------------------------------
    //  MAIN — Manual tests
    // --------------------------------------------------------
    public static void main(String[] args) {
        BinaryTreeProblems solution = new BinaryTreeProblems();

        // Test LC 226: Invert Binary Tree
        //       4
        //      / \
        //     2   7
        //    / \ / \
        //   1  3 6  9
        TreeNode root1 = new TreeNode(4);
        root1.left         = new TreeNode(2);
        root1.right        = new TreeNode(7);
        root1.left.left    = new TreeNode(1);
        root1.left.right   = new TreeNode(3);
        root1.right.left   = new TreeNode(6);
        root1.right.right  = new TreeNode(9);

        System.out.println("=== LC 226: Invert Binary Tree ===");
        TreeNode inverted = solution.invertTree(root1);
        System.out.println("Root.left (expect 7): " + inverted.left.val);
        System.out.println("Root.right (expect 2): " + inverted.right.val);

        // Test LC 101: Symmetric Tree
        //       1
        //      / \
        //     2   2
        //    / \ / \
        //   3  4 4  3
        TreeNode root2 = new TreeNode(1);
        root2.left         = new TreeNode(2);
        root2.right        = new TreeNode(2);
        root2.left.left    = new TreeNode(3);
        root2.left.right   = new TreeNode(4);
        root2.right.left   = new TreeNode(4);
        root2.right.right  = new TreeNode(3);

        System.out.println("\n=== LC 101: Symmetric Tree ===");
        System.out.println("Is Symmetric (expect true): " + solution.isSymmetric(root2));

        // Test LC 236: LCA
        //        3
        //       / \
        //      5   1
        //     / \ / \
        //    6  2 0  8
        TreeNode root3 = new TreeNode(3);
        root3.left         = new TreeNode(5);
        root3.right        = new TreeNode(1);
        root3.left.left    = new TreeNode(6);
        root3.left.right   = new TreeNode(2);
        root3.right.left   = new TreeNode(0);
        root3.right.right  = new TreeNode(8);

        System.out.println("\n=== LC 236: Lowest Common Ancestor ===");
        TreeNode lca = solution.lowestCommonAncestor(root3, root3.left, root3.right);
        System.out.println("LCA(5,1) (expect 3): " + lca.val);

        // Test LC 113: Path Sum II
        //       5
        //      / \
        //     4   8
        //    /   / \
        //   11  13   4
        //  /  \       \
        // 7    2       1
        TreeNode root4 = new TreeNode(5);
        root4.left              = new TreeNode(4);
        root4.right             = new TreeNode(8);
        root4.left.left         = new TreeNode(11);
        root4.right.left        = new TreeNode(13);
        root4.right.right       = new TreeNode(4);
        root4.left.left.left    = new TreeNode(7);
        root4.left.left.right   = new TreeNode(2);
        root4.right.right.right = new TreeNode(1);

        System.out.println("\n=== LC 113: Path Sum II (target=22) ===");
        List<List<Integer>> paths = solution.pathSum(root4, 22);
        for (List<Integer> path : paths) {
            System.out.println(path);
        }
        // Expected: [5,4,11,2] and [5,8,4,5] ← wait, 5+8+4+1=18≠22
        // Actually: [5,4,11,2]=22 and [5,8,4,5] doesn't exist
        // Only [5,4,11,2] = 22 ✅
    }
}
