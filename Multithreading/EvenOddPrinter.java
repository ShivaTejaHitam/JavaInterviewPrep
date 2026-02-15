class EvenOddPrinter{
    private int number = 1;
    private final int max = 10;
    
    public synchronized void printEven(){
        while(number <= max){
            if(number % 2 == 1) {  // why if ? why not while
                try{
                      wait();  
                } catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            } else {
                System.out.println("Even :"+number);
                number++;
                notify();
            }   
        }  
    }
    
    public synchronized void printOdd(){
        while(number <= max){
            if(number % 2 == 0){
                try{
                      wait();
                } catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            } else {
                System.out.println("Odd :"+number);
                number++;
                notify();
            }
        }
    }
}



public class Main {
    public static void main(String[] args) {
        EvenOddPrinter evenOddPrinter = new EvenOddPrinter();
        Thread t1 = new Thread(() -> evenOddPrinter.printEven());
        Thread t2 = new Thread(() -> evenOddPrinter.printOdd());
        
        t1.start();
        t2.start();
        
        
    }
}
