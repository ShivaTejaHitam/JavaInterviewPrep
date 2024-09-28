import java.util.concurrent.*;

class AllOfExample {
    public static void main(String[] args) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Shiva Teja");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Charan Teja");

        CompletableFuture<Void> future3 = CompletableFuture.allOf(future1, future2);
        future3.thenRun(() -> System.out.println("All done!"));
    }
}
