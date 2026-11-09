# 🖥️ Operating System Concepts — Interview Notes
> Covers all OS topics asked in Google, Microsoft, Amazon, Flipkart interviews.

---

## 1. Process vs Thread

| | Process | Thread |
|---|---|---|
| Definition | Executing program instance | Smallest unit of CPU execution inside a process |
| Memory | Own separate memory space | Shares heap with other threads in same process |
| Communication | Expensive (IPC) | Easy (shared heap) |
| Overhead | Heavy | Lightweight |
| Example | Chrome browser | Each Chrome tab's JS engine |

```
Process Memory Layout:
┌─────────────────┐
│   Code Segment  │  ← shared by all threads
├─────────────────┤
│   Heap          │  ← shared by all threads (⚠️ race conditions!)
├─────────────────┤
│   Stack T1      │  ← private to Thread 1
├─────────────────┤
│   Stack T2      │  ← private to Thread 2
└─────────────────┘
```

---

## 2. Mutex vs Semaphore

| | Mutex | Semaphore |
|---|---|---|
| Connections | Only 1 thread at a time | N threads (counting permits) |
| Ownership | Yes — locker must unlock | No — any thread can release |
| Use case | Exclusive resource access | Signaling between threads |
| Java API | `synchronized`, `ReentrantLock` | `java.util.concurrent.Semaphore` |

**Real World Analogy:**
```
Mutex     = Single-key bathroom lock (only 1 person, same person unlocks)
Semaphore = Parking lot with N slots (N cars allowed, any car can leave)
```

**Java Semaphore API:**
```java
Semaphore sem = new Semaphore(3);  // 3 permits
sem.acquire();  // wait for a permit (blocks if 0)
sem.release();  // release a permit (any thread can call)
```

---

## 3. Deadlock

> A deadlock occurs when two or more processes are **blocked forever**, each waiting for a resource held by the other.

### 4 Necessary Conditions (ALL must hold for deadlock):
1. **Mutual Exclusion** — resource held by only one process at a time
2. **Hold & Wait** — process holds one resource, waits for another
3. **No Preemption** — resource cannot be forcibly taken away
4. **Circular Wait** — P1 waits for P2, P2 waits for P1 (cycle!)

### Prevention — Break ANY ONE condition:
```
Break Hold & Wait    → request ALL resources at once before starting
Break Circular Wait  → always acquire locks in same fixed ORDER
Break No Preemption  → allow OS to forcibly take resources
```

**Classic Deadlock Example:**
```java
// Thread 1          // Thread 2        ← DEADLOCK!
lock(A);             lock(B);
lock(B); ← waits     lock(A); ← waits
```
**Fix:** Always acquire in same order: `lock(A)` then `lock(B)` in BOTH threads.

---

## 4. Thread Safety in Java

| Mechanism | How | Speed | Use Case |
|---|---|---|---|
| `synchronized` | One thread at a time, others sleep | Slow (blocking) | Complex critical sections |
| `volatile` | Reads/writes directly to RAM (no cache) | Fast | Single variable visibility |
| `AtomicInteger` | Hardware CAS (no locks, no queue) | Fastest | Simple counters |

```java
// synchronized — thread queue forms
synchronized(this) { count++; }

// AtomicInteger — no queue, hardware-level
AtomicInteger count = new AtomicInteger(0);
count.incrementAndGet(); // Compare-And-Swap (CAS)
```

---

## 5. CPU Scheduling Algorithms

| Algorithm | Rule | Problem | Real-World |
|---|---|---|---|
| **FCFS** | First arrival first served | Convoy Effect 🚛 | Printer queue |
| **SJF** | Shortest burst time first | Starvation 😴 | Hospital triage |
| **Round Robin** | Fixed time quantum, rotate | Context switch overhead | Linux/Windows OS ⭐ |
| **Priority** | Highest priority first | Starvation (fix: Aging) | Real-time systems |

### Key Formulas:
```
Turnaround Time (TAT) = Completion Time (CT) - Arrival Time (AT)
Waiting Time (WT)     = Turnaround Time (TAT) - Burst Time (BT)
```

### Round Robin Golden Rule:
> If remaining BT < Time Quantum → process runs for remaining BT only, NOT full TQ!

---

## 6. Memory Management — Paging

> **Paging** = Split process into equal **Pages**, split RAM into equal **Frames**. Pages fit into any Frame.

```
Page Table (OS's address book):
┌──────┬───────┐
│ Page │ Frame │
├──────┼───────┤
│  0   │   2   │  ← Page 0 stored in Frame 2
│  1   │   0   │  ← Page 1 stored in Frame 0
│  2   │   3   │  ← Page 2 stored in Frame 3
└──────┴───────┘
```

| Term | Meaning | Analogy |
|---|---|---|
| Page | Fixed chunk of a process | 1 chapter of a book |
| Frame | Fixed slot in RAM | 1 slot on your desk |
| Page Table | Maps page → frame | Address book |
| Page Fault | Needed page NOT in RAM | Chapter is in cupboard, not desk |
| Virtual Memory | Disk acts as extra RAM | Hotel rooms for overflow guests |

---

## 7. Page Replacement Algorithms

| Algorithm | Rule | Problem |
|---|---|---|
| **FIFO** | Remove page that entered RAM first | Belady's Anomaly, removes frequently used pages |
| **LRU** ⭐ | Remove Least Recently Used page | Best for real-world (keeps hot pages) |
| **Optimal** | Remove page not used for longest future time | Impossible in practice (needs future knowledge) |

**Why LRU > FIFO?**
> FIFO blindly removes the oldest page even if it's frequently used.
> LRU removes the page least recently accessed — smarter heuristic.

---

## 8. Thrashing

> **Thrashing** = OS spends MORE time swapping pages in/out of RAM than actually executing processes.

**Cause:** Too many processes running, limited RAM frames → constant page faults.

**Prevention:**
1. **Working Set Model** — only run processes whose total pages fit in RAM
2. **Page Fault Frequency** — monitor and control page fault rate
3. **Reduce Multiprogramming** — run fewer processes simultaneously

---

## 9. Segmentation

> **Segmentation** = Split process into **logical sections** (code, heap, stack, data) of variable size.

```
Java JVM Segmentation:
├── Method Area  → class definitions, static variables
├── Heap         → new objects (Garbage Collected)
├── Stack        → one per thread, method calls, local variables
└── PC Register  → current instruction per thread
```

---

## 🎯 Top Interview Questions & Answers

**Q: What is the difference between process and thread?**
> A process is an executing program instance with its own memory. A thread is the smallest unit of CPU execution that shares the heap with other threads inside the same process.

**Q: Can Thread B release a mutex locked by Thread A?**
> No. Mutex has ownership — only the thread that locked it can unlock it.

**Q: What is deadlock? Name two conditions.**
> Deadlock is when two or more processes are blocked forever, each waiting for a resource held by the other. Two conditions: Hold & Wait, Circular Wait.

**Q: Which scheduling algorithm does modern OS use and why?**
> Round Robin — it gives every process a fair fixed time quantum, prevents starvation, and no single process can hog the CPU.

**Q: What is thrashing?**
> When the OS spends more time swapping pages in and out of RAM than executing processes, caused by too many processes running with insufficient RAM frames.
