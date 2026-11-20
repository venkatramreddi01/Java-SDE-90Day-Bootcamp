package trees;

import java.util.*;

/**
 * ============================================================
 *  DAY 31 — BFS Extensions: Right Side View & Average of Levels
 * ============================================================
 *
 *  KEY INSIGHT: Both problems use the EXACT same BFS template
 *  from Day 30. Only the logic inside the inner loop changes.
 *
 *  BFS Skeleton (memorize once, apply everywhere):
 *  ------------------------------------------------
 *  Queue<TreeNode> queue = new LinkedList<>();
 *  queue.offer(root);
 *  while (!queue.isEmpty()) {
 *      int levelSize = queue.size();           // snapshot!
 *      for (int i = 0; i < levelSize; i++) {
 *          TreeNode node = queue.poll();
 *          // ← problem-specific logic here
 *          if (node.left  != null) queue.offer(node.left);
 *          if (node.right != null) queue.offer(node.right);
 *      }
 *  }
 *
 *  Time  : O(N) — every node visited exactly once
 *  Space : O(N) — queue holds at most one full level (N/2 nodes)
 * ============================================================
 */
public class BinaryTreeBFSExtensions {

    // --------------------------------------------------------
    //  PROBLEM 1: LC 199 - Binary Tree Right Side View
    //
    //  Imagine standing to the RIGHT of the tree and looking left.
    //  You see only the LAST (rightmost) node at each level.
    //
    //  Key: When i == levelSize - 1 → that's the rightmost node
    //
    //  Example:
    //       1          → see 1
    //      / \
    //     2   3        → see 3
    //    /     \
    //   4       6      → see 6
    //
    //  Output: [1, 3, 6]
    // --------------------------------------------------------
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // snapshot of current level size

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                // Only capture the LAST node of each level
                if (i == levelSize - 1) {
                    result.add(node.val);
                }

                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }

        return result; // one value per level = right side view
    }

    // --------------------------------------------------------
    //  PROBLEM 2: LC 637 - Average of Levels in Binary Tree
    //
    //  For each level, compute the average of all node values.
    //
    //  Key: Track running `sum` during the inner loop,
    //       then divide by `levelSize` after the inner loop.
    //       Use `double` to avoid integer division truncation!
    //
    //  Example:
    //       3           → avg = 3.0
    //      / \
    //     9  20         → avg = (9+20)/2 = 14.5
    //       /  \
    //      15   7       → avg = (15+7)/2 = 11.0
    //
    //  Output: [3.0, 14.5, 11.0]
    // --------------------------------------------------------
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // snapshot
            double sum = 0;              // reset sum for each level

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                sum += node.val;         // accumulate values

                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(sum / levelSize); // average = sum / count
        }

        return result;
    }

    // --------------------------------------------------------
    //  MAIN — Manual tests
    //
    //  Tree 1:          Tree 2:
    //       1                3
    //      / \              / \
    //     2   3            9  20
    //    /     \              /  \
    //   4       6            15   7
    // --------------------------------------------------------
    public static void main(String[] args) {
        BinaryTreeBFSExtensions solution = new BinaryTreeBFSExtensions();

        // Tree 1 for Right Side View
        TreeNode root1 = new TreeNode(1);
        root1.left          = new TreeNode(2);
        root1.right         = new TreeNode(3);
        root1.left.left     = new TreeNode(4);
        root1.right.right   = new TreeNode(6);

        System.out.println("=== LC 199: Right Side View ===");
        System.out.println(solution.rightSideView(root1));
        // Expected: [1, 3, 6]

        // Tree 2 for Average of Levels
        TreeNode root2 = new TreeNode(3);
        root2.left          = new TreeNode(9);
        root2.right         = new TreeNode(20);
        root2.right.left    = new TreeNode(15);
        root2.right.right   = new TreeNode(7);

        System.out.println("\n=== LC 637: Average of Levels ===");
        System.out.println(solution.averageOfLevels(root2));
        // Expected: [3.0, 14.5, 11.0]
    }
}
