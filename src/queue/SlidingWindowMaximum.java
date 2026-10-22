package queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 239: Sliding Window Maximum
 * Pattern: Monotonic Decreasing Deque (Indices)
 * Time Complexity: O(N) — Each element added and removed from deque at most once
 * Space Complexity: O(K) — Deque stores at most K elements
 */
public class SlidingWindowMaximum {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - k + 1];
        
        // Deque stores INDICES in monotonically decreasing order of values
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // 1. Remove elements that have fallen out of the current window [i - k + 1, i]
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // 2. Remove smaller elements from back because current element nums[i] is larger and newer
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            // 3. Add current element's index to back
            deque.offerLast(i);

            // 4. Record current window maximum once we have traversed at least k elements
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
