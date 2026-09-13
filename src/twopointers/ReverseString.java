package twopointers;

/**
 * LeetCode 344: Reverse String
 * Time Complexity: O(N)
 * Space Complexity: O(1)
 */
public class ReverseString {
    public void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }
}
