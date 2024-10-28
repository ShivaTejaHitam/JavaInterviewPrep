
import java.util.function.*;
import java.util.stream.*;
import java.util.*;



class Employee implements Comparable<Employee>{
    private String name;
    private int age;

    Employee(String name,int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    @Override
    public int compareTo(Employee other){
        return this.name.compareTo(other.name);
    }

}


class EmployeeComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        return e1.getAge() - e2.getAge();
    }
}

class Interview {
    public static void main(String[] args){
        List<Employee> employees = new ArrayList<>();
        
        employees.add(new Employee("Rashmika", 24));
        employees.add(new Employee("Samantha", 27));
        employees.add(new Employee("Anushka", 42));

        Collections.sort(employees, (e1,e2) -> e1.getAge() - e2.getAge());

        employees.forEach(employee -> System.out.println(employee.getName() + " " + employee.getAge()));

    }
}
