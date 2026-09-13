package slidingwindow;

/**
 * LeetCode 643: Maximum Average Subarray I (Fixed Sliding Window)
 * Time Complexity: O(N)
 * Space Complexity: O(1)
 */
public class MaxAverageSubarray {
    public double findMaxAverage(int[] nums, int k) {
        int currentSum = 0;
        for (int i = 0; i < k; i++) {
            currentSum += nums[i];
        }

        int maxSum = currentSum;

        for (int i = k; i < nums.length; i++) {
            currentSum += nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, currentSum);
        }

        return (double) maxSum / k;
    }
}
