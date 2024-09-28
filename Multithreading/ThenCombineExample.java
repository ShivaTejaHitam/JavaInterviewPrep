import java.util.concurrent.*;

class ThenCombineExample {
    public static void main(String[] args) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            return "Shiva Teja";
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            return 24;
        });

        future1.thenCombine(future2,(name,age) -> {
            return name + " " + age;
        }).thenAccept(System.out::println);

        
    }
}
