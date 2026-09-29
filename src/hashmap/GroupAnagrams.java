package hashmap;

import java.util.*;

/**
 * LeetCode 49: Group Anagrams
 * Pattern: Sorted Character Key Categorization
 * Time Complexity: O(N * K log K)
 * Space Complexity: O(N * K)
 */
public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sortedKey = new String(chars);

            map.putIfAbsent(sortedKey, new ArrayList<>());
            map.get(sortedKey).add(str);
        }

        return new ArrayList<>(map.values());
    }
}
