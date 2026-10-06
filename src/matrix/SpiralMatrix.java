package matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 54: Spiral Matrix
 * Pattern: 4-Boundary Shrinking Strategy
 * Time Complexity: O(M * N)
 * Space Complexity: O(1) extra space
 */
public class SpiralMatrix {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;

        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {
            // 1. Traverse Left to Right along top boundary
            for (int j = left; j <= right; j++) {
                result.add(matrix[top][j]);
            }
            top++;

            // 2. Traverse Top to Bottom along right boundary
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;

            // 3. Traverse Right to Left along bottom boundary (if valid)
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    result.add(matrix[bottom][j]);
                }
                bottom--;
            }

            // 4. Traverse Bottom to Top along left boundary (if valid)
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
        }

        return result;
    }
}
