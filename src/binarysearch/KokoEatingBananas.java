package binarysearch;

/**
 * LeetCode 875: Koko Eating Bananas
 * Pattern: Binary Search on Answer / Search Space Reduction
 * Time Complexity: O(N * log(max(piles)))
 * Space Complexity: O(1)
 */
public class KokoEatingBananas {
    public int minEatingSpeed(int[] piles, int h) {
        int low = 1;
        int high = 0;

        for (int pile : piles) {
            high = Math.max(high, pile);
        }

        int result = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canEatAll(piles, mid, h)) {
                result = mid;       // Valid speed! Save candidate
                high = mid - 1;     // Try to find a smaller valid speed
            } else {
                low = mid + 1;      // Too slow! Increase speed
            }
        }

        return result;
    }

    private boolean canEatAll(int[] piles, int speed, int h) {
        long hoursNeeded = 0;
        for (int pile : piles) {
            // Integer ceiling division: ceil(pile / speed) == (pile + speed - 1) / speed
            hoursNeeded += (pile + speed - 1) / speed;
        }
        return hoursNeeded <= h;
    }
}
