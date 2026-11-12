# 🔗 SQL Joins, Subqueries & Aggregations — Interview Notes

> Complete reference for technical interviews at Google, Microsoft, Amazon, and top startups.

---

## 1. The 4 Core Joins

```
Table A: Left Table (e.g. Employees)
Table B: Right Table (e.g. Departments)
Common Column: dept_id
```

| Join Type | Keyword | What it Returns | Missing Rows |
| :--- | :--- | :--- | :--- |
| **INNER JOIN** | `INNER JOIN ... ON` | Only rows matching in **both** tables | Dropped completely |
| **LEFT JOIN** | `LEFT JOIN ... ON` | **All** Left rows + matching Right rows | `NULL` on Right |
| **RIGHT JOIN** | `RIGHT JOIN ... ON` | **All** Right rows + matching Left rows | `NULL` on Left |
| **FULL JOIN** | `FULL OUTER JOIN ... ON`| **All** rows from **both** tables | `NULL` wherever unmatched |

### Visual Memory Anchor:
```
      LEFT JOIN                 INNER JOIN                RIGHT JOIN
   ┌─────────┬───┐             ┌───┬───┬───┐             ┌───┬─────────┐
   │ All A   │ A∩B│             │   │A∩B│   │             │A∩B│ All B   │
   └─────────┴───┘             └───┴───┴───┘             └───┴─────────┘
```

---

## 2. The 4-Step Mental Blueprint to Write Any Query

Never start writing directly from `SELECT`. Follow the execution order:

1. **Step 1: FROM & JOIN** — Where does the data live? Which tables connect, and how?
2. **Step 2: WHERE** — What filters/conditions apply to individual rows?
3. **Step 3: GROUP BY & HAVING** — Do we need aggregates (`COUNT`, `SUM`, `AVG`, `MAX`, `MIN`) per category?
4. **Step 4: SELECT** — What final columns to display?

---

## 3. Top Interview Patterns

### Pattern A: "Find entities with ZERO associated records"
> e.g. "Find departments with 0 employees" or "Customers with no orders".

**Method 1: LEFT JOIN with IS NULL**
```sql
SELECT d.dept_name
FROM Departments d
LEFT JOIN Employees e ON d.dept_id = e.dept_id
WHERE e.emp_id IS NULL;
```

**Method 2: Subquery with NOT IN**
```sql
SELECT dept_name
FROM Departments
WHERE dept_id NOT IN (
    SELECT DISTINCT dept_id FROM Employees WHERE dept_id IS NOT NULL
);
```

---

### Pattern B: "Second Highest Salary" (Very Common FAANG Question!)

```sql
SELECT MAX(salary) AS second_highest_salary
FROM Employees
WHERE salary < (
    SELECT MAX(salary) FROM Employees
);
```

---

### Pattern C: Aggregation with Group By & Count

```sql
-- Count employees per department (including departments with 0 employees)
SELECT d.dept_name, COUNT(e.emp_id) AS total_employees
FROM Departments d
LEFT JOIN Employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_name;
```

> **Key Rule**: Use `COUNT(e.emp_id)` instead of `COUNT(*)` with `LEFT JOIN` so that empty groups show `0` instead of `1`.

---

## 4. WHERE vs HAVING

| Clause | Operates On | Used With Aggregates? | Example |
| :--- | :--- | :--- | :--- |
| **WHERE** | Individual rows *before* grouping | ❌ No | `WHERE salary > 50000` |
| **HAVING** | Grouped rows *after* grouping | ✅ Yes | `HAVING COUNT(emp_id) >= 5` |
