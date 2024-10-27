import java.util.ArrayList;
import java.util.Spliterator;
import java.util.function.Consumer;

public class SpliteratorExample {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        list.add("Date");

        Spliterator<String> spliterator = list.spliterator();

        // Sequential traversal
        spliterator.forEachRemaining(System.out::println);

        // Example of trySplit
        Spliterator<String> spliterator1 = list.spliterator();
        Spliterator<String> spliterator2 = spliterator1.trySplit();

        if (spliterator2 != null) {
            System.out.println("First spliterator:");
            spliterator1.forEachRemaining(System.out::println);

            System.out.println("Second spliterator:");
            spliterator2.forEachRemaining(System.out::println);
        }
    }
}
