# 💳 DBMS Transactions & Concurrency Control — Interview Notes

> High-frequency interview topic for backend engineering (Fintech, E-commerce, Distributed Systems).

---

## 1. What is a Transaction?

A **Transaction** is a logical unit of database processing that includes one or more database access operations (Read/Write).
- **Rule:** It must be **Atomic** — either all operations execute completely or none of them do.
- **Example (Bank Transfer):**
  1. Deduct ₹1,000 from Account A.
  2. Add ₹1,000 to Account B.
  - If Step 1 succeeds and Step 2 fails, the system must **ROLLBACK** Step 1 so money is not lost.

---

## 2. Transaction Lifecycle & States

```
                ┌─────────┐
                │ ACTIVE  │ (Initial state; operations executing)
                └────┬────┘
                     │ All operations completed
                ┌────▼─────────────┐
                │ PARTIALLY        │
                │ COMMITTED        │
                └────┬─────────────┘
          Success    │           │ Failure occurs
        ┌────────────▼──┐     ┌──▼───────────┐
        │   COMMITTED   │     │    FAILED    │
        │ (Saved to DB) │     └──────┬───────┘
        └───────────────┘            │ Undo all changes (Rollback)
                              ┌──────▼───────┐
                              │  TERMINATED  │ (Aborted/Rolled back)
                              └──────────────┘
```

---

## 3. ACID Properties in Depth

| Property | Meaning | Handled By | Real-world Scenario |
| :--- | :--- | :--- | :--- |
| **Atomicity** | "All or Nothing" | Transaction Manager (Undo Logs / Rollback) | Deducting & crediting bank accounts simultaneously. |
| **Consistency** | Database remains in a valid state satisfying all constraints | Application + DBMS Constraints | Balance cannot drop below zero. Total money across accounts remains constant. |
| **Isolation** | Concurrent transactions don't interfere with each other | Concurrency Control Manager (Locks/2PL) | Two ATM withdrawals on the same account at the exact same millisecond. |
| **Durability** | Committed data is permanently written to non-volatile storage | Recovery Manager (Redo Logs / WAL - Write-Ahead Logging) | Server crashes 1 millisecond after user sees "Payment Successful". |

---

## 4. Concurrency Read Phenomena (The 3 Classic Problems)

When multiple transactions run simultaneously without proper isolation:

1. **Dirty Read (Read Uncommitted Data):**
   - $T_1$ updates a row without committing.
   - $T_2$ reads that updated row.
   - $T_1$ fails and executes `ROLLBACK`.
   - Result: $T_2$ operated on "dirty" (phantom) data that never officially existed!

2. **Non-Repeatable Read (Unrepeatable Read):**
   - $T_1$ reads a row (e.g., `salary = 50000`).
   - $T_2$ updates that row (`salary = 70000`) and commits.
   - $T_1$ reads the exact same row again and gets `70000`.
   - Result: Same query executed twice within the same transaction produces different values.

3. **Phantom Read:**
   - $T_1$ reads a range of rows (`SELECT COUNT(*) FROM Employees WHERE dept = 'IT'` -> 5 rows).
   - $T_2$ inserts a new employee in 'IT' and commits.
   - $T_1$ executes the same query again -> 6 rows!
   - Result: A new "phantom" row appeared inside $T_1$'s range.

---

## 5. Transaction Isolation Levels

SQL standard defines 4 isolation levels to balance performance vs correctness:

| Isolation Level | Dirty Read | Non-Repeatable Read | Phantom Read | Concurrency / Speed |
| :--- | :---: | :---: | :---: | :---: |
| **Read Uncommitted** | ⚠️ Allowed | ⚠️ Allowed | ⚠️ Allowed | 🚀 Highest |
| **Read Committed** | ❌ Prevented | ⚠️ Allowed | ⚠️ Allowed | ⚡ Fast (Default in PostgreSQL, Oracle) |
| **Repeatable Read** | ❌ Prevented | ❌ Prevented | ⚠️ Allowed | 🐢 Balanced (Default in MySQL InnoDB) |
| **Serializable** | ❌ Prevented | ❌ Prevented | ❌ Prevented | 🐌 Slowest (Complete serial execution) |

---

## 6. Two-Phase Locking (2PL) Protocol

To guarantee **serializability**, databases use the 2PL protocol.

### The Two Phases:
1. **Growing Phase:**
   - A transaction may **acquire locks**, but **cannot release any lock**.
2. **Shrinking Phase:**
   - The moment the **first lock is released**, the transaction enters the shrinking phase.
   - A transaction may **release locks**, but **CANNOT acquire any new lock**!

```
 Locks
   ▲             [Lock Point]
   │                 /\
   │                /  \
   │   GROWING     /    \    SHRINKING
   │  (Only Lock) /      \  (Only Unlock)
   │             /        \
   └────────────/──────────\──────────► Time
```

### Lock Types:
- **Shared Lock ($S$):** Read lock. Multiple transactions can hold $S$-locks on the same item.
- **Exclusive Lock ($X$):** Write lock. Only ONE transaction can hold an $X$-lock; blocks all other locks ($S$ and $X$).

---

## 7. Deadlocks in DBMS

Occurs when two transactions each hold a lock that the other needs to proceed:
- $T_1$ holds Lock on Resource $A$, waits for Lock on Resource $B$.
- $T_2$ holds Lock on Resource $B$, waits for Lock on Resource $A$.

### Deadlock Handling:
1. **Deadlock Detection:** Maintain a **Wait-For Graph (WFG)**.
   - Vertices = Transactions.
   - Edges = Waiting for resource.
   - **A cycle in WFG indicates a Deadlock!**
2. **Deadlock Recovery:** Select a "Victim Transaction" based on cost/age, abort it, and `ROLLBACK`.
3. **Deadlock Prevention:**
   - **Wait-Die Scheme (Non-preemptive):** Older waits for younger; younger dies if it requests older's resource.
   - **Wound-Wait Scheme (Preemptive):** Older wounds (preempts) younger; younger waits if older has resource.

---

## Top Interview Questions & Summary

- **Q: What is Write-Ahead Logging (WAL)?**
  - Before any database modification is written to disk, the corresponding log record must be written to stable storage first. This guarantees Durability and Atomicity during crashes.
- **Q: What is the difference between Repeatable Read and Serializable?**
  - Repeatable Read prevents dirty and non-repeatable reads on existing rows, but phantom reads can still happen (new rows inserted). Serializable prevents phantom reads by locking index ranges or table partitions.
- **Q: Can a transaction acquire locks after releasing one in 2PL?**
  - **No.** Releasing a lock transitions the transaction into the shrinking phase, where no new locks can ever be acquired.
