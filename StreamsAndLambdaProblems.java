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
