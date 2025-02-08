package PracticeModule.MultiThreadingProgramming;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Fine-grained implementation for the producer-consumer problem
class FineGrainedLyricsBuffer {
    private final ArrayDeque<String> buffer = new ArrayDeque<>(2); // Shared buffer
    private final int capacity;
    private final Object bufferLock = new Object();  // Lock for producers


    public FineGrainedLyricsBuffer(int capacity) {
        this.capacity = capacity;
    }

    // Method to produce lyrics
    public void produce(String lyric) throws InterruptedException {
        synchronized (bufferLock) {
            while (buffer.size() == capacity) { // Wait if the buffer is full
                System.out.println("From thread {" + Thread.currentThread().getName() +
                                           "], Waiting: to be consumed.");
                bufferLock.wait(); // Release producerLock and wait
            }

            buffer.add(lyric);
            System.out.println("From thread {" + Thread.currentThread().getName() +
                                           "], Produced: " + lyric);

            bufferLock.notify();

        }
    }

    // Method to consume lyrics
    public String consume() throws InterruptedException {
        String lyric;

        synchronized (bufferLock) {
            while (buffer.isEmpty()) { // Wait if the buffer is empty
                System.out.println("From thread {" + Thread.currentThread().getName() +
                                           "], Waiting: for data to be produced.");
                bufferLock.wait(); // Release consumerLock and wait
            }
            lyric = buffer.poll();
            System.out.println("From thread {" + Thread.currentThread().getName() +
                                           "], Consumed: " + lyric);
            bufferLock.notify();

        }

        return lyric;
    }
}

// Main class to demonstrate Producer-Consumer problem
public class CoarseGrainedProducerConsumerDemo {
    public static void main(String[] args) {
        FineGrainedLyricsBuffer lyricsBuffer = new FineGrainedLyricsBuffer(2); // Shared buffer with capacity of 2

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
                for (int i = 0; i < 6; i++) { // Consume the same number of lyrics produced
                    lyricsBuffer.consume();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer was interrupted.");
            }
        });

        // Executor service to run producer and consumer threads
        try (ExecutorService service = Executors.newCachedThreadPool()) {
            service.execute(producer); // Start producer thread
            service.execute(consumer); // Start consumer thread
            service.shutdown(); // Shutdown executor service
        }

        System.out.println("Fine-grained Producer-Consumer demo finished.");
    }
}