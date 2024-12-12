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
    Arrays.stream("hello".split("")).mapToObj( c -> (char) c).filter(c -> s.indexOf(c) == s.lastIndexOf(c)).findFirst().get();
25) 
26) 
27) Find first 10 odd numbers
Instream.rangeClosed(1,20).filter(n -> n%2 == 0).forEach(System.out::println);.
28) Find last element of array,
   Arrays.asList(1,2,3,4,5).stream().reduce((first,second)->second).get();
29)
