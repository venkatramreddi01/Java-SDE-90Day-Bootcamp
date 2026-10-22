package queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 232: Implement Queue using Stacks
 * Pattern: Two Stacks (inStack & outStack) for Amortized O(1) Queue
 * Time Complexity: Amortized O(1) for push, pop, peek, empty
 * Space Complexity: O(N)
 */
public class MyQueue {
    private final Deque<Integer> inStack;
    private final Deque<Integer> outStack;

    public MyQueue() {
        inStack = new ArrayDeque<>();
        outStack = new ArrayDeque<>();
    }

    public void push(int x) {
        inStack.push(x);
    }

    public int pop() {
        shiftStacks();
        return outStack.pop();
    }

    public int peek() {
        shiftStacks();
        return outStack.peek();
    }

    public boolean empty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    // Helper: Transfer elements from inStack to outStack only when outStack is empty
    private void shiftStacks() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
    }
}
