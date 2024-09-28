import java.util.concurrent.*;

class ThenComposeExample {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10)
        .thenCompose(result -> CompletableFuture.supplyAsync(() -> result * 2));

        future.thenAccept(System.out::println); // Output: 20
    }
}
