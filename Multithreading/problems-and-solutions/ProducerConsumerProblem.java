import java.util.LinkedList;
import java.util.Queue;

class SharedResource {
    private Queue<Integer> buffer = new LinkedList<>();
    private final int CAPACITY = 5;

    public void produce(int value) throws InterruptedException {
        synchronized(this){
            while(buffer.size() == CAPACITY) {
                wait();
            }

            buffer.add(value);
            System.out.println("Produced: " + value);
            notifyAll(); // do not use notify because it may lead to deadlock
        }
    }

    public void consume() throws InterruptedException{
        synchronized(this){
            while(buffer.isEmpty()) {
                wait();
            }

            int value = buffer.poll();
            System.out.println("Consumed: " + value);
            notifyAll();
        }
    }
}

class InterThreadCommunication {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {// replace with while(true) for infinite production
                    sharedResource.produce(i);
                    Thread.sleep(100); 
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) { // replace with while(true) for infinite consumption
                    sharedResource.consume();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
