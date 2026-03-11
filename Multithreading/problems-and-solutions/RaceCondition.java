// problematic code which leads to race condition

public class Counter {
    private int count = 0;

    public void increment() {
        count++; // This is NOT thread-safe!
    }

    public int getCount() {
        return count;
    }
}

// solutions
//  solution 1
public class Counter {
    private int count = 0;

    // Only one thread can enter this method at a time
    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}

// solution 2 
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet(); // Thread-safe and atomic
    }

    public int getCount() {
        return count.get();
    }
}


