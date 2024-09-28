import java.util.concurrent.*;

class SupplyAsyncExample {
    public static void main(String[] args) throws Exception{
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "supply async example");

        future.thenAccept(System.out::println);
        // or 
        System.out.println(future.join());

        // future.get() is used we need to handle checked exception using throws
    }
}

