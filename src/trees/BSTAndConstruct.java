package trees;

import java.util.*;

/**
 * ============================================================
 *  DAY 35 — LCA of BST (LC 235) + Construct BT (LC 105)
 * ============================================================
 *
 *  Problems:
 *   1. LC 235 — Lowest Common Ancestor of a BST
 *   2. LC 105 — Construct Binary Tree from Preorder + Inorder
 *
 *  Time  : O(N) for both
 *  Space : O(H) recursion stack + O(N) HashMap for LC 105
 * ============================================================
 */
public class BSTAndConstruct {

    // --------------------------------------------------------
    //  PROBLEM 1: LC 235 — LCA of a Binary Search Tree
    //
    //  Unlike LC 236 (generic tree), BST property lets us NAVIGATE
    //  directly — no need for post-order DFS on both subtrees.
    //
    //  3 Cases at each node:
    //   1. Both p,q < node.val → LCA in LEFT subtree
    //   2. Both p,q > node.val → LCA in RIGHT subtree
    //   3. They split (or one equals node) → THIS node IS the LCA
    //
    //  Example:
    //        6
    //       / \
    //      2   8
    //     / \
    //    0   4
    //       / \
    //      3   5
    //
    //  LCA(0,5): at 6→left, at 2→split → LCA=2
    //  LCA(2,8): at 6→split → LCA=6
    // --------------------------------------------------------
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        // Both smaller → LCA must be in left subtree
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestorBST(root.left, p, q);
        }
        // Both larger → LCA must be in right subtree
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestorBST(root.right, p, q);
        }
        // They split here OR one of them IS the current node → LCA found!
        return root;
    }

    // --------------------------------------------------------
    //  PROBLEM 2: LC 105 — Construct Binary Tree from Preorder + Inorder
    //
    //  KEY FACTS:
    //   Preorder → [ROOT, left subtree..., right subtree...]
    //              First element is ALWAYS the current root
    //
    //   Inorder  → [left subtree..., ROOT, right subtree...]
    //              Root splits left and right subtree nodes
    //
    //  ALGORITHM:
    //   1. Root = preorder[preStart]
    //   2. Find root in inorder → splits left & right
    //   3. leftSize = inRootIdx - inStart
    //   4. Recurse on left  (preorder: [preStart+1 .. preStart+leftSize])
    //   5. Recurse on right (preorder: [preStart+leftSize+1 .. preEnd])
    //
    //  Use HashMap for O(1) inorder index lookup
    //  (instead of O(N) scan every call → avoids O(N²) total)
    //
    //  Example:
    //   preorder = [3, 9, 20, 15, 7]
    //   inorder  = [9, 3, 15, 20, 7]
    //
    //   Root = 3, inRootIdx = 1, leftSize = 1
    //   Left subtree:  preorder[1..1]=[9],     inorder[0..0]=[9]
    //   Right subtree: preorder[2..4]=[20,15,7], inorder[2..4]=[15,20,7]
    //
    //   Result:
    //        3
    //       / \
    //      9  20
    //        /  \
    //       15   7
    // --------------------------------------------------------
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // Build HashMap: value → inorder index (O(N) one-time setup)
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        return build(preorder, 0, preorder.length - 1,
                     0, inorder.length - 1, inorderMap);
    }

    private TreeNode build(int[] preorder,
                           int preStart, int preEnd,
                           int inStart,  int inEnd,
                           Map<Integer, Integer> inorderMap) {
        // Base case: no nodes to build
        if (preStart > preEnd) return null;

        // Root = first element of current preorder range
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        // Find root's position in inorder
        int inRootIdx = inorderMap.get(rootVal);
        int leftSize  = inRootIdx - inStart; // nodes in left subtree

        // Build left subtree
        root.left = build(preorder,
                          preStart + 1, preStart + leftSize, // left preorder slice
                          inStart,      inRootIdx - 1,       // left inorder slice
                          inorderMap);

        // Build right subtree
        root.right = build(preorder,
                           preStart + leftSize + 1, preEnd,  // right preorder slice
                           inRootIdx + 1,           inEnd,   // right inorder slice
                           inorderMap);

        return root;
    }

    // --------------------------------------------------------
    //  MAIN — Manual tests
    // --------------------------------------------------------
    public static void main(String[] args) {
        BSTAndConstruct solution = new BSTAndConstruct();

        // Test LC 235: LCA of BST
        //       6
        //      / \
        //     2   8
        //    / \
        //   0   4
        //      / \
        //     3   5
        TreeNode root = new TreeNode(6);
        root.left           = new TreeNode(2);
        root.right          = new TreeNode(8);
        root.left.left      = new TreeNode(0);
        root.left.right     = new TreeNode(4);
        root.left.right.left  = new TreeNode(3);
        root.left.right.right = new TreeNode(5);

        System.out.println("=== LC 235: LCA of BST ===");
        TreeNode lca1 = solution.lowestCommonAncestorBST(root, root.left, root.left.right);
        System.out.println("LCA(2,4) expect 2: " + lca1.val);

        TreeNode lca2 = solution.lowestCommonAncestorBST(root, root.left, root.right);
        System.out.println("LCA(2,8) expect 6: " + lca2.val);

        // Test LC 105: Construct BT
        System.out.println("\n=== LC 105: Construct Binary Tree ===");
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder  = {9, 3, 15, 20, 7};
        TreeNode built = solution.buildTree(preorder, inorder);
        System.out.println("Root (expect 3): " + built.val);
        System.out.println("Root.left (expect 9): " + built.left.val);
        System.out.println("Root.right (expect 20): " + built.right.val);
        System.out.println("Root.right.left (expect 15): " + built.right.left.val);
        System.out.println("Root.right.right (expect 7): " + built.right.right.val);
    }
}
