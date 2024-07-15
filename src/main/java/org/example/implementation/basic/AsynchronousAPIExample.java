package org.example.implementation.basic;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.CompletableFuture;

public class AsynchronousAPIExample {

    public static void main(String[] args) {
        CompletableFuture.supplyAsync(()->1)
                .thenApplyAsync(x->1)
                .thenApplyAsync(y->1)
                .thenAcceptAsync(x->System.out.println("done"));


    }
}
