# 📊 DBMS Indexing & Query Optimization — Interview Notes

> High-yield topic for Microsoft, Amazon, Google, and backend-heavy startups.

---

## 1. What is a Database Index?

An index is a **separate, sorted data structure** stored alongside the table that tells the database exactly where a row lives on disk — without scanning the entire table.

**Real-World Analogy:**
```
Without Index → Read a 1000-page textbook page by page to find "Semaphore"
With Index    → Flip to the alphabetical index at the back → "Semaphore → Page 742"
```

---

## 2. Full Table Scan vs Index Scan

| Situation | Operation | Time Complexity | Performance |
| :--- | :--- | :--- | :--- |
| No index on searched column | **Full Table Scan** | O(N) | Reads every row |
| B+ Tree index exists | **Index Scan (B+ Tree)** | O(log N) | Tree traversal |
| Hash index exists | **Index Scan (Hash)** | O(1) | Direct lookup |

**Interview Answer for "No Index":**
> "Without an index, the database performs a Full Table Scan — it reads every single row from disk one by one, which is O(N) and extremely slow at scale."

---

## 3. B+ Tree Index (Default in MySQL InnoDB, PostgreSQL)

```
                        [  50  ]
                       /         \
             [ 20 | 35 ]         [ 70 | 85 ]
            /    |    \          /    |    \
        [10,20]->[35,40]->[50,70]->[85,90]
        (Leaf nodes are a DOUBLY LINKED LIST -> enables range queries!)
```

**Why B+ Tree over a regular BST?**
- Wide branching factor: tree height stays tiny (3-4 levels for millions of rows) = only 3-4 disk reads!
- Leaf nodes are linked: supports BETWEEN, >, <, ORDER BY efficiently!

---

## 4. Hash Index

| Feature | Detail |
| :--- | :--- |
| Time Complexity | O(1) |
| Best For | Exact match: WHERE id = 12345 |
| Supports Range? | NO — hashing destroys ordering |
| Analogy | Java HashMap — fast lookup but no ordering |

---

## 5. B+ Tree vs Hash Index — Side by Side

| | **B+ Tree** | **Hash Index** |
| :--- | :--- | :--- |
| Time | O(log N) | O(1) |
| Range Queries | YES | NO |
| ORDER BY / GROUP BY | YES | NO |
| Exact Match | YES | YES |
| Default in MySQL | YES (InnoDB) | Only in MEMORY tables |

```sql
-- B+ Tree wins here (range query):
WHERE salary BETWEEN 50000 AND 90000

-- Hash Index wins here (exact match, O(1)):
WHERE customer_id = 98452

-- Hash Index FAILS here (no ordering concept):
ORDER BY created_at
```

---

## 6. Clustered vs Non-Clustered Index

| | **Clustered Index** | **Non-Clustered (Secondary) Index** |
| :--- | :--- | :--- |
| What it is | Defines physical storage order of rows on disk | Separate lookup structure pointing to data rows |
| Count per table | ONE only | Multiple allowed |
| Auto-created on | PRIMARY KEY | Manually on any column |
| Analogy | Dictionary (words physically sorted A-Z) | Index at back of a book (points to page numbers) |

---

## 7. When to Create an Index — Golden Rules

```
CREATE an index when:
  - Column is frequently used in WHERE clause
  - Column is used in JOIN conditions (foreign keys)
  - Column is used in ORDER BY / GROUP BY
  - Column has HIGH CARDINALITY (many unique values: email, customer_id)

AVOID an index when:
  - Table has very few rows (< ~1000)
  - Column has LOW CARDINALITY (e.g., gender: only 'M'/'F' — 2 values!)
  - Table has very heavy INSERT/UPDATE load (each write must update all indexes)
```

---

## 8. EXPLAIN — The Query Optimizer's X-Ray

```sql
EXPLAIN SELECT * FROM Orders WHERE customer_id = 98452;
```

| Column | What to Look For |
| :--- | :--- |
| type | ref / const = index used. ALL = Full Table Scan |
| key | Shows WHICH index was used (NULL = no index!) |
| rows | Estimated rows scanned (smaller = better!) |

```
No Index:   type: ALL,  key: NULL,              rows: 50,000,000
With Index: type: ref,  key: idx_customer_id,   rows: 3
```

---

## Top Interview Q&A

**Q: What is a Full Table Scan?**
> When the database reads every row from disk to find matching records, because no index exists. O(N).

**Q: Why does MySQL use B+ Tree and not BST for indexes?**
> B+ Tree has a high branching factor (fewer disk reads) and leaf nodes are linked for range queries. BST can degrade to O(N) height.

**Q: Can you have multiple clustered indexes?**
> No. Only ONE clustered index per table — rows can only be physically sorted one way. Auto-created on PRIMARY KEY.

**Q: Should you index a gender column (M/F)?**
> No. Very low cardinality (2 unique values) — the database would still scan ~50% of the table. Index overhead not worth it.

**Q: B+ Tree vs Hash Index?**
> B+ Tree: O(log N), supports range queries and ORDER BY.
> Hash Index: O(1), exact match only — cannot support range queries or ORDER BY.
