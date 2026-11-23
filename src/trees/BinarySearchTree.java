package trees;

/**
 * ============================================================
 *  DAY 34 — Binary Search Trees (BST): Search, Insert, Validate
 * ============================================================
 *
 *  BST PROPERTY: For every node:
 *    ALL nodes in left subtree  < node.val
 *    ALL nodes in right subtree > node.val
 *
 *  This one rule gives O(log N) search on a balanced BST.
 *
 *  Problems:
 *   1. LC 700 — Search in BST
 *   2. LC 701 — Insert into BST
 *   3. LC 98  — Validate BST (min/max bounds approach)
 *
 *  Time  : O(H) — H = tree height
 *                 Balanced: O(log N) | Skewed: O(N)
 *  Space : O(H) — recursion stack depth
 * ============================================================
 */
public class BinarySearchTree {

    // --------------------------------------------------------
    //  PROBLEM 1: LC 700 — Search in BST
    //
    //  At each node: go left if val < node.val, right if val > node.val
    //  Return the subtree rooted at the found node (or null)
    //
    //  Example:
    //       4
    //      / \
    //     2   7      Search(2) → return node(2) and its subtree
    //    / \
    //   1   3
    // --------------------------------------------------------
    public TreeNode searchBST(TreeNode root, int val) {
        // Base case: not found OR exact match
        if (root == null) return null;
        if (root.val == val) return root;

        // BST property: eliminate half the tree each step → O(log N)
        if (val < root.val) {
            return searchBST(root.left, val);   // must be in left subtree
        } else {
            return searchBST(root.right, val);  // must be in right subtree
        }
    }

    // --------------------------------------------------------
    //  PROBLEM 2: LC 701 — Insert into BST
    //
    //  Navigate using BST property until we hit null.
    //  That null position is exactly where the new node belongs.
    //  Return new node there → recursion rewires parent's pointer.
    //
    //  New node is ALWAYS inserted as a leaf (no restructuring needed)
    //
    //  Example: Insert 5 into BST
    //       4                4
    //      / \    →         / \
    //     2   7            2   7
    //                         /
    //                        5   ← new leaf
    // --------------------------------------------------------
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // Base case: found the insertion point → create new leaf!
        if (root == null) return new TreeNode(val);

        // Navigate using BST property
        if (val < root.val) {
            root.left  = insertIntoBST(root.left,  val); // rewire left
        } else {
            root.right = insertIntoBST(root.right, val); // rewire right
        }

        return root; // return current node back to parent
    }

    // --------------------------------------------------------
    //  PROBLEM 3: LC 98 — Validate BST
    //
    //  COMMON MISTAKE: Only checking direct parent-child relationship.
    //  This misses cases where a node violates a distant ancestor's bound.
    //
    //  CORRECT APPROACH: Pass min and max bounds downward.
    //   - Every node must be STRICTLY within (min, max)
    //   - Going LEFT  → upper bound tightens to node.val
    //   - Going RIGHT → lower bound tightens to node.val
    //
    //  Use Long (not int) to handle edge cases where node.val
    //  equals Integer.MIN_VALUE or Integer.MAX_VALUE
    //
    //  Example of the trap:
    //       5
    //      / \
    //     1   7
    //        / \
    //       3   8   ← 3 is in right subtree of 5, so 3 must be > 5. INVALID!
    // --------------------------------------------------------
    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean validate(TreeNode node, long min, long max) {
        // Base case: null node is valid
        if (node == null) return true;

        // Current node must be STRICTLY inside (min, max)
        if (node.val <= min || node.val >= max) return false;

        // Recurse with tightened bounds
        return validate(node.left,  min,       node.val)  // left: must be < node.val
            && validate(node.right, node.val,  max);      // right: must be > node.val
    }

    // --------------------------------------------------------
    //  MAIN — Manual tests
    // --------------------------------------------------------
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        // Build BST:
        //       4
        //      / \
        //     2   7
        //    / \
        //   1   3
        TreeNode root = new TreeNode(4);
        root.left        = new TreeNode(2);
        root.right       = new TreeNode(7);
        root.left.left   = new TreeNode(1);
        root.left.right  = new TreeNode(3);

        // Test LC 700: Search
        System.out.println("=== LC 700: Search in BST ===");
        TreeNode found = bst.searchBST(root, 2);
        System.out.println("Search(2) root (expect 2): " + (found != null ? found.val : "null"));

        // Test LC 701: Insert
        System.out.println("\n=== LC 701: Insert into BST ===");
        TreeNode updated = bst.insertIntoBST(root, 5);
        System.out.println("After inserting 5, root.right.left (expect 5): "
                + updated.right.left.val);

        // Test LC 98: Validate
        System.out.println("\n=== LC 98: Validate BST ===");
        // Valid BST
        TreeNode valid = new TreeNode(5);
        valid.left  = new TreeNode(1);
        valid.right = new TreeNode(7);
        System.out.println("Valid BST (expect true): " + bst.isValidBST(valid));

        // Invalid BST (the classic trap)
        //     5
        //    / \
        //   1   7
        //      / \
        //     3   8   ← 3 < 5 but in right subtree!
        TreeNode invalid = new TreeNode(5);
        invalid.left        = new TreeNode(1);
        invalid.right       = new TreeNode(7);
        invalid.right.left  = new TreeNode(3);
        invalid.right.right = new TreeNode(8);
        System.out.println("Invalid BST (expect false): " + bst.isValidBST(invalid));
    }
}
