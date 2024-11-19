package org.example.additional;

import java.util.concurrent.*;

public class InterruptsExample {
    /*
    Interrupts are the  co-operative mechanism for indicating stop signal to thread
    - This is the used approach for stopping the thread from execution
       The thread which interrupted can choose to stop o not to stop
    - This approach provides a safe way to handle
        a) Data integrity
        b) Half operations
        c) Open Connections

        Interrupt can be called even when the thread is sleep
        because of the same reason we surround sleep with Interrupted exception
     */

    public static void main(String[] args) throws InterruptedException {

        Runnable task = ()->{
          for(int i=0;i<1_000_00;i++){
              System.out.println(i);
              if(Thread.currentThread().isInterrupted()){
                  System.out.println("Interrupted and stopping..");
                  return;
              }
          }
        };

        Runnable taskIgnoreInterrupt = ()->{
            for(int i=0;i<1_000_00;i++){
                System.out.println(i);
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Interrupted");
                    Thread.interrupted(); // bypass interrupt
                }
            }
        };

        // Only callable allows checked exception
        Callable<Integer> taskThrowsInterrupt = new Callable<Integer>() {
            @Override
            public Integer call() throws InterruptedException {
                for(int i=0;i<1_000_00;i++){
                    System.out.println(i);
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Interrupted");
                        throw new InterruptedException();
                    }
                }
                return null;
            }
        };

        Thread t1 = new Thread(task);
        t1.start();
        t1.interrupt();

//        Thread t2 = new Thread(taskIgnoreInterrupt);
//        t2.start();
//        t2.interrupt();

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Integer> future = service.submit(taskThrowsInterrupt);
        service.shutdownNow();



    }

}
