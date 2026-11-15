package recursion;

/**
 * Day 26: Core Recursion Fundamentals
 * Demonstrates:
 * 1. Pre-Order vs Post-Order execution on the JVM Call Stack (Print N to 1 vs 1 to N)
 * 2. Tree Recursion (Branching / In-order pattern)
 * 3. Divide and Conquer Fast Power: myPow(x, n) in O(log N) time
 */
public class RecursionBasics {

    // 1. Post-Order (Work during stack unwinding): Prints 1 to N
    public static void print1ToN(int n) {
        if (n == 0) return; // Base Case
        print1ToN(n - 1);   // Hypothesis: Assume 1 to n-1 is printed
        System.out.print(n + " "); // Induction: print n on return
    }

    // 2. Pre-Order (Work during stack winding): Prints N to 1
    public static void printNTo1(int n) {
        if (n == 0) return; // Base Case
        System.out.print(n + " "); // Induction: print n first
        printNTo1(n - 1);   // Hypothesis: Then print remaining n-1 to 1
    }

    // 3. Tree Recursion: mystery / In-Order pattern (Left -> Root -> Right)
    public static void mysteryTree(int n) {
        if (n == 0) return;
        mysteryTree(n - 1);
        System.out.print(n + " ");
        mysteryTree(n - 1);
    }

    // 4. Divide and Conquer: Fast Power (LeetCode 50 - Pow(x, n))
    // Time Complexity: O(log N)
    // Space Complexity: O(log N) on call stack
    public static double myPow(double x, long n) {
        if (n == 0) return 1.0;
        if (n < 0) {
            x = 1.0 / x;
            n = -n;
        }

        double half = myPow(x, n / 2);

        if (n % 2 == 0) {
            return half * half;
        } else {
            return x * half * half;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- 1. Print 1 to N (n=5) ---");
        print1ToN(5);
        System.out.println("\n");

        System.out.println("--- 2. Print N to 1 (n=5) ---");
        printNTo1(5);
        System.out.println("\n");

        System.out.println("--- 3. Mystery Tree Pattern (n=3) ---");
        mysteryTree(3);
        System.out.println("\n");

        System.out.println("--- 4. Fast Power (2^10) ---");
        System.out.println("2^10 = " + myPow(2.0, 10)); // Expected: 1024.0
        System.out.println("2^(-3) = " + myPow(2.0, -3)); // Expected: 0.125
    }
}
