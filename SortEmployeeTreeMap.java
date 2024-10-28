import java.util.*;
import java.util.stream.Collectors;  

class SortEmployeeTreeMap
{
    static TreeMap<Employee,Integer> getMap(TreeMap<Employee,Integer> employeelist)
    {
        
        employeelist.put(new Employee("Jo"),4);
        employeelist.put(new Employee("Shiva"),6);
        employeelist.put(new Employee("Sandeep"),10);
        employeelist.put(new Employee("Pranay"),5);
        
        return employeelist;
    }

    public static void main(String args[])
    {
        /* Without using lambdas */
        TreeMap<Employee,Integer> employeelist = new TreeMap<Employee,Integer>(new ReverseEmployeeSorter());
        
        employeelist = getMap(employeelist);

        for(Map.Entry<Employee,Integer> entry : employeelist.entrySet())
        {
            System.out.println("Employee Name :" + entry.getKey().getName() + " " + " Salary : " + entry.getValue() + " LPA");
        }
        
        /* using lambdas sort employees by name */
        employeelist = new TreeMap<Employee,Integer>((Employee e1,Employee e2) -> e2.getName().compareTo(e1.getName()));        
        employeelist = getMap(employeelist);
        employeelist.forEach((k,v)->System.out.println("Employee Name " + k.getName() + " Salary " + v));

        /* Using lambdas sort employees by salaries  */
        employeelist = getMap(employeelist);

        employeelist = employeelist.entrySet().stream.sorted(Map.Entry.ComparingByValue().reversed()).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
        
        System.out.println(employeelist);
        
    }
}
