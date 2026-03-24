				Java Interview Preparation

classes have only two access specifiers public and default(nothing)

Reentrant locks do not allow multiple threads to access the critical section of different objects.
Synchronized is a monitor lock which aquires lock on critical section of single object. Two different threads can access critical section of synchronized method of different objects simultaneously.


what are new features of java 8 ?
lambda expressions, functional interfaces , Optional,  CompletableFuture , default methods ,  new Date and Time API using package java.time




what is yield method ?

Explain garbage collection.

If we return in try block, does finally execute ? yes 

How string immutablility provides thread safety ?

Can we extend interfaces from another interface? 
Yes we can use extend keyword similar to classes

what is Arrays.asList() ?
This method is used to create a fixed size list from an array or elements.













9.	Explain Functional Interfaces in java
In java , functional interface is an interface that contains only one abstract method. Functional interfaces are key concept in Java’s support for functional programming, introduced in Java 8. They enable the use of lambda expressions and method references to represent instances of single method interfaces more concisely and effectively.
Here are key points about functional interfaces in Java: 
1.Single Abstract Method(SAM) Interface : A functional Interface must contain exactly one abstract method.It can contain multiple default or static methods, but only one abstract method.This single method represents the behaviour encapsulated by the functional interface.
2.@FunctionalInterface Annotation : 
While it's not strictly required, it's a good practice to annotate functional interfaces with @FunctionalInterface annotation. This annotation ensures that the interface is intended to be used as a functional interface, and the compiler will produce an error if more than one abstract method is added to it.
3.Lambda Expressions and Method References: Functional interfaces are primarily used to represent behaviors as lambda expressions or method references. Instead of creating separate implementations of the interface for each behavior, you can pass lambda expressions or method references that define the behavior directly, making the code more concise and expressive.
4. Built-in Functional Interfaces: Java provides several built-in functional interfaces in the java.util.function package, such as Predicate, Function, Consumer, Supplier, etc. These interfaces cover common functional use cases and can be used directly or extended to create custom functional interfaces.
5.Usage in Streams and APIs : 
Functional Interfaces are used extensively in Java Stream Api and other APIs introduced in Java 8 and later. They allow for a more functional style of programming, where operations like mapping, filtering, and reducing can be performed concisely using lambda expressions.

Example : 
@FunctionalInterface
interface Printer{
	   void printMessage(String message);
}

class Main{
	   public static void main(String[] args){
        Printer printer = (String msg) -> System.out.println(msg);
	      printer.printMessage(“Hello”);
  }
}

10.	Explain Inbuilt Functional Interfaces in Java .
In java, the java.util.function package provides a set of  built-in functional interfaces that represent common function types. These interfaces are widely used in stream API and many other cases of functional programming.

Here are few built-in functional interfaces in java :

•	Predicate<T> : Represents a predicate(Boolean valued function) of one argument. It is commonly used to filter elements in collections or streams
Example : 
Predicate<Integer> evenChecker = n -> n % 2 == 0;
System.out.println(evenChecker.test(10));
•	Function<T, R>: Represents a function that accepts one argument and produces a result. It's commonly used for mapping or transforming elements.
Example : 
Function<String,Integer> findStringLength = str -> str.length();
System.out.println(findStringLength.apply(“Shiva”));
•	Consumer<T> : Represents an operation that accepts a single input argument and returns no result. It's often used for consuming or processing elements.
Example: 
Consumer<String> welcomeGreeter = str -> System.out.println(“Welcome “+str);
System.out.println(welcomeGreeter.accept(“Shiva Teja”));
•	Supplier<T>: Represents a supplier of results. It has no input arguments and produces a result. It's commonly used for lazy initialization or generating values.
Example:
Supplier<Double> randomDouble = () -> Math.random();
System.out.println(randomDouble.get());
•	UnaryOperator<T>: Represents an operation on a single operand that produces a result of the same type as its operand. It's often used for unary operations.
Example:
UnaryOperator<Integer> square = n -> n *n;
System.out.prinltn(square.apply(2));
•	BinaryOperator<T> : Represents an operation on two operands of same type and produces a result of same type. 
Example
BinaryOperator<Integer,Integer> multipler = (n,m) -> n *m ;
System.out.println(multipler.apply(2,4));

11.Explain Stream API in java 8
In java, the stream API was introduced in java 8 as new abstraction that allows developers to process collections of data in functional and declarative manner. It provides a set of methods to perform operations on sequences of elements, such as filtering, mapping, sorting, and reducing. Streams allow for concise and expressive code, promoting readability and maintainability.
Here's an overview of some key concepts and functionalities of the Stream API:
1.Stream Creation : Stream can be created from various data sources such as collections,arrays, or even I/O operations.They can also be generated using static factory methods provided by stream interface.
List<Integer> numbers = Arrays.asList(1,2,3,4,5);
Stream<Integer> stream = numbers.stream();
2.Intermediate Operations :
These operations are applied to stream and return another stream. Intermediate operations include filtering, mapping, sorting etc. These operations are lazy, meaning they do not perform any processing until the terminal operation is invoked.
Stream<Integer> filteredStream = numbers.stream().filter(n -> n % 2==0);
Stream<Integer> mappedStream = numbers.stream().map(n-> n+1);
3.Terminal Operations : 
These operations consume the stream and produce a result. Terminal operations include reduction operations like reduce, collect, and forEach, as well as operations to find elements like findFirst, findAny, and count.
int sum = numbers.stream().reduce((a,b)-> a+b);
List<Integer> evenNumbers = numbers.stream().filter(n -> n%2==0).collect(Collectors.toList());

4.Parallel Streams : 
Streams can be processed sequentially or parallel.Parallel streams automatically divide the workload across multiple threads, which can lead to improved performance for large datasets.
Example: 
List<Integer> evenNumbers = numbers.parallelStream().filter(n -> n % 2 ==0).collect(Collectors.toList());

5.Lazy Evaluation : 
Intermediate operations are lazily evaluated, meaning they are only executed only when a terminal operation is invoked.This allows for efficient processing of large datasets because unnecessary computations are avoided.
List<String> strings = Arrays.asList(“abc”,”efg”,”hij”);
Stream<String> filteredStrings = strings.stream().filter(s -> { 
System.out.println(“Filtering” +s);
return s.contains(“a”);
});

filteredStrings.forEach(System.out::println); // without this statement nothing is printed


12.Explain difference between map and flatMap.
map and flatMap are higher order functions used in java.

map : 
The map function takes each element of a collection and applies a function to it, returning a new collection of same size where each element is the result of applying the function to the corresponding element in the original collection.

flatMap :

•	The flatMap function is similar to map, but it’s used when mapping function returns a collection itself.It then flattens these nested collections in to a single collection.
•	It first applies the transformation function to each element of the collection, resulting in a collection of collections.Then it flattens this collection of collections in to a single collection.
•	flatMap is often used when you want to perform a one-to-many transformation and then flatten the result.
public class Main {
    public static void main(String[] args) {
        List<List<Integer>> nestedList = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );

        // Using flatMap to flatten the nested lists
        List<Integer> flattenedList = nestedList.stream()
                                                .flatMap(List::stream)
                                                .collect(Collectors.toList());

        System.out.println(flattenedList); // Output: [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }
}

In this example : 
•	We have a nested list of integers (nestedList) , where each element is a list of integers.
•	We use the flatMap operation to transform each nested list in to a stream of its elements.
•	The resulting stream is a single stream containing all the elements from the nested lists.
•	Finally, we collect the elements of the flattened stream in to a list.



13.Explain method reference and constructor reference in java.

14.Explain Comparator and Comparable in java
In Java, ‘Comparator’ and ‘Comparable’ are interfaces used for sorting and ordering objects in collections.They both serve the similar purpose but differ in implementation and usage scenarios.
1.Comparable
•	Comparable is an interface that allows objects to be compared to each other for the purpose of natural ordering.
•	It is used to define default sorting behaviour of objects of class.
•	The compareTo method is implemented in the class whose objects need to be sorted.
•	This interface is typically implemented by the class of objects that need to be sorted.
import java.util.*;

class Student implements Comparable<Student> {
    private int id;
    private String name;

    // Constructor, getters, setters...

    @Override
    public int compareTo(Student otherStudent) {
        // Compare based on student names
        return this.name.compareTo(otherStudent.getName());
    }
}

public class ComparableExample {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(101, "Alice"));
        students.add(new Student(102, "Bob"));
        students.add(new Student(103, "Charlie"));

        Collections.sort(students);

        for (Student student : students) {
            System.out.println(student.getName());
        }
    }
}
2.Comparator
•	Comparator is an interface that provides a way to sort objects based on different criteria.
•	It allows the definition of custom comparison logic separate from the class whose objects are being sorted.
•	It is useful when you want to define multiple sorting criteria or when you cannot modify the class of objects being sorted.
•	The  compare method is used to compare two objects.
•	
Usage:
import java.util.*;

class Student {
    private int id;
    private String name;

    // Constructor, getters, setters...

    // Static inner class implementing Comparator
    static class NameComparator implements Comparator<Student> {
        @Override
        public int compare(Student student1, Student student2) {
            // Compare based on student names
            return student1.getName().compare(student2.getName());
        }
    }
}

public class ComparatorExample {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(101, "Alice"));
        students.add(new Student(102, "Bob"));
        students.add(new Student(103, "Charlie"));

        // Create a Comparator object
        Student.NameComparator nameComparator = new Student.NameComparator();

        // Sort the list using the Comparator
        students.sort(nameComparator);

        for (Student student : students) {
            System.out.println(student.getName());
        }
    }
}
•	For Comparable, sorting is done using Arrays.sort() or Collections.sort(), where the objects themselves define the sorting logic.
•	For Comparator, sorting can be done using overloaded versions of sort() method or using classes like TreeSet and TreeMap, where the sorting logic is provided externally through a Comparator object.

15.Explain volatile and transient keyword in java

Volatile: 
•	The volatile keyword is used to indicate that a variable’s value may be modified by multiple threads that are executing concurrently.When a variable is declared as volatile, it guarantees visibility of changes made to the variable across threads.
•	Essentially , when a variable is declared as volatile, any read or write operation on that variable will be performed directly on the main memory rather than in thread’s cache.This ensures that changes made by one thread to a volatile variable are immediately visible to other threads.
•	However volatile does not provide atomicity or synchronization . It only ensures visibility. 
Code Snippet: 
Suppose we have a boolean flag variable shared among multiple threads, and one thread modifies the flag while other threads read it. We want to ensure that changes to the flag made by one thread are immediately visible to other threads.
 class SharedResource {
    private volatile boolean flag = false;

    public void setFlag(boolean value) {
        flag = value;
    }

    public boolean getFlag() {
        return flag;
    }
}

In this example, the volatile keyword is used for the flag variable. Now, any changes made to flag by one thread will be immediately visible to other threads.
public class Main {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        // Thread 1 - sets the flag
        new Thread(() -> {
            sharedResource.setFlag(true);
        }).start();

        // Thread 2 - reads the flag
        new Thread(() -> {
            System.out.println("Flag value: " + sharedResource.getFlag());
        }).start();
    }
}

Transient: 
•	The transient keyword is used to indicate that a variable should not be serialized when the object is serialized. In Java, serialization is the process of converting an object into a byte stream, which can then be saved to a file or sent over a network.
•	When an object is serialized , all of its non-transient fields are written to output stream.However,if field is marked as transient , its value will not be persisted during serialization. When the object is deserialized,the transient variable will be initialized to its default value(eg: null for object references and , 0 for primitive values).
•	  The transient keyword is often used for fields that hold temporary or derived data, or for fields that should not be serialized due to security or performance reasons.
•	Code snippet 
import java.io.Serializable;
import java.io.*;


public class Person implements Serializable {
    private String name;
    private transient String ssn;

    public Person(String name, String ssn) {
        this.name = name;
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public String getSsn() {
        return ssn;
    }
}


public class SerializationExample {
    public static void main(String[] args) {
        Person person = new Person("John Doe", "123-45-6789");

        // Serialize the object
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.ser"))) {
            oos.writeObject(person);
            System.out.println("Object has been serialized");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialize the object
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.ser"))) {
            Person deserializedPerson = (Person) ois.readObject();
            System.out.println("Deserialized Person: " + deserializedPerson.getName());
            System.out.println("Deserialized SSN: " + deserializedPerson.getSsn());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

16) Why String is immutable ? 
  String is often used as database passwords and usernames etc. So it needs to be immutable to prevent modification .
  It saves lot of space in string pool of heap by avoiding duplicate creation
  It can be safely used in multithreaded environments.

18) What are JAR and WAR files ? what are difference between JAR and WAR ?
JAR - It is a compressed archive of .class files . They are used to distribute java libraries, java application
WAR - Compressed archive of a web application, They are used to distribure web application

20) What are features of Java 17 ? sealed class 
21) Can we override static methods in Java ? No. 
22) Why String is immutable ? 
  String is often used as database passwords and usernames etc. So it needs to be immutable to prevent modification .
  It saves lot of space in string pool of heap by avoiding duplicate creation
  It can be safely used in multithreaded environments.
23) What is the use of default methods in interfaces ? what is the signature of default methods.
24) Does abstract class have constructor ? yes







32) What is the difference between Stream.of and Arrays.stream in java ?
	Stream.of() creates entire object as stream if array is passed
	Arrays.stream() treats elements of array as individual elements of stream

38) what are different ways to achieve synchronization in java
39) Explain how abstract class is different from interface. When to use what ?
interfaces :
	- meant to define a contract that classes can implement. 
	- multiple inheritence
	- no state . since there are no instance variables. 
	- no constructor since there is no state to initialize.
abstract class :
	- They provide base class with some behaviour. They are used to share code between closely related classes
	- have state
	- has constructor which can be called when  subclass is initialized.
Java Versions--

Are you familiar with the latest java release/java 11. ?

What is the current version of java you use?



6. What are the new features of java 8 you are aware of

Functional interfaces

lambda expression

What is method references and default and static methods

7. What Is the Difference Between Intermediate and Terminal Operations?

8. What Is the Difference Between Map and flatMap Stream Operation?

9.  Any predefined functional interfaces--predicate, Function ====

10. Are you aware about memory management related changes in java 8--Permgen and meta space===

11. What is method reference and constructor reference====

12. What is binary operator===

13. Are you aware about CompletableFuture class in java 8



9. What is immutability in java?How can we create an immutable class?

10. Why String class made as immutable

11.  How can we create a singleton class ?

11. How you are implementing security in your application

12. How you are validating your API calls.Are you aware about jwT token.

13. What is try with resources =======

14. What is the difference between static and instance variable.

15. What are the solid principles ======

16. What are the methods available in Object class

17. What is the contract between equals and hashcode methods

18. What is the difference between shallow and deep cloning ?

19. What is the difference between Comparator and Comparable

20. What is the difference between static and dynamic polymorphism?

Can we overload main methods

Can we override static methods?

21. What are the iterators in java?



22. What is context switching ?

23. What is time slicing

Time slicing is the process used by the thread scheduler to divide CPU time between the available active threads.'

24. What is a daemon thread?

25. How do you create a daemon thread in Java-setDaemon

22. How can we create threads in java

22. What is the difference Callable and Runnable in java

23. How can threads can communicate with each others ======

14. What is the difference between start and run methods====

23. How can we create thread pool in java======



24. What is synchronization ===================

25. what is the difference between == and .equals method

26. difference between throw and throws 

17. What is composition and aggregation ==============

28. final finally finalize

29. What are the methods available to print exception information--printstacktrace/getmessage/toString

30. What are the design pattern you are aware of ==============

31. Define System.out.println().

32.What are the possible ways of making object eligible for garbage collection (GC) in Java?===



1. what is the collection hirerchy in java

2. What is the difference between collection and collections

3. What is the difference between ArrayList and Vectors.===

  In which case you will go for arraylist(retrival) and in which case you will pick linked list(insertion and deletion)

4. What is the internal implementation of hashmap and arrayList.=======

5. What is the diff between hashmap and concurrentHashmap(Synch & thread safe)===

6. Can multiple threads read from ConcurrentHashMap same time--yes===

7. What is the diff b/w Fail Fast and Fail Safe Iterator in Java=====







5 What is volatile and transient keywords.=====
SQL

=====================================================

1. What is index in sql ?

1. Write a query to retrieve duplicate records from a table.===

SELECT EmpID, EmpFname, Department COUNT(*) 

FROM EmployeeInfo GROUP BY EmpID, EmpFname, Department 

HAVING COUNT(*) > 1;



2. SQL query to find third highest salary in company.

  => SELECT * FROM `employee` ORDER BY `salary` DESC LIMIT 1 OFFSET 2;

  => SELECT DISTINCT `salary` FROM `employee` ORDER BY `salary` DESC LIMIT 1 OFFSET 2;

  => SELECT *

   FROM `employee`

   WHERE

    `Salary` = (SELECT DISTINCT `Salary`

        FROM `employee`

        ORDER BY `salary` DESC

     LIMIT 1 OFFSET 2

     )

    LIMIT 1;



3.Write an SQL query to calculate the average salary for each department, including the department name and the average salary.===

SELECT d.department_name, AVG(e.salary) AS average_salary

FROM employees e

JOIN departments d ON e.department_id = d.department_id

GROUP BY d.department_name;





Rest API

========================================================



1). What is the difference between POST and PUT methods?

post-create resource/not idempotent

put- Replace resource/idempotent

2) What is URI?explain different parts of uri ?

3) What is the concept of statelessness in REST?

2). Http Response code?

400 Bad Request: The request was not fulfilled due to an error in the request, such as a typo or missing data.

401 Unauthorized: The request was not fulfilled because the client is not authenticated or authorized to access the requested resource.



3). What is payload?

4).What is the difference between REST and SOAP?

5). which tools you are using to test API

6). How do you keep REST APIs secure?

7). How can you handle versioning in REST APIs?- URL Versioning:, Query Parameter Versioning:Header Versioning===

8). What is content negotiation in REST API===

9). What is the role of query parameters and path parameters in REST API? When should each be used, and what are their differences?

10) While creating URI for web services, what are the best practices that needs to be followed?
11) What are Idempotent methods? 

12) Based on what factors, you can decide which type of web services you need to use - SOAP or REST?






SpringBoot

===================================

1. What is the difference between BeanFactory and ApplicationContext ===============

2. What is the life cycle of spring beans?

3. What is @Qualifier used for ?

4. What are the different bean scopes in spring ?--How can we change default scope of beans ============

5. What is a Spring IoC container and types of Ioc

6. Can you explain spring mvc architecture

1. How you are handling exception in your spring boot application====

1. application.properties files

1. What are the benefits of spring boot over spring

2. What is AutoConfiguration in spring boot?

3. What does an actuator do in Spring Boot? ===========

4. How do you implement Spring Security in a Spring Boot application?===OAuth

5. What is the difference between @Controller and @RestController

6. What are the annotations that you are using in spring boot application

7. How do you configure logging in Spring Boot? What are some commonly used logging frameworks in Spring Boot?===

8. How does Spring Boot support caching? What caching libraries or mechanisms can be used with Spring Boot====

  @EnableCaching on configuration class, @Cacheable on methods

9. How can profiles be used to manage different configurations for different environments?

10.  Define RestTemplate in Spring.

11.  What are the differences between the annotations @Controller and @RestController?







JPA

=================================================

1. How do you manage transactions in your application ?

2. How can we implement pagination in spring JPA ?

3 .What types of identifier generation does JPA support?
4. Explain the difference between a shared cache mode and a local cache mode in JPA? What are the advantages and disadvantages of each?





   

Webservice

=================================================

1.What is the difference between soap/ Restful full webservices ?





Hibernate

====================================================

1. What is the difference between openSession and getCurrentSession?

2. How can we integrate spring with hibernate

1. Mention some advantages of using ORM over JDBC.

2. What is the difference between get and load method?

3. What is the difference between session.save() and session.persist() method?

 Ans : -   The return type of the save method is java.io.Serializable. It returns the generated Id after saving the record.

    It can save the changes to the database outside of the transaction. i.e. Save method can be used inside or outside the transaction.

4. How can you achieve one to many relationship in hibernate===

5. What are the states of the object in hibernate?

6. What is lazy loading in hibernate?

7. What is the first level cache and second level cache?





Microservices

================================

1) What are the main differences between Microservices and Monolithic Architecture?

The main differences between Microservices and Monolithic Architecture:



Microservices                                 Monolithic Architecture

The service startup is fast in Microservices. The service startup takes time as it is slow in Monolithic Architecture.

It is a loosely coupled architecture.         It is primarily a tightly coupled architecture.



In Microservices, if you make changes in a 

single data model, it does not affect others. In Monolithic Architecture, any changes in the data model affect the entire database.

It mainly focuses on products, not projects. It mainly focuses on the whole project.



**which one is lossely coupled architecture



2). What are the main components of Microservices?

Containers, Clustering, and Orchestration

IaC [Infrastructure as Code Conception]

Cloud Infrastructure

API Gateway

Enterprise Service Bus

Service Delivery



3). How do you design communication between microservices? Discuss different approaches and their trade-offs ?

Answer: There are multiple approaches to microservice communication:



Synchronous HTTP/REST: Services communicate over HTTP using synchronous request-response calls. It's simple to implement but can lead to tight coupling and performance issues.

Asynchronous messaging: Services communicate through message brokers, such as RabbitMQ or Apache Kafka. This allows loose coupling and better scalability but adds complexity.

Service mesh: A dedicated infrastructure layer that handles communication between services. It provides features like load balancing, service discovery, and circuit breaking.

Event-driven communication: Services communicate through events using publish-subscribe mechanisms. This enables loose coupling and scalability but adds complexity to event handling.

The choice depends on factors like performance requirements, coupling constraints, scalability needs, and the complexity tolerance of the system.



4.What is service discovery in the context of microservices? what are some popular service discovery mechanisms?--Netflix Eureka, Kubernetes



5. What is fault tolerance in microservices? How can these principles be implemented?- Redundancy, Circuit Breaker Pattern,Timeout and Retry Policies =====

6. What is API gateway in a microservices context?

7. How would you handle security and authentication in a microservices architecture?



4) What are the different strategies used in Microservices deployment?

Multiple Service Instance per Host: It is used to run single or multiple service instances of the application on a single or multiple physical/virtual hosts.

Service Instance per Host: It is used to run a service instance per host.

Service Instance per Container: It is used to run each service instance in its respective container.

Serverless Deployment: It packages the service as a ZIP file and uploads it to the Lambda function. 

The Lambda function is a stateless service that automatically runs enough micro-services to handle all requests.







==================================================================================

What is the biggest challenge you faced recently that in to developement or implementation

1. What is normalization in SQL?


Please show us an example of denormalized table and how it can be normalized.


2. Why do we need it from the SQL paradigm point of view?


3. Can FK be non-unique or empty? Can PK be non-unique or empty?


(D2+) 4. What is downsides of normalization? How can we fix this?


(D2+) 5. Give us an example of many-to-many relationship, when do we need M2M?

(Knows SQL)


1. What is indexes?


2. What is downside of indexes? When should we avoid them?


(D2+) 3. How do they work under the hood? (during data selection; during data modification)


What is complexity (in terms of Big-O notation) of selecting single element by condition on non-indexed column 'SELECT * FROM TABLE1 WHERE NON_INXEDED_COLUMN = 42'?


What is the complexity of selecting single element by condition on indexed column 'SELECT * FROM TABLE1 WHERE INXEDED_COLUMN = 42'?


Can index provide O(1) complexity?


(D2+, knows MS SQL) 4. What is clustered index? Benefits/downsides?


(D2+, knows Oracle) 4. What is index organized table? Benefits/downsides?

We have 2 tables: ROOM (ROOM_ID, ROOM_NAME) and EMPLOYEE (EMPLYEE_ID, NAME, ROOM_ID)


1. D3+: Please find a single employee who sits in the most overcrowded room.


2. D1: Please find all rooms where there is no any employee.


D2: w/o sub-selects?


3. D1: Please find all employee who has not assigned to any room.


4. D0: Please find all employee who has assigned to room number 1.
