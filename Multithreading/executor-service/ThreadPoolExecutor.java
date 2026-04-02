import java.util.concurrent.ThreadPoolExecutor;

class ThreadPool {

    public static void main(String[] args){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 10, 
            java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.LinkedBlockingQueue<Runnable>());

        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Executing task " + taskId + " by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }

}
