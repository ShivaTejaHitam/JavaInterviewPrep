Think of SQL **Window Functions** as a way to look at a specific "slice" of your data without losing sight of the whole. Unlike standard aggregate functions (`SUM`, `AVG`), which collapse your rows into a single result, window functions allow each row to retain its individual identity while still performing calculations across a set of related rows.

The magic happens in the `OVER()` clause, which defines the "window" of data the function sees.

---

## 1. Aggregate Window Functions
These perform calculations like sum or average over a defined group of rows (the partition) while keeping the detail rows intact.

### `SUM()`, `AVG()`, `COUNT()`
**Example:** Calculate the total department salary next to each employee's individual salary.

| ID | Name | Dept | Salary |
| :--- | :--- | :--- | :--- |
| 1 | Alice | IT | 5000 |
| 2 | Bob | IT | 6000 |
| 3 | Charlie | HR | 4500 |

**Query:**
```sql
SELECT Name, Dept, Salary,
       SUM(Salary) OVER(PARTITION BY Dept) as DeptTotal
FROM Employees;
```

**Output:**
| Name | Dept | Salary | DeptTotal |
| :--- | :--- | :--- | :--- |
| Alice | IT | 5000 | 11000 |
| Bob | IT | 11000 | 11000 |
| Charlie | HR | 4500 | 4500 |

---

## 2. Ranking Window Functions
These assign a number to each row based on its ordering within the partition.

### `ROW_NUMBER()`
Assigns a unique, sequential integer to rows. If two rows have the same value, they still get different numbers.

### `RANK()`
Assigns a rank to each row. If there is a tie, it skips the next ranking numbers (e.g., 1, 2, 2, 4).

### `DENSE_RANK()`
Similar to `RANK()`, but it does **not** skip numbers after a tie (e.g., 1, 2, 2, 3).



**Example Query:**
```sql
SELECT Name, Salary,
       ROW_NUMBER() OVER(ORDER BY Salary DESC) as RowNum,
       RANK() OVER(ORDER BY Salary DESC) as RankNum,
       DENSE_RANK() OVER(ORDER BY Salary DESC) as DenseRankNum
FROM Employees;
```

**Output:**
| Name | Salary | RowNum | RankNum | DenseRankNum |
| :--- | :--- | :--- | :--- | :--- |
| Bob | 6000 | 1 | 1 | 1 |
| Alice | 5000 | 2 | 2 | 2 |
| Dave | 5000 | 3 | 2 | 2 |
| Charlie | 4500 | 4 | 4 | 3 |

---

## 3. Value (Offset) Window Functions
These allow you to reach forward or backward to compare values from different rows without using a complex `JOIN`.

### `LAG()`
Accesses data from a previous row. Great for calculating month-over-month growth.

### `LEAD()`
Accesses data from a subsequent row.

**Example:** Compare today's sales to yesterday's.

**Query:**
```sql
SELECT Day, Sales,
       LAG(Sales) OVER(ORDER BY Day) as PrevDaySales
FROM DailySales;
```

**Output:**
| Day | Sales | PrevDaySales |
| :--- | :--- | :--- |
| 1 | 100 | NULL |
| 2 | 150 | 100 |
| 3 | 120 | 150 |

---

## 4. Distribution Window Functions
These help identify where a row sits within the statistical distribution of the dataset.

### `NTILE(n)`
Divides the rows into `n` roughly equal buckets. If you want to find the "top 25%" of earners, you would use `NTILE(4)`.

**Example:** Divide employees into two performance quartiles.

**Query:**
```sql
SELECT Name, Salary,
       NTILE(2) OVER(ORDER BY Salary DESC) as Quartile
FROM Employees;
```

**Output:**
| Name | Salary | Quartile |
| :--- | :--- | :--- |
| Bob | 6000 | 1 |
| Alice | 5000 | 1 |
| Dave | 4000 | 2 |
| Charlie | 3000 | 2 |

---

### Summary Table: When to use what?

| Function Category | Common Functions | Best Use Case |
| :--- | :--- | :--- |
| **Aggregate** | `SUM`, `AVG`, `COUNT` | Running totals or comparing row vs group average. |
| **Ranking** | `RANK`, `DENSE_RANK` | Creating leaderboards or finding top N items. |
| **Offset** | `LAG`, `LEAD` | Year-over-year or Delta (change) calculations. |
| **Statistical** | `NTILE`, `PERCENT_RANK` | Percentiles and bucketing data. |
