package fastslow;

/**
 * LeetCode 283: Move Zeroes
 * Time Complexity: O(N)
 * Space Complexity: O(1)
 */
public class MoveZeroes {
    public void moveZeroes(int[] nums) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                int temp = nums[slow];
                nums[slow] = nums[fast];
                nums[fast] = temp;
                slow++;
            }
        }
    }
}
