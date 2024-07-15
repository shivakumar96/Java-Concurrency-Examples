package org.example.implemantion.basic;

/**
 * Interrupts are co-operative mechanisms to indicate stop signals to a thread.
 */
public class InterruptsExample {
    public static void main(String[] args) {
        Runnable r1 = ()->{
            for(int i=0;i<10;i++){
                //process something
                if(Thread.currentThread().isInterrupted()){
                    //It do some final operations
                    //return; // stops execution
                    // or it can ignore the interrupt as
                    Thread.currentThread().interrupt();//
                }
            }
        };

        //eg:
        Thread t = new Thread(r1);
        t.interrupt();

    }
}
