
1. **List all employees whose salary is higher than the salary of their manager.**

Select * from Employee E where E.salary > (Select salary from Employee M where M.id = E.managerId);

2. **For each department, list the employee with the second-highest salary.**
  SELECT 
    empname, 
    dept_name, 
    salary
FROM (
    SELECT 
        e.empname, 
        d.dept_name, 
        e.salary,
        DENSE_RANK() OVER (PARTITION BY d.dept_name ORDER BY e.salary DESC) as salary_rank
    FROM employee e
    JOIN department d ON e.dept_id = d.dept_id
) ranked_salaries
WHERE salary_rank = 2;

3. **Find the department with the highest average salary.**



4. **Find the employees who earn more than the average salary of their department.**

SELECT 
    e.employee_id,
    e.employee_name,
    e.department_id,
    e.salary
FROM 
    employees e
WHERE 
    e.salary > (
        SELECT 
            AVG(salary)
        FROM 
            employees
        WHERE 
            department_id = e.department_id
    );

5. Departments with more employees than average department size



5. **List all employees along with their manager’s name. If the employee does not have a manager, display 'No Manager'.**



6. **For each department, list the name of the employee with the highest salary.**
  
8. Write a query to find the **second highest salary** from an Employee table.

9. Write a query to find the **Nth highest salary** from a table.

10. Find employees who earn **more than the average salary**.

11. Write a query to **remove duplicate rows** from a table.

12. Write a query to **find duplicate records** in a table.

14. Write a query to display **the current date** in SQL.

17. Write a query to **count number of employees in each department**.

19. Write a query to **find employees working in the same department**.

21. Write a query to **list departments with no employees**.
  SELECT d.name
FROM departments d
LEFT JOIN employees e
    ON d.id = e.dept_id
WHERE e.dept_id IS NULL;

22. Write a query using **SELF JOIN** to find employee-manager relationships.

22. Find no of reportees for each employee. 

    SELECT 
    e.emp_id,
    e.emp_name,
    COUNT(r.emp_id) AS reportee_count
FROM Employee e
LEFT JOIN Employee r
ON e.emp_id = r.manager_id
GROUP BY e.emp_id, e.emp_name;

23. Write a query to **find department with highest number of employees**.

24. Write a query to **calculate total salary department-wise**.

25. Write a query to **find departments having more than 5 employees**.

26. Write a query to **find the maximum salary in each department**.

27. Write a query to **find departments where average salary is greater than 50,000**.

28 Write a query to **find top 3 salaries in each department**.

29. Write a query using **ROW_NUMBER()** to assign row numbers to employees.

30. Write a query to **rank employees based on salary**.

31. Write a query to **find employees with duplicate salaries**.

32. Write a query to **find cumulative salary** of employees.

33. Write a query to **swap values of two columns** in a table.

34. Write a query to **find missing numbers** in a sequence.

35. Write a query to **pivot rows into columns**.

36. Write a query to **unpivot columns into rows**.

37. Write a query to **find consecutive records** (e.g., consecutive login days).
