package simple;

class MyJob implements Runnable {
    private int i = 0;

    @Override
    public void run() {
        for (; i < 10_000; i++) {
            System.out.println(Thread.currentThread().getName() + " i is " + i);
        }
        System.out.println("Thread ending...");
    }
}
public class RunanbleOne {
    public static void main(String[] args) {
        Runnable r = new MyJob();

        Thread t1 = new Thread(r);
//        t1.setDaemon(true); NONONONONO!!(almost never at least)
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();

        System.out.println(Thread.currentThread().getName() + " exiting...");
    }
}
