package heaps;

import java.util.*;

/**
 * ============================================================
 *  DAY 38 — Top-K Patterns: Custom Comparators & 2D Heaps
 * ============================================================
 *
 *  Problems:
 *   1. LeetCode 347 — Top K Frequent Elements
 *   2. LeetCode 973 — K Closest Points to Origin
 *
 *  CORE PATTERN:
 *   - K Highest Frequencies --> MIN-Heap of size K (evicts lowest frequency).
 *   - K Closest Distances   --> MAX-Heap of size K (evicts largest distance).
 *
 *  TIME COMPLEXITY : O(N log K) for both.
 *  SPACE COMPLEXITY: O(N) for HashMap/Heap storage.
 * ============================================================
 */
public class TopKElements {

    // --------------------------------------------------------
    //  PROBLEM 1: LC 347 — Top K Frequent Elements
    // --------------------------------------------------------
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: Count frequencies
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        // Step 2: Min-Heap ordered by frequency ascending
        // The number with lowest frequency sits at peek()
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
            (a, b) -> countMap.get(a) - countMap.get(b)
        );

        // Step 3: Keep only K elements in heap
        for (int num : countMap.keySet()) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // Drop smallest frequency
            }
        }

        // Step 4: Extract result
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = minHeap.poll();
        }
        return result;
    }

    // --------------------------------------------------------
    //  PROBLEM 2: LC 973 — K Closest Points to Origin
    // --------------------------------------------------------
    public int[][] kClosest(int[][] points, int k) {
        // Max-Heap ordered by squared distance descending
        // The point with the largest distance sits at peek()
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
            (p1, p2) -> Integer.compare(
                (p2[0] * p2[0] + p2[1] * p2[1]), // d2
                (p1[0] * p1[0] + p1[1] * p1[1])  // d1
            )
        );

        for (int[] point : points) {
            maxHeap.offer(point);
            if (maxHeap.size() > k) {
                maxHeap.poll(); // Drop farthest point
            }
        }

        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll();
        }
        return result;
    }

    // --------------------------------------------------------
    //  MAIN — Manual verification
    // --------------------------------------------------------
    public static void main(String[] args) {
        TopKElements solver = new TopKElements();

        // Test LC 347
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k1 = 2;
        System.out.println("=== LC 347: Top 2 Frequent ===");
        System.out.println(Arrays.toString(solver.topKFrequent(nums, k1)));
        // Expected: [2, 1] or [1, 2]

        // Test LC 973
        int[][] points = {{1, 3}, {-2, 2}, {5, 8}, {0, 1}};
        int k2 = 2;
        System.out.println("\n=== LC 973: 2 Closest Points to (0,0) ===");
        int[][] closest = solver.kClosest(points, k2);
        for (int[] pt : closest) {
            System.out.println(Arrays.toString(pt) + " (dist^2 = " + (pt[0]*pt[0] + pt[1]*pt[1]) + ")");
        }
        // Expected: [0, 1] (dist 1) and [-2, 2] (dist 8)
    }
}
