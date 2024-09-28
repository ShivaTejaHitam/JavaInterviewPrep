import java.util.concurrent.*;

class ThenRunExample {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Shiva Teja");

        future.thenRun(() -> System.out.println("Task completed successfully"));
    }
}
