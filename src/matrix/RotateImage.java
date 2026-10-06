package matrix;

/**
 * LeetCode 48: Rotate Image (90 Degrees Clockwise In-Place)
 * Pattern: Matrix Transpose + Row Reversal
 * Time Complexity: O(N^2)
 * Space Complexity: O(1)
 */
public class RotateImage {
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Transpose matrix (swap matrix[i][j] with matrix[j][i])
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            int left = 0, right = n - 1;
            while (left < right) {
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;
                left++;
                right--;
            }
        }
    }
}
