# 🗄️ DBMS Concepts — Interview Notes
> Covers ACID Properties, Normalization, Joins, Indexing & Transactions

---

## 1. ACID Properties

> ACID = 4 guarantees every reliable database must provide

| Property | Guarantee | Keyword | Example |
|---|---|---|---|
| **Atomicity** | All or Nothing | `ROLLBACK` | Money transfer either fully happens or fully undoes |
| **Consistency** | Valid state always | `CONSTRAINTS` | Balance never goes negative |
| **Isolation** | No interference between concurrent transactions | `LOCKS` | Two transfers don't corrupt each other |
| **Durability** | Committed = permanent (survives crashes) | `WAL/Disk` | Deposit survives a power cut |

### Atomicity
```java
// Think of it like try-catch-rollback
try {
    deductFromVenkat(500);
    addToRavi(500);
    commit();      // ✅ Both done → save permanently
} catch (Exception e) {
    rollback();    // ❌ Something failed → undo everything
}
```

### Consistency
```
Rule: Balance >= 0 (CHECK constraint)
Venkat has ₹200, tries to withdraw ₹500
→ Transaction REJECTED → balance stays ₹200 ✅
```

### Isolation Levels (Low → High safety)
```
READ UNCOMMITTED → can read dirty/uncommitted data  (fastest, least safe)
READ COMMITTED   → only reads committed data         (most common)
REPEATABLE READ  → same read always returns same val (MySQL default)
SERIALIZABLE     → transactions fully isolated       (slowest, safest)
```

### Durability
```
Power cut after commit? Data is SAFE.
OS uses WAL (Write Ahead Log) → writes to disk before confirming commit.
"Committed = Permanent. Forever."
```

---

## 2. Normalization

> Normalization = Organizing tables to reduce redundancy and prevent anomalies.

### Why Normalization?
Storing everything in ONE big table causes:
- **Update Anomaly**: Change one value → must update many rows
- **Delete Anomaly**: Delete a row → lose unrelated important data
- **Insert Anomaly**: Can't add data without other unrelated data

### The 3 Normal Forms

```
1NF = Lay the FOUNDATION     (atomic values)
2NF = Build the WALLS        (fix partial dependencies)
3NF = Put on the ROOF        (fix transitive dependencies)

Each level BUILDS on the previous one.
```

---

### 1NF — First Normal Form
**Rule**: Every cell must have ONE single atomic value. No repeating groups.

```
❌ BAD (not 1NF):
StudID | Courses
  1    | Java, Python    ← TWO values in one cell!

✅ GOOD (1NF):
StudID | Course
  1    | Java
  1    | Python          ← separate rows
```

---

### 2NF — Second Normal Form
**Rule**: 1NF + Every non-key column must depend on the WHOLE composite key (not just part of it).

> Only applies when Primary Key = 2 or more columns (Composite Key)

```
Table: Enrollment(StudID, Course, StudName, TeacherName)
Primary Key = (StudID + Course)  ← Composite Key

StudName    → needs only StudID  (not Course) = PARTIAL DEPENDENCY ❌
TeacherName → needs only Course  (not StudID) = PARTIAL DEPENDENCY ❌

Fix — Split into 3 tables:
  Students(StudID, StudName)          ✅
  Courses(Course, TeacherName)        ✅
  Enrollments(StudID, Course)         ✅
```

**Definition:**
```
PARTIAL DEPENDENCY = non-key column depends on only PART of composite key
                   = 2NF VIOLATION
```

---

### 3NF — Third Normal Form
**Rule**: 2NF + Every non-key column must depend DIRECTLY on the primary key (no middlemen!).

```
Table: Courses(CourseID, TeacherID, TeacherName)
Primary Key = CourseID

CourseID → TeacherID → TeacherName
(Key)      (non-key)    (non-key)

TeacherName should depend DIRECTLY on CourseID ✅
But it goes through TeacherID (non-key middleman) ❌
= TRANSITIVE DEPENDENCY = 3NF VIOLATION

Fix — Split:
  Courses(CourseID, TeacherID)          ✅
  Teachers(TeacherID, TeacherName)      ✅
```

**Definition:**
```
TRANSITIVE DEPENDENCY = non-key column depends on ANOTHER non-key column
                        instead of directly on the primary key
                      = 3NF VIOLATION
```

---

### Quick Reference — Normalization

```
┌────────────────────────────────────────────────────────────────┐
│ 1NF: Every cell = ONE value only                               │
│                                                                │
│ 2NF: Every non-key column must depend on the WHOLE             │
│      composite key — not just PART of it                       │
│      (Only applies when primary key has 2+ columns)            │
│                                                                │
│ 3NF: Every non-key column must depend DIRECTLY on the          │
│      primary key — not through another non-key column          │
│      (No middlemen allowed!)                                   │
└────────────────────────────────────────────────────────────────┘

Memory Trick:
  2NF = problem with the KEY itself (partial)
  3NF = problem between NON-KEY columns (transitive)

Boss Analogy:
  2NF → Every column reports to the WHOLE boss (not half the boss)
  3NF → Every column reports DIRECTLY to the boss (no middleman)
```

---

## 🎯 Top Interview Questions & Answers

**Q: What are ACID properties?**
> Atomicity (all or nothing), Consistency (valid state always), Isolation (no interference), Durability (committed = permanent).

**Q: What is Atomicity?**
> A transaction either completes fully or rolls back entirely. No partial updates.

**Q: What is the difference between 2NF and 3NF?**
> 2NF removes partial dependencies (non-key depends on part of composite key).
> 3NF removes transitive dependencies (non-key depends on another non-key instead of directly on primary key).

**Q: What is a transitive dependency?**
> When a non-key column does not directly depend on the primary key, but instead depends on another non-key column acting as a middleman. e.g., CourseID → TeacherID → TeacherName.

**Q: Why do we normalize?**
> To eliminate data redundancy and prevent update, delete, and insert anomalies.
