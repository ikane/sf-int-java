package simple;

public class Stopper {
    static volatile boolean stop = false;
    public static void main(String[] args) throws Throwable {

        new Thread(() -> {
            System.out.println("Worker thread started...");
            while (! stop)
                ;
            System.out.println("Worker thread stopping...");
        }).start();
        Thread.sleep(1000);
        stop = true;

        System.out.println("Exiting main, stop is " + stop);
    }
}
