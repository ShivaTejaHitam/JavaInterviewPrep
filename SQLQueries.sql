CREATE TABLE Employee (
    EmployeeID INT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    DepartmentID INT,
    Salary DECIMAL(10,2),
    HireDate DATE,
    ManagerID INT
);


CREATE TABLE Department (
    DepartmentID INT PRIMARY KEY,
    DepartmentName VARCHAR(100),
    Location VARCHAR(100)
);


CREATE TABLE Project (
    ProjectID INT PRIMARY KEY,
    ProjectName VARCHAR(100),
    DepartmentID INT,
    StartDate DATE,
    EndDate DATE
);

CREATE TABLE EmployeeProject (
    EmployeeID INT,
    ProjectID INT,
    Role VARCHAR(50),
    AssignmentDate DATE,
    PRIMARY KEY (EmployeeID, ProjectID)
);


CREATE TABLE Timesheet (
    TimesheetID INT PRIMARY KEY,
    EmployeeID INT,
    ProjectID INT,
    WorkDate DATE,
    HoursWorked DECIMAL(5,2)
);





Here are the 40 SQL interview questions shuffled:

1. **List all employees whose salary is higher than the salary of their manager.**

Select * from Employee E where E.salary > (Select salary from Employee M where M.id = E.managerId);

2. **Find employees who have never worked on a project that ended within the last 6 months.**

3. **Find the names of employees who work on more projects than their manager.**

4. **Write a query to find employees who work in departments located in 'Hyderabad' but are assigned to projects managed by departments located in 'Mumbai'.**

5. **For each department, list the employee with the second-highest salary.**

6. **List the names of employees whose salary is greater than the average salary of all employees who work on the same project as them.**

7. **Find the department with the highest average salary.**


9. **Find employees who work in the same department as the employee with the highest salary.**

10. **Find the employees who earn more than the average salary of their department.**

11. **Write a query to list employees who are working on projects that have exceeded their planned end date.**

12. **List the departments where the total salary of employees is higher than the total salary of employees in the 'HR' department.**

13. **Find the number of projects that have at least one employee assigned to them.**

14. **For each department, find the average salary of employees who have been hired in the last 2 years.**

15. **List the employees who have worked on the same projects as at least one of their direct subordinates.**

16. **Find the names of employees who work on every project in the company.**

17. **Find employees who have never worked on a project.**

20. **Write a query to display the employee ID and the number of projects each employee is currently assigned to.**

21. **List the names of employees who share the same last name as their manager.**

22. **Find the names of employees who work on the same project as the employee with the highest salary.**

23. **Find employees who are assigned to more than one project but all the projects they work on are in the same department.**

24. **List all employees who have worked on all projects in their department.**

25. **List all employees along with their manager’s name. If the employee does not have a manager, display 'No Manager'.**

26. **Find the names of employees who do not manage anyone but work in the same department as an employee who manages others.**

27. **Find employees who work on projects that have at least one employee with a higher salary than them.**

28. **For each department, list the name of the employee with the highest salary.**

29. **Find employees who work in the same department as the employee with the highest salary.**

30. **List the names of employees who have worked on more than 10 projects.**

31. **For each employee, calculate the total number of hours worked in the current year.**

32. **Find employees who are assigned to more than one project but all the projects they work on are in the same department.**

33. **Find the department with the highest average salary.**

34. **Find the names of employees who do not manage anyone but work in the same department as an employee who manages others.**

36. **List the employees who have worked on the same projects as at least one of their direct subordinates.**

37. **Find employees who work on more than one project.**

38. **Find the names of employees who work on more projects than their manager.**

39. **For each project, find the total number of hours worked by employees, and display projects that have more than 1000 hours worked.**

40. **Write a query to display the employee name, project name, and total hours worked on each project, sorted by the employee name.**

Feel free to dive into these questions for a comprehensive SQL practice session!

































