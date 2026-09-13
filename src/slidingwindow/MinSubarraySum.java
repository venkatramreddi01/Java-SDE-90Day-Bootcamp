package slidingwindow;

/**
 * LeetCode 209: Minimum Size Subarray Sum (Dynamic Sliding Window)
 * Time Complexity: O(N)
 * Space Complexity: O(1)
 */
public class MinSubarraySum {
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int currentSum = 0;
        int minLength = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right];

            while (currentSum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                currentSum -= nums[left];
                left++;
            }
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
}
