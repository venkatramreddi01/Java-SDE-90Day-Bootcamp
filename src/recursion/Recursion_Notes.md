# 🔁 Recursion Fundamentals & Memory Call Stack — Interview Notes

> Foundational topic for Trees, Graphs, Backtracking, and Dynamic Programming.

---

## 1. What is Recursion?

A function that **calls itself** to solve a smaller instance of the same problem.
- **Base Case:** Stopping condition that breaks the recursive loop.
- **Recursive Hypothesis:** Assuming `f(n-1)` or smaller subproblem works correctly.
- **Induction Step:** Combining the subproblem solution with current step to produce the final answer.

---

## 2. Memory Model & JVM Call Stack

Every method call creates a **Stack Frame** in the thread's call stack:
- Contains:
  1. Parameters passed to the method.
  2. Local variables.
  3. Return address back to the caller.

```
Stack Growth (Winding)       Stack Shrinking (Unwinding)
  ▲                              │
  │ fact(1) -> hits base case    ▼ fact(1) returns 1
  │ fact(2) -> waits for fact(1)   fact(2) computes 2 * 1 = 2
  │ fact(3) -> waits for fact(2)   fact(3) computes 3 * 2 = 6
```

### ⚠️ StackOverflowError
- Thrown when the recursion depth exceeds the allocated JVM stack memory limit (default `-Xss` is typically 1MB).
- **Causes:**
  1. Missing or incorrect Base Case.
  2. Recursive step doesn't move toward the base case (e.g., `f(n)` instead of `f(n-1)`).
  3. Recursion depth is too large for the stack (e.g., $N > 10^5$). In such cases, use an **iterative** approach or an explicit stack on the heap.

---

## 3. Pre-Order vs Post-Order Execution

The location of the recursive call relative to the processing logic determines the order:

| Type | Structure | When Work Happens | Example Output ($n=3$) |
| :--- | :--- | :--- | :--- |
| **Pre-Order** | Work first $\rightarrow$ Recursive call | On the way DOWN (Winding) | `3 2 1` (Descending) |
| **Post-Order** | Recursive call first $\rightarrow$ Work | On the way UP (Unwinding) | `1 2 3` (Ascending) |

---

## 4. Linear vs Tree (Branching) Recursion

### Linear Recursion (e.g., Factorial, Print 1 to N):
- Exactly **one** recursive call per frame.
- Call stack depth: $O(N)$.
- Total function calls: $O(N)$.

### Tree / Branching Recursion (e.g., Fibonacci, Combinations):
- Multiple recursive calls per frame (e.g., $f(n-1) + f(n-2)$).
- Total function calls: Exponential ($O(2^N)$).
- **Auxiliary Space Complexity on Call Stack:** $O(N)$ (Height of the tree, because only one path is active at any point).

---

## 5. Divide and Conquer: Fast Power ($O(\log N)$)

Instead of multiplying $x$, $n$ times ($O(N)$):
$$x^n = \begin{cases} (x^{n/2})^2 & \text{if } n \text{ is even} \\ x \cdot (x^{n/2})^2 & \text{if } n \text{ is odd} \end{cases}$$

- Halves the exponent at every recursive level.
- **Time Complexity:** $O(\log N)$.
- **Space Complexity:** $O(\log N)$ on the call stack.
