package org.example.implemantion.basic;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MyInt{
    int it;
    MyInt(int it){
        this.it =it;
    }
}

public class ThreadLocalExample {

    static ThreadLocal<MyInt> counter = new ThreadLocal<>(){
        @Override
        protected MyInt initialValue(){
            return  new MyInt(0);
        }

        @Override
        public MyInt get(){
            return  super.get();
        }
    };

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for(int i=0;i<100;i++){
            int finalI = i;
            service.execute(()->{
                MyInt ct = counter.get();
                System.out.println("itr PV: "+ finalI +" -> "+
                        Thread.currentThread().getName() +" "+ ct.it+" "+ counter.get());
                for(int j=0;j<20;j++)
                    ct.it++;
                //counter.set(ct);
                System.out.println("itr   : "+ finalI +" -> "+
                        Thread.currentThread().getName() +" "+ ct.it+" "+ counter.get());

            });
        }
        service.shutdown();
        System.out.println(service);

    }

}
