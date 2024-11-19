package org.example.implementation.basic;

import java.nio.channels.CompletionHandler;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynchronousAPIExample {

    public static void main(String[] args) {
        // thenApplyAsync vs thenApply => different thread will be used in Async,
        // suggested to use thenApplyAsync with executor

        ExecutorService service = Executors.newFixedThreadPool(10);
        ExecutorService service2 = Executors.newFixedThreadPool(10);

        // Internally it uses Fork-Join thread pool when we don't provide the thread pool

        CompletableFuture.supplyAsync(() -> new Random().nextInt(10), service)
                .thenApplyAsync(x->2*x, service2)
                .thenApplyAsync(y->3*y)
                .thenAcceptAsync(z->System.out.println(z));

        CompletableFuture.supplyAsync(() -> new Random().nextInt(10))
                .thenApply(x->2*x)
                .thenApply(y->3*y)
                .thenAccept(z->System.out.println(z));
        service.shutdown();
        service2.shutdown();

        // catching exception
        CompletableFuture.supplyAsync(() -> new Random().nextInt(10))
                .thenApply(x->2*x)
                .thenApply(y->3*y)
                .exceptionally(e->1)
                .thenAccept(z->System.out.println(z));

    }
}
