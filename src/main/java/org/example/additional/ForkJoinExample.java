package org.example.additional;

/*
This is very similar to executor service, but it used for the tasks that forks/produce subtasks
and joining then the final results to get the total value eg: recursion

ForkJoin has per thread queueing and Work stealing (from back of the queue)

it has three methods    || call from within the forks

1. Arrange Async execution -> execute (ForkJoinTask)  - > fork()
2. Await and obtain result -> invoke (ForkJoinTask)   -> invoke()
3. Arrange exec and obtain future -> submit (ForkJoinTask)   -> fork()

 */

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class Fibonacci extends RecursiveTask<Integer>{
    int n;

    public Fibonacci(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if(n<=1)
            return n;
        Fibonacci f1 = new Fibonacci(n-1);
        f1.fork();
        Fibonacci f2 = new Fibonacci(n-2);
        f2.fork();
        return  f1.join() + f1.join();
    }
}
public class ForkJoinExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Fibonacci fibonacci = new Fibonacci(10);
        ForkJoinPool pool = new ForkJoinPool(4);
        int res = pool.invoke(fibonacci);
        System.out.println(res);
        ForkJoinTask<Integer> task = pool.submit(fibonacci); // future
        res = task.get();
        System.out.println(res);
    }

}
