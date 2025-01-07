// newFixedThreadPool
// newCachedThreadPool
// newSingleThreadExecutor
// submit(Runnable runnable)
// submit(Callable callable)


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        for(int i = 1 ; i <= 5; i++){
            int task = i;
            executor.execute(() -> {
                System.out.println("Executing Task " + task + " by " + Thread.currentThread().getName());
            });
        }
        
        executor.shutdown();
        
    }
}
