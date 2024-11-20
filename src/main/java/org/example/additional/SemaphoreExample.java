package org.example.additional;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    static class Memory{
        int val;
    }

    static class Producer implements Runnable{
        Semaphore readSemaphore;
        Semaphore writeSemaphore;
        Memory memory;

        public Producer(Semaphore readSemaphore, Semaphore writeSemaphore, Memory memory) {
            this.readSemaphore = readSemaphore;
            this.writeSemaphore = writeSemaphore;
            this.memory = memory;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int randomNumber = new Random().nextInt(100);
                    writeSemaphore.acquire();
                    memory.val = randomNumber;
                    readSemaphore.release();
                }

            }catch (InterruptedException e){

            }
        }
    }

    static class Consumer implements Runnable{
        Semaphore readSemaphore;
        Semaphore writeSemaphore;

        Memory memory;

        public Consumer(Semaphore readSemaphore, Semaphore writeSemaphore, Memory memory) {
            this.readSemaphore = readSemaphore;
            this.writeSemaphore = writeSemaphore;
            this.memory = memory;
        }

        @Override
        public void run() {
            try {
                while(true) {
                    readSemaphore.acquire();
                    System.out.println(memory.val);
                    writeSemaphore.release();
                    Thread.sleep(200);
                }

            }catch (InterruptedException e){

            }

        }
    }


    public static void main(String[] args) {

        Semaphore readSemaphore = new Semaphore(0);
        Semaphore writeSemaphore = new Semaphore(1);
        Memory memory = new Memory();

        Runnable producer = new Producer(readSemaphore,writeSemaphore,memory);
        Runnable consumer = new Consumer(readSemaphore,writeSemaphore,memory);

        ExecutorService service = Executors.newFixedThreadPool(3);
        service.execute(producer);
        service.execute(consumer);

    }
}
