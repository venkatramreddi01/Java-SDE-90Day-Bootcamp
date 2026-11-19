package trees;

import java.util.*;

/**
 * ============================================================
 *  DAY 30 — BFS / Level Order Traversal of Binary Trees
 * ============================================================
 *
 *  PATTERN: BFS using a Queue (FIFO)
 *  KEY INSIGHT: queue.size() at the START of each outer loop
 *               = exact count of nodes on that level
 *
 *  Problems:
 *   1. LC 102 — Binary Tree Level Order Traversal
 *   2. LC 103 — Zigzag Level Order Traversal
 *
 *  Time Complexity : O(N) — every node visited exactly once
 *  Space Complexity: O(N) — queue holds at most one full level
 *                           (widest level can have N/2 nodes)
 * ============================================================
 */
public class BinaryTreeBFS {

    // --------------------------------------------------------
    //  PROBLEM 1: LC 102 - Level Order Traversal
    //  Input : root of binary tree
    //  Output: List<List<Integer>> — values grouped by level
    // --------------------------------------------------------
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        // Edge case: empty tree
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);  // Step 1: seed the queue with root

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // ← number of nodes at THIS level
            List<Integer> currentLevel = new ArrayList<>();

            // Process exactly levelSize nodes (all nodes on current level)
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();         // remove from front
                currentLevel.add(node.val);           // record value

                // Add children for the NEXT level
                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(currentLevel); // done with this level
        }

        return result;
    }

    // --------------------------------------------------------
    //  PROBLEM 2: LC 103 - Zigzag Level Order Traversal
    //  Same BFS pattern — only difference: alternate direction
    //  Level 0 → left to right
    //  Level 1 → right to left
    //  Level 2 → left to right  ... and so on
    //
    //  Trick: Use a boolean flag `leftToRight`
    //         When false → use addFirst() instead of add()
    //         → LinkedList as a Deque gives us this for free
    // --------------------------------------------------------
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true; // start left → right

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            LinkedList<Integer> currentLevel = new LinkedList<>(); // ← LinkedList for addFirst()

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                // Direction decides where to insert the value
                if (leftToRight) {
                    currentLevel.addLast(node.val);  // normal order
                } else {
                    currentLevel.addFirst(node.val); // reversed order
                }

                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(currentLevel);
            leftToRight = !leftToRight; // flip direction after each level
        }

        return result;
    }

    // --------------------------------------------------------
    //  MAIN — Manual test with a sample tree
    //
    //       1
    //      / \
    //     2   3
    //    / \   \
    //   4   5   6
    //
    //  Level Order  : [[1], [2,3], [4,5,6]]
    //  Zigzag Order : [[1], [3,2], [4,5,6]]
    // --------------------------------------------------------
    public static void main(String[] args) {
        /*
         * Build the tree manually:
         *       1
         *      / \
         *     2   3
         *    / \   \
         *   4   5   6
         */
        TreeNode root = new TreeNode(1);
        root.left           = new TreeNode(2);
        root.right          = new TreeNode(3);
        root.left.left      = new TreeNode(4);
        root.left.right     = new TreeNode(5);
        root.right.right    = new TreeNode(6);

        BinaryTreeBFS solution = new BinaryTreeBFS();

        System.out.println("=== LC 102: Level Order Traversal ===");
        List<List<Integer>> levelResult = solution.levelOrder(root);
        for (int i = 0; i < levelResult.size(); i++) {
            System.out.println("Level " + i + ": " + levelResult.get(i));
        }

        System.out.println("\n=== LC 103: Zigzag Level Order ===");
        List<List<Integer>> zigzagResult = solution.zigzagLevelOrder(root);
        for (int i = 0; i < zigzagResult.size(); i++) {
            System.out.println("Level " + i + ": " + zigzagResult.get(i));
        }
    }
}
