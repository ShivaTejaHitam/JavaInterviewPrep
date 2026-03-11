// Shared buffer class
class Buffer {
    private int[] buffer;
    private int count = 0;
    private int size;

    public Buffer(int size) {
        this.size = size;
        buffer = new int[size];
    }

    // Produce an item
    public synchronized void produce(int item) throws InterruptedException {
        // Wait if buffer is full
        while (count == size) {
            wait();
        }
        buffer[count] = item;
        count++;
        System.out.println("Produced: " + item);
        notify(); // Notify consumer
    }

    // Consume an item
    public synchronized int consume() throws InterruptedException {
        // Wait if buffer is empty
        while (count == 0) {
            wait();
        }
        int item = buffer[count - 1];
        count--;
        System.out.println("Consumed: " + item);
        notify(); // Notify producer
        return item;
    }
}

// Producer thread
class Producer extends Thread {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int item = 0;
        while (true) {
            try {
                buffer.produce(item++);
                Thread.sleep(500); // simulate time to produce
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Consumer thread
class Consumer extends Thread {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                buffer.consume();
                Thread.sleep(800); // simulate time to consume
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Main class
public class ProducerConsumerDemo {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(5); // Buffer size 5

        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();
    }
}
