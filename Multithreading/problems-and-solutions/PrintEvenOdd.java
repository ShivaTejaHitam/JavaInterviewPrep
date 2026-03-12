public class SharedPrinter {
    private int counter = 1;
    private final int limit;

    public SharedPrinter(int limit) {
        this.limit = limit;
    }

    // Method for the Odd thread
    public synchronized void printOdd() {
        while (counter <= limit) {
            // If the current number is even, wait for the even thread to finish
            while (counter % 2 == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (counter <= limit) {
                System.out.println(Thread.currentThread().getName() + ": " + counter);
                counter++;
            }
            notify(); // Wake up the even thread
        }
    }

    // Method for the Even thread
    public synchronized void printEven() {
        while (counter <= limit) {
            // If the current number is odd, wait for the odd thread to finish
            while (counter % 2 != 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (counter <= limit) {
                System.out.println(Thread.currentThread().getName() + ": " + counter);
                counter++;
            }
            notify(); // Wake up the odd thread
        }
    }

    public static void main(String[] args) {
        SharedPrinter printer = new SharedPrinter(10);

        Thread oddThread = new Thread(printer::printOdd, "Odd-Thread");
        Thread evenThread = new Thread(printer::printEven, "Even-Thread");

        oddThread.start();
        evenThread.start();
    }
}
