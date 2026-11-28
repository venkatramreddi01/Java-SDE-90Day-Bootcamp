package heaps;

import java.util.*;

/**
 * ============================================================
 *  DAY 39 — Advanced Heap Patterns
 * ============================================================
 *
 *  Problems:
 *   1. LeetCode 23  — Merge k Sorted Lists (Hard)
 *   2. LeetCode 621 — Task Scheduler
 *
 *  PATTERNS:
 *   - LC 23:  Min-Heap of size K holding the active list heads.
 *             Time: O(N log K), Space: O(K).
 *   - LC 621: Greedy Max-Heap / Frequency Framing.
 *             Time: O(N), Space: O(1) auxiliary.
 * ============================================================
 */

// Simple ListNode definition for LC 23
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class AdvancedHeaps {

    // --------------------------------------------------------
    //  PROBLEM 1: LC 23 — Merge k Sorted Lists (Hard)
    // --------------------------------------------------------
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        // Min-Heap ordered by node value
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.val, b.val)
        );

        // Step 1: Push initial heads of all non-empty lists
        for (ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        // Step 2: Extract minimum and push its next pointer
        while (!minHeap.isEmpty()) {
            ListNode smallest = minHeap.poll();
            curr.next = smallest;
            curr = curr.next;

            if (smallest.next != null) {
                minHeap.offer(smallest.next);
            }
        }

        return dummy.next;
    }

    // --------------------------------------------------------
    //  PROBLEM 2: LC 621 — Task Scheduler
    // --------------------------------------------------------
    public int leastInterval(char[] tasks, int n) {
        // Step 1: Count frequency of each task
        int[] count = new int[26];
        int maxFreq = 0;
        for (char task : tasks) {
            count[task - 'A']++;
            maxFreq = Math.max(maxFreq, count[task - 'A']);
        }

        // Step 2: Count how many tasks share the maximum frequency
        int maxCount = 0;
        for (int freq : count) {
            if (freq == maxFreq) {
                maxCount++;
            }
        }

        // Step 3: Math formula for frames
        // (maxFreq - 1) chunks of size (n + 1) plus tasks with maxFreq in last chunk
        int ans = (maxFreq - 1) * (n + 1) + maxCount;

        // Cannot be shorter than the number of tasks themselves
        return Math.max(tasks.length, ans);
    }

    // --------------------------------------------------------
    //  MAIN — Verification
    // --------------------------------------------------------
    public static void main(String[] args) {
        AdvancedHeaps solver = new AdvancedHeaps();

        // Test LC 23:
        // List 1: 1 -> 4 -> 5
        // List 2: 1 -> 3 -> 4
        // List 3: 2 -> 6
        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode l3 = new ListNode(2, new ListNode(6));

        ListNode[] lists = {l1, l2, l3};
        ListNode merged = solver.mergeKLists(lists);

        System.out.print("=== LC 23 Merged List (Expect 1->1->2->3->4->4->5->6): ");
        while (merged != null) {
            System.out.print(merged.val + (merged.next != null ? "->" : ""));
            merged = merged.next;
        }
        System.out.println();

        // Test LC 621:
        char[] tasks1 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n1 = 2;
        System.out.println("\n=== LC 621 Task Scheduler (Expect 8): " + solver.leastInterval(tasks1, n1));

        char[] tasks2 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n2 = 0;
        System.out.println("LC 621 with n=0 (Expect 6): " + solver.leastInterval(tasks2, n2));
    }
}
