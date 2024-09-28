import java.util.concurrent.*;

class AnyOfExample {
    public static void main(String[] args) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Shiva Teja");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Charan Teja");

        CompletableFuture<Object> future3 = CompletableFuture.anyOf(future1, future2);
        // prints whichever executes first
        future3.thenAccept(System.out::println);
    }
}
