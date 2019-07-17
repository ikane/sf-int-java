package executors;

import java.util.concurrent.*;


class MyCallable implements Callable<String> {
    private static int nextId = 0;
    private String jobName = "Job " + nextId++;
    @Override
    public String call() throws Exception {
        System.out.println(jobName + " starting");
        UseExecutor.delay(ThreadLocalRandom.current().nextInt(1000, 3000));
        System.out.println(jobName + " finishing...");
        return jobName + " Completed!";
    }
}

public class UseExecutor {
    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            System.out.println("Ouch you woke me up!");
        }
    }

    public static void main(String[] args) throws Throwable {
        ExecutorService ex = Executors.newFixedThreadPool(2);

        Future<String> handle1 = ex.submit(new MyCallable());
        Future<String> handle2 = ex.submit(new MyCallable());
        Future<String> handle3 = ex.submit(new MyCallable());
        Future<String> handle4 = ex.submit(new MyCallable());
        while (!handle1.isDone()){
            System.out.print(".");
            System.out.flush();
        }
        System.out.println("\nGetting from job 1: " + handle1.get());

        delay(4_000);
        System.out.println("Getting from job 2: " + handle2.get());
        System.out.println("Getting from job 3: " + handle3.get());
        System.out.println("Getting from job 4: " + handle4.get());
    }
}
