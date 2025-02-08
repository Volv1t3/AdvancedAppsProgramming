package PracticeModule.MultiThreadingProgramming;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Shared buffer using synchronized blocks
class LyricsBuffer {
    private final ArrayDeque<String> buffer = new ArrayDeque<>(2);
    private final int capacity;

    public LyricsBuffer(int capacity) {
        this.capacity = capacity;
    }

    public void produce(String lyric)
            throws InterruptedException {
        synchronized (buffer) {
            while (buffer.size() == capacity) {
                // Wait if the buffer is full
                System.out.println("From thread {" + Thread.currentThread().getName() + "], Waiting:" +
                                       " to be consumed.");
                buffer.wait();
            }
            buffer.add(lyric);
            System.out.println("From thread {" + Thread.currentThread().getName() + "], Produced:" +
                                       " " + lyric);
            // Notify consumers 
            // that there is new data
            buffer.notifyAll();
        }
    }

    public String consume()
            throws InterruptedException {
        synchronized (buffer) {
            while (buffer.isEmpty()) {
                // Wait if the buffer is empty
                System.out.println("From thread {" + Thread.currentThread().getName() + "], Waiting:" +
                                       " for data to be produced.");
                buffer.wait();
            }
            String lyric = buffer.poll();
            System.out.println("From thread {" + Thread.currentThread().getName() + "], Consumed:" +
                                       " " + lyric);
            // Notify producers that 
            // there is space in the buffer
            buffer.notifyAll();
            return lyric;
        }
    }
}


// Class demonstrating producer and consumer threads using LyricsBuffer
public class ProducerConsumerDemo {

    public static void main(String[] args) {
        // Define the buffer with a capacity of 2
        LyricsBuffer lyricsBuffer = new LyricsBuffer(2);

        // Producer thread
        Thread producer = new Thread(() -> {
            String[] lyrics = {
                    "Is this the real life?",
                    "Is this just fantasy?",
                    "Caught in a landslide,",
                    "No escape from reality.",
                    "Open your eyes,",
                    "Look up to the skies and see..."
            };

            try {
                for (String lyric : lyrics) {
                    lyricsBuffer.produce(lyric);

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer was interrupted.");
            }
        });

        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 6; i++) {
                    // Consume the same number of lyrics produced
                    String lyric = lyricsBuffer.consume();

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer was interrupted.");
            }
        });



        // Wait for both threads to finish

        try (ExecutorService service = Executors.newCachedThreadPool()){
            service.execute(producer);
            service.execute(consumer);
            service.shutdown();
        }

        System.out.println("Producer-Consumer demo finished.");
    }
}