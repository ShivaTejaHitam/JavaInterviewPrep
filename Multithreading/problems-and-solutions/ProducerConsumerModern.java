import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerModern {
    public static void main(String[] args) {
        // Create a thread-safe queue with a capacity of 5
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        // Producer Thread
        Thread producer = new Thread(() -> {
            try {
                int value = 0;
                while (true) {
                    queue.put(value); // Automatically waits if queue is full
                    System.out.println("Produced: " + value);
                    value++;
                    Thread.sleep(500); 
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer Thread
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    int value = queue.take(); // Automatically waits if queue is empty
                    System.out.println("Consumed: " + value);
                    Thread.sleep(1000); 
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
