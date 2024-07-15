package org.example.problems;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Solving dining philosophers problem
 * Idea is to eliminate circular dependency by
 * acquiring locks in global ordering.
 */

/**
 * Creates circular dependency
 */
class Philosopher {
    private Semaphore[] semaphores ;

    public Philosopher(int n){
        semaphores = new Semaphore[n];
        for(int i=0; i<n;i++){
            semaphores[i] = new Semaphore(1);
        }
    }

    Runnable method =()->{
        while (true) {
            think();
            int i = Integer.parseInt(Thread.currentThread().getName());
            try {
                semaphores[i].acquire();
                semaphores[(i + 1) % semaphores.length].acquire();
                eat();
                semaphores[(i + 1) % semaphores.length].release();
                semaphores[i].release();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private void think(){
        Random random = new Random();
        int n = random.nextInt(500,800);
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void eat(){
        System.out.println(Thread.currentThread().getName()+": Eating");
        for(long i=0; i<10000000;)
            i++;
        System.out.println(Thread.currentThread().getName()+": Done Eating");
    }
}

/**
 * Uses Global ordering;
 */
class Philosopher2 {
    private Semaphore[] semaphores ;

    public Philosopher2(int n){
        semaphores = new Semaphore[n];
        for(int i=0; i<n;i++){
            semaphores[i] = new Semaphore(1);
        }
    }

    Runnable method =()->{
        while (true) {
            think();
            int i = Integer.parseInt(Thread.currentThread().getName());
            try {
                int next = (i + 1) % semaphores.length;
                int first = Math.min(i,next);
                int second = Math.max(i,next);
                semaphores[first].acquire();
                semaphores[second].acquire();
                eat();
                semaphores[second].release();
                semaphores[first].release();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private void think(){
        Random random = new Random();
        int n = random.nextInt(500,1000);
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void eat(){
        System.out.println(Thread.currentThread().getName()+": Eating");
        for(long i=0; i<10000000;)
            i++;
        System.out.println(Thread.currentThread().getName()+": Done Eating");
    }
}

public class DiningPhilosophersProblem {
    public static void main(String[] args) throws InterruptedException {
        int k = 5;
        //Philosopher p = new Philosopher(k);
        Philosopher2 p = new Philosopher2(k);
        Thread[] ps = new Thread[k];
        for(int i=0;i<k;i++){
            ps[i] = new Thread(p.method,""+i);
            ps[i].start();
        }
        ps[0].join();

    }
}
