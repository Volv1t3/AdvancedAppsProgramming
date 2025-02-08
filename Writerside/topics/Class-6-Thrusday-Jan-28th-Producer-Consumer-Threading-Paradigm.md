# Class #6 | Thursday Jan 30th | Producer-Consumer Threading Paradigm

> The following file contains the last of the content ever presented in the multi-threading java fundamentals 
> section of our course.
> It includes a new and mostly useful model for multithreaded applications, 
> producer-consumer model, its characteristics, synchronization, examples, etc. Additionally, we present information 
> about JavaFX concurrency using the Task class.
> Lastly, we close this chapter by talking about CompletableFuture&lt;E&gt;

<p>In the last leg of the course content for multi-threading applications, we reviewed an important paradigm called 
the producer-consumer relationship, which states that there is always a thread that produces some output, and 
another thread that consumes said output. Examples of this representation can be found throughout computer science, 
for example video and audio streaming services often use this model to handle streaming of cloud content while 
simultaneously requesting and grouping new content such that there is a continuous flow of information towards the 
user.</p>
<p>The model that we studied here, seen mostly through examples in Deitel Deitel Java Programming book, has little 
to do with these complicated applications, but rather has a lot to do with the inner workings and characteristics of 
this process.
</p>

## Producer-Consumer Thread Coordination
<p>Within the <code>concurrent and parallel programming world</code>, certain design patterns have come about to 
simplify architectures, streamline solutions, and optimize codebases that handle these types of application. One 
such design pattern is the <b><code>producer-consumer design patter</code></b></p>
<note><p>A <b><code>producer-consumer design pattern</code></b>, designates a process (thread) as a producer, and 
another one as a consumer. Through these roles, <b><code>the producer is responsible for constantly populating a shared 
memory space or data structure</code></b>, while the consumer <b><code>is in charge of taking/removing 
information from said data structure</code></b>
</p></note>
<p>One of the main issues that can come about from this implementation, given that it can support <b>multiple 
consumers, multiple produces, or both cases at the same time</b>, is the issue of memory corruption (garbling), or, 
in the broadest sense, issues with synchronization.</p>

### Granularity in Producer-Consumer Thread Synchronization
<p>
Within a multi-threaded environment, as we have discussed in previous sections, there is always the inherent thread 
of race conditions, the lost operation problem, thrashing, incorrect or misleading outputs, code that appears to 
work but is actually broken inside, etc. All of these issues are exacerbated in the case of a producer and consumer 
relationship as it is not just one thread that is trying to do a single operation on a shared buffer or data 
structure, but not two, or more threads doing different operations within it.
</p>
<p>For this reason, depending on the multiplicity of these two thread types, we can identify some clear 
complexisites that we must deal with</p>
<procedure collapsible="true" title="Producer-Consumer Issues">
<deflist>
<def title="Single Producer-Consumer Issues">
<list>
<li>If these two attempt to <b>modify or consume shared data at the same time</b>, synchronization issues might 
force some of the information to be lost</li>
<li>Due to the complexity of operations, one or the other might be more expensive and longer than the other, 
forcing one party to wait (eager thread), this leads to a <b>quick consumer that <code>processes elements 
fast and waits longer</code></b>.</li>
<li>On the other hand, if the removing or processing of the produced informatino is slower than its production, we 
have what is known as a <b><code>queue overflow issue</code></b></li>
</list>
</def>
<def title="Multiple Producer-Consumer Issues">
<p>If our application works with more than one of each type of thread, we might have these problems on larger scales,
causing significant issues in performance and synchrony.
</p>
</def>
</deflist>
</procedure>
<p>For these reasons, often there is a need for a buffer, or some data structure that is designed and capable of 
handling multiple producer and consumer threads at the same time. In addition to this, however, it is also important 
to keep a <b><code>lock around data that is shared</code> in order to prevent corruption</b>
. Moreover, one must implement <i><b><code>state-dependent operations</code>, i.e, operations whose inner 
workings depend on the shared state marked by other threads</b></i>. THis idea means that, in the case of shared 
memory and data structures, threads must wait and allow for, a) producers to produce information if there is none to 
read, or b) consumers to consume information if there is no more space to produce information to.
</p>
<p>In general then, we must make our operations granular, and think deeply about the way in which we architect our 
application and use this pattern, it is not just about writing a consumer and a producer that alter some piece of 
memory in our application, it is about enforcing rules, design patterns, and defining code that handles a shared 
buffer of memory effectively.</p>
<p>The next block presents information about granularity, its types and their pros, and cons. The idea is not to go 
too deep into granularity as it is not the concept of this talk, but there will be a listing underneath, detailing 
granularity with a bit more detail</p>
<procedure title="Synchronization Granularity in Producer-Consumer Pattern" collapsible="true">
<list columns="1" type="none">
<li>
<deflist type="full">
<def title="Granularity Definition and Types">
<list>
<li>Refers to the scope and size of synchronized code sections in concurrent operations</li>
<li>Fine-grained: Small, specific sections of code are synchronized independently</li>
<li>Coarse-grained: Larger blocks of code or entire methods are synchronized as a unit</li>
<li>State-dependent: Synchronization based on runtime conditions and buffer states</li>
<li>Hybrid approaches combining multiple granularity levels for optimal performance</li>
</list>
</def>
</deflist>
</li>
<li>
<deflist type="full">
<def title="Advantages of Different Granularity Levels">
<list>
<li>Fine-grained enables higher concurrent throughput and better resource utilization</li>
<li>Coarse-grained provides simpler implementation and easier maintenance</li>
<li>State-dependent allows for dynamic resource management and flow control</li>
<li>Multiple locks enable parallel access to different parts of shared resources</li>
<li>Selective synchronization reduces unnecessary thread blocking</li>
</list>
</def>
</deflist>
</li>
<li>
<deflist type="full">
<def title="Challenges and Limitations">
<list>
<li>Fine-grained synchronization increases code complexity and debugging difficulty</li>
<li>Multiple locks raise the risk of deadlocks and race conditions</li>
<li>Coarse-grained approach may create performance bottlenecks</li>
<li>Overhead from lock acquisition and release in fine-grained systems</li>
<li>Increased testing and verification effort required for complex synchronization</li>
</list>
</def>
</deflist>
</li>
</list>
</procedure>

#### Granularity Types ― Fine-Grained
<deflist type="full" collapsible="true">
<def title="Fine-Grained Granularity Definition">
Fine-grained granularity in producer-consumer threading refers to a <i><code>synchronization approach where 
individual operations or small sections of code are synchronized independently. This pattern uses multiple, specific 
locks to protect distinct parts of the shared resource rather than locking the entire data structure</code></i>. The 
synchronization is <i><code>applied at the lowest 
practical level</code></i>, such as individual buffer elements or specific operations like enqueue/dequeue, 
allowing maximum potential for concurrent execution while maintaining thread safety. This approach <i><code>typically involves separate locks for producers and consumers</code></i>, 
enabling them 
to operate simultaneously on different parts of the shared buffer when possible.
</def>

<def title="Advantages and Disadvantages">
<list>
<li>Advantages:
    <list>
        <li>Maximizes concurrent throughput and parallelism</li>
        <li>Reduces thread contention and waiting times</li>
        <li>Better resource utilization</li>
        <li>Improved scalability for multiple producers/consumers</li>
        <li>More efficient CPU usage in multi-core systems</li>
    </list>
</li>
<li>Disadvantages:
    <list>
        <li>Increased code complexity and maintenance difficulty</li>
        <li>Higher risk of deadlocks and race conditions</li>
        <li>More challenging to debug and test</li>
        <li>Additional overhead from managing multiple locks</li>
        <li>Requires careful design to maintain data consistency</li>
    </list>
</li>
</list>
</def>

<def title="When to Implement Fine-Grained Granularity">
<list>
<li>High contention scenarios with multiple concurrent producers and consumers</li>
<li>Performance-critical systems where minimizing thread blocking is essential</li>
<li>When the shared resource can be naturally partitioned into independent sections</li>
<li>Systems with frequent, small operations that don't require complete resource locking</li>
<li>When the overhead of synchronization significantly impacts system performance</li>
<li>In applications where different operations can safely proceed in parallel</li>
<li>When dealing with large data structures where global locks create bottlenecks</li>
</list>
</def>
</deflist>
<p>Having defined, generally, what this fine-grained system is, let us take a look at a fine-grained example for 
a producer-consumer application example that produces a song lyrics (in practice it would most likely come over the 
internet), and consumes it as a cute print message to the console</p>

```Java
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
```
<p>Running the previous code outputs this</p>

```Markdown
Starting lyrics processing...
Producer-[1pool-1-thread-1]: Added lyric -> When I find myself in times of trouble
Producer-[1pool-1-thread-1]: Buffer size is now 1
Producer-[1pool-1-thread-1]: Added lyric -> Mother Mary comes to me
Producer-[1pool-1-thread-1]: Buffer size is now 2
Producer-[1pool-1-thread-1]: Added lyric -> Speaking words of wisdom
Producer-[1pool-1-thread-1]: Buffer size is now 3
Producer-[1pool-1-thread-1]: Buffer is full, waiting...
Producer-[2pool-1-thread-2]: Buffer is full, waiting...
Consumer-[1pool-1-thread-3]: Consumed lyric -> When I find myself in times of trouble
Consumer-[1pool-1-thread-3]: Buffer size is now 2
Consumer-[2pool-1-thread-4]: Consumed lyric -> Mother Mary comes to me
Consumer-[2pool-1-thread-4]: Buffer size is now 1
Producer-[1pool-1-thread-1]: Added lyric -> Let it be
Producer-[1pool-1-thread-1]: Buffer size is now 2
Producer-[1pool-1-thread-1]: Finished producing lyrics
Producer-[2pool-1-thread-2]: Added lyric -> And in my hour of darkness
Producer-[2pool-1-thread-2]: Buffer size is now 3
Producer-[2pool-1-thread-2]: Buffer is full, waiting...
Consumer-[2pool-1-thread-4]: Consumed lyric -> Speaking words of wisdom
Consumer-[2pool-1-thread-4]: Buffer size is now 2
Consumer-[1pool-1-thread-3]: Consumed lyric -> Let it be
Consumer-[1pool-1-thread-3]: Buffer size is now 1
Producer-[2pool-1-thread-2]: Added lyric -> She is standing right in front of me
Producer-[2pool-1-thread-2]: Buffer size is now 2
Producer-[2pool-1-thread-2]: Added lyric -> Speaking words of wisdom
Producer-[2pool-1-thread-2]: Buffer size is now 3
Producer-[2pool-1-thread-2]: Buffer is full, waiting...
Consumer-[2pool-1-thread-4]: Consumed lyric -> And in my hour of darkness
Consumer-[2pool-1-thread-4]: Buffer size is now 2
Consumer-[1pool-1-thread-3]: Consumed lyric -> She is standing right in front of me
Consumer-[1pool-1-thread-3]: Buffer size is now 1
Producer-[2pool-1-thread-2]: Added lyric -> Let it be
Producer-[2pool-1-thread-2]: Buffer size is now 2
Producer-[2pool-1-thread-2]: Finished producing lyrics
Consumer-[2pool-1-thread-4]: Consumed lyric -> Speaking words of wisdom
Consumer-[2pool-1-thread-4]: Buffer size is now 1
Consumer-[1pool-1-thread-3]: Consumed lyric -> Let it be
Consumer-[1pool-1-thread-3]: Buffer size is now 0
Consumer-[1pool-1-thread-3]: Finished consuming lyrics
Consumer-[2pool-1-thread-4]: Finished consuming lyrics
Lyrics processing completed!

Process finished with exit code 0
```

#### Granularity Types ― State-dependent
<deflist type="full" collapsible="true">
<def title="State-Dependent Granularity Definition">
State-dependent granularity in producer-consumer threading <i><code>is a synchronization strategy where the locking mechanism 
adapts based on the current state of the shared resource or system conditions</code></i>. This approach implements 
<i><b><code>synchronization controls that respond to runtime conditions</code></b></i> such as buffer capacity, 
resource availability, or 
processing states. The pattern <i><b>typically uses condition variables or monitors to coordinate thread access based on 
predefined states (e.g., buffer full, buffer empty, or threshold conditions)</b></i>. This dynamic synchronization model 
ensures that threads are blocked or released based on specific state conditions rather than simple mutual exclusion.
</def>

<def title="Advantages and Disadvantages">
<list>
<li>Advantages:
    <list>
        <li>Efficient resource utilization through condition-based synchronization</li>
        <li>Better control over thread coordination and scheduling</li>
        <li>Reduced unnecessary polling and busy-waiting</li>
        <li>More natural modeling of complex producer-consumer scenarios</li>
        <li>Improved handling of varying workload conditions</li>
    </list>
</li>
<li>Disadvantages:
    <list>
        <li>Increased complexity in state management and condition handling</li>
        <li>Potential for condition-based deadlocks if not carefully designed</li>
        <li>Higher overhead for state checking and condition signaling</li>
        <li>More difficult to test all possible state transitions</li>
        <li>Can be challenging to debug state-dependent timing issues</li>
    </list>
</li>
</list>
</def>

<def title="When to Implement State-Dependent Granularity">
<list>
<li>Systems with complex buffer management requirements (e.g., bounded buffers)</li>
<li>When thread coordination depends on specific resource states or thresholds</li>
<li>Applications requiring flow control based on runtime conditions</li>
<li>Scenarios where simple mutex-based synchronization is insufficient</li>
<li>When implementing quality of service or prioritization mechanisms</li>
<li>Systems that need to handle resource exhaustion gracefully</li>
<li>When producer-consumer rates need to be balanced dynamically</li>
</list>
</def>
</deflist>
<p>Having defined this little model through our good friend Amazon Q, let us create a single examples that showcases 
its use and implementation
</p>

```Java
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
```
<p>The previous code, when run, produces an output in the following style</p>

```Markdown
From thread {pool-1-thread-1], Produced: Is this the real life?
From thread {pool-1-thread-1], Produced: Is this just fantasy?
From thread {pool-1-thread-1], Waiting: to be consumed.
From thread {pool-1-thread-2], Consumed: Is this the real life?
From thread {pool-1-thread-2], Consumed: Is this just fantasy?
From thread {pool-1-thread-2], Waiting: for data to be produced.
From thread {pool-1-thread-1], Produced: Caught in a landslide,
From thread {pool-1-thread-1], Produced: No escape from reality.
From thread {pool-1-thread-1], Waiting: to be consumed.
From thread {pool-1-thread-2], Consumed: Caught in a landslide,
From thread {pool-1-thread-2], Consumed: No escape from reality.
From thread {pool-1-thread-2], Waiting: for data to be produced.
From thread {pool-1-thread-1], Produced: Open your eyes,
From thread {pool-1-thread-1], Produced: Look up to the skies and see...
From thread {pool-1-thread-2], Consumed: Open your eyes,
From thread {pool-1-thread-2], Consumed: Look up to the skies and see...
Producer-Consumer demo finished.

Process finished with exit code 0
```

#### Granularity Types ― Coarse-Grained
<p>The last granularity type that is going to be studied (not at depth) in this section is coarse grained 
granularity.</p>
<deflist type="full" collapsible="true">
<def title="Coarse-Grained Granularity Definition">
Coarse-grained granularity in producer-consumer threading represents a synchronization approach <i><b>where larger 
sections of code or entire methods are synchronized as a single unit</b></i>. This pattern 
typically <i><b><code>employs a single lock or synchronization mechanism to protect the entire shared resource or a substantial 
portion of it</code></b></i>. Rather than managing multiple fine-grained locks, it implements broader synchronization 
boundaries 
that encompass complete operations or transactions. The approach <b><code>prioritizes simplicity and safety over maximum 
concurrency</code></b>, using mechanisms like synchronized 
methods or 
blocks that protect entire sequences of operations at once.
</def>
<def title="Advantages and Disadvantages">
<list>
<li>Advantages:
    <list>
        <li>Simpler implementation and easier to understand code structure</li>
        <li>Reduced risk of deadlocks due to fewer lock interactions</li>
        <li>Easier to maintain and debug synchronization logic</li>
        <li>Better guarantee of data consistency across operations</li>
        <li>Lower overhead from lock management and coordination</li>
    </list>
</li>
<li>Disadvantages:
    <list>
        <li>Reduced concurrent throughput due to broader locking scope</li>
        <li>Higher thread contention and longer wait times</li>
        <li>Less efficient utilization of multi-core systems</li>
        <li>Potential performance bottlenecks under high load</li>
        <li>Limited scalability with increasing number of threads</li>
    </list>
</li>
</list>
</def>

<def title="When to Implement Coarse-Grained Granularity">
<list>
<li>Simple producer-consumer scenarios with moderate concurrency requirements</li>
<li>When operation atomicity is more critical than maximum throughput</li>
<li>Systems where simplicity and maintainability are primary concerns</li>
<li>Applications with low to moderate thread contention</li>
<li>When operations on shared resources are naturally atomic</li>
<li>In prototyping phases where quick implementation is needed</li>
<li>When the overhead of fine-grained synchronization outweighs its benefits</li>
</list>
</def>
</deflist>
<p>With this definition, lets take a look at the example above, but this time in the coarse-grained methodology</p>


```Java
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
public class FineGrainedProducerConsumerDemo {
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
                for (int i = 0; i < 6; i++) { 
                // Consume the same number of lyrics produced
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
```
<p>The previous block of code, outputs these results</p>

```Markdown
From thread {pool-1-thread-1], Produced: Is this the real life?
From thread {pool-1-thread-1], Produced: Is this just fantasy?
From thread {pool-1-thread-1], Waiting: to be consumed.
From thread {pool-1-thread-2], Consumed: Is this the real life?
From thread {pool-1-thread-2], Consumed: Is this just fantasy?
From thread {pool-1-thread-2], Waiting: for data to be produced.
From thread {pool-1-thread-1], Produced: Caught in a landslide,
From thread {pool-1-thread-1], Produced: No escape from reality.
From thread {pool-1-thread-1], Waiting: to be consumed.
From thread {pool-1-thread-2], Consumed: Caught in a landslide,
From thread {pool-1-thread-2], Consumed: No escape from reality.
From thread {pool-1-thread-2], Waiting: for data to be produced.
From thread {pool-1-thread-1], Produced: Open your eyes,
From thread {pool-1-thread-1], Produced: Look up to the skies and see...
From thread {pool-1-thread-2], Consumed: Open your eyes,
From thread {pool-1-thread-2], Consumed: Look up to the skies and see...
Fine-grained Producer-Consumer demo finished.

Process finished with exit code 0

```

### Characteristics of the Producer-Consumer Thread Model
<p>With the previous code blocks and information, we effectively reviewed a section of Java programming which 
showcased a series of useful classes, and methodologies that, to some extent, go outside the scope of this class and 
this study session (granularity is not a topic in this class, after all we are already looking at databases). 
Nevertheless, the previous code showcases that java allows you to work both incorrectly and correctly, generally 
correctly, as their methods are meant to work unless you seriously kill them by introducing contention or deadlocks. 
</p>
<p>Admittedly, this happened to me in one of the sections above as fine-grained requires multiple locks, and 
implementing those with simple synchronized blocks creates contention problems right away. Rather the idea of lock 
conditions, acquiring reentrant locks, etc., made the code more concise, concurrency-oriented, and cleaner. 
Nevertheless, we saw a series of classes that I would like to review later on when this file is done, i.e., at the 
end of this section we will review Reentrant Locks, conditions, etc.</p>
<p>For now, lets take a look at the main characteristics of the producer-consumer thread model, pros, cons, and use 
cases!</p>

<deflist type="full" collapsible="true">
<def title="Producer-Consumer Pattern Characteristics" collapsible="true">
<list>
<li><strong>Shared Buffer Management</strong>: Implements a shared buffer or queue that mediates data transfer between producer and consumer threads</li>
<li><strong>Thread Synchronization</strong>: Uses synchronization mechanisms to ensure thread-safe access to shared resources</li>
<li><strong>Flow Control</strong>: Manages production and consumption rates through blocking or signaling mechanisms</li>
<li><strong>Decoupling</strong>: Separates data production from consumption, allowing independent operation of both components</li>
<li><strong>Resource Management</strong>: Handles buffer capacity limits and prevents overflow/underflow conditions</li>
</list>
</def>
</deflist>

<deflist type="full" collapsible="true">
<def title="Producer-Consumer Pattern Benefits and Drawbacks" collapsible="true">
<list>
<li><strong>Advantages</strong>:
    <list>
        <li>Enables efficient parallel processing and workload distribution</li>
        <li>Provides natural load balancing between producers and consumers</li>
        <li>Improves system modularity and maintainability</li>
        <li>Handles varying processing speeds between components</li>
        <li>Supports multiple producer and consumer configurations</li>
    </list>
</li>
<li><strong>Disadvantages</strong>:
    <list>
        <li>Complexity in implementing proper synchronization</li>
        <li>Potential for deadlocks and race conditions</li>
        <li>Memory overhead from buffer management</li>
        <li>Performance impact from synchronization mechanisms</li>
        <li>Challenging to debug and test thoroughly</li>
    </list>
</li>
</list>
</def>
</deflist>

<deflist type="full" collapsible="true">
<def title="Producer-Consumer Pattern Use Cases">
<list>
<li><strong>Event Processing Systems</strong>: Ideal for handling event queues where events are generated by multiple sources and processed by multiple handlers</li>
<li><strong>Data Pipeline Processing</strong>: Perfect for scenarios where data needs to be processed through multiple stages with different processing rates</li>
<li><strong>Task Scheduling Systems</strong>: Suitable for managing task queues where tasks are generated and executed asynchronously</li>
<li><strong>Log Processing Applications</strong>: Effective for systems where log entries are generated continuously and need to be processed or stored</li>
<li><strong>Resource Pool Management</strong>: Useful for managing pools of resources where allocation and deallocation need to be coordinated</li>
<li><strong>Message Queuing Systems</strong>: Essential for implementing message brokers or communication systems with buffering requirements</li>
<li><strong>Batch Processing Operations</strong>: Beneficial for systems that need to collect and process data in batches</li>
</list>
</def>
</deflist>

### Examples of Producer-Consumer Thread Models
<p>Having checked some examples of the producer-consumer thread design pattern, it is time for us to take a look at 
three examples that come from the Deitel, Deitel book that we are using to study this section. These are some 
relevant examples as they present information crucial to our understanding of this pattern</p>

#### Single Value Buffer
<p>
This first example, contained and spread around chapter 23.5, presents a code example that works around a single 
value unsynchronized buffer. In this particular example, a producer thread creates a single value at the time, from 
1 to ten, and attempts to place it within the buffer. Once the value is placed the secondary consumer thread 
attempts to read the value and add it to a total. The sum of these values should be <b>55</b>, however, as we know 
due to the use of non-synchronized methods, we should never expect this value to appear.
</p>
<p>The code example in this chapter showcases how a program can be written completely incorrectly even though it is 
using this design pattern. The reason that this code example is important is that it <b><code>showcases 
what can go wrong when we apply multithreading design patterns without considering memory management, 
synchronization and locks.</code></b></p>

```Java

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
            System.out.printf("\t\t\t\2d\n", sum);
         } 
         catch (InterruptedException exception) {
            Thread.currentThread().interrupt(); 
         } 
      } 

      System.out.printf("\n\s \d\n\s\n", 
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
         "------\t\t-----\t---------------\t---------------\n\n");

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
      System.out.printf("Consumer reads\t\2d", buffer);
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
            System.out.printf("\t\2d\n", sum);
         } 
         catch (InterruptedException exception) {
            Thread.currentThread().interrupt(); 
         } 
      } 

      System.out.printf(
         "Producer done producing%nTerminating Producer\n");
   } 
} 
```

<p>The previous code produces an output similar to that of this.</p>

```Text

Action		Value	Sum of Produced	Sum of Consumed
------		-----	---------------	---------------

Producer writes	 1	 1
Producer writes	 2	 3
Consumer reads	 2			 2
Consumer reads	 2			 4
Consumer reads	 2			 6
Producer writes	 3	 6
Consumer reads	 3			 9
Producer writes	 4	10
Consumer reads	 4			13
Consumer reads	 4			17
Producer writes	 5	15
Producer writes	 6	21
Consumer reads	 6			23
Consumer reads	 6			29
Producer writes	 7	28
Producer writes	 8	36
Consumer reads	 8			37
Consumer reads	 8			45

Consumer read values totaling 45
Terminating Consumer
Producer writes	 9	45
Producer writes	10	55
Producer done producing
Terminating Producer

```

<p>This is what happens when we are working with a series of unsynchronized methods. Generally, since there are no 
synchronization rules, there are issues with reading and writing to a shared buffered state, notice how some methods 
read the values correctly, but on some other cases the consumer appears to read a value that is not there at all, 
while the producer is updating the values correctly, the issue lies with the interleaving of these two in our system 
and the synchronization issues with the writing onto the shared sum of values buffer.
</p>
<p>From the methods displayed above, nothing comes to mind that is different or new, the only remarkable thing is 
that we are using <b>executors</b> to handle thread start, joining, and concurrent considerations.</p>

#### ArrayBlockingQueue Buffer
<p>The next example we reviewed in class is the ArrayBlockingQueue buffer implementation. The ArrayBlockingQueue is 
part of a series of classes I had wanted to cover later on this section but I will do not, that come in the 
concurrent package within java base. These are specialized data structures that implement the concept of blocking 
queues, or some blocking mechanism to provide a layer of synchronization to Threads.</p>
<note><p>All blocking data structures pick up from the interface <i><b><code>BlockingQueue&lt;
E&gt;</code></b></i>, that is designed to handle both operation from Collection, but also operations that 
tolerate <b>waiting until there is a space in the queue to place or remove data</b>. These effectively then 
support blocking (locks for memory) for operations like put or take</p>
<p>There are five versions of this <b><code> LinkedBlockingQueue, ArrayBlockingQueue, SynchronousQueue, 
PriorityBlockingQueue, and DelayQueue</code></b></p></note>

<p>The idea of this implementation, then, is to showcase how a class can implement the synchronization methods that 
we require for a producer-consumer implementation for us, showing that Java has various functionalities that are 
already built in to make programming in multithreading applications easier. The following is the ordered code 
segments from the book, formatted to be run in a single java file</p>

```
Java
import java.security.SecureRandom;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Fig. 23.9: Buffer.java
// Buffer interface specifies methods 
// called by Producer and Consumer.

interface Buffer {
   // place int value into Buffer
   public void blockingPut(int value) 
            throws InterruptedException; 

   // return int value from Buffer
   public int blockingGet() 
            throws InterruptedException; 
} 

// Fig. 23.10: Producer.java
// Producer with a run method that 
// inserts the values 1 to 10 in buffer.
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
         } 
         catch (InterruptedException exception) {
            Thread.currentThread().interrupt(); 
         } 
      } 

      System.out.printf(
         "Producer done producing\nTerminating Producer\n");
   } 
} 

// Fig. 23.11: Consumer.java
// Consumer with a run method that 
//loops, reading 10 values from buffer.
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
         } 
         catch (InterruptedException exception) {
            Thread.currentThread().interrupt(); 
         } 
      } 

      System.out.printf("\n\s \d\n\s\n", 
         "Consumer read values totaling", sum, "Terminating Consumer");
   } 
} 

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
      System.out.printf("\s\2d\t\s\d\n", "Producer writes ", value,
         "Buffer cells occupied: ", buffer.size());
   }

   // return value from buffer
   @Override
   public int blockingGet() throws InterruptedException {
      int readValue = buffer.take(); // remove value from buffer
      System.out.printf("\s \2d\t\s\d\n", "Consumer reads ",
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
```
<p>The output of this code is.</p>

```Markdown
Producer writes  1	Buffer cells occupied: 1
	 1
Consumer reads   1	Buffer cells occupied: 0
			 1
Producer writes  2	Buffer cells occupied: 1
	 3
Consumer reads   2	Buffer cells occupied: 0
			 3
Producer writes  3	Buffer cells occupied: 1
	 6
Consumer reads   3	Buffer cells occupied: 0
			 6
Producer writes  4	Buffer cells occupied: 1
	10
Consumer reads   4	Buffer cells occupied: 0
			10
Producer writes  5	Buffer cells occupied: 1
	15
Consumer reads   5	Buffer cells occupied: 0
			15
Producer writes  6	Buffer cells occupied: 1
	21
Consumer reads   6	Buffer cells occupied: 0
			21
Producer writes  7	Buffer cells occupied: 1
	28
Consumer reads   7	Buffer cells occupied: 0
			28
Producer writes  8	Buffer cells occupied: 1
	36
Consumer reads   8	Buffer cells occupied: 0
			36
Producer writes  9	Buffer cells occupied: 1
	45
Consumer reads   9	Buffer cells occupied: 0
			45
Producer writes 10	Buffer cells occupied: 1
	55
Producer done producing
Terminating Producer
Consumer reads  10	Buffer cells occupied: 0
			55

Consumer read values totaling 55
Terminating Consumer
```
<p>As can be noted, the usage of this internal class from Java has not only reduced the complexity of the code that 
we have to write, but it also guarantees that our output follows a logical execution and is factually correct even 
if we execute threads independently or through an executor service. Again, the only different between the two 
versions is the usage of a blocking queue, and an executor service to handle the threads internally.</p>
<p>The last version of this code in this section will showcase how to to work with more advanced methods, and locks.</p>

#### ArrayBlockingQueue Buffer By Hand
<p>This example was the second to last example we saw in class. We saw here how to use a complex 
implementation of a buffer where we had a buffer size and we handled how threads iterated over our buffer through 
methods defined or locks. These methods are part of the Object class (<code>wait(), notify(), and notifyAll()</code>)
. THe use of these methods guarantees that the code will pause and resume execution depending on the state within 
our code, this being an example of a coarse-grained implementation of the producer-consume granularity principle.
</p>
<p>The idea in this example is that we will not only use the methods defined for thread control and synchronization, 
but we will also use guards (conditions and base parameters of a coarse-grained implementation), all in an effort to 
control the access of these producer and consumer threads to our shared buffer. Before we go deep into the code, we 
should note two things
</p>
<note><p>To move away from an <i><b>non-synchronized execution methodology</b></i>, we should first identify 
variables (state) that might be shared, and attempt to use either synchronized methods or blocks to handle these 
data points
</p></note>
<note><p>In Java, within all sections of code we can call methods like <i><b><code>wait(), 
notify(), and notifyAll() </code> to <code>force a thread to pause its execution until 
notified, notify any waiting thread to resume execution, and notify all waiting threads to 
resume execution</code></b></i>. In general these methods are meant to be used to move a thread from the 
Runnable (Running state) to the waiting state.</p></note>

```Java

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
         System.out.println("Producer tries to write."); // for demo only
         displayState("Buffer full. Producer waits."); // for demo only
         wait();
      } 
        
      buffer = value; // set new buffer value
        
      // indicate producer cannot store another value 
      // until consumer retrieves current buffer value
      occupied = true;                                
        
      displayState("Producer writes " + buffer); // for demo only
      
      notifyAll(); // tell waiting thread(s) to enter runnable state
   } // end method blockingPut; releases lock on SynchronizedBuffer 
    
   // return value from buffer
   @Override
   public synchronized int blockingGet() throws InterruptedException {
      // while no data to read, place thread in waiting state
      while (!occupied) {
         // output thread information and buffer information, then wait
         System.out.println("Consumer tries to read."); // for demo only
         displayState("Buffer empty. Consumer waits."); // for demo only
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
      System.out.printf("\-40s\d\t\t\b\n\n", operation, buffer, occupied);
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

      System.out.printf("\-40s\s\t\t\s\n\-40s\s\n\n", "Operation", 
         "Buffer", "Occupied", "---------", "------\t\t--------");

      // execute the Producer and Consumer tasks
      executorService.execute(new Producer(sharedLocation));
      executorService.execute(new Consumer(sharedLocation));

      executorService.shutdown();
      executorService.awaitTermination(1, TimeUnit.MINUTES); 
   }
}
```
<p>The output of the previous code is.</p>

```Markdown
Operation                               Buffer		Occupied
---------                               ------		--------

Producer writes 1                       1		true

	 1
Producer tries to write.
Buffer full. Producer waits.            1		true

Consumer reads 1                        1		false

			 1
Producer writes 2                       2		true

	 3
Consumer reads 2                        2		false

			 3
Producer writes 3                       3		true

	 6
Consumer reads 3                        3		false

			 6
Producer writes 4                       4		true

	10
Producer tries to write.
Buffer full. Producer waits.            4		true

Consumer reads 4                        4		false

			10
Producer writes 5                       5		true

	15
Producer tries to write.
Buffer full. Producer waits.            5		true

Consumer reads 5                        5		false

			15
Producer writes 6                       6		true

	21
Consumer reads 6                        6		false

			21
Consumer tries to read.
Buffer empty. Consumer waits.           6		false

Producer writes 7                       7		true

	28
Consumer reads 7                        7		false

			28
Producer writes 8                       8		true

	36
Producer tries to write.
Buffer full. Producer waits.            8		true

Consumer reads 8                        8		false

			36
Producer writes 9                       9		true

	45
Producer tries to write.
Buffer full. Producer waits.            9		true

Consumer reads 9                        9		false

			45
Producer writes 10                      10		true

	55
Producer done producing
Terminating Producer
Consumer reads 10                       10		false

			55

Consumer read values totaling 55
Terminating Consumer

Process finished with exit code 0
```
<p>Again, as with our discussion on coarse-grained granularity, we can see that this class implements a series of 
methods from the Buffer interface in such a way that they use both <b><code>synchronized methods, and 
contorl flags (i.e., occupied variable)</code></b> to control method access, state access, and memory locks for each 
thread. Since they use a cached thread pool, the most logical idea is that the method uses one thread for the 
consumer and another one for the producer. However, this then means that the program needs to handle their state to 
work properly. To do this, the program implements the control flag occupied and uses it to determine whether to 
notify threads to wake up, or send the current thread to sleep.</p>
<p>The methods used for these two are <code>notifyAll(), and wait() respectively</code>, given that the first 
notifies all threads to wake up, and wait tells the current thread to sleep</p>
<warning><p>Although we can think of this as two threads running at any given time, the use of the while 
loop for condition checking guarantees that if more than one thread is created at any given moment, the 
condition is checked for all threads.</p></warning>

#### Bounded Buffer
<p>The last example that was presented in the book was a new, and improved, version of the ArrayBlockingQueue buffer 
with a circular buffer that had three positions, being capable of handling both a Read and Writer Index and their 
positions independently. The idea of this example was that internally, these two consumer and producer threads would 
have to communicate with the shared memory and determine where the read and write indexes are based on internally 
defined values that are updated with each read and write operation. Let us take a look at the code at hand so we can 
see it closely 
</p>

```Java

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Fig. 23.18: CircularBuffer.java
// Synchronizing access to a shared three-element bounded buffer.
class CircularBuffer implements Buffer {
   private final int[] buffer = {-1, -1, -1}; // shared buffer

   private int occupiedCells = 0; // count number of buffers used
   private int writeIndex = 0; // index of next element to write to
   private int readIndex = 0; // index of next element to read
   
   // place value into buffer
   @Override
   public synchronized void blockingPut(int value)
      throws InterruptedException {               
   
      // wait until buffer has space available, then write value; 
      // while no empty locations, place thread in blocked state  
      while (occupiedCells == buffer.length) {                    
         System.out.printf("Buffer is full. Producer waits.%n");  
         wait(); // wait until a buffer cell is free              
      }                                            

      buffer[writeIndex] = value; // set new buffer value

      // update circular write index
      writeIndex = (writeIndex + 1) % buffer.length;

      ++occupiedCells; // one more buffer cell is full
      displayState("Producer writes " + value);
      notifyAll(); // notify threads waiting to read from buffer
   }
    
   // return value from buffer
   @Override
   public synchronized int blockingGet() throws InterruptedException {
      // wait until buffer has data, then read value;
      // while no data to read, place thread in waiting state
      while (occupiedCells == 0) { 
         System.out.printf("Buffer is empty. Consumer waits.%n");
         wait(); // wait until a buffer cell is filled
      } 

      int readValue = buffer[readIndex]; // read value from buffer

      // update circular read index
      readIndex = (readIndex + 1) % buffer.length;

      --occupiedCells; // one fewer buffer cells are occupied
      displayState("Consumer reads " + readValue);
      notifyAll(); // notify threads waiting to write to buffer

      return readValue;
   } 
    
   // display current operation and buffer state
   public synchronized void displayState(String operation) {
      // output operation and number of occupied buffer cells
      System.out.printf("\s\s\d)\n\s", operation, 
         " (buffer cells occupied: ", occupiedCells, "buffer cells:  ");

      for (int value : buffer) {
         System.out.printf(" \2d  ", value); // output values in buffer
      }

      System.out.printf("%n               ");

      for (int i = 0; i < buffer.length; i++) {
         System.out.print("---- ");
      }

      System.out.printf("%n               ");

      for (int i = 0; i < buffer.length; i++) {
         if (i == writeIndex && i == readIndex) {
            System.out.print(" WR"); // both write and read index
         }
         else if (i == writeIndex) {
            System.out.print(" W   "); // just write index  
         }
         else if (i == readIndex) {
            System.out.print("  R  "); // just read index
         }
         else {
            System.out.print("     "); // neither index
         }
      } 

      System.out.printf("\n\n");
   } 
}
// Fig. 23.19: CircularBufferTest.java
// Producer and Consumer threads correctly 
// manipulating a circular buffer.


public class CircularBufferTest {
   public static void main(String[] args) throws InterruptedException {
      // create new thread pool 
      ExecutorService executorService = Executors.newCachedThreadPool();

      // create CircularBuffer to store ints               
      CircularBuffer sharedLocation = new CircularBuffer();

      // display the initial state of the CircularBuffer
      sharedLocation.displayState("Initial State");

      // execute the Producer and Consumer tasks
      executorService.execute(new Producer(sharedLocation));
      executorService.execute(new Consumer(sharedLocation));

      executorService.shutdown();
      executorService.awaitTermination(1, TimeUnit.MINUTES); 
   }
}

```

<p>The output of the previous code is.</p>

```Text

Initial State (buffer cells occupied: 0)
buffer cells:   -1   -1   -1  
               ---- ---- ---- 
                WR          

Buffer is empty. Consumer waits.
Producer writes 1 (buffer cells occupied: 1)
buffer cells:    1   -1   -1  
               ---- ---- ---- 
                 R   W        

	 1
Consumer reads 1 (buffer cells occupied: 0)
buffer cells:    1   -1   -1  
               ---- ---- ---- 
                     WR     

			 1
Buffer is empty. Consumer waits.
Producer writes 2 (buffer cells occupied: 1)
buffer cells:    1    2   -1  
               ---- ---- ---- 
                      R   W   

	 3
Consumer reads 2 (buffer cells occupied: 0)
buffer cells:    1    2   -1  
               ---- ---- ---- 
                          WR

			 3
Producer writes 3 (buffer cells occupied: 1)
buffer cells:    1    2    3  
               ---- ---- ---- 
                W          R  

	 6
Producer writes 4 (buffer cells occupied: 2)
buffer cells:    4    2    3  
               ---- ---- ---- 
                     W     R  

	10
Consumer reads 3 (buffer cells occupied: 1)
buffer cells:    4    2    3  
               ---- ---- ---- 
                 R   W        

			 6
Consumer reads 4 (buffer cells occupied: 0)
buffer cells:    4    2    3  
               ---- ---- ---- 
                     WR     

			10
Producer writes 5 (buffer cells occupied: 1)
buffer cells:    4    5    3  
               ---- ---- ---- 
                      R   W   

	15
Consumer reads 5 (buffer cells occupied: 0)
buffer cells:    4    5    3  
               ---- ---- ---- 
                          WR

			15
Producer writes 6 (buffer cells occupied: 1)
buffer cells:    4    5    6  
               ---- ---- ---- 
                W          R  

	21
Consumer reads 6 (buffer cells occupied: 0)
buffer cells:    4    5    6  
               ---- ---- ---- 
                WR          

			21
Producer writes 7 (buffer cells occupied: 1)
buffer cells:    7    5    6  
               ---- ---- ---- 
                 R   W        

	28
Producer writes 8 (buffer cells occupied: 2)
buffer cells:    7    8    6  
               ---- ---- ---- 
                 R        W   

	36
Consumer reads 7 (buffer cells occupied: 1)
buffer cells:    7    8    6  
               ---- ---- ---- 
                      R   W   

			28
Producer writes 9 (buffer cells occupied: 2)
buffer cells:    7    8    9  
               ---- ---- ---- 
                W     R       

	45
Producer writes 10 (buffer cells occupied: 3)
buffer cells:   10    8    9  
               ---- ---- ---- 
                     WR     

	55
Producer done producing
Terminating Producer
Consumer reads 8 (buffer cells occupied: 2)
buffer cells:   10    8    9  
               ---- ---- ---- 
                     W     R  

			36
Consumer reads 9 (buffer cells occupied: 1)
buffer cells:   10    8    9  
               ---- ---- ---- 
                 R   W        

			45
Consumer reads 10 (buffer cells occupied: 0)
buffer cells:   10    8    9  
               ---- ---- ---- 
                     WR     

			55

Consumer read values totaling 55
Terminating Consumer

```
<p>Effectively then, we can see that we can create our own buffers and manage synchronization on our own with simple 
structures so long as we use the correct keyword and control operations thoroughly.
</p>


### Locks, Reentrant Locks, and Conditions
<p>Before we move onto Multithreading in JavaFX (A topic that was covered briefly in class), we should take a look 
at the methodology that Java offers to handle fine-grained synchronization. These classes, <b><code>Lock, 
ReentrantLock, Condition
</code></b>, among others, allow the programmer to control specifically how a program executes, and how the producer 
consumer relationship should work with respect to halting conditions (<code>waiting transitions</code>), and 
runnning state transitions.
</p>
<p>To this end, we will discuss each of these classes briefly and list some of their useful methods with a small 
description, for a more thorough view of them, refer back to the Java Specification API Overview webpage.</p>

<deflist collapsible="true" type="full">
<def title="Java.util.concurrent.locks">
<p>This is the base package where most interface and classes dedicated to thread synchronization lie. These classes 
are meant to be used as a framework for locking and waiting for conditions in a way that is distinct from the 
built-in synchronization methods or blocks, or monitor locks. </p>
</def>
<def title="Lock Interface"><p>TheLock interface is meant to <i>"support locking disciplines that differ 
in semantics (reentrant, air, etc), and that can be used in non-block-structured contexts including 
hand-over-hand and lock reordering algorithms"</i></p>
<p>The main implementation of this interface is <b><code>ReentrantLock</code></b>. Locks are generally 
preffered when the implementation has to do with precise control over threads, and require not just the simple 
single-thread-modification rule that monitor locks bring about.
</p>
<p>Internally, the main implementation comes in the form of ReentrantLocks, a class that has the basic 
functioning of a monitor lock, but with various additional methods that allow you to resolve contention easily (by a 
parameter on its constructor), and methods to investigate ownership on the fly.
</p></def>
<def title="Conditions">
<p>Condition is a class meant to replace, and abstract away, the methodology used by monitor locks, i.e. 
<code>wait, notify, and notifyAll</code>, with methods like <code>await(), and signal/signalAll()</code>. </p>
<note>Whenever we replace a synchronized block or method with Locks, we should also replace monitor locks with 
Conditions</note>
</def>
</deflist>
<p>Lastly,let us take a look at an example for this fine-grained thread implementation for a Flight control 
system where the produces simulates inbound planes attempting to land, the consumer a controller ordering the planes 
to land, and the runway a concurrent array where each plane has to be inputted in an order from less remaining fuel 
to more remaining fuel.
</p>

```Java
package PracticeModule.MultiThreadingProgramming;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FlightControlProducerConsumerExample {

    public static class Airplane{
        private int remainingFuelInMinutes;
        private String airplanePilotName;
        private String airplaneAirline;

        public Airplane(int remainingFuelInMinutes, String airplanePilotName, String airplaneAirline) {
            this.remainingFuelInMinutes = remainingFuelInMinutes;
            this.airplanePilotName = airplanePilotName;
            this.airplaneAirline = airplaneAirline;
        }
        public int getRemainingFuelInMinutes() {
            return remainingFuelInMinutes;
        }
        public void setRemainingFuelInMinutes(int remainingFuelInMinutes) {
            this.remainingFuelInMinutes = remainingFuelInMinutes;
        }
        public String getAirplanePilotName() {
            return airplanePilotName;
        }
        public void setAirplanePilotName(String airplanePilotName) {
            this.airplanePilotName = airplanePilotName;
        }
        public String getAirplaneAirline() {
            return airplaneAirline;
        }
        public void setAirplaneAirline(String airplaneAirline) {
            this.airplaneAirline = airplaneAirline;
        }

        @Override
        public String toString() {
            return "Airplane{" +
                    "remainingFuelInMinutes=" + remainingFuelInMinutes +
                    ", airplanePilotName='" + airplanePilotName + '\'' +
                    ", airplaneAirline='" + airplaneAirline + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !obj.getClass().equals(this.getClass())){return false;}
            else if (obj == this) {return true;}
            else {
                Airplane another = (Airplane) obj;
                return this.getAirplaneAirline().equals(another.getAirplaneAirline());
            }
        }
    }

    public static class Runway{

        private final ArrayDeque<Airplane> runwayPlanesThatLanded =
                new ArrayDeque<>(2);
        private final AtomicInteger runwayTotalPlanesLanded = new AtomicInteger(0);
        private final Lock lockingMechanismForRunway = new ReentrantLock(true);
        private final Condition conditionForRunwaySize = lockingMechanismForRunway.newCondition();
        private final Condition conditionForRunwayEmpty = lockingMechanismForRunway.newCondition();


        public void landAirplaneOnRunway(Airplane planeReadyToLand){
            //! Acquire lock
            lockingMechanismForRunway.lock();
            try{
                //! Add plane into the runway if there is space for him
                while (runwayPlanesThatLanded.size() == 2){
                    displayMessage("Runway is full, waiting for runway to be empty");
                    conditionForRunwaySize.await();
                }

                //! If the condition is over then we can land a plane
                runwayPlanesThatLanded.add(planeReadyToLand);
                displayMessage("Plane " + planeReadyToLand.getAirplaneAirline() + " landed on " +
                                       "runway");
                runwayTotalPlanesLanded.incrementAndGet();
                //! Notify all waiting threads because it was empty that they can come and
                //! register a plane
                conditionForRunwayEmpty.signalAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lockingMechanismForRunway.unlock();
            }
        }

        public void registerAnAirplane(){
            //! Acquire lock
            lockingMechanismForRunway.lock();
            try{
                //! Wait until there is a plane on the runway
                while (runwayPlanesThatLanded.isEmpty()){
                    displayMessage("Runway is empty, waiting for runway to be full");
                    conditionForRunwayEmpty.await();
                }

                //! If the condition is over then we can register a plane
                Airplane planeToRegister = runwayPlanesThatLanded.pollFirst();
                displayMessage("Plane " + planeToRegister.getAirplaneAirline() + " registered");
                runwayTotalPlanesLanded.decrementAndGet();
                //! Notify all waiting threads because it was full that they can come and
                //! land a plane
                conditionForRunwaySize.signalAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lockingMechanismForRunway.unlock();
            }
        }


        public synchronized void displayMessage(String msg){
            System.out.println(msg);
        }
    }

    public static class ProducerConsumerImplementations{

        private final List<Airplane> planesToLandHolder;
        private final Runway runway = new Runway();
        private volatile boolean fuelReductionActive =true;
        private Thread fuelReductionThread;

        public ProducerConsumerImplementations(ArrayList<Airplane> planesToLand) {
            //! Fill up internal data based on a filtering operation over planesToLand
            this.planesToLandHolder = Collections.synchronizedList(planesToLand);
        }

        public void startFuelReduction() {
            fuelReductionThread = new Thread(this::drippingFuel, "FuelReductionThread");
            fuelReductionThread.start();

        }

        public void stopFuelReduction() {
            fuelReductionActive = false;
            if (fuelReductionThread != null) {
                fuelReductionThread.interrupt();
            }
        }

        //! With the data separated, lets categorize them in the consumer based on a roundrobin
        //! style. While I am not familiar with the correct implementation, I am going to
        // prioritize removing elements from criticalTime rather than the rest.

        public class PlaneRunwayProducer implements Runnable {

            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    Airplane planeToLand = null;

                    synchronized (planesToLandHolder) {
                        if (planesToLandHolder.isEmpty()) {
                            break;
                        }
                        // Priority: Critical planes first
                        for (Iterator<Airplane> iterator = planesToLandHolder.iterator();
                             iterator.hasNext(); ) {
                            Airplane plane = iterator.next();
                            if (plane.getRemainingFuelInMinutes() <= 5) { // CRITICAL_THRESHOLD
                                planeToLand = plane;
                                iterator.remove();
                                break;
                            }
                        }

                        // If no critical planes, take from risk queue
                        if (planeToLand == null) {
                            for (Iterator<Airplane> iterator = planesToLandHolder.iterator();
                                 iterator.hasNext(); ) {
                                Airplane plane = iterator.next();
                                if (plane.getRemainingFuelInMinutes() <= 15) { // RISK_THRESHOLD
                                    planeToLand = plane;
                                    iterator.remove();
                                    break;
                                }
                            }
                        }
                    }

                    if (planeToLand != null) {
                        runway.landAirplaneOnRunway(planeToLand);
                        runway.displayMessage("Plane landed: " + planeToLand);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // Optional: Sleep or wait based on runway availability
                    planesToLandHolder.remove(planeToLand);
                }
            }
        }

        public class PlaneRunwayConsumer implements Runnable{

            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        runway.registerAnAirplane();
                        Thread.sleep(2000);

                    }
                } catch (Exception e) {
                    runway.displayMessage("Consumer interrupted.");
                }
            }
        }


        /**
         * Continuously reduces the remaining fuel of each airplane by one every second.
         */
        public void drippingFuel() {
            while (fuelReductionActive && !Thread.currentThread().isInterrupted()) {
                try {
                    synchronized (planesToLandHolder) {
                        if (planesToLandHolder.isEmpty()){
                            fuelReductionActive = false;
                        }
                        for (Airplane airplane : planesToLandHolder) {
                            if (airplane.getRemainingFuelInMinutes() > 0) {
                                airplane.setRemainingFuelInMinutes(airplane.getRemainingFuelInMinutes() - 1);
                                // Optionally, log or handle planes that reach zero fuel
                                if (airplane.getRemainingFuelInMinutes() == 0) {
                                    runway.displayMessage("Plane out of fuel: " + airplane);
                                    runway.landAirplaneOnRunway(airplane);
                                }
                            }
                        }
                    }
                    // Wait for 1 second before next iteration
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    runway.displayMessage("Fuel reduction thread interrupted.");
                }
            }
        }
    }

    public static void main(String[] args) {
        // Initialize Runway
        FlightControlProducerConsumerExample.Runway runway = new FlightControlProducerConsumerExample.Runway();

        // Initialize planes
        ArrayList<FlightControlProducerConsumerExample.Airplane> initialPlanes = new ArrayList<>();
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(10, "John Doe", "AirlineA"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(20, "Jane Smith", "AirlineB"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(5, "Mike Johnson", "AirlineC"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(15, "Alice Brown", "AirlineD"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(25, "Bob Martin", "AirlineE"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(3, "Charlie White", "AirlineF"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(8, "David Black", "AirlineG"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(18, "Ella Green", "AirlineH"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(12, "Frank Yellow", "AirlineI"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(28, "Grace Purple", "AirlineJ"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(4, "Hank Blue", "AirlineK"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(22, "Ivy Orange", "AirlineL"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(6, "Jack Red", "AirlineM"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(16, "Karen Gray", "AirlineN"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(30, "Leo Pink", "AirlineO"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(2, "Mia Violet", "AirlineP"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(9, "Noah Brown", "AirlineQ"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(14, "Olivia Cyan", "AirlineR"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(23, "Paul Gold", "AirlineS"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(7, "Quincy White", "AirlineT"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(19, "Rachel Amber", "AirlineU"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(27, "Sam Silver", "AirlineV"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(1, "Tom Cash", "AirlineW"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(13, "Uma Dawn", "AirlineX"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(11, "Victor Gray", "AirlineY"));
        // Add more planes as needed

        FlightControlProducerConsumerExample.ProducerConsumerImplementations pci =
                new FlightControlProducerConsumerExample.ProducerConsumerImplementations(initialPlanes);

        // Start the fuel reduction thread
        pci.startFuelReduction();

        ExecutorService service = Executors.newFixedThreadPool(2);
        try {
            // Submit tasks and get futures
            Future<?> producerFuture = service.submit(pci.new PlaneRunwayProducer());
            Future<?> consumerFuture = service.submit(pci.new PlaneRunwayConsumer());

            // Wait for producer to complete
            try {
                producerFuture.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            // Once producer is done, cancel consumer
            consumerFuture.cancel(true);

            // Shutdown the executor service
            service.shutdown();
            if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        } finally {
            pci.stopFuelReduction();
        }
    }

}

```

## Multithreading in JavaFX
<p>In JavaFX, the last stop in our journey on having Threads and concurrent programming, there is a singular class 
that allows us to execute this model of programming, <code>Tasks</code>. Task is a class that allows you to run 
operations in parallel with the main application Thread, it effectively serves the same purpose as a normal Thread 
in a console applicaiton, with the difference between Runnable Threads and Tasks being that Task can send signals 
and information back up to the screen through Bound Properties, binding them to the original window or main thread 
through observable values.</p>
<p>In JavaFX, our applications often reside in a single core thread, called the JavaFX <code>Application 
Thread</code>, from which we modify the GUI based on user's interactions and internal state of the application. 
However, when our application handles some specific requirement that is hard and long to compute on a single thread, 
we risk having our application become unresponsive, something that would likely end up angering the user, and is 
effectively negatively viewed by most users.
</p>
<p>The solution to this is to.</p>
<note><p>Always isolate long-running operations to Threads outside the JavaFX Application Thread. Remember 
that a JavaFX Application can have backend tasks done in other threads if needed, using structures just like 
we have used for console applications</p></note>
<p>However, it is more appropriate in JavaFX to use a class that is implemented to handle these long running tasks 
while also connecting itself to the JavaFX main application thread through property binding, i.e., it is 
<i>generally better to handle lon-running tasks using Tasks rather than Threads in JavaFX</i></p>

### Tasks
<p>JavaFX provides a series of classes that are used to handle concurrent modifications to our application while 
keeping the main Application Thread running and responsive, from these the most common, and the ones that are 
studied in the book are <i><b><code>Worker, Task and ScheduledService (not covered in our course)</code></b></i>. 
From these two, Task is an implementation of Worker (which is an interface), that allows you to both run a task in 
another Thread, but also return a value (like an async operation), and handle updates to the GUI through its 
properties</p>
<p>Tasks are generally implemented with a specific generic type, as these are more akin to CompletableFutures rather 
than Threads, in their ability to return a value once their execution has finished. While this class is huge, and 
provides not only methods to handle concurrent programming and result handling, it also provides methods for 
interconnection with another thread (JAVAFX Application Thread), and can be graciously used to produce and update 
constantly values to the UI, or to produce content to the UI. 
</p>

#### Tasks ― Fibonacci Calculator
<p>This is one of the examples of the book, reproduced here for completeness and for analysis purposes. The idea is 
to evaluate how this method works by presenting side by side the JavaFX controller and the Task code</p>

<compare type="top-bottom" first-title="Java Task Code" second-title="JavaFX Controller Code">

```Java
// Fig. 23.24: FibonacciTask.java
// Task subclass for calculating Fibonacci numbers in the background
import javafx.concurrent.Task;

public class FibonacciTask extends Task<Long> {
   private final int n; // Fibonacci number to calculate

   // constructor
   public FibonacciTask(int n) {
      this.n = n;
   } 

   // long-running code to be run in a worker thread
   @Override
   protected Long call() {
      updateMessage("Calculating...");
      long result = fibonacci(n);
      updateMessage("Done calculating."); 
      return result;
   } 

   // recursive method fibonacci; calculates nth Fibonacci number
   public long fibonacci(long number) {
      if (number == 0 || number == 1) {
         return number;
      }
      else {
         return fibonacci(number - 1) + fibonacci(number - 2);
      }
   }
} 

```

```Java
// Fig. 23.26: FibonacciNumbersController.java
// Using a Task to perform a long calculation 
// outside the JavaFX application thread.
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FibonacciNumbersController {
   @FXML private TextField numberTextField;
   @FXML private Button goButton;
   @FXML private Label messageLabel;
   @FXML private Label fibonacciLabel;
   @FXML private Label nthLabel;
   @FXML private Label nthFibonacciLabel;

   private long n1 = 0; // initialize with Fibonacci of 0
   private long n2 = 1; // initialize with Fibonacci of 1
   private int number = 1; // current Fibonacci number to display

   // starts FibonacciTask to calculate in background
   @FXML
   void goButtonPressed(ActionEvent event) {
      // get Fibonacci number to calculate
      try {
         int input = Integer.parseInt(numberTextField.getText());

         // create, configure and launch FibonacciTask
         FibonacciTask task = new FibonacciTask(input);

         // display task's messages in messageLabel
         messageLabel.textProperty().bind(task.messageProperty());

         // clear fibonacciLabel when task starts
         task.setOnRunning((succeededEvent) -> {
            goButton.setDisable(true);
            fibonacciLabel.setText(""); 
         });
         
         // set fibonacciLabel when task completes successfully
         task.setOnSucceeded((succeededEvent) -> {
            fibonacciLabel.setText(task.getValue().toString());
            goButton.setDisable(false);
         });

         // create ExecutorService to manage threads
         ExecutorService executorService = 
            Executors.newFixedThreadPool(1); // pool of one thread
         executorService.execute(task); // start the task
         executorService.shutdown();
      }
      catch (NumberFormatException e) {
         numberTextField.setText("Enter an integer");
         numberTextField.selectAll();
         numberTextField.requestFocus();
      }
   }

   // calculates next Fibonacci value   
   @FXML
   void nextNumberButtonPressed(ActionEvent event) {
      // display the next Fibonacci number
      nthLabel.setText("Fibonacci of " + number + ": ");
      nthFibonacciLabel.setText(String.valueOf(n2));
      long temp = n1 + n2;
      n1 = n2;
      n2 = temp;
      ++number;
   }
}
```
</compare>
<p>From the code example above, we can see four important sections which can be broken down into two groups, worker 
-> main thread, and main thread -> worker communication. The first group is easy to see in the first codeblock as it 
is shown in statements like <b><code>updateMessage()</code></b>, these are the methods that come from the 
<i><b><code>observable value parameters within the Task class</code></b></i>, meaning that this method is 
effectively talking to the main application thread from the worker thread. On the other hand, in the second 
codeblock we can see the second communication path, through <b>property binding</b> which takes care of connecting 
those observable values from the Task to the properties of the main JavaFX application thread.
</p>
<p>All in all, the interconnection method is not complicated, it is simply a second layer of complexity that is 
added ontop of the normal JavaFX property binding methodology. To finish up this section, I shall call on our friend 
Amazon Q to provide us with a listing of key concepts, pros, cons, and use cases.
</p>
<deflist type="full" collapsible="true">
<def title="JavaFX Task Pattern Characteristics" collapsible="true">
<list>
<li><strong>Background Thread Execution</strong>: Tasks run on separate threads, keeping the UI responsive while performing long operations</li>
<li><strong>Progress Monitoring</strong>: Built-in support for tracking and updating task progress through progress properties</li>
<li><strong>State Management</strong>: Manages task lifecycle states (READY, RUNNING, SUCCEEDED, FAILED, CANCELLED)</li>
<li><strong>UI Thread Safety</strong>: Automatically handles updates to UI components on the JavaFX Application Thread</li>
<li><strong>Exception Handling</strong>: Provides mechanisms for managing and reporting errors that occur during task execution</li>
</list>
</def>
</deflist>

<deflist type="full" collapsible="true">
<def title="JavaFX Task Pattern Benefits and Drawbacks" collapsible="true">
<list>
<li><strong>Advantages</strong>:
    <list>
        <li>Maintains UI responsiveness during long-running operations</li>
        <li>Provides built-in progress reporting and cancellation support</li>
        <li>Simplifies thread synchronization with Platform.runLater()</li>
        <li>Enables easy binding to UI controls for progress updates</li>
        <li>Supports chaining of multiple tasks and dependencies</li>
    </list>
</li>
<li><strong>Disadvantages</strong>:
    <list>
        <li>Tasks can only be executed once</li>
        <li>Memory overhead from creating new Task instances</li>
        <li>Complexity in handling task cancellation properly</li>
        <li>Learning curve for proper event handling</li>
        <li>Limited to single-execution scenarios</li>
    </list>
</li>
</list>
</def>
</deflist>

<deflist type="full" collapsible="true">
<def title="JavaFX Task Pattern Use Cases">
<list>
<li><strong>File Operations</strong>: Ideal for handling file uploads, downloads, and processing operations</li>
<li><strong>Network Operations</strong>: Perfect for making HTTP requests, API calls, and data fetching</li>
<li><strong>Data Processing</strong>: Suitable for complex calculations or data transformations</li>
<li><strong>Database Operations</strong>: Effective for database queries and updates that shouldn't block the UI</li>
<li><strong>Image Processing</strong>: Useful for handling image loading and manipulation operations</li>
<li><strong>Progress Reporting</strong>: Essential for operations that need to show progress to users</li>
<li><strong>Animation Preparation</strong>: Beneficial for preparing complex animations or visual effects</li>
</list>
</def>
</deflist>


## Completable Futures
<p>The last topic we are going to cover here are completable futures. Before, we studied the concept of Completables,
which we did with the class Callable (returned a future that we can call). Now the superset of this class is 
CompletableFutures which is a parametrized class that allow us to execute code asynchronously, be it a 
<b><code>supplier that returns a value or a Runnable that only executes code</code></b>. The benefit of this class 
is that it presents methods not just for handling results, exceptions, or interruptions, but also to concatenate 
multiple Runnable or Suppliers, as well as defining more complex methodologies like performing a second task after a 
completable future, or two Runnables in tandem. The following is a listing provided by Amazon Q for pros, cons, use 
cases, and characteristics of Completable Futures.
</p>
<deflist type="full" collapsible="true">
<def title="CompletableFuture Pattern Characteristics" collapsible="true">
<list>
<li><strong>Asynchronous Computation</strong>: Supports non-blocking operations with the ability to chain multiple operations</li>
<li><strong>Pipeline Processing</strong>: Enables creation of processing pipelines through composition methods (thenApply, thenCompose, etc.)</li>
<li><strong>Exception Handling</strong>: Provides comprehensive error handling through exceptionally() and handle() methods</li>
<li><strong>Completion Management</strong>: Offers explicit completion control through complete(), completeExceptionally(), and cancel()</li>
<li><strong>Thread Management</strong>: Flexible thread execution using custom executors or the default ForkJoinPool</li>
</list>
</def>
</deflist>

<deflist type="full" collapsible="true">
<def title="CompletableFuture Pattern Benefits and Drawbacks" collapsible="true">
<list>
<li><strong>Advantages</strong>:
    <list>
        <li>Enables functional-style asynchronous programming</li>
        <li>Supports complex operation composition and chaining</li>
        <li>Provides built-in exception handling mechanisms</li>
        <li>Allows combining multiple asynchronous operations</li>
        <li>Offers both synchronous and asynchronous completion options</li>
    </list>
</li>
<li><strong>Disadvantages</strong>:
    <list>
        <li>Steep learning curve for complex compositions</li>
        <li>Potential for callback hell in nested operations</li>
        <li>Memory overhead from maintaining completion chains</li>
        <li>Debugging complexity in asynchronous execution flows</li>
        <li>Thread pool management considerations required</li>
    </list>
</li>
</list>
</def>
</deflist>

<deflist type="full" collapsible="true">
<def title="CompletableFuture Pattern Use Cases">
<list>
<li><strong>Microservices Integration</strong>: Ideal for orchestrating multiple service calls and aggregating responses</li>
<li><strong>Parallel Processing</strong>: Perfect for executing multiple independent operations concurrently</li>
<li><strong>Data Pipeline Processing</strong>: Suitable for creating sequential or parallel data transformation pipelines</li>
<li><strong>External API Integration</strong>: Effective for managing multiple asynchronous API calls and their dependencies</li>
<li><strong>Resource Loading</strong>: Useful for loading multiple resources asynchronously and combining results</li>
<li><strong>Event-Driven Processing</strong>: Essential for handling asynchronous events and their subsequent processing</li>
<li><strong>Cache Loading</strong>: Beneficial for implementing asynchronous cache loading and refresh strategies</li>
</list>
</def>
</deflist>

<deflist type="full" collapsible="true">
<def title="Common CompletableFuture Operations">
<list>
<li><strong>Creation</strong>: 
    <list>
        <li>CompletableFuture.supplyAsync(() -> result)</li>
        <li>CompletableFuture.runAsync(() -> void)</li>
        <li>new CompletableFuture&lt;&gt;()</li>
    </list>
</li>
<li><strong>Transformation</strong>:
    <list>
        <li>thenApply(function) - Transform result</li>
        <li>thenCompose(function) - Chain futures</li>
        <li>thenCombine(other, function) - Combine two futures</li>
    </list>
</li>
<li><strong>Error Handling</strong>:
    <list>
        <li>exceptionally(function) - Handle exceptions</li>
        <li>handle((result, ex) -> value) - Handle both success and failure</li>
    </list>
</li>
</list>
</def>
</deflist>
<p>Lastly, let us define an example for this multithreading concept</p>

```Java

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo {

    public static void main(String[] args) {
        // Define initial amounts, interest rate, and duration
        BigDecimal initialAmount1 = new BigDecimal("1000000.00"); // 1 million
        BigDecimal initialAmount2 = new BigDecimal("500000.00");  // 500k
        BigDecimal annualInterestRate = new BigDecimal("0.05");   // 5%
        int years = 450;

        // Use a custom thread pool for demonstration
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Run two separate computations for both amounts
        CompletableFuture<BigDecimal> future1 = CompletableFuture.supplyAsync(
            () -> calculateCompoundInterest(initialAmount1, annualInterestRate, years), executor
        );

        CompletableFuture<BigDecimal> future2 = CompletableFuture.supplyAsync(
            () -> calculateCompoundInterest(initialAmount2, annualInterestRate, years), executor
        );

        // Attach asynchronous progress logging to both futures
        future1.thenAcceptAsync(result -> {
            System.out.println(Thread.currentThread().getName() + " - Final result for Amount 1: " + result);
        });

        future2.thenAcceptAsync(result -> {
            System.out.println(Thread.currentThread().getName() + " - Final result for Amount 2: " + result);
        });

        // Combine and compare the results
        CompletableFuture<Void> combinedFuture = future1.thenCombineAsync(future2, (result1, result2) -> {
            System.out.println(Thread.currentThread().getName() + " - Comparing results:");
            System.out.println("Amount 1 after " + years + " years: " + result1);
            System.out.println("Amount 2 after " + years + " years: " + result2);
            return null;
        });

        combinedFuture.join(); // Wait for everything to complete
        executor.shutdown();  // Shut down the custom executor
    }

    // Method to calculate compound interest
    private static BigDecimal calculateCompoundInterest(BigDecimal principal, BigDecimal rate, int years) {
        BigDecimal amount = principal;
        for (int year = 1; year <= years; year++) {
            amount = amount.add(amount.multiply(rate)); // A = P(1 + r)^t
            if (year % 50 == 0 || year == years) { // Print progress for every 50 years or the final year
                System.out.println(Thread.currentThread().getName() +
                        " - Year " + year + ": Current amount is " + amount);
            }
        }
        return amount.setScale(2, RoundingMode.HALF_EVEN); // Round to 2 decimal places
    }
}
```


