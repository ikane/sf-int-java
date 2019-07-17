package executors;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;


class MyCallable implements Callable<String> {
    private static int nextId = 0;
    private String jobName = "Job " + nextId++;
    @Override
    public String call() throws Exception {
        System.out.println(jobName + " starting");
        UseExecutor.delay(ThreadLocalRandom.current().nextInt(1000, 3000));
        if (ThreadLocalRandom.current().nextInt(0, 4) > 2) throw new SQLException();
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
        List<Future<String>> futures = new ArrayList<>();

        futures.add(ex.submit(new MyCallable()));
        futures.add(ex.submit(new MyCallable()));
        futures.add(ex.submit(new MyCallable()));
//        ex.shutdown();
        futures.add(ex.submit(new MyCallable()));
//        ex.shutdownNow();
        ex.shutdown();

        try {
            while (futures.size() > 0) {
                Iterator<Future<String>> ifs = futures.iterator();
                while (ifs.hasNext()) {
//                    System.out.print(".");
                    Future<String> fs = ifs.next();
                    if (fs.isCancelled()) {
                        System.out.println("job canceled");
                        ifs.remove();
                    } else if (fs.isDone()) {
                        System.out.println("Trying to get result");
                        try {
                            System.out.println(fs.get());
                        } catch(ExecutionException ee) {
                            System.out.println("Job threw executionexceptoin, cause is " + ee.getCause());
                        }
                        ifs.remove();
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        /*
        ExecutorService.shutdown... shutdownNow...
        Future.cancel... some / one of the jobs to be canceled a) before it starts b) while running
        What happens if a job throws an exception during execution?
         */

    }
}
