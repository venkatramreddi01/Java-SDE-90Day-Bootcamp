package stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 84: Largest Rectangle in Histogram
 * Pattern: Monotonic Increasing Stack (Boundary Calculation)
 * Time Complexity: O(N) — Each bar index is pushed and popped at most once
 * Space Complexity: O(N)
 */
public class LargestRectangleInHistogram {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;

        // Loop runs up to n (using index n with height 0 as sentinel to flush remaining bars)
        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            // If current bar is shorter than the bar at stack top, pop and calculate area
            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                
                // If stack is empty, it means this bar was the shortest so far (width extends from 0 to i)
                int width = stack.isEmpty() ? i : (i - stack.peek() - 1);
                
                maxArea = Math.max(maxArea, height * width);
            }

            stack.push(i);
        }

        return maxArea;
    }
}
