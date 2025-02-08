package PracticeModule.MultiThreadingProgramming;

// Deitel Deitel Java 23.5
import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class Consumer implements Runnable {
    private static final SecureRandom generator = new SecureRandom();
    private final Buffer sharedLocation; // reference to shared object

    // constructor
    public Consumer(Buffer sharedLocation) {
        this.sharedLocation = sharedLocation;
    }

    // read sharedLocation's value 10 times and sum the values
    @Override
    public void run() {
        int sum = 0;

        for (int count = 1; count <= 10; count++) {
            // sleep 0 to 3 seconds, read value from buffer and add to sum
            try {
                Thread.sleep(generator.nextInt(3000));
                sum += sharedLocation.blockingGet();
                System.out.printf("\t\t\t%2d%n", sum);
            }
            catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.printf("%n%s %d%n%s%n",
                          "Consumer read values totaling", sum, "Terminating Consumer");
    }
}

interface Buffer {
    // place int value into Buffer
    public void blockingPut(int value) throws InterruptedException;

    // return int value from Buffer
    public int blockingGet() throws InterruptedException;
}

public class SharedBufferTest {
    public static void main(String[] args) throws InterruptedException {
        // create new thread pool
        ExecutorService executorService = Executors.newCachedThreadPool();

        // create UnsynchronizedBuffer to store ints
        Buffer sharedLocation = new UnsynchronizedBuffer();

        System.out.println(
                "Action\t\tValue\tSum of Produced\tSum of Consumed");
        System.out.printf(
                "------\t\t-----\t---------------\t---------------%n%n");

        // execute the Producer and Consumer, giving each
        // access to the sharedLocation
        executorService.execute(new Producer(sharedLocation));
        executorService.execute(new Consumer(sharedLocation));

        executorService.shutdown(); // terminate app when tasks complete
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}

class UnsynchronizedBuffer implements Buffer {
    private int buffer = -1; // shared by producer and consumer threads

    // place value into buffer
    @Override
    public void blockingPut(int value) throws InterruptedException {
        System.out.printf("Producer writes\t%2d", value);
        buffer = value;
    }

    // return value from buffer
    @Override
    public int blockingGet() throws InterruptedException {
        System.out.printf("Consumer reads\t%2d", buffer);
        return buffer;
    }
}

class Producer implements Runnable {
    private static final SecureRandom generator = new SecureRandom();
    private final Buffer sharedLocation; // reference to shared object

    // constructor
    public Producer(Buffer sharedLocation) {
        this.sharedLocation = sharedLocation;
    }

    // store values from 1 to 10 in sharedLocation
    @Override
    public void run() {
        int sum = 0;

        for (int count = 1; count <= 10; count++) {
            try { // sleep 0 to 3 seconds, then place value in Buffer
                Thread.sleep(generator.nextInt(3000)); // random sleep
                sharedLocation.blockingPut(count); // set value in buffer
                sum += count; // increment sum of values
                System.out.printf("\t%2d%n", sum);
            }
            catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.printf(
                "Producer done producing%nTerminating Producer%n");
    }
}

