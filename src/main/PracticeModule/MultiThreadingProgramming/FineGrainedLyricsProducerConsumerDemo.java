package PracticeModule.MultiThreadingProgramming;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedLyricsProducerConsumerDemo {
    private static final int BUFFER_SIZE = 3;
    private static final Queue<String> lyricsBuffer = new LinkedList<>();
    private static final Lock lock = new ReentrantLock();
    private static final Condition notFull = lock.newCondition();
    private static final Condition notEmpty = lock.newCondition();
    /*
     ? Volatile is a keyword that indicates to the language that this variable might
     ? be changed atomically by different threads and synchronized methods. It is a guarantee of
     ? visibility and ordering within a multithreaded environment
     ?
     ?
     ? Volatile guaranttes visibility: i.e., that any read of this variable will see the most up
     ? to date value, even if not on the main thread.
     ? Ordering: this prevents instruction reordering on compilation or by the CPU, ensuring
     ?consistent execution.
     */
    private static volatile boolean producingComplete = false;

    static class LyricsProducer implements Runnable {
        private final String[] lyrics;
        private final int producerId;

        public LyricsProducer(String[] lyrics, int producerId) {
            this.lyrics = lyrics;
            this.producerId = producerId;
        }

        @Override
        public void run() {
            for (String lyric : lyrics) {
                try {
                    lock.lock();
                    while (lyricsBuffer.size() == BUFFER_SIZE) {
                        System.out.println("Producer-[" + producerId + Thread.currentThread().getName() +
                                                   "]: Buffer is full, waiting...");
                        notFull.await();
                    }

                    // Simulate network delay
                    Thread.sleep(500);
                    lyricsBuffer.offer(lyric);

                    System.out.println("Producer-[" + producerId + Thread.currentThread().getName() +
                                               "]: Added lyric -> " + lyric);
                    System.out.println("Producer-[" + producerId + Thread.currentThread().getName() +
                                               "]: Buffer size is now " + lyricsBuffer.size());

                    notEmpty.signal();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } finally {
                    lock.unlock();
                }
            }

            lock.lock();
            try {
                producingComplete = true;
                notEmpty.signalAll(); // Signal all consumers that production is complete
            } finally {
                lock.unlock();
            }

            System.out.println("Producer-[" + producerId + Thread.currentThread().getName() + "]:" +
                                       " Finished " +
                                       "producing lyrics");
        }
    }

    static class LyricsConsumer implements Runnable {
        private final int consumerId;

        public LyricsConsumer(int consumerId) {
            this.consumerId = consumerId;
        }

        @Override
        public void run() {
            while (!producingComplete || !lyricsBuffer.isEmpty()) {
                String lyric = null;
                try {
                    lock.lock();
                    while (lyricsBuffer.isEmpty() && !producingComplete) {
                        System.out.println("Consumer-[" + consumerId + Thread.currentThread().getName() +
                                                   "]: Buffer is empty, waiting...");
                        notEmpty.await();
                    }

                    if (lyricsBuffer.isEmpty() && producingComplete) {
                        break;
                    }

                    lyric = lyricsBuffer.poll();
                    System.out.println("Consumer-[" + consumerId + Thread.currentThread().getName() +
                                               "]: Consumed lyric -> " + lyric);
                    System.out.println("Consumer-[" + consumerId + Thread.currentThread().getName() +
                                               "]: Buffer size is now " + lyricsBuffer.size());

                    notFull.signal();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } finally {
                    lock.unlock();
                }

                // Process the consumed lyric
                if (lyric != null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            System.out.println("Consumer-[" + consumerId + Thread.currentThread().getName() +"]: Finished consuming lyrics");
        }
    }

    public static void main(String[] args) {
        // Define the lyrics to be processed
        String[] songLyrics = {
                "When I find myself in times of trouble",
                "Mother Mary comes to me",
                "Speaking words of wisdom",
                "Let it be",
                "And in my hour of darkness",
                "She is standing right in front of me",
                "Speaking words of wisdom",
                "Let it be"
        };

        // Split lyrics between two producers
        int midPoint = songLyrics.length / 2;
        String[] firstHalf = new String[midPoint];
        String[] secondHalf = new String[songLyrics.length - midPoint];

        System.arraycopy(songLyrics, 0, firstHalf, 0, midPoint);
        System.arraycopy(songLyrics, midPoint, secondHalf, 0, songLyrics.length - midPoint);

        // Create producer and consumer threads
        Thread producer1 = new Thread(new LyricsProducer(firstHalf, 1), "Producer-1");
        Thread producer2 = new Thread(new LyricsProducer(secondHalf, 2), "Producer-2");
        Thread consumer1 = new Thread(new LyricsConsumer(1), "Consumer-1");
        Thread consumer2 = new Thread(new LyricsConsumer(2), "Consumer-2");

        // Start all threads
        System.out.println("Starting lyrics processing...");


        // Wait for all threads to complete
        try (ExecutorService service = Executors.newCachedThreadPool()){
            service.execute(producer1);
            service.execute(producer2);
            service.execute(consumer1);
            service.execute(consumer2);
            service.shutdown();
        }

        System.out.println("Lyrics processing completed!");
    }
}