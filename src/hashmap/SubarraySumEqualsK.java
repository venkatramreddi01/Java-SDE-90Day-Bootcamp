package hashmap;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 560: Subarray Sum Equals K
 * Pattern: Prefix Sum + HashMap
 * Time Complexity: O(N)
 * Space Complexity: O(N)
 */
public class SubarraySumEqualsK {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int prefixSum = 0;

        // HashMap stores: <PrefixSum, Frequency>
        Map<Integer, Integer> map = new HashMap<>();

        // Base case: Prefix sum 0 has occurred 1 time (before array index 0)
        map.put(0, 1);

        for (int num : nums) {
            // 1. Update current prefix sum
            prefixSum += num;

            // 2. Check if (prefixSum - k) exists in history
            if (map.containsKey(prefixSum - k)) {
                count += map.get(prefixSum - k);
            }

            // 3. Record current prefix sum in history for future elements
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }
}
