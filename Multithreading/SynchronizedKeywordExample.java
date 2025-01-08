
// if an instance method is synchronized then only one thread can access that method as well as other synchronized methods of that object at a time
import java.util.*;

class Resource {
    static synchronized void perform(){
        try{
            for(int i = 0 ; i < 5 ; i++){
                Thread.sleep(1000);
                System.out.println("This is sensitive"+ Thread.currentThread().getName());
            }
        }catch(InterruptedException e){
            System.out.println("InterruptedException");
        }
    }

    static synchronized void useLess(){
        try{
            for(int i = 0 ; i < 5 ; i++){
                Thread.sleep(1000);
                System.out.println("after useless sleep"+ Thread.currentThread().getName());
            }
        }catch(InterruptedException e){
            System.out.println("InterruptedException");
        }
    }
}


class Interview
{
    public static void main(String args[])
    {

       Thread t1 = new Thread(() -> {
            Resource.useLess();
       });

       Thread t2 = new Thread(() -> {
            Resource.perform();
       });

       t1.start();
       t2.start();


        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


