package org.example.delayed;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class QObject implements Delayed {
    int val;
    Long ScheduledTime;

    public QObject(int val, Long scheduledTime) {
        this.val = val;
        ScheduledTime = scheduledTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long delay = ScheduledTime - System.currentTimeMillis();
        return unit.convert(delay,TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
    }
}

class DelayedQ {
    DelayQueue<QObject> queue ;

    public DelayedQ() {
        this.queue = new DelayQueue<>();
    }

    public void add(int val, Long ScheduledTime){
        queue.add(new QObject(val,ScheduledTime));
    }

    public QObject getObject() throws InterruptedException {
        return queue.take();
    }

    boolean isEmpty(){
        return queue.isEmpty();
    }
}

public class DelayedQueueExample {
    public static void main(String[] args) throws InterruptedException {
        DelayedQ queue = new DelayedQ();
        queue.add(10,System.currentTimeMillis()+6000);
        queue.add(20,System.currentTimeMillis()+10000);

        while (!queue.isEmpty()){
            System.out.println(queue.getObject().val);
        }
    }
}
