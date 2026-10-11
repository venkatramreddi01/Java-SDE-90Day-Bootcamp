package stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 155: Min Stack
 * Pattern: Dual Stack for O(1) Minimum Retrieval
 * Time Complexity: O(1) for push, pop, top, and getMin
 * Space Complexity: O(N)
 */
public class MinStack {
    private final Deque<Integer> mainStack;
    private final Deque<Integer> minStack;

    public MinStack() {
        mainStack = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }

    public void push(int val) {
        mainStack.push(val);
        // MinStack tracks the minimum seen so far up to this level
        if (minStack.isEmpty()) {
            minStack.push(val);
        } else {
            minStack.push(Math.min(val, minStack.peek()));
        }
    }

    public void pop() {
        mainStack.pop();
        minStack.pop();
    }

    public int top() {
        return mainStack.peek();
    }

    public int getMin() {
        return minStack.peek(); // Returns minimum in O(1) time
    }
}
