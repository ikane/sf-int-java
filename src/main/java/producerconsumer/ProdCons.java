package producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer implements Runnable {
    BlockingQueue<int[]> queue;
    private Producer(BlockingQueue<int[]> queue) {
        this.queue = queue;
    }
    public static Producer of(BlockingQueue<int[]> queue) {
        return new Producer(queue);
    }
    public void run() {
        try {
            for (int i = 0; i < 10_000; i++) {
                int [] data = new int[2];
                data[0] = i;
                if (i < 100) {
                    Thread.sleep(10);
                }
                data[1] = i;
                if (i == 5_000) {
                    data[0] = -1;
                }
                queue.put(data);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.println("Producer finished");
    }
}

class Consumer implements Runnable {
    BlockingQueue<int[]> queue;
    private Consumer(BlockingQueue<int[]> queue) {
        this.queue = queue;
    }
    public static Consumer of(BlockingQueue<int[]> queue) {
        return new Consumer(queue);
    }
    public void run() {
        try {
            for (int i = 0; i < 10_000; i++) {
                int[] data = queue.take();
                if (data[0] != i || data[1] != i) {
                    System.out.println("ERROR iteration " + i + " values " + data[0] + ", " + data[1]);
                }
                if (i > 9_900) {
                    Thread.sleep(10);
                }
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.println("Consumer finished");
    }
}

public class ProdCons {
    public static void main(String[] args) {
        BlockingQueue<int[]> queue = new ArrayBlockingQueue<>(10);
        Producer prod = Producer.of(queue);
        Consumer cons = Consumer.of(queue);
        new Thread(prod).start();
        new Thread(cons).start();
        System.out.println("Producer and consumer started, main exiting.");
    }
}
