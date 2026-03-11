import java.util.concurrent.locks.*;


class SharedBuffer {
    private String data = "Initial Data";
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void read(String threadName){
        lock.readLock().lock();
        try {
            System.out.println(threadName + " is reading: " + data);
            Thread.sleep(1000); // Simulating time taken to read
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(threadName + " has finished reading.");
            lock.readLock().unlock();
        }

    }

    public void write(String threadName,String newValue){
        lock.writeLock().lock();
        try {
            System.out.println(threadName + " is writing: " + newValue);
            this.data = newValue;
            Thread.sleep(1500); // Simulating time taken to write
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(threadName + " has finished writing.");
            lock.writeLock().unlock();
        }
    }
}

class ReadWriteLockModern {
    public static void main(String[] args){
        SharedBuffer buffer = new SharedBuffer();

        Thread reader = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                buffer.read(Thread.currentThread().getName());
            }
        });
    
        Thread writer = new Thread(() ->{
            for (int i = 0; i < 3; i++) {
                buffer.write(Thread.currentThread().getName(), "Updated Data " + Math.random());
            }
        });
        

        reader.start();
        writer.start();
    }
}
