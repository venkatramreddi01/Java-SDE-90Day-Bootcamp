package trees;

/**
 * ============================================================
 *  DAY 32 — DFS Post-Order: Diameter & Balanced Binary Tree
 * ============================================================
 *
 *  PATTERN: Post-Order DFS (Left → Right → Process Node)
 *  WHY POST-ORDER: Children report their heights FIRST,
 *                  then parent uses those heights to decide.
 *
 *  Problems:
 *   1. LC 543 — Diameter of Binary Tree
 *   2. LC 110 — Balanced Binary Tree
 *
 *  Time  : O(N) — every node visited exactly once
 *  Space : O(H) — recursion stack depth = tree height H
 *                 Best (balanced): O(log N)
 *                 Worst (skewed) : O(N)
 * ============================================================
 */
public class BinaryTreeDFSAdvanced {

    // --------------------------------------------------------
    //  PROBLEM 1: LC 543 - Diameter of Binary Tree
    //
    //  Diameter = longest path between ANY two nodes (in edges)
    //  At each node: path through it = leftHeight + rightHeight
    //  We track the GLOBAL MAX across all nodes.
    //
    //  Example:
    //       1
    //      / \
    //     2   3
    //    / \
    //   4   5
    //
    //  At node 2: left=1, right=1 → diameter candidate = 2
    //  At node 1: left=2, right=1 → diameter candidate = 3 ← MAX
    //  Answer: 3
    // --------------------------------------------------------

    private int maxDiameter = 0; // global max — updated at every node

    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0;          // reset for each call
        heightForDiameter(root);  // kick off post-order DFS
        return maxDiameter;
    }

    // Returns the HEIGHT of subtree rooted at `node`
    // Side effect: updates maxDiameter whenever a longer path is found
    private int heightForDiameter(TreeNode node) {
        // Base case: null node contributes 0 height
        if (node == null) return 0;

        // POST-ORDER: get children's heights first
        int leftHeight  = heightForDiameter(node.left);
        int rightHeight = heightForDiameter(node.right);

        // Diameter candidate passing through THIS node
        maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);

        // Return MY height to my parent
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // --------------------------------------------------------
    //  PROBLEM 2: LC 110 - Balanced Binary Tree
    //
    //  Balanced = at EVERY node, |leftHeight - rightHeight| <= 1
    //
    //  TRICK: Return -1 as a sentinel for "unbalanced detected"
    //  → If any child returns -1, immediately return -1 upward
    //  → This short-circuits — no wasted computation!
    //
    //  Example 1 (Balanced):       Example 2 (Unbalanced):
    //       3                           1
    //      / \                         / \
    //     9  20                       2   2
    //       /  \                     / \
    //      15   7                   3   3
    //                              / \
    //                             4   4
    // --------------------------------------------------------

    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;  // -1 means unbalanced
    }

    // Returns height if subtree is balanced, -1 if unbalanced
    private int checkHeight(TreeNode node) {
        // Base case: null node is balanced, height = 0
        if (node == null) return 0;

        // POST-ORDER: check left subtree first
        int leftHeight = checkHeight(node.left);
        if (leftHeight == -1) return -1; // propagate imbalance immediately!

        // Check right subtree
        int rightHeight = checkHeight(node.right);
        if (rightHeight == -1) return -1; // propagate imbalance immediately!

        // Check balance at THIS node
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1; // THIS node is unbalanced → signal upward
        }

        // Balanced at this node → return height to parent
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // --------------------------------------------------------
    //  MAIN — Manual tests
    // --------------------------------------------------------
    public static void main(String[] args) {
        BinaryTreeDFSAdvanced solution = new BinaryTreeDFSAdvanced();

        // Tree for LC 543:
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        TreeNode root1 = new TreeNode(1);
        root1.left         = new TreeNode(2);
        root1.right        = new TreeNode(3);
        root1.left.left    = new TreeNode(4);
        root1.left.right   = new TreeNode(5);

        System.out.println("=== LC 543: Diameter of Binary Tree ===");
        System.out.println("Diameter: " + solution.diameterOfBinaryTree(root1));
        // Expected: 3

        // Tree for LC 110 (Balanced):
        //       3
        //      / \
        //     9  20
        //       /  \
        //      15   7
        TreeNode root2 = new TreeNode(3);
        root2.left         = new TreeNode(9);
        root2.right        = new TreeNode(20);
        root2.right.left   = new TreeNode(15);
        root2.right.right  = new TreeNode(7);

        System.out.println("\n=== LC 110: Balanced Binary Tree ===");
        System.out.println("Is Balanced (expect true): " + solution.isBalanced(root2));

        // Tree for LC 110 (Unbalanced):
        //       1
        //      / \
        //     2   2
        //    / \
        //   3   3
        //  / \
        // 4   4
        TreeNode root3 = new TreeNode(1);
        root3.left              = new TreeNode(2);
        root3.right             = new TreeNode(2);
        root3.left.left         = new TreeNode(3);
        root3.left.right        = new TreeNode(3);
        root3.left.left.left    = new TreeNode(4);
        root3.left.left.right   = new TreeNode(4);

        System.out.println("Is Balanced (expect false): " + solution.isBalanced(root3));
    }
}
