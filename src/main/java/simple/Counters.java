package simple;

public class Counters {
    static volatile int count = 0;
    public static void main(String[] args) throws Throwable {
//        final int[] count = { 0 };

        Runnable r = () -> {
            for (int i = 0; i < 10_000; i++) {
//                count[0]++;
                count++;
            }
        };

        Thread t1 = new Thread(r);
        t1.start();

        Thread t2 = new Thread(r);
        t2.start();

//        Thread.sleep(1000);
        t1.join();
        t2.join();
//        if (!t1.isAlive()) {
//            System.out.println("count is " + count[0]);
            System.out.println("count is " + count);
//        }
    }
}
