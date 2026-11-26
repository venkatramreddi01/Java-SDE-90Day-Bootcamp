package heaps;

import java.util.PriorityQueue;

/**
 * ============================================================
 *  DAY 37 — Heap & PriorityQueue Foundations
 * ============================================================
 *
 *  PROBLEM: LeetCode 215 — Kth Largest Element in an Array
 *
 *  CORE PATTERN:
 *   - To find K-th LARGEST  --> Use MIN-HEAP of size K.
 *   - To find K-th SMALLEST --> Use MAX-HEAP of size K.
 *
 *  TIME COMPLEXITY : O(N log K) — much faster than O(N log N) sorting.
 *  SPACE COMPLEXITY: O(K) — only K elements kept in memory.
 * ============================================================
 */
public class KthLargestElement {

    public int findKthLargest(int[] nums, int k) {
        // Min-Heap: smallest element stays at the root (peek)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);

            // Maintain strictly K elements in the heap
            if (minHeap.size() > k) {
                minHeap.poll(); // Evict the smallest among the (K+1) elements
            }
        }

        // The root is the K-th largest element in the entire array
        return minHeap.peek();
    }

    public static void main(String[] args) {
        KthLargestElement solver = new KthLargestElement();

        int[] nums1 = {3, 2, 1, 5, 6, 4};
        int k1 = 2;
        System.out.println("Test 1 (Expect 5): " + solver.findKthLargest(nums1, k1));

        int[] nums2 = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int k2 = 4;
        System.out.println("Test 2 (Expect 4): " + solver.findKthLargest(nums2, k2));
    }
}
