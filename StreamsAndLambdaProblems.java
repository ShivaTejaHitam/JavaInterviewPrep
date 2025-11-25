Below is an **advanced, production-oriented guide** for experienced Java developers (3+ years) covering **Java 8 Stream API pain points, idioms, and tricky areas**—without basic definitions.

---

# **Advanced Java 8 Stream API Guide**

## 1. Primitive Streams vs Object Streams — *Conversions, Pitfalls & Idioms*

### ✔ Why Primitive Streams Matter

Primitive streams (`IntStream`, `LongStream`, `DoubleStream`) avoid boxing overhead—but only when used correctly.

### ✔ Common Conversions

#### **1.1 Object → Primitive**

```java
List<Integer> list = Arrays.asList(1, 2, 3);
IntStream s = list.stream().mapToInt(Integer::intValue);
```

**Pain point:** `mapToInt()` requires a mapper; `map()` does not auto-unbox.

#### **1.2 Primitive → Object**

```java
Stream<Integer> stream = IntStream.range(0, 10).boxed();
```

Idiomatic ✔ *Always use `.boxed()`* (not `.mapToObj(i -> i)`).

#### **1.3 Primitive Stream → Another Primitive Stream**

```java
DoubleStream ds = IntStream.range(0, 10).asDoubleStream();
```

### ✔ Common Mistake

```java
Stream<int[]> wrong = IntStream.range(0, 10).map(i -> new int[]{i});  // awkward
```

Use:

```java
IntStream.range(0, 10).boxed().map(i -> new int[]{i});
```

---

## 2. Conversions Between Streams, Collections, Arrays & Primitives

### **2.1 Stream → List / Set (fast idioms)**

```java
List<String> l = myStream.collect(Collectors.toList());  // OK
Set<String> s = myStream.collect(Collectors.toSet());    // OK but unordered
```

**Production idiom (specific type):**

```java
ArrayList<String> list = myStream.collect(Collectors.toCollection(ArrayList::new));
```

### **2.2 Stream → Map (collision-free idioms)**

#### Handling duplicates:

```java
Map<String, Long> counts =
    list.stream().collect(Collectors.toMap(
        k -> k,
        v -> 1L,
        Long::sum
    ));
```

**Always provide a merge function in real-world data**, unless you want exceptions.

### **2.3 Stream → Array (object)**

```java
String[] arr = list.stream().toArray(String[]::new);
```

### **2.4 Primitive Stream → Array**

```java
int[] arr = IntStream.range(0, 10).toArray();
```

### **2.5 Array → Stream**

```java
IntStream s = Arrays.stream(new int[]{1,2,3});
Stream<String> ss = Arrays.stream(new String[]{"a","b"});
```

### **2.6 Collection of primitives → primitive streams**

Because Java lacks `List<int>`, the pattern is:

```java
List<Integer> ints = ...
IntStream s = ints.stream().mapToInt(Integer::intValue);
```

---

## 3. Advanced Usage Patterns & Real-World Tricks

### **3.1 `flatMap` vs `flatMapToInt`**

If mapping to primitives, always use the primitive version:

```java
IntStream s =
    users.stream()
         .flatMapToInt(u -> u.getScores().stream().mapToInt(Integer::intValue));
```

Avoid:

```java
flatMap(u -> u.getScores().stream()) // leads to Stream<Integer> → boxing cost
```

---

### **3.2 Collecting into a Map of Lists (multi-map)**

Very common in enterprise code.

```java
Map<String, List<Order>> byCustomer =
    orders.stream().collect(
        Collectors.groupingBy(Order::getCustomer)
    );
```

### With downstream collectors:

```java
Map<String, Set<String>> tagSets =
    products.stream().collect(
        groupingBy(Product::getCategory, 
                   flatMapping(p -> p.getTags().stream(), toSet()))
    );
```

---

### **3.3 "Index stream" pattern**

Java streams don’t provide indexes naturally.

#### Idiomatic:

```java
IntStream.range(0, list.size())
    .forEach(i -> process(i, list.get(i)));
```

#### With a result:

```java
Map<Integer, String> indexed =
    IntStream.range(0, list.size())
        .boxed()
        .collect(toMap(i -> i, list::get));
```

---

### **3.4 The "switch on optional" pattern**

Avoid `ifPresent` chains:

```java
User user =
    findUser(id)
        .filter(User::isActive)
        .orElseGet(() -> loadFromBackup(id));
```

---

### **3.5 Avoid `parallelStream()` in most cases**

Use only with:

* CPU-heavy operations
* No shared mutability
* Large data sets

**Production rule:** Avoid parallelization unless benchmarked.

---

## 4. Performance & Side-Effects

### **4.1 Use `peek()` only for debugging**

```java
stream.peek(x -> log.debug("x={}", x));
```

Avoid business logic in `peek()`—it may never run depending on terminal ops.

---

### **4.2 Cache collectors if reused**

```java
private static final Collector<String, ?, Set<String>> DISTINCT_SET =
    Collectors.toCollection(LinkedHashSet::new);

Set<String> s = list.stream().collect(DISTINCT_SET);
```

---

### **4.3 Avoid Too Many Boxing/Unboxing Conversions**

Bad:

```java
IntStream.range(0, 1000).boxed().map(i -> i * 2).mapToInt(i -> i);
```

Good:

```java
IntStream.range(0, 1000).map(i -> i * 2);
```

---

## 5. Stream Error Handling Idioms (without breaking functional flow)

### **5.1 Wrap checked exceptions**

```java
Stream<String> lines =
    files.stream().map(path -> {
        try { return new String(Files.readAllBytes(path)); }
        catch (IOException e) { throw new UncheckedIOException(e); }
    });
```

### **5.2 “Try wrapper” pattern (custom helper)**

```java
<T,R> Function<T,R> rethrow(FunctionWithException<T,R> f) {
    return t -> {
        try { return f.apply(t); }
        catch (Exception ex) { throw new RuntimeException(ex); }
    };
}
```

Usage:

```java
list.stream().map(rethrow(this::dangerousOp));
```

---

## 6. Complex Transformations & Stream Pipelines

### **6.1 Transforming a Map's Entries**

```java
Map<String, Integer> updated =
    map.entrySet().stream()
       .filter(e -> e.getValue() > 10)
       .collect(toMap(
           Map.Entry::getKey,
           e -> e.getValue() * 2
       ));
```

---

### **6.2 Most useful terminal ops in real-world code**

* `collect()` → building results
* `anyMatch()` / `noneMatch()` ← fast early exit
* `reduce()` ← advanced folding logic
* `summaryStatistics()` ← primitive stream statistics
* `joining()` ← building JSON-like strings

Example:

```java
String csv = list.stream().collect(joining(","));
```

---

# **Advanced Java 8 Stream API Cheat Sheet**

## **Primitive Streams**

* Convert object → primitive: `.mapToInt(Integer::intValue)`
* Convert primitive → object: `.boxed()`
* Convert int → double: `.asDoubleStream()`
* Use primitive versions of map/flatMap for performance.

## **Conversions**

* Stream → List: `collect(toList())`
* Stream → Array: `toArray(Type[]::new)`
* Primitive stream → array: `.toArray()`
* Collection → primitive stream: `.stream().mapToInt(...)`
* Array → primitive stream: `Arrays.stream(int[])`

## **Map Handling**

* Always supply merge function in `toMap()`
  `toMap(k, v, mergeFn)`
* Multimap: `groupingBy(key, toList())`

## **Indexing Patterns**

* `IntStream.range(0, list.size())` for index-based operations.

## **Idiomatic Patterns**

* Debug: `.peek(log::debug)`
* Avoid business logic in `peek()`
* Avoid excessive boxing; stick with primitive streams.

## **Error Handling**

* Wrap checked exceptions with `UncheckedIOException` or custom wrapper.

## **Performance**

* Prefer primitives for heavy numeric pipelines.
* Avoid parallel streams unless benchmarked.
* Cache reusable collectors.

## **String & Collecting Tricks**

* CSV: `joining(",")`
* Stats: `mapToInt(...).summaryStatistics()`
* Remove duplicates but preserve order: `toCollection(LinkedHashSet::new)`

---

If you want, I can also generate:

* **A printable PDF cheat sheet**
* **Refactor your existing project code to use these patterns**
* **A comparison of Stream API vs Kotlin/Scala equivalents**










1) Separate odd and even numbers in a list .
   Arrays.asList(1,2,3,4,5).stream().collect(Collectors.partitioningBy(n -> n%2 == 0)).forEach((k,v) -> System.out.println(k + " : " + v));
2) Remove duplicate elements from list using java 8 streams.
   Arrays.asList(1,2,2,2,3,4).stream().distinct().forEach(System.out::println);
3) Find the frequency of each character in string using Java 8 streams.
   String string = "helloworld";
   Arrays.stream(string.split("")).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
4) Sort a given list of decimals in reverse order.
   Arrays.asList(3.14, 1.59, 2.65, 4.33, 5.67).stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).forEach(System.out::println);
5) Join a list of strings with '['  as prefix, ']' as suffix, and ',' as delimiter.
   System.out.println(Arrays.asList("Shiva","Teja","Bandaru").stream().map( s-> "[" + s + "]").collect(Collectors.joining(",")));
6) Print the numbers from a given list of integers that are multiples of 5
   Arrays.asList(5,6,10,15,16,20).stream().filter(n -> n%5==0).collect(Collectors.toList()).forEach(System.out::println);
7) Find maximum and minimum of list of integers. 
    System.out.println(Arrays.asList(1,2,3,4,5).stream().min(Comparator.naturalOrder()).get());
    System.out.println(Arrays.asList(1,2,3,4,5).stream().max(Comparator.naturalOrder()).get());
8) Merge two unsorted arrays in to single sorted array without duplicates.
    Stream.concat(Arrays.asList(5,4,3,3,2,1).stream(),Arrays.asList(10,9,8,7,6).stream()).distinct().sorted().forEach(System.out::println);
9) Get 3 maximum and 3 minimum numbers from list of integers.
   Arrays.asList(1,2,3,4,5,6).stream().sorted().limit(3).forEach(System.out::println);
Arrays.asList(1,2,3,4,5,6).stream().sorted(Comparator.reverseOrder()).limit(3).forEach(System.out::println);
10) 

11)

12) Find second largest element in array 
    Arrays.asList(2,1,4,3,5).stream().sorted(Comparator.reverseOrder()).skip(1).findFirst().get();
13) Sort list of strings in increasing order 
    Arrays.asList("shiva","is","engineer").stream().sorted(Comparator.comparing(String::length)).collect(Collectors.toList());
14) Sum and Average of array 
    Arrays.stream(numbers).sum();
    Arrays.stream(numbers).average().orElse(0.0);
15) 

16) Reverse each word of string using stream
    Arrays.stream(input.split(" ")) // Split the string into words
                .map(word -> new StringBuilder(word).reverse().toString()) // Reverse each word
                .collect(Collectors.joining(" "));
17) Find sum of first 10 natural numbers
   IntStream.rangeClosed(1,10).sum();
18) 
19) print the first 10 even numbers.
    Instream.rangeClosed(1,20).filter(n -> n%2 == 0).forEach(System.out::println);
20) Find the most repeated element in the array.
   Arrays.stream(numbers) // Convert array to stream
                .boxed() // Convert int to Integer to work with Collectors
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())) // Count occurrences
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()) // Find the entry with the max value
                .map(Map.Entry::getKey) // Extract the key (element)
                .orElseThrow(() -> new IllegalArgumentException("Array is empty"));
21) 
22).
23) 
24) Find first repeated character in string
    String s = "hello";
        
       System.out.println(Arrays.stream("hello".split("")).filter(c -> s.indexOf(c) == s.lastIndexOf(c)).findFirst().get());
25) 
26) 
27) Find first 10 odd numbers
Instream.rangeClosed(1,20).filter(n -> n%2 == 0).forEach(System.out::println);.
28) Find last element of array,
   Arrays.asList(1,2,3,4,5).stream().reduce((first,second)->second).get();
29)
