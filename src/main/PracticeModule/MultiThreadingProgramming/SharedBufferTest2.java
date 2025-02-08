package PracticeModule.MultiThreadingProgramming;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Fig. 23.16: SynchronizedBuffer.java
// Synchronizing access to shared mutable data using Object
// methods wait and notifyAll.
class SynchronizedBuffer implements Buffer {
    private int buffer = -1; // shared by producer and consumer threads
    private boolean occupied = false;

    // place value into buffer
    @Override
    public synchronized void blockingPut(int value)
            throws InterruptedException {
        // while there are no empty locations, place thread in waiting state
        while (occupied) {
            // output thread information and buffer information, then wait
            System.out.println("Producer" + Thread.currentThread().getName() +  "tries to write."); // for
            // demo only
            displayState("Buffer full." + Thread.currentThread().getName() + " Producer waits.");
            // for
            // demo only
            wait();
        }

        buffer = value; // set new buffer value

        // indicate producer cannot store another value
        // until consumer retrieves current buffer value
        occupied = true;

        displayState("Producer " +
                Thread.currentThread().getName() + "writes " + buffer);

        notifyAll(); // tell waiting thread(s) to enter runnable state
    } // end method blockingPut; releases lock on SynchronizedBuffer

    // return value from buffer
    @Override
    public synchronized int blockingGet() throws InterruptedException {
        // while no data to read, place thread in waiting state
        while (!occupied) {
            // output thread information and buffer information, then wait
            System.out.println("Consumer " + Thread.currentThread().getName() + " tries to read."); // for demo only
            displayState("Buffer empty. " + Thread.currentThread().getName() + ". Consumer waits" +
                                 "."); // for demo only
            wait();
        }

        // indicate that producer can store another value
        // because consumer just retrieved buffer value
        occupied = false;

        displayState("Consumer reads " + buffer); // for demo only

        notifyAll(); // tell waiting thread(s) to enter runnable state

        return buffer;
    } // end method blockingGet; releases lock on SynchronizedBuffer

    // display current operation and buffer state; for demo only
    private synchronized void displayState(String operation) {
        System.out.printf("%-40s%d\t\t%b%n%n", operation, buffer, occupied);
    }
}


// Fig. 23.17: SharedBufferTest2.java
// Two threads correctly manipulating a synchronized buffer.


public class SharedBufferTest2 {
    public static void main(String[] args) throws InterruptedException {
        // create a newCachedThreadPool
        ExecutorService executorService = Executors.newCachedThreadPool();

        // create SynchronizedBuffer to store ints
        Buffer sharedLocation = new SynchronizedBuffer();

        System.out.printf("%-40s%s\t\t%s%n%-40s%s%n%n", "Operation",
                          "Buffer", "Occupied", "---------", "------\t\t--------");

        // execute the Producer and Consumer tasks
        executorService.execute(new Producer(sharedLocation));
        executorService.execute(new Consumer(sharedLocation));

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}
