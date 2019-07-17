package producerconsumer;

import java.util.concurrent.*;

class ProducerMulti implements Runnable {
    public static final int MAX_NUMBER = 10_000;
    private BlockingQueue<Integer> queue;
    private Semaphore sem;
    private ProducerMulti(BlockingQueue<Integer> queue, Semaphore sem) {
        this.queue = queue;
        this.sem = sem;
    }
    public static ProducerMulti of(BlockingQueue<Integer> queue, Semaphore sem) {
        return new ProducerMulti(queue, sem);
    }
    public void run() {
        try {
            for (int i = 0; i < MAX_NUMBER; i++) {
                if (i < 100) {
                    Thread.sleep(10);
                }
                System.out.println("p");
                queue.put(i);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        sem.release();
        System.out.println("ProducerMulti finished");
    }
}

class ConsumerMulti implements Runnable {
    private BlockingQueue<Integer> queue;
    private Semaphore sem;
    private boolean shuttingDown = false;
    public int[] counters = new int[ProducerMulti.MAX_NUMBER];

    private ConsumerMulti(BlockingQueue<Integer> queue, Semaphore sem) {
        this.queue = queue;
        this.sem = sem;
    }
    public static ConsumerMulti of(BlockingQueue<Integer> queue, Semaphore sem) {
        return new ConsumerMulti(queue, sem);
    }
    public void run() {
        while (true) {
            try {
                Integer i = queue.poll(100, TimeUnit.MILLISECONDS);
                if (i != null) {
                    System.out.println("c");
                    counters[i]++;
                } else {
                    System.out.println("c null");
                    if (shuttingDown) { // empty queue and producers have quit, time to exit
                        break;
                    }
                }
            } catch (InterruptedException ie) {
                System.out.println("c interrupt");
                shuttingDown = true;
            }
        }
        sem.release();
        System.out.println("ConsumerMulti finished");
    }
}

public class ProdConsMulti {
    public static void main(String[] args) {
        final int PRODUCER_COUNT = 5;
        final int CONSUMER_COUNT = 3;

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        Semaphore prodSem = new Semaphore(1 - PRODUCER_COUNT);
        Semaphore consSem = new Semaphore(1 - CONSUMER_COUNT);
        for (int i = 0; i < PRODUCER_COUNT; i++) {
            ProducerMulti prod = ProducerMulti.of(queue, prodSem);
            new Thread(prod).start();
        }
        ConsumerMulti[] consumers = new ConsumerMulti[CONSUMER_COUNT];
        Thread[] consumerThreads = new Thread[CONSUMER_COUNT];

        for (int i = 0; i < CONSUMER_COUNT; i++) {
            consumers[i] = ConsumerMulti.of(queue, consSem);
            consumerThreads[i] = new Thread(consumers[i]);
            consumerThreads[i].start();
        }
        System.out.println("Producers and consumers started, main waiting...");

        try {
            prodSem.acquire();
            System.out.println("main awaiting consumer completions");
            for (int i = 0; i < CONSUMER_COUNT; i++) {
                consumerThreads[i].interrupt();
            }
            consSem.acquire();
            System.out.println("consumers completed");

            for (int i = 1; i < consumers.length; i++) {
                for (int j = 0; j < ProducerMulti.MAX_NUMBER; j++) {
                    consumers[0].counters[j] += consumers[i].counters[j];
                }
            }
            boolean success = true;
            for (int j = 0; j < ProducerMulti.MAX_NUMBER; j++) {
                if (consumers[0].counters[j] != PRODUCER_COUNT) {
                    System.out.println("ERROR: index " + j + " count is " + consumers[0].counters[j]
                     + " (should be " + PRODUCER_COUNT +")");
                    success = false;
                }
            }
            if (success) {
                System.out.println("SUCCESS! Checks verified");
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace(); // not expected
        }

    }
}
