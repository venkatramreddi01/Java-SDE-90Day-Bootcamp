package fastslow;

/**
 * LeetCode 26: Remove Duplicates from Sorted Array
 * Time Complexity: O(N)
 * Space Complexity: O(1)
 */
public class RemoveDuplicates {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }

        return slow + 1;
    }
}
