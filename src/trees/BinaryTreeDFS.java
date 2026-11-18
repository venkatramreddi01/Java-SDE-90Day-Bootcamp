package trees;

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

/**
 * Day 29: Binary Tree Foundations & DFS Traversals
 * Covers:
 * 1. Pre-Order Traversal (Root -> Left -> Right)
 * 2. In-Order Traversal (Left -> Root -> Right)
 * 3. Post-Order Traversal (Left -> Right -> Root)
 * 4. Maximum Depth of Binary Tree (LeetCode 104)
 */
public class BinaryTreeDFS {

    // 1. Pre-Order: Root -> Left -> Right
    public static void preOrder(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    // 2. In-Order: Left -> Root -> Right
    public static void inOrder(TreeNode root) {
        if (root == null) return;
        inOrder(root.left);
        System.out.print(root.val + " ");
        inOrder(root.right);
    }

    // 3. Post-Order: Left -> Right -> Root (Bottom-up processing)
    public static void postOrder(TreeNode root) {
        if (root == null) return;
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.val + " ");
    }

    // 4. Maximum Depth of Binary Tree (LeetCode 104)
    // Time Complexity: O(N) where N is the number of nodes
    // Space Complexity: O(H) where H is tree height (O(log N) balanced, O(N) skewed)
    public static int maxDepth(TreeNode root) {
        if (root == null) return 0; // Base case: empty tree has depth 0

        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);

        // Current node's depth is 1 + maximum of children's depths
        return 1 + Math.max(leftDepth, rightDepth);
    }

    public static void main(String[] args) {
        /*
         * Constructing sample binary tree:
         *          1
         *        /   \
         *       2     3
         *      / \
         *     4   5
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        System.out.print("Pre-Order  (Root -> Left -> Right): ");
        preOrder(root); // Expected: 1 2 4 5 3
        System.out.println();

        System.out.print("In-Order   (Left -> Root -> Right): ");
        inOrder(root);  // Expected: 4 2 5 1 3
        System.out.println();

        System.out.print("Post-Order (Left -> Right -> Root): ");
        postOrder(root); // Expected: 4 5 2 3 1
        System.out.println();

        System.out.println("\nMaximum Depth of Tree (LC 104): " + maxDepth(root)); // Expected: 3
    }
}
