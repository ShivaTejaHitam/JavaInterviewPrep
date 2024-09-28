import java.util.concurrent.*;

class SupplyAsyncExample {
    public static void main(String[] args) throws Exception{
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "supply async example");

        // does not wait . runs asynchronously
        future.thenAccept(System.out::println);
        // waits for future to complete
        System.out.println(future.join());

        // future.get() is used we need to handle checked exception using throws
    }
}

