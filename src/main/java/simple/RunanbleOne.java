package simple;

class MyJob implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 1_000; i++) {
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

        t1.start();

        System.out.println(Thread.currentThread().getName() + " exiting...");
    }
}
