# 🚀 Java SDE 90-Day Bootcamp

> **Goal**: Master Data Structures, Algorithms, Computer Science Fundamentals, and Enterprise Java Engineering to crack top product-based companies (Google, Microsoft, Amazon, Apple, Rippling, Meesho, etc.).

---

## 📂 Repository Structure

```text
Java-SDE-90Day-Bootcamp/
├── README.md
└── src/
    ├── collections/          # Java Memory Model, Equals & HashCode, Collections Framework
    ├── twopointers/          # Inward & Fast/Slow Two Pointer Patterns
    ├── fastslow/             # Fast & Slow Pointers (Hare & Tortoise), Array Partitioning
    ├── slidingwindow/        # Fixed & Dynamic Sliding Window Patterns
    ├── hashmap/              # Prefix Sum + HashMap, String Categorization, HashSet Sequences
    ├── matrix/               # 2D Matrix Manipulation & Spiral Traversals
    └── binarysearch/         # Rotated Binary Search & Binary Search on Answer
```

---

## 📊 Progress Summary

### **Week 1: Core Fundamentals & Linear Patterns**
| Topic | Pattern | Problems Completed | Time Complexity | Space Complexity |
| :--- | :--- | :--- | :--- | :--- |
| **Collections** | Memory & Equals Contract | `MemoryModelDemo` | - | - |
| **Two Pointers** | Inward Search | `TwoSumSorted`, `ValidPalindrome`, `ReverseString` | $O(N)$ | $O(1)$ |
| **Two Pointers** | Optimization / Search | `ContainerWithMostWater`, `ThreeSum` | $O(N)$, $O(N^2)$ | $O(1)$ |
| **Fast & Slow** | Array Partitioning | `MoveZeroes`, `RemoveDuplicates` | $O(N)$ | $O(1)$ |
| **Fast & Slow** | Cycle Detection | `LinkedListCycle` (Floyd's Algorithm) | $O(N)$ | $O(1)$ |
| **Sliding Window**| Fixed Size | `MaxAverageSubarray` | $O(N)$ | $O(1)$ |
| **Sliding Window**| Dynamic Size | `MinSubarraySum`, `LongestSubstringWithoutRepeating` | $O(N)$ | $O(K)$ |

### **Week 2: Advanced Search, Strings & Operating Systems**
| Topic | Pattern | Problems Completed | Time Complexity | Space Complexity |
| :--- | :--- | :--- | :--- | :--- |
| **Prefix Sum** | State Tracking + Map | `SubarraySumEqualsK` (LeetCode 560) | $O(N)$ | $O(N)$ |
| **HashMap** | Key Sorting Categorization| `GroupAnagrams` (LeetCode 49) | $O(N \cdot K \log K)$ | $O(N \cdot K)$ |
| **HashSet** | Sequence-Start Detection | `LongestConsecutiveSequence` (LeetCode 128) | $O(N)$ | $O(N)$ |
| **Matrix** | Transpose + Reverse | `RotateImage` (LeetCode 48) | $O(N^2)$ | $O(1)$ |
| **Matrix** | 4-Boundary Shrinking | `SpiralMatrix` (LeetCode 54) | $O(M \times N)$ | $O(1)$ |
| **Binary Search**| Rotated Sorted Array | `SearchRotatedSortedArray` (LeetCode 33) | $O(\log N)$ | $O(1)$ |
| **Binary Search**| First & Last Boundary | `FirstAndLastPosition` (LeetCode 34) | $O(\log N)$ | $O(1)$ |
| **BS on Answer** | Search Space Reduction | `KokoEatingBananas` (LeetCode 875) | $O(N \log M)$ | $O(1)$ |
| **BS on Answer** | Capacity Minimization | `CapacityToShipPackages` (LeetCode 1011) | $O(N \log S)$ | $O(1)$ |
| **CS Core** | OS Fundamentals | Process vs Thread, Call Stack vs Heap, Concurrency | - | - |

---

## 🛠️ How to Run
Compile and run any Java file using JDK 17+:
```bash
javac src/binarysearch/KokoEatingBananas.java
java src/binarysearch/KokoEatingBananas
```
