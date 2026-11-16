package backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Day 27: Backtracking Core Patterns
 * Covers:
 * 1. Subsets (LeetCode 78) - Power Set
 * 2. Permutations (LeetCode 46) - Arranging elements where order matters
 * 3. Combination Sum (LeetCode 39) - Unlimited reuse of elements with pruning
 */
public class BacktrackingPatterns {

    // ==========================================
    // PATTERN 1: SUBSETS (LeetCode 78)
    // Time Complexity: O(N * 2^N)
    // Space Complexity: O(N) auxiliary recursion stack
    // ==========================================
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackSubsets(0, nums, new ArrayList<>(), result);
        return result;
    }

    private static void backtrackSubsets(int start, int[] nums, List<Integer> current, List<List<Integer>> result) {
        // Every state visited is a valid subset -> create deep copy snapshot
        result.add(new ArrayList<>(current));

        for (int i = start; i < nums.length; i++) {
            // 1. Choose
            current.add(nums[i]);
            // 2. Explore (move to i + 1 so elements cannot be reused)
            backtrackSubsets(i + 1, nums, current, result);
            // 3. Unchoose / Backtrack
            current.remove(current.size() - 1);
        }
    }

    // ==========================================
    // PATTERN 2: PERMUTATIONS (LeetCode 46)
    // Time Complexity: O(N * N!)
    // Space Complexity: O(N) stack + O(N) used array
    // ==========================================
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        backtrackPermute(nums, used, new ArrayList<>(), result);
        return result;
    }

    private static void backtrackPermute(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        // Base case: full permutation formed
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue; // Skip already chosen elements

            // 1. Choose
            used[i] = true;
            current.add(nums[i]);

            // 2. Explore
            backtrackPermute(nums, used, current, result);

            // 3. Unchoose / Backtrack
            used[i] = false;
            current.remove(current.size() - 1);
        }
    }

    // ==========================================
    // PATTERN 3: COMBINATION SUM (LeetCode 39)
    // Time Complexity: O(2^T) where T = target / min(candidates)
    // Space Complexity: O(T) stack depth
    // ==========================================
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates); // Sorting enables efficient pruning
        backtrackCombSum(0, candidates, target, new ArrayList<>(), result);
        return result;
    }

    private static void backtrackCombSum(int start, int[] candidates, int remain, List<Integer> current, List<List<Integer>> result) {
        // Base case 1: Target reached
        if (remain == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // Pruning: Since array is sorted, if current candidate exceeds remain,
            // all subsequent candidates will also exceed remain!
            if (candidates[i] > remain) break;

            // 1. Choose
            current.add(candidates[i]);
            // 2. Explore: pass 'i' (not i + 1) because same element can be reused
            backtrackCombSum(i, candidates, remain - candidates[i], current, result);
            // 3. Unchoose / Backtrack
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Subsets of [1, 2, 3] ===");
        List<List<Integer>> subResult = subsets(new int[]{1, 2, 3});
        System.out.println("Total subsets: " + subResult.size());
        System.out.println(subResult);

        System.out.println("\n=== 2. Permutations of [1, 2, 3] ===");
        List<List<Integer>> permResult = permute(new int[]{1, 2, 3});
        System.out.println("Total permutations: " + permResult.size());
        System.out.println(permResult);

        System.out.println("\n=== 3. Combination Sum ([2, 3, 6, 7], target = 7) ===");
        List<List<Integer>> combResult = combinationSum(new int[]{2, 3, 6, 7}, 7);
        System.out.println("Combinations: " + combResult);
    }
}
