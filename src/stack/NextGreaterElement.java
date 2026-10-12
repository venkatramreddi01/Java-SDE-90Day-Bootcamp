package stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 496: Next Greater Element I
 * Pattern: Monotonic Decreasing Stack + HashMap
 * Time Complexity: O(N + M)
 * Space Complexity: O(N)
 */
public class NextGreaterElement {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // Map stores: <Number, Its Next Greater Element>
        Map<Integer, Integer> nextGreaterMap = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();

        // Process nums2 using Monotonic Decreasing Stack
        for (int num : nums2) {
            while (!stack.isEmpty() && num > stack.peek()) {
                nextGreaterMap.put(stack.pop(), num);
            }
            stack.push(num);
        }

        // Remaining elements in stack have no next greater element (-1)
        while (!stack.isEmpty()) {
            nextGreaterMap.put(stack.pop(), -1);
        }

        // Build result for nums1 in O(1) per lookup
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextGreaterMap.getOrDefault(nums1[i], -1);
        }

        return result;
    }
}
