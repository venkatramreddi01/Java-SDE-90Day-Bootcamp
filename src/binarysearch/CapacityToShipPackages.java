package binarysearch;

/**
 * LeetCode 1011: Capacity To Ship Packages Within D Days
 * Pattern: Binary Search on Answer / Search Space Reduction
 * Time Complexity: O(N * log(sum(weights) - max(weights)))
 * Space Complexity: O(1)
 */
public class CapacityToShipPackages {
    public int shipWithinDays(int[] weights, int days) {
        int low = 0;
        int high = 0;

        for (int weight : weights) {
            low = Math.max(low, weight); // Ship must carry at least the heaviest item
            high += weight;              // Max capacity is carrying everything in 1 day
        }

        int result = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canShip(weights, mid, days)) {
                result = mid;       // Valid capacity! Save candidate
                high = mid - 1;     // Try to find a smaller valid capacity
            } else {
                low = mid + 1;      // Capacity too small! Increase it
            }
        }

        return result;
    }

    private boolean canShip(int[] weights, int capacity, int days) {
        int daysNeeded = 1;
        int currentWeight = 0;

        for (int weight : weights) {
            if (currentWeight + weight > capacity) {
                daysNeeded++;            // Need a new day
                currentWeight = weight;  // Start new day with current package
            } else {
                currentWeight += weight;
            }
        }

        return daysNeeded <= days;
    }
}
