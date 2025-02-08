package PracticeModule.MultiThreadingProgramming;

import java.security.SecureRandom;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



// Fig. 23.14: BlockingBuffer.java
// Creating a synchronized buffer using an ArrayBlockingQueue.
class BlockingBuffer implements Buffer {
    private final ArrayBlockingQueue<Integer> buffer; // shared buffer

    public BlockingBuffer() {
        buffer = new ArrayBlockingQueue<Integer>(1);
    }

    // place value into buffer
    @Override
    public void blockingPut(int value) throws InterruptedException {
        buffer.put(value); // place value in buffer
        System.out.printf("%s%2d\t%s%d%n", "Producer writes ", value,
                          "Buffer cells occupied: ", buffer.size());
    }

    // return value from buffer
    @Override
    public int blockingGet() throws InterruptedException {
        int readValue = buffer.take(); // remove value from buffer
        System.out.printf("%s %2d\t%s%d%n", "Consumer reads ",
                          readValue, "Buffer cells occupied: ", buffer.size());

        return readValue;
    }
}

// Fig. 23.15: BlockingBufferTest.java
// Two threads manipulating a blocking buffer that properly
// implements the producer/consumer relationship.

public class BlockingBufferTest {
    public static void main(String[] args) throws InterruptedException {
        // create new thread pool
        ExecutorService executorService = Executors.newCachedThreadPool();

        // create BlockingBuffer to store ints
        Buffer sharedLocation = new BlockingBuffer();

        executorService.execute(new Producer(sharedLocation));
        executorService.execute(new Consumer(sharedLocation));

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}
