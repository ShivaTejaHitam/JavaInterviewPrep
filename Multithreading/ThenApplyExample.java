import java.util.concurrent.*;

class ThenApplyExample {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Shiva Teja");

        future.thenApply(result -> result + "Bandaru").thenAccept(System.out::println);
    }
}
