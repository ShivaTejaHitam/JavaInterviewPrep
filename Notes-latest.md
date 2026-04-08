# Java Interview Preparation

## Core Java

### Access specifiers

Classes have only two access specifiers: **public** and **default** (package-private).

### Reentrant locks vs `synchronized`

- **Reentrant locks** do not allow multiple threads to access the critical section of different objects.
- **`synchronized`** is a monitor lock which acquires a lock on the critical section of a single object. Two different threads can access the critical section of a synchronized method of different objects simultaneously.

### Java 8 features

Lambda expressions, functional interfaces, `Optional`, `CompletableFuture`, default methods, new Date and Time API (`java.time`).

### Short topics

- **What is `yield`?** (Explain.)
- **Explain garbage collection.**
- **If we return in a `try` block, does `finally` execute?** Yes.
- **How does string immutability provide thread safety?**
- **Can interfaces extend other interfaces?** Yes — use `extends`, similar to classes.
- **What is `Arrays.asList()`?** Creates a fixed-size list backed by an array (or from varargs).

---

## Functional interfaces

In Java, a **functional interface** is an interface that contains only one abstract method. They are central to Java 8+ functional programming and enable lambda expressions and method references.

Key points:

1. **Single Abstract Method (SAM)** — Exactly one abstract method; may have multiple `default` or `static` methods.
2. **`@FunctionalInterface`** — Optional but recommended; compiler errors if more than one abstract method is added.
3. **Lambdas and method references** — Behaviors can be passed without separate implementation classes.
4. **Built-in interfaces** — `java.util.function`: `Predicate`, `Function`, `Consumer`, `Supplier`, etc.
5. **Streams and APIs** — Used heavily in the Stream API and other Java 8+ APIs.

Example:

```java
@FunctionalInterface
interface Printer {
    void printMessage(String message);
}

class Main {
    public static void main(String[] args) {
        Printer printer = (String msg) -> System.out.println(msg);
        printer.printMessage("Hello");
    }
}
```

---

## Built-in functional interfaces (`java.util.function`)

- **`Predicate<T>`** — Boolean-valued function of one argument; often used to filter.

  ```java
  Predicate<Integer> evenChecker = n -> n % 2 == 0;
  System.out.println(evenChecker.test(10));
  ```

- **`Function<T, R>`** — One argument, one result; mapping/transforming.

  ```java
  Function<String, Integer> findStringLength = str -> str.length();
  System.out.println(findStringLength.apply("Shiva"));
  ```

- **`Consumer<T>`** — Accepts one argument, returns nothing.

  ```java
  Consumer<String> welcomeGreeter = str -> System.out.println("Welcome " + str);
  welcomeGreeter.accept("Shiva Teja");
  ```

- **`Supplier<T>`** — No arguments, supplies a result.

  ```java
  Supplier<Double> randomDouble = () -> Math.random();
  System.out.println(randomDouble.get());
  ```

- **`UnaryOperator<T>`** — Unary operation on one operand of type `T`.

  ```java
  UnaryOperator<Integer> square = n -> n * n;
  System.out.println(square.apply(2));
  ```

- **`BinaryOperator<T>`** — Binary operation on two operands of the same type.

  ```java
  BinaryOperator<Integer> multiplier = (n, m) -> n * m;
  System.out.println(multiplier.apply(2, 4));
  ```

---

## Stream API (Java 8)

Streams process collections in a functional, declarative way (filter, map, sort, reduce).

1. **Creation** — From collections, arrays, I/O, or factory methods.

   ```java
   List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
   Stream<Integer> stream = numbers.stream();
   ```

2. **Intermediate operations** — Return another stream; lazy until a terminal operation runs (e.g. `filter`, `map`).

   ```java
   Stream<Integer> filteredStream = numbers.stream().filter(n -> n % 2 == 0);
   Stream<Integer> mappedStream = numbers.stream().map(n -> n + 1);
   ```

3. **Terminal operations** — Produce a result or side effect (`reduce`, `collect`, `forEach`, `findFirst`, `count`, etc.).

   ```java
   int sum = numbers.stream().reduce((a, b) -> a + b).orElse(0);
   List<Integer> evenNumbers = numbers.stream()
       .filter(n -> n % 2 == 0)
       .collect(Collectors.toList());
   ```

4. **Parallel streams** — `parallelStream()` splits work across threads (use when beneficial).

   ```java
   List<Integer> evenNumbers = numbers.parallelStream()
       .filter(n -> n % 2 == 0)
       .collect(Collectors.toList());
   ```

5. **Lazy evaluation** — Intermediate ops run only when a terminal operation is invoked.

   ```java
   List<String> strings = Arrays.asList("abc", "efg", "hij");
   Stream<String> filteredStrings = strings.stream().filter(s -> {
       System.out.println("Filtering " + s);
       return s.contains("a");
   });
   filteredStrings.forEach(System.out::println); // without this, nothing prints
   ```

---

## `map` vs `flatMap`

Both are used on streams/collections in a functional style.

- **`map`** — Applies a function to each element; one output element per input (same “shape” of structure).
- **`flatMap`** — When the mapping function returns a stream/collection per element, `flatMap` flattens nested structures into a single stream.

```java
public class Main {
    public static void main(String[] args) {
        List<List<Integer>> nestedList = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );
        List<Integer> flattenedList = nestedList.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println(flattenedList); // [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }
}
```

---

## Method reference and constructor reference

*(Add notes when you fill this in.)*

---

## `Comparator` and `Comparable`

Both are used for ordering; **Comparable** defines *natural* order on the type itself; **Comparator** defines *external* comparison logic.

### Comparable

```java
import java.util.*;

class Student implements Comparable<Student> {
    private int id;
    private String name;
    // Constructor, getters, setters...

    @Override
    public int compareTo(Student otherStudent) {
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
```

### Comparator

```java
import java.util.*;

class Student {
    private int id;
    private String name;
    // Constructor, getters, setters...

    static class NameComparator implements Comparator<Student> {
        @Override
        public int compare(Student student1, Student student2) {
            return student1.getName().compareTo(student2.getName());
        }
    }
}

public class ComparatorExample {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(101, "Alice"));
        students.add(new Student(102, "Bob"));
        students.add(new Student(103, "Charlie"));
        Student.NameComparator nameComparator = new Student.NameComparator();
        students.sort(nameComparator);
        for (Student student : students) {
            System.out.println(student.getName());
        }
    }
}
```

- **Comparable** — `Arrays.sort` / `Collections.sort` using natural order defined on the class.
- **Comparator** — `List.sort(Comparator)`, `TreeSet`/`TreeMap` with external `Comparator`, etc.

- **Methods to revisit** - comparing, naturalOrder, reverseOrder, reversed, thenComparing
---

## `volatile` and `transient`
### Java Memory Model

### `volatile`

- Indicates the variable may be updated by multiple threads.
- Reads/writes go through main memory (visibility), not only thread-local caches.
- Does **not** replace synchronization for compound actions (no atomicity for `i++` by itself).
- singleton pattern using volatile
```java
class SharedResource {
    private volatile boolean flag = false;

    public void setFlag(boolean value) {
        flag = value;
    }

    public boolean getFlag() {
        return flag;
    }
}
```

### `transient`

- Field is **not** serialized with the object.
- On deserialization, `transient` fields get default values (`null`, `0`, `false`, etc.).

```java
import java.io.*;

public class Person implements Serializable {
    private String name;
    private transient String ssn;

    public Person(String name, String ssn) {
        this.name = name;
        this.ssn = ssn;
    }

    public String getName() { return name; }
    public String getSsn() { return ssn; }
}

public class SerializationExample {
    public static void main(String[] args) {
        Person person = new Person("John Doe", "123-45-6789");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.ser"))) {
            oos.writeObject(person);
            System.out.println("Object has been serialized");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.ser"))) {
            Person deserializedPerson = (Person) ois.readObject();
            System.out.println("Deserialized Person: " + deserializedPerson.getName());
            System.out.println("Deserialized SSN: " + deserializedPerson.getSsn());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

---

## Miscellaneous Java topics

### Why is `String` immutable?

- Often used for sensitive data (e.g. credentials); immutability reduces accidental modification.
- String pool deduplication saves memory.
- Safe to share across threads without extra synchronization.

### JAR vs WAR

| | **JAR** | **WAR** |
|---|----------|---------|
| | Archive of `.class` files and resources; libraries and apps | Web application archive for servlet containers |
| | Distribute libraries / standalone apps | Deploy web apps (HTML, JSP, `WEB-INF`, etc.) |

### Quick facts

- **Java 17** — e.g. sealed classes, pattern matching enhancements, etc.
- **Override static methods?** No — static methods are hidden, not overridden polymorphically.
- **Abstract class constructor?** Yes — subclasses invoke it via `super(...)`.
- **`Stream.of` vs `Arrays.stream` (array argument)** — `Stream.of(array)` treats the array as a single element (stream of one reference). `Arrays.stream(array)` streams the elements of the array.
- **Default methods in interfaces** — Allow evolution of interfaces; `default` return type is part of the method signature; body provided in the interface.

### Synchronization

- Ways to achieve synchronization: `synchronized` blocks/methods, `java.util.concurrent` locks, atomic classes, concurrent collections, etc.

### Interface vs abstract class

| **Interfaces** | **Abstract classes** |
|----------------|----------------------|
| Contract for implementers | Base for related types |
| Multiple inheritance of type | Single inheritance |
| No instance state (until `private` fields in Java 8+) | Can have fields and constructors |
| No constructors (for the interface itself) | Subclass constructor chains to abstract class |

### Java versions

- Familiarity with **Java 11** LTS and current versions.
- **Java 8 memory** — PermGen removed; **Metaspace** for class metadata (native memory).

---

## Practice questions (mixed)

Core / OOP / language:

- Immutability; how to design an immutable class.
- Singleton patterns.
- Security in applications; JWT for APIs.
- Try-with-resources.
- Static vs instance variables.
- SOLID principles.
- `Object` class methods.
- `equals` and `hashCode` contract.
- Shallow vs deep cloning.
- Static vs dynamic polymorphism.
- Overloading `main`; overriding static methods.
- Iterators.
- Context switching; time slicing; daemon threads (`setDaemon`).
- Creating threads; `Runnable` vs `Callable`; thread pools; inter-thread communication.
- `start()` vs `run()`.
- `==` vs `.equals`.
- `throw` vs `throws`.
- Composition vs aggregation.
- `final`, `finally`, `finalize`.
- Exception reporting: `printStackTrace`, `getMessage`, `toString`.
- Design patterns.
- `System.out.println`.
- Making objects eligible for GC.

Collections:

- Collection hierarchy.
- `Collection` vs `Collections`.
- `ArrayList` vs `Vector`; when to prefer `ArrayList` vs `LinkedList`.
- `HashMap` / `ArrayList` internals.
- `HashMap` vs `ConcurrentHashMap`.
- Can multiple threads read `ConcurrentHashMap` concurrently? (Generally yes for reads; details depend on version/operation.)
- Fail-fast vs fail-safe iterators.

---

## SQL

### Index

What is an index?

### Duplicate rows

```sql
SELECT EmpID, EmpFname, Department, COUNT(*)
FROM EmployeeInfo
GROUP BY EmpID, EmpFname, Department
HAVING COUNT(*) > 1;
```

### Third highest salary

```sql
SELECT * FROM employee ORDER BY salary DESC LIMIT 1 OFFSET 2;
```

```sql
SELECT DISTINCT salary FROM employee ORDER BY salary DESC LIMIT 1 OFFSET 2;
```

```sql
SELECT *
FROM employee
WHERE salary = (
    SELECT DISTINCT salary
    FROM employee
    ORDER BY salary DESC
    LIMIT 1 OFFSET 2
)
LIMIT 1;
```

### Average salary by department

```sql
SELECT d.department_name, AVG(e.salary) AS average_salary
FROM employees e
JOIN departments d ON e.department_id = d.department_id
GROUP BY d.department_name;
```

### More SQL topics

- Normalization; denormalized example and how to normalize.
- Why normalization from a relational perspective.
- FK nullable/non-unique? PK nullable/non-unique?
- Downsides of normalization; mitigations.
- Many-to-many relationships.
- Index downsides; when to avoid.
- How indexes work (select vs modify).
- Big-O: full scan on non-indexed column vs indexed lookup; can an index be O(1)?
- Clustered index (e.g. MS SQL); index-organized table (Oracle).

**Sample schema:** `ROOM (ROOM_ID, ROOM_NAME)`, `EMPLOYEE (EMPLOYEE_ID, NAME, ROOM_ID)`

- Employee in the most “overcrowded” room.
- Rooms with no employees.
- Same without subqueries (level D2).
- Employees not assigned to any room.
- Employees in room number 1.

---

## REST API

- **POST vs PUT** — POST often creates; not necessarily idempotent. PUT replaces a resource; idempotent.
- **URI** — Parts: scheme, authority, path, query, fragment.
- **Statelessness** in REST.
- **HTTP status codes** — e.g. 400 Bad Request, 401 Unauthorized.
- **Payload**
- **REST vs SOAP**
- API testing tools.
- Securing REST APIs.
- Versioning — URL, query param, header.
- Content negotiation.
- Query vs path parameters.
- URI best practices.
- Idempotent methods.
- Choosing SOAP vs REST.

---

## Spring Boot

- **BeanFactory vs ApplicationContext**
- Spring bean lifecycle.
- **`@Qualifier`**
- Bean scopes; changing default scope.
- IoC container types.
- Spring MVC architecture.
- Exception handling in Spring Boot.
- `application.properties` / `application.yml`
- Benefits of Spring Boot over Spring.
- **Auto-configuration**
- **Actuator**
- Spring Security / OAuth.
- **`@Controller` vs `@RestController`**
- Common annotations.
- Logging and frameworks.
- Caching (`@EnableCaching`, `@Cacheable`, etc.).
- **Profiles**
- **RestTemplate**

---

## JPA

- Transaction management.
- Pagination in Spring Data JPA.
- Identifier generation strategies.
- Shared vs local cache mode — pros/cons.

---

## Web services

- SOAP vs RESTful services — differences.

---

## Hibernate

- `openSession` vs `getCurrentSession`.
- Spring + Hibernate integration.
- ORM advantages vs JDBC.
- `get` vs `load`.
- **`save` vs `persist`** — `save` returns serializable id; can participate outside a strict transaction boundary depending on usage (clarify with your Hibernate version).
- One-to-many mapping.
- Entity states.
- Lazy loading.
- First-level vs second-level cache.

---

## Microservices

### vs monolithic

| Microservices | Monolithic |
|---------------|------------|
| Faster startup per service | Slower startup of whole app |
| Loosely coupled | Tighter coupling |
| Localized data model changes | Broader impact on one DB |
| Often product-oriented | Whole-project orientation |

### Components

Containers, clustering/orchestration, IaC, cloud, API gateway, ESB (where used), service delivery.

### Communication

- Synchronous HTTP/REST
- Async messaging (Kafka, RabbitMQ, etc.)
- Service mesh
- Event-driven / pub-sub

Trade-offs: coupling, latency, complexity, scalability.

### Other topics

- Service discovery (e.g. Eureka, Kubernetes).
- Fault tolerance — redundancy, circuit breaker, timeouts/retries.
- API gateway role.
- Security and authentication across services.
- Deployment strategies — multiple instances per host, instance per host, instance per container, serverless/Lambda-style.

---

## Behavioral / misc

- Biggest recent challenge in development or implementation.

---

*End of notes.*
