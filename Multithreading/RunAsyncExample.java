import java.util.concurrent.*;
// CompletableFuture is intended for chaining


class RunAsyncExample {
    public static void main(String[] args) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Welcome to completable Future");
        });

        future.join();
    }
}
