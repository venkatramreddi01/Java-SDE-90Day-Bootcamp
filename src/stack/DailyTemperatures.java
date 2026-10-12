package stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 739: Daily Temperatures
 * Pattern: Monotonic Decreasing Stack (Storing Indices)
 * Time Complexity: O(N) — Every index is pushed and popped at most once
 * Space Complexity: O(N)
 */
public class DailyTemperatures {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        
        // Stack stores INDICES of temperatures in decreasing order
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // While current temperature is WARMER than the temperature at stack top
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevDayIndex = stack.pop();
                result[prevDayIndex] = i - prevDayIndex; // Days waited
            }
            
            // Push current day index onto stack
            stack.push(i);
        }

        return result;
    }
}
