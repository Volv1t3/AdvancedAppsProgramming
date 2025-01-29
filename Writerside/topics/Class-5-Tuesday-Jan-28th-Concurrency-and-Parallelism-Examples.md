# Class #5 | Tuesday Jan 28th | Concurrency and Parallelism Examples and Theory

> THe following file contains theory about concurrency and parallelism, as well as the comparison between virtual 
> threads and physical threads. 


## Physical Threads and Virtual Threads
<p>In java there are two styles of threads that allow concurrent programming. There are physical 
(<i><code>platform threads</code></i>) threads, and virtual threads. The idea of virtual threads 
is not something that is completely done in Java, as in implemented, at the moment. There are 
various projects like <i><b>Helicon and Loom</b></i> where this type of thread is implemented as 
is currently in production, however, for most of our programs we will be using platform threads.
</p>
<procedure type="choices" title="Physical Threads and Virtual Threads Definition">
<list columns="2">
<li>
<deflist type="full">
<def title="Java Platform Threads">
<p>Java Platform Threads are lightweight units of execution within a Java program that enable 
concurrent processing. They are managed by the Java Virtual Machine (JVM) and allow multiple 
tasks to run simultaneously, sharing the same memory space. Java threads can be created by 
extending the Thread class or implementing the Runnable interface, providing a foundation for 
concurrent and parallel programming in Java applications.</p>
<note>We can think of this kind of threads as wrapped by <b><code>JVM classes</code></b>, but at 
the same time managed entirely by the <i>thread scheduler in our OS</i>.
</note>
</def>
</deflist>
</li>
<li>
<deflist>
<def title="Java Virtual Threads">
<p>These are defined as follows in the SDK specification. <i>"Virtual threads are typically 
user-mode threads, scheduled by the Java virtual machine, rather than the operating system. 
These require <b>few resources</b>, allowing for a single JVM to support millions of virtual 
threads.
</i></p>
<note>Internally, <i><b><code>all virtual threads eventually use a platform 
thread known as carrier thread</code></b></i>, a carrier thread is nothing more than a 
platform thread that is acquired to facilitate the communication between the CPU and the 
virtual thread.</note>
</def>
</deflist>
</li>
</list>
</procedure>
<p>The idea of these two types of threads then is that, while one of them is connected directly 
to the CPU thread that we are given, the other remains <i><code>only connected to the JVM, 
allowing it to use fewer resources and only lock with a platform thread when needed
</code></i>. It is interesting to note the use here of a third type of Thread, known as a 
carrier thread. This type is a subtype of a platform thread that at any given moment may or may 
not be connected to a virtual thread, depending on their Runnable or Running state.</p>
<p>Another key aspect of this definition is the fact that <b><code>for platform 
Threads (most of what we are going to do in this course), we are limited by CPU's 
threads</code></b>, while with virtual threads, in theory we <b><code>could have 
millions of them simply waiting to be executed (sadly still locked by the processing 
ability of our core)</code></b>.</p>
<p>The following is a useful graph that I found online that represents this</p>
<img src="https://segmentfault.com/img/bVdeZP4"/>
<p>As can be noted, there is an OS layer, holding all the hardware threads of our computer, to 
which <i><code>all the platform threads we can have in our program are connected</code></i>. This 
is because platform threads in Java are always connected to a single hardware thread through 
their execution, so in this case, this would show a set of <b>four running threads</b>.
</p>
<p>These threads are help within a ForkJoinPool, a pool of threads that help us in execution. 
Above it all, we have the JVM's top of the line Java Virtual Threads, known as the knights in 
shining armor for concurrent programming. They can handle <b>scalability, simplify your code and 
even improve performance
</b></p>
<p>The folllowing listing defines some of the main characteristics of each of these types of 
Threads, including Carrier Threads</p>
<procedure title="Thread Types and Their Characteristics in Java">
<list columns="1" type="none">
<li>
<deflist type="full">
<def title="Platform Threads Characteristics">
<list>
<li>Heavyweight threads that consume significant memory (approximately 1MB stack space)</li>
<li>Directly mapped to OS-level threads and managed by the operating system scheduler</li>
<li>Limited in scalability due to resource constraints</li>
<li>Blocking operations cause the entire thread to remain idle</li>
<li>Created by extending Thread class or implementing Runnable interface</li>
</list>
</def>
</deflist>
</li>
<li>
<deflist type="full">
<def title="Virtual Threads Characteristics">
<list>
<li>Lightweight threads with minimal memory footprint</li>
<li>Managed and scheduled by the JVM rather than the OS</li>
<li>Highly scalable, supporting millions of threads per JVM instance</li>
<li>Efficient handling of blocking operations through thread mounting/unmounting</li>
<li>Created using Thread.startVirtualThread() or Thread.ofVirtual().start()</li>
</list>
</def>
</deflist>
</li>
<li>
<deflist type="full">
<def title="Carrier Threads Characteristics">
<list>
<li>Platform threads that execute virtual threads</li>
<li>Managed by the JVM's internal thread pool</li>
<li>Can execute multiple virtual threads over time through mounting/unmounting</li>
<li>Typically fewer in number than virtual threads (usually matches CPU core count)</li>
<li>Facilitates communication between virtual threads and CPU</li>
</list>
</def>
</deflist>
</li>
</list>
</procedure>
<p>Again, through these definitions, we notice that in the case of Platform Threads, the memory 
layout and usage is huge, compare that to a typically Java program overhead, and we can start to 
see how these can boggle down our runtime. The solution is to use virtual threads, and although 
they are not entirely finished, there are methods defined within the Thread class that allow us 
to statically define a thread that internally will be managed as a virtual thread.
</p>
<p>However, one thing that we are forgetting to note is the use cases that these satisfy. For 
example, platform threads are used for CPU-bound tasks such as <i><code>mathematical 
operations or data processing</code></i>. On the other hand, virutal threads can be used 
specifically for <i><code>blocking tasks like i/o operations.</code></i>. This will be discussed 
in detail in our next block.
</p>
<deflist type="full" collapsible="true">
<def title="Platform Threads Use Cases" collapsible="true">
<list>
<li><strong>CPU-bound tasks</strong>: Use platform threads for tasks that require heavy computation, such as mathematical calculations or data processing, as they are directly mapped to OS threads and can fully utilize CPU cores.</li>
<li><strong>Long-running tasks</strong>: Ideal for tasks that run for extended periods, such as background services or batch processing, where the overhead of creating and managing threads is justified.</li>
<li><strong>Legacy applications</strong>: Use platform threads in older Java applications that are not yet optimized for virtual threads or rely on traditional threading models.</li>
<li><strong>Real-time systems</strong>: Suitable for real-time systems where predictable performance and low latency are critical, as platform threads provide direct control over thread execution.</li>
<li><strong>Thread-local storage</strong>: Use platform threads when your application relies heavily on thread-local storage (TLS), as virtual threads may not support TLS efficiently in all scenarios.</li>
</list>
</def>
</deflist>
<deflist type="full" collapsible="true">
<def title="Virtual Threads Use Cases" collapsible="true">
<list>
<li><strong>High-volume concurrent operations</strong>: Perfect for handling large numbers of concurrent web requests, API calls, or microservices interactions where traditional platform threads would consume too many resources.</li>
<li><strong>I/O-intensive applications</strong>: Ideal for applications that perform numerous blocking operations like database queries, file operations, or network calls, as virtual threads can efficiently yield during blocking periods.</li>
<li><strong>Batch processing systems</strong>: Excellent for processing large datasets in parallel where each processing unit involves waiting on external resources, allowing for better resource utilization and throughput.</li>
<li><strong>Event-driven architectures</strong>: Well-suited for applications handling numerous concurrent events or messages, particularly when each event handler involves I/O operations or external service calls.</li>
<li><strong>Legacy code modernization</strong>: Useful for modernizing existing synchronous blocking code without major refactoring, as virtual threads maintain the familiar thread-per-request programming model while improving scalability.</li>
</list>
</def>
</deflist>
<deflist type="full" collapsible="true">
<def title="Carrier Threads Use Cases">
<list>
<li><strong>Executing virtual threads</strong>: Use carrier threads to execute virtual threads, as they are responsible for mounting and unmounting virtual threads onto platform threads, enabling efficient task switching.</li>
<li><strong>High-concurrency applications</strong>: Ideal for applications requiring thousands or millions of concurrent tasks, such as web servers or microservices, where carrier threads manage the execution of lightweight virtual threads.</li>
<li><strong>I/O-bound tasks</strong>: Use carrier threads for tasks that involve frequent I/O operations, such as database queries or API calls, as they allow virtual threads to yield control during blocking operations.</li>
<li><strong>Resource-efficient task scheduling</strong>: Use carrier threads to optimize resource usage in applications with a large number of short-lived tasks, as they reduce the overhead of creating and managing OS threads.</li>
<li><strong>Scalable event-driven systems</strong>: Suitable for event-driven architectures, such as reactive programming or message brokers, where carrier threads enable efficient handling of asynchronous events.</li>
</list>
</def>
</deflist>
<p>Having taken a look at this, let us now take a look at how to define these quickly in a 
snippet of code</p>
<deflist collapsible="true" type="full">
<def title="Java Threads Example — Creating Simple Threads, no Runnable Implementations">

<code-block lang="Java" collapsed-title-line-number="1" ignore-vars="true">
package example;

public class examples{

    public static void main(String[] args){

        //! Defining a Base Platform Thread
        Thread platformThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello Wold!");
            }
        });
        //! Deifning a Virtual Thread
        Thread virtualThread = Thread
                               .ofVirtual()
                               .unstarted(new Runnable() {
            @Override
            public void run() {
                System.out
                .println("Hello World! from Virtual Thread");
            }
        });

        //! Starting both Threads
        virtualThread.start();
        thread.start();
        try {
            virtualThread.join();
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    
    }
}
</code-block>
</def>
<def title="Java Threads Example — Creating Simple Threads, Runnable Implementations">

```Java
package example;

public class examples{

    public static void main(String[] args){
        //! Lets create two platform threads and 
        //!two virtual threads, which are going to help us
        //! count from 1 to 100 and 2 to 200 in steps of 1
        int beginNum = 1;
        int endNum = 100_000;

        Thread platformThreadOne =
                Thread.ofPlatform()
                .unstarted(new CountNumberTask(beginNum, 
                                     endNum, beginNum));
        platformThreadOne.setName("platofrmThreadOne");
        Thread platformThreadTwo =
                Thread.ofPlatform()
                .unstarted(new CountNumberTask(beginNum + 1, 
                                endNum*2,beginNum));
        platformThreadTwo.setName("platformThreadTwo");

        // Print the number of active threads 
        // before starting the platform threads
        System.out
        .println("Number of active threads before starting: " 
                                    + Thread.activeCount());

        // Record the start time
        long startTime = System.currentTimeMillis();

        // Start both platform threads
        platformThreadOne.start();
        platformThreadTwo.start();

        try {
            // Join both platform threads
            platformThreadOne.join();
            platformThreadTwo.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Calculate and print the execution time
        long endTime = System.currentTimeMillis();
        System.out
        .println("Execution time for platform threads: "
                         + (endTime - startTime) + " ms");

        Thread virtualThreadOne = Thread.ofVirtual()
            .unstarted(new CountNumberTask(beginNum,
                                 endNum, beginNum));
        virtualThreadOne.setName("virtualThreadOne");
        Thread virtualThreadTwo = Thread.ofVirtual()
            .unstarted(new CountNumberTask(beginNum + 1, 
                                  endNum*2, beginNum));
        virtualThreadTwo.setName("virtualThreadTwo");

        // Print the number of active threads 
        // before starting the platform threads
        System.out
        .println("Number of active threads before starting: " 
                                    + Thread.activeCount());

        // Record the start time
        startTime = System.nanoTime();

        // Start both platform threads
        virtualThreadOne.start();
        virtualThreadTwo.start();

        try {
            // Join both platform threads
            virtualThreadOne.join();
            virtualThreadTwo.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Calculate and print the execution time
        endTime = System.nanoTime();
        System.out.println("Execution time for virtual threads: " 
                                + (endTime - startTime) + " ns"); 
    }

    public static class CountNumberTask implements Runnable {

        private int param_beginNum = 0;
        private int param_endNum = 0;
        private int param_stepCount = 0;
        private static final String taskName = "CountNumbersTask";

        @Override
        public void run() {
            int count = count();
            System.out.println("Task Name: " + taskName + ", Running on Thread: "
                                       + Thread.currentThread().getName() +
                                       ", Total Count: " + count);
        }

        public CountNumberTask(int beginNum, int endNum, int stepCount) {
            if (beginNum > endNum) {
                throw new IllegalArgumentException("Begin number must be less than end number");
            }
            if (stepCount <= 0) {
                throw new IllegalArgumentException("Step count must be greater than 0");
            }
            this.param_beginNum = beginNum;
            this.param_endNum = endNum;
            this.param_stepCount = stepCount;
        }

        public int count() {
            int count = 0;
            for (int i = param_beginNum; i <= param_endNum; i ++) {
                count+=param_stepCount;
            }
            return count;
        }
    }
}
```
</def>
</deflist>
<p>Internally, the Thread class has a series of methods defined to help us 
<i><b><code>note in which thread we are in, sleep, wait, interrupt, join or start, a thread, etc.
</code></b></i> Some of these methods are <i>static</i>, while others are related to a specific 
instance, like the <code>setThreadName method that we used above</code>. In any event, I will 
not be putting all of the Thread methods here with examples, rather a little listing where we 
define in a sentence or two what they do, and list out their requirements</p>
<procedure title="Method Listing">
<tabs>
<tab title="Method Listing | Useful Instance Methods">
<deflist collapsible="true">
<def title="void start()">
<p>Causes this thread to begin execution; the JVM calls the run method of this thread. The result 
is that two threads are running concurrently: the current thread and the new thread.</p>
<warning>Throws IllegalThreadStateException if the thread was already started. A thread can only 
be started once.</warning>
<note>The newly created thread will compete for CPU resources with other running threads based 
on its priority and the OS scheduler.</note>
</def>

<def title="void setName(String name)">
<p>Changes the name of this thread to be equal to the argument name. Thread names do not have 
to be unique.</p>
<warning>If name is null, a NullPointerException will be thrown.</warning>
<note>Thread names are primarily useful for debugging and monitoring purposes.</note>
</def>

<def title="String getName()">
<p>Returns this thread's name. If no name was set during construction or via setName(), a default 
name in the format "Thread-N" is returned, where N is a unique integer.</p>
<warning>This method never returns null.</warning>
<note>The returned name is the most recently set name, either by construction or setName().</note>
</def>

<def title="void setDaemon(boolean on)">
<p>Marks this thread as either a daemon thread or a user thread. Must be called before the thread 
is started.</p>
<warning>Throws IllegalThreadStateException if called after the thread has been started. 
SecurityException may be thrown if the current thread cannot modify this thread.</warning>
<note>The JVM will exit when only daemon threads remain running in the system.</note>
</def>

<def title="boolean isDaemon()">
<p>Tests if this thread is a daemon thread. Daemon threads are background threads that do not 
prevent the JVM from exiting when all user threads finish.</p>
<warning>The daemon status of a thread can only be set before it is started.</warning>
<note>A thread inherits its daemon status from its parent thread if not explicitly set.</note>
</def>

<def title="void run()">
<p>The entry point for the thread's execution. If this thread was constructed using a separate 
Runnable object, that Runnable object's run method is called; otherwise, this method does nothing.</p>
<warning>This method should not be called directly; use start() to begin thread execution.</warning>
<note>Subclasses of Thread should override this method to define thread behavior.</note>
</def>

<def title="boolean isVirtual()">
<p>Returns true if this thread is a virtual thread. Virtual threads are lightweight threads that 
are managed by the JVM rather than the operating system.</p>
<warning>This method was introduced in recent Java versions and may not be available in older versions.</warning>
<note>Virtual threads are designed for concurrent applications that require a large number of threads.</note>
</def>

<def title="boolean isInterrupted()">
<p>Tests whether this thread has been interrupted. The interrupted status is not cleared by this method.</p>
<warning>The interrupted status will be cleared when the thread throws an InterruptedException.</warning>
<note>This method returns the interrupt status without modifying it, unlike the static 
Thread.interrupted() method.</note>
</def>

<def title="boolean isAlive()">
<p>Tests if this thread is alive. A thread is alive if it has been started and has not yet died.</p>
<warning>The result of this method is time-sensitive and may change immediately after returning.</warning>
<note>A thread is considered alive after start() has been called but before it terminates.</note>
</def>

<def title="void interrupt()">
<p>Interrupts this thread. The interrupted status of the thread is set, and if the thread is 
waiting or sleeping, it will throw an InterruptedException.</p>
<warning>If this thread is blocked in an I/O operation, the channel will be closed and the thread 
will receive a ClosedByInterruptException.</warning>
<note>Interrupting a thread that is not alive has no effect.</note>
</def>

<def title="Object clone()">
<p>Throws CloneNotSupportedException as a Thread can not be meaningfully cloned. Construct a new 
Thread instead.</p>
<warning>This method always throws CloneNotSupportedException and cannot be used to duplicate a thread.</warning>
<note>Thread objects contain unique state that cannot be safely copied, such as native thread 
identifiers and synchronization state.</note>
</def>
</deflist>
</tab>
<tab title="Method Listing | Static Methods">

<deflist collapsible="true">
<def title="static int activeCount()">
<p>The active count method returns an estimated number of live platform threads in the current 
thread's thread group and its subgroups. For example, calling this from main usually returns two,
indicating the main platform thread and the gdc thread.
</p>
<warning>This method does not take any parameters, and it is static, only returning an 
approximate <code>int</code> value of the number of active threads</warning>
<note>The number returned might change form time to time, virtual threads are not even counted 
on this total.
</note>
</def>
<def title="static void enumerate(Thread[] tarray)">
<p>Copies into the specified array every active thread in the current thread's thread group and 
subgroups. This method acts as a snapshot of the currently running threads at the moment of invocation.</p>
<warning>If the array is too small to hold all threads, extra threads are silently ignored. 
The method returns void and modifies the array in-place.</warning>
<note>Like activeCount(), this method only provides an estimate as the thread system is dynamic 
and the count may change during execution.</note>
</def>

<def title="void join()">
<p>Waits for this thread to die. Blocks the current thread until the thread represented by this 
instance terminates.</p>
<warning>This method throws InterruptedException if any thread interrupts the current thread 
while waiting.</warning>
<note>The waiting thread will wait indefinitely until the target thread terminates.</note>
</def>

<def title="void join(long millis)">
<p>Waits at most the specified milliseconds for this thread to die. A timeout of 0 means to 
wait forever.</p>
<warning>Throws InterruptedException if interrupted while waiting. Throws IllegalArgumentException 
if millis is negative.</warning>
<note>Returns normally if the thread terminates or if the timeout elapses.</note>
</def>

<def title="void join(long millis, int nanos)">
<p>Waits at most the specified milliseconds plus nanoseconds for this thread to die.</p>
<warning>Throws InterruptedException if interrupted. Throws IllegalArgumentException if either 
millis or nanos is negative, or if nanos is > 999999.</warning>
<note>Provides finer timeout granularity than the milliseconds-only version.</note>
</def>

<def title="static void dumpStack()">
<p>Prints a stack trace of the current thread to the standard error stream.</p>
<warning>This method is used primarily for debugging purposes.</warning>
<note>The output format is the same as that of Throwable.printStackTrace().</note>
</def>

<def title="void setDaemon(boolean on)">
<p>Marks this thread as either a daemon thread or a user thread.</p>
<warning>Must be called before the thread is started. Throws IllegalThreadStateException if 
thread is already running.</warning>
<note>The Java Virtual Machine exits when only daemon threads remain.</note>
</def>

<def title="boolean isDaemon()">
<p>Tests if this thread is a daemon thread.</p>
<warning>This method simply returns a boolean and takes no parameters.</warning>
<note>A daemon thread is a background thread that does not prevent the JVM from exiting.</note>
</def>

<def title="ClassLoader getContextClassLoader()">
<p>Returns the context ClassLoader for this Thread.</p>
<warning>May return null if the context class loader has not been set.</warning>
<note>The context ClassLoader is provided by the creator of the thread for use by code running 
in this thread.</note>
</def>

<def title="void setContextClassLoader(ClassLoader cl)">
<p>Sets the context ClassLoader for this Thread.</p>
<warning>Throws SecurityException if the current thread cannot modify this thread.</warning>
<note>Can be set to null to clear the context ClassLoader.</note>
</def>

<def title="static boolean holdsLock(Object obj)">
<p>Returns true if and only if the current thread holds the monitor lock on the specified object.</p>
<warning>Throws NullPointerException if obj is null.</warning>
<note>This method is designed for testing and debugging purposes.</note>
</def>

<def title="StackTraceElement[] getStackTrace()">
<p>Returns an array of stack trace elements representing the stack dump of this thread.</p>
<warning>Some virtual machines may omit one or more stack frames.</warning>
<note>The returned array represents a snapshot of the stack at the time this method is called.</note>
</def>

<def title="static Map<Thread,StackTraceElement[]> getAllStackTraces()">
<p>Returns a map of stack traces for all live threads.</p>
<warning>This operation may be expensive and should not be called frequently.</warning>
<note>The returned map is a snapshot and may not reflect the current state of the thread system.</note>
</def>

<def title="long threadId()">
<p>Returns the identifier of this Thread. The thread ID is unique during the thread's lifetime.</p>
<warning>Thread IDs may be reused after a thread has terminated.</warning>
<note>The ID is assigned during thread construction and remains unchanged during its lifetime.</note>
</def>

<def title="Thread.State getState()">
<p>Returns the state of this thread.</p>
<warning>The returned state may not reflect the thread's current state after this method returns.</warning>
<note>Possible states are: NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED.</note>
</def>

<def title="static void setDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler eh)">
<p>Sets the default handler invoked when a thread abruptly terminates due to an uncaught exception.</p>
<warning>Can be set to null to disable default handler.</warning>
<note>This handler is used when no other uncaught exception handler is explicitly set.</note>
</def>

<def title="static Thread.UncaughtExceptionHandler getDefaultUncaughtExceptionHandler()">
<p>Returns the default handler invoked when a thread abruptly terminates due to an uncaught exception.</p>
<warning>May return null if no default uncaught exception handler has been set.</warning>
<note>This is a global setting for all threads that don't have their own handler set.</note>
</def>

<def title="void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler eh)">
<p>Sets the handler invoked when this thread abruptly terminates due to an uncaught exception.</p>
<warning>Can be set to null to remove the handler.</warning>
<note>This handler takes precedence over the thread group's and default handler.</note>
</def>
</deflist>

</tab>
</tabs>
</procedure>




## Executors, Executor Services, and Managed Thread Pools
<p>Given the hassle that creating and managing threads, their start states, joining them, 
interruptions, etc. Java provides a series of already defined wrappers for Thread pools, 
effectively, thread pools can be defined as <b><code>a collection of threads, created and held 
for executing tasks. These collections reduce resource thrashing by keeping these threads alive, 
removing the thread life cycle overhead
</code></b>.</p>
<p>In Java, the Executor framework is centered around the Executor interface and its 
<i>sub-interface</i>, ExecutorService, that provides a series of well defined constructors for 
thread pools ranging from different sizes to different styles. Of these, in class we have 
reviewed three, a single thread pool, a cached thread pool, and a n-ary thread pool where we 
could define the number of allocated threads ourselves.</p>
<p>The process of using these differs in terms of what we use, if its a callable method then we 
might have to work with futures (to be defined further, later on). On the other hand, if we were 
to work with normal threads we then have a simple way of using these at all times.
</p>
<p>The following section will focus on furthering our conceptual understanding on all of these 
thread pool types</p>
<procedure title="Executors ― Thread Pool Implementations">
<tip title="Thread Factory">A Thread factory, as can be noted in most of the functional 
definitions, is simply a way for us to define a factory method to build our own threads. 
Basically it removes the need for us to write new threads, submit calls, or execute tasks</tip>
<deflist>
<def title="static ExecutorService newFixedThreadPool(int nThreads)/
newFixedThreadPool(int nThreads, ThreadFactory)">
<p>These methods are the basic ones that we have come across in class, these are effectively 
ways to allocated, within a single pool, n threads to be used for execution. The core aspect to 
know about the creation of this thread pool is that <b><code>although we are able to 
define the number of threads we want, this does not mean that at any given time these will 
instantiated at once (during definition)
</code></b>, rather the documentation states that <b><code>at most nThreads will be 
available processing tasks</code></b>. Thereby articulating that this system creates threads 
lazily, rather than on definition, saving resources and computation time by reducing the time it 
has to interact with platform threads and the OS.
</p>
<p>Internally, this method holds a <i>LinkedBlockingQueue</i> which holds the tasks that will be 
avaliable for processing at any given time, locking each task as a thread accesses this linked 
list. 
</p>
<note>Once created, threads remain in the execution pool until otherwise shutdown, 
additionally <i><b><code>threads that have died due to failures are 
automatically replaced</code></b></i></note>
<p>These are some common characteristics, as well as a code example for its definition and usage</p>
<procedure title="Characteristics" collapsible="true">
<list>
<li><strong>Core Characteristics</strong>: Creates a thread pool with a fixed number of threads 
that remain constant throughout the application lifecycle, queuing additional tasks when all 
threads are busy using a LinkedBlockingQueue.</li>
<li><strong>Key Benefits</strong>: Provides predictable resource usage, prevents thread 
explosion, offers better memory management, and ensures controlled concurrency levels for 
CPU-bound tasks where thread count should match available processors.</li>
<li><strong>Potential Drawbacks</strong>: Can lead to thread starvation if pool size is too 
small, may waste resources if pool size is too large, and the unbounded queue can potentially 
consume large amounts of memory if tasks accumulate faster than they can be processed.</li>
<li><strong>Optimal Use Cases</strong>: Well-suited for CPU-intensive computations, parallel 
processing of fixed-size workloads, applications requiring predictable thread management, and 
scenarios where you need to limit resource consumption with a known upper bound.</li>
<li><strong>Important Notes</strong>: Threads persist until explicitly shutdown, failed threads 
are automatically replaced, blocking queue can grow indefinitely if tasks are submitted faster 
than they can be processed, and pool size should typically match the number of available 
processors for CPU-bound tasks (Runtime.getRuntime().availableProcessors()).</li>
</list>

</procedure>
<procedure title="Example" collapsible="true">

```Java

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple banking application demonstrating 
 * the use of a fixed thread pool
 * to process multiple banking transactions concurrently.
 */
public class BankingApplication {

    // Simulates a banking transaction task
    static class TransactionTask implements Runnable {
        private final String transactionName;

        public TransactionTask(String transactionName) {
            this.transactionName = transactionName;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + 
            " is processing transaction: " + transactionName);
            try {
                // Simulate transaction processing time
                Thread.sleep(2000); 
            } catch (InterruptedException e) {
                System.err.println("Transaction interrupted: " 
                                + transactionName);
                Thread.currentThread().interrupt();
            }
            System.out.println(Thread.currentThread().getName() + 
            " finished processing transaction: " 
            + transactionName);
        }
    }

    public static void main(String[] args) {
        // Define a fixed thread pool 
        // with a size of 3, for example
        ExecutorService fixedThreadPool =
            Executors.newFixedThreadPool(3);

        try {
            // Submit multiple tasks 
            // (transactions) to the thread pool
            for (int i = 1; i <= 10; i++) {
                fixedThreadPool
                    .submit(new TransactionTask("Transaction-" + i));
            }
        } finally {
            // Shutdown the thread pool after tasks are submitted
            fixedThreadPool.shutdown();
        }
    }
}
```
</procedure>
</def>
<def title="static ExecutorService newCachedThreadPool()/newCachedThreadPool(ThreadFactory 
threadFactory)">
<p>This second way is even more straightforward than before. A Cached Thread Pool is defined as 
a <i><b><code>thread pool that creates new threads as needed, but will reuse 
previously constructed threads when they are available</code></b></i>. This then means that we 
are benefitting from the execution performance of programs that do not handle many long living 
threads, and rather use a couple and at a regular interval.</p>
<p>One key point from this implementation is that <i><b><code>after 60 seconds of inactivity a 
thread is removed from the cache, and if at some point there aren't enough they are 
automatically created.
</code></b></i></p>
<p>As before, here are some characteristics for this method</p>
<procedure title="Characteristics" collapsible="true">
<list>
<li><strong>Core Characteristics</strong>: Creates an <i><code>expandable thread pool</code></i> 
that <i><b><code>dynamically adds new threads as needed and reuses existing idle threads</code></b></i>. Uses a 
SynchronousQueue for task handoff, and threads that remain idle for 60 seconds are 
automatically terminated and removed from the cache.</li>
<li><strong>Key Benefits</strong>: Offers excellent flexibility for varying workloads, <i><code>provides 
automatic resource management through thread recycling</code></i>, eliminates the need to manually 
configure thread pool size, and <i><code>delivers better queuing performance than fixed thread pools for 
short-lived tasks</code></i>.</li>
<li><strong>Potential Drawbacks</strong>: Can lead to thread explosion under heavy loads since 
there's no upper bound on thread creation, <i><code>may consume excessive system resources in high-load 
scenarios, and isn't suitable for applications requiring strict resource control</code></i>.</li>
<li><strong>Optimal Use Cases</strong>: <i><code>Well-suited for applications with many short-lived tasks,
dynamic workload patterns, bursty task submission rates</code></i>, and scenarios where the number of 
concurrent tasks varies significantly but typically remains moderate.</li>
<li><strong>Important Notes</strong>: Thread creation is unbounded (up to Integer.MAX_VALUE), 
idle threads are automatically cleaned up after 60 seconds of inactivity, uses direct task 
handoff through SynchronousQueue which means no task queuing, and performs best when task 
execution time is relatively short.</li>
<li><strong>Resource Management</strong>: While it provides automatic thread cleanup, monitoring 
thread creation rates and system resources is important in production environments to prevent 
resource exhaustion.</li>
<li><strong>Best Practices</strong>: Should be used with caution in production environments 
where load is unpredictable, consider implementing thread factory with meaningful names for 
debugging, and monitor thread creation patterns to ensure system stability.</li>
</list>
</procedure>
<procedure title="Example" collapsible="true">

```Java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankAccountExample {

    // Simulates a bank account with 
    // synchronized methods for thread safety
    static class BankAccount {
        private double balance;

        public BankAccount(double initialBalance) {
            this.balance = initialBalance;
        }

        // Synchronized method to deposit money
        public synchronized 
            void deposit(double amount) {
            System.out.println(Thread
                .currentThread().getName() + 
                " is depositing: " + amount);
            balance += amount;
            System.out.println(Thread
                .currentThread().getName() + 
                " new balance after deposit: " + balance);
        }

        // Synchronized method to withdraw money
        public synchronized void withdraw(double amount) {
            if (balance >= amount) {
                System.out.println(Thread
                .currentThread().getName()
                 + " is withdrawing: " + amount);
                balance -= amount;
                System.out.println(Thread
                .currentThread().getName() 
                + " new balance after withdrawal: " 
                + balance);
            } else {
                System.out.println(Thread
                .currentThread().getName() 
                + " insufficient funds for withdrawal: " 
                + amount);
            }
        }

        // Method to check balance 
        // (synchronized block for 
        // finer locking granularity)
        public void checkBalance() {
            synchronized (this) {
                System
                    .out.println(Thread
                        .currentThread().getName() 
                        + " checking balance: " + balance);
            }
        }
    }

    public static void main(String[] args) {
        // Create a cached thread pool
        ExecutorService cachedThreadPool = 
            Executors.newCachedThreadPool();

        // Start with an initial balance
        BankAccount account = new BankAccount(1000.0); 

        try {
            // Submit tasks to the thread pool
            cachedThreadPool.submit(
                () -> account.deposit(200.0));
            cachedThreadPool.submit(
                () -> account.withdraw(150.0));
            cachedThreadPool.submit(
                () -> account.checkBalance());
            cachedThreadPool.submit(
                () -> account.withdraw(1200.0)); 
                // Should trigger insufficient funds
            cachedThreadPool.submit(
                () -> account.deposit(500.0));
            cachedThreadPool.submit(    
                () -> account.checkBalance());
        } finally {
            // Shutdown the thread pool 
            // after all tasks are submitted
            cachedThreadPool.shutdown();
        }
    }
}
```
</procedure>
</def>
<def title="static ExecutorService newSingleThreadExecutor()/ newSingleThreadExecutor
(ThreadFactory threadFactory)">
<p>This version of the ExecutorService implementations is by far the simples, so simple in fact 
it can be summarized in a tooltip</p>
<tip><p><i>newSingleThreadExecutor creates an executor that uses a single worker 
thread, operating off an unbounded queue, appending to it the ability to regenerate 
this <b>singular thread worker were it to die to an exception prior to shut down</b>. 
Additionally, <b><code>tasks are guaranteed to execute sequentially and one at the time
</code></b></i></p></tip>
<p>As before, we are going to include some characteristics for their review, as well as an 
example</p>
<procedure title="Characteristics" collapsible="true">
<list>
<li><strong>Core Characteristics</strong>: Establishes a <i><code>single-worker thread executor that 
processes tasks sequentially from an unbounded queue, guaranteeing FIFO (First-In-First-Out) 
task execution order</code></i>. The executor automatically 
replaces the worker thread if it terminates unexpectedly, ensuring continuous operation until 
explicit shutdown.</li>
<li><strong>Potential Drawbacks</strong>: <i><code>Limited throughput due to single-threaded execution, 
potential bottlenecks under high task submission rates, risk of unbounded queue growth leading 
to memory issues</code></i>, and inability to parallelize task processing 
even when resources are available.</li>
<li><strong>Optimal Use Cases</strong>: Perfect for scenarios requiring <i><code>strict task ordering, 
single-threaded event processing systems, sequential file writing operations, maintaining 
database transaction sequences</code></i>, and applications where task interdependencies mandate 
sequential execution.</li>
<li><strong>Performance Considerations</strong>: While offering predictable execution patterns, 
this executor type trades parallel processing capabilities for sequential consistency. Task 
processing rate is limited by the single thread's capacity, making it unsuitable for 
CPU-intensive workloads requiring parallel execution.</li>
<li><strong>Resource Management</strong>: The unbounded task queue requires careful monitoring 
in production environments to prevent memory exhaustion. Implementing task rejection policies or 
queue size limits might be necessary for long-running applications.</li>
<li><strong>Best Practices</strong>: Monitor queue size growth in production environments, 
implement task timeout mechanisms for long-running operations, consider using thread factory for 
custom thread naming and error handling, and ensure proper executor shutdown to prevent resource 
leaks.</li>
</list>
</procedure>
<procedure title="Example" collapsible="true">

```Java

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.HashMap;

public class POSExample {

    // Simulates a Point-of-Sale system with synchronized operations
    static class POS {
        private final HashMap<String, Integer> 
                inventory = new HashMap<>();

        // Synchronized method to add an item
        public synchronized void addItem(String item, 
                                         int amount) {
            inventory.put(item, 
                inventory.getOrDefault(item, 0) + amount);
            System.out.println(Thread
                .currentThread().getName() + 
                " added: " + amount + " units of " + item + 
                " | Current inventory: " + inventory);
        }

        // Synchronized method to review an item
        public synchronized void reviewItem(String item) {
            int stock = inventory.getOrDefault(item, 0);
            System.out.println(Thread
                .currentThread().getName() + 
                " reviewed: " + item + " | Stock: " + stock);
        }

        // Synchronized method to remove an item
        public synchronized void removeItem(String item, 
                                            int amount) {
            if (inventory.containsKey(item) 
                && inventory.get(item) >= amount) {
                inventory.put(item, 
                    inventory.get(item) - amount);
                if (inventory.get(item) == 0) {
                    // Remove the item 
                    // completely if stock is 0
                    inventory.remove(item); 
                }
                System.out.println(Thread
                    .currentThread().getName() + 
                    " removed: " + amount + " units of " 
                    + item + 
                    " | Current inventory: " + inventory);
            } else {
                System.out.println(Thread
                    .currentThread().getName() + 
                    " tried to remove: " + amount 
                    + " units of " + item + 
                    " | Not enough stock or" + 
                    "item does not exist!");
            }
        }
    }

    public static void main(String[] args) {
        // Create a single-threaded executor
        ExecutorService singleThreadExecutor = 
            Executors.newSingleThreadExecutor();

        // Create a POS instance
        POS posSystem = new POS();

        try {
            // Submit tasks to the executor 
            // to demonstrate sequential execution
            singleThreadExecutor.submit(
                () -> posSystem.addItem("Apples", 50));
            singleThreadExecutor.submit(
                () -> posSystem.addItem("Bananas", 30));
            singleThreadExecutor.submit(
                () -> posSystem.reviewItem("Apples"));
            singleThreadExecutor.submit(
                () -> posSystem.removeItem("Apples", 20));
            singleThreadExecutor.submit(
                () -> posSystem.reviewItem("Apples"));
            singleThreadExecutor.submit(
                // Should fail
                () -> posSystem.removeItem("Bananas", 40)); 
            singleThreadExecutor.submit(
                () -> posSystem.reviewItem("Bananas"));
        } finally {
            // Shutdown the executor 
            // after tasks are completed
            singleThreadExecutor.shutdown();
        }
    }
}
```
</procedure>
</def>
</deflist>
</procedure>

## Callable Interface 
<p>The Callable interface is another way to implement asynchronous execution in concurrent 
programming.The idea is that, by now we have seen how to execute tasks that were supposed to be 
stateless on their own, modify some content (synchronously), and follow locks for information 
that passed through them. This environment, although much more complex than what we are lead to 
believe in this course, forms the basis of concurrent programming in java, and even parallel 
programming (as there are some methods for this littered in the Executors class)</p>
<p>Despite this, however, we have not touched asynchronous programming, which often times is the 
backbone of services written in Java, or applications on the web that depend on the language for 
their backend, webservice management, or microservices.  
</p>
<p>This is where this section's topic comes into play, <i><b><code>the Callable 
interface</code></b></i> defines the basis of operation for asynchronous code executed through 
threads, allowing its executor the execute its task, and even return a result. This then means 
that these are not <b>totally without side-effects</b>, as they can inherently produce outcomes 
that can even alter the flow of a program.</p>
<p>Before delving deeper into this structure however, we should take a look at what is 
asynchronous programming</p>
<deflist type="full">
<def title="Asynchronous Programming">
<p>Asynchronous programming is a methodology of application implementation that focuses on 
making some parts capable of running on the background, in other threads, or in any way that is 
<i><b><code>non-blocking</code></b></i>, to the main execution, allowing programmers to 
enhance application responsiveness, and resource utilization, in applications with I/O or 
network intensive operations.</p>
</def>
</deflist>
<p>Although in many languages there are specific keywords used to define these in Java we make 
use of futures and Callables, as well as some other classes that are not covered in this course 
like <b><code>CompletableFuture</code></b>, however, as a Java enthusiast I would like to point 
out that there are various other sources for async-programming like <i>Guava, ea-async, 
Cactoos</i>, etc.</p>

### Callable Interface — Main Definition
<p>The Callable interface contains a single method, defined as <code>V call()</code>, where 
<code>V</code> represents the parametrized type variable of this implementation, often the 
return type of what we are going to do. Additionally, this method defines that 
<i><code>it will compute a result, or throw an exception if unable to do so</code></i>. Again,
 this differs from what we have seen of Threads, if a thread with a Runnable dies, it dies and 
causes a mess both with the thread and with our system, whilst runnable can throw checked 
exceptions and unchecked exceptions, allowing us to manage their state more efficiently. 
</p>
<note>Exceptions thrown, be them runtime or checked, during the execution of a Callable's 
call() method, will be stored within the Future that it returns. This means that 
<code>once we prompt for the value, we will get a runtime exception that reports what 
happened internally to us</code></note>
<p>We often call this interface passing it to the <code>submit()</code> either through a class 
instance, anonymous class or lambda, as it is a functional interface with a single method. These 
will return to the caller a Future, through which the submitter can check the state of their 
callable, the results and any exceptions that might have happened within.
</p>
<p>Now it is time for us to move to what Futures are, just a bit of a technical sidenote 
before we go into keynotes, pros, cons, and examples of Callables</p>

### Futures 
<deflist>
<def title="Futures —— What are they?">
<p>A Java Future represents the result of an asynchronous computation. It is a 
<i><b><code>placeholder for a result that will become available</code></b> at some point in 
the future</i>. Like a promise of a computational future result and the ability to check it</p>
</def>
</deflist>
<p>In addition to the aforedmentioned definition, one must point out that <code>this 
class provides various methods for both managing the state (if its cancelled, finished, 
etc), as well as getting the value through get()</code>. All in all its a way for us to monitor 
a task that is being done, and modify it from another thread of execution.</p>

### Callable Interface — Key notes, pros, and cons
<procedure collapsible="false">
<tabs>
<tab title="Key Notes">
<list>
<li><strong>Return Value Support</strong>
Unlike Runnable, Callable allows tasks to return values upon completion. This interface declares 
a single call() method that returns a generic type V, enabling flexible return types. The 
ability to return values makes it suitable for computational tasks requiring results.</li>
<li><strong>Exception Handling Mechanism</strong>
Callable's call() method can throw checked exceptions, providing better error handling 
capabilities. This design allows for more granular exception management compared to Runnable. 
Exceptions are wrapped in ExecutionException when retrieved through Future objects.</li>
<li><strong>Future Integration</strong>
Works seamlessly with Future interface for asynchronous result handling and task status tracking.
Callable tasks submitted to an ExecutorService return Future objects immediately. This enables 
non-blocking execution while maintaining access to eventual results.</li>
<li><strong>Thread Pool Compatibility</strong>
Designed to work efficiently with Java's ExecutorService framework and thread pools. Callable 
tasks can be submitted directly to thread pools using submit() methods. The framework handles 
task queuing and execution management automatically.</li>
</list>
</tab>
<tab title="Pros">
<list>
<li><strong>Type-Safe Results</strong>
Provides compile-time type safety through generic return types. Results can be strongly typed to 
match specific requirements. This prevents runtime type casting errors and improves code 
reliability.</li>
<li><strong>Enhanced Error Handling</strong>
Supports checked exception propagation without requiring try-catch blocks in task code. 
Exceptions are captured and accessible through the Future interface. This design promotes more 
robust error handling strategies.</li>
<li><strong>Asynchronous Processing</strong>
Enables non-blocking execution with result retrieval when needed through Future objects. Tasks 
can execute independently while the main program flow continues. Results can be collected at 
appropriate points in the program.</li>
<li><strong>Flexible Task Design</strong>
Allows for complex computational tasks that need to return results or throw exceptions. Can be 
used with lambda expressions for concise task definitions. Supports various return types through 
generics.</li>
</list>
</tab>
<tab title="Cons">
<list>
<li><strong>Complexity Overhead</strong>
Requires more complex implementation compared to simple Runnable tasks. Developers must handle 
Future objects and potential exceptions explicitly. Additional boilerplate code may be needed 
for result handling.</li>
<li><strong>Resource Management</strong>
Future objects must be properly managed to prevent memory leaks. Unchecked Future results can 
accumulate and consume memory. Careful consideration is needed for task timeout and cancellation 
scenarios.</li>
<li><strong>Learning Curve</strong>
More challenging for developers new to concurrent programming concepts. Requires understanding 
of Future, ExecutorService, and exception handling patterns. May lead to incorrect 
implementations if not properly understood.</li>
<li><strong>Synchronization Considerations</strong>
Result retrieval through Future.get() can block execution if not properly managed. Timeout 
mechanisms should be implemented to prevent indefinite blocking. Care is needed when sharing 
resources between multiple Callable tasks.</li>
</list>
</tab>
</tabs>
</procedure>

### Callable Interface — Complete Example
<procedure>

```Java
import java.util.concurrent.*;
import java.util.*;

// Record to represent a seat reservation
record SeatReservation(String userName, 
    long reservationTime, int seatNumber) {}

// Class representing a Seat
class Seat {
private final int seatNumber;
private boolean reserved;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.reserved = false;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public synchronized boolean reserve() {
        if (!reserved) {
            reserved = true;
            return true;
        }
        return false;
    }

}

// Class representing a User
class User {
private final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

// Callable task to reserve a seat
class SeatReservationTask implements Callable<SeatReservation> {
private final User user;
private final int seatNumber;
private final Map<Integer, Seat> seatMap;

    public SeatReservationTask(User user, int seatNumber, 
                    Map<Integer, Seat> seatMap) {
        this.user = user;
        this.seatNumber = seatNumber;
        this.seatMap = seatMap;
    }

    @Override
    public SeatReservation call() {
        Seat seat = seatMap.get(seatNumber);
        if (seat != null && seat.reserve()) {
            return new SeatReservation(user.getName(), 
                System.currentTimeMillis(), seat.getSeatNumber());
        } else {
            throw new IllegalStateException("Seat " + seatNumber 
            + " is already reserved or does not exist.");
        }
    }

}

// Main class to simulate the movie theater seat reservation system
public class MovieTheaterReservationSystem {
    private final Map<Integer, Seat> seatMap = new HashMap<>();
    private final Map<String, SeatReservation> 
        reservationMap = new ConcurrentHashMap<>();
    private final ExecutorService 
        executor = Executors.newCachedThreadPool();

    public MovieTheaterReservationSystem(int totalSeats) {
        for (int i = 1; i <= totalSeats; i++) {
            seatMap.put(i, new Seat(i));
        }
    }

    public Future<SeatReservation> reserveSeat(User user, 
                            int seatNumber) {
        SeatReservationTask task = new SeatReservationTask(user, 
                                          seatNumber, seatMap);
        return executor.submit(task);
    }

    public static void main(String[] args) {
        MovieTheaterReservationSystem system = 
            new MovieTheaterReservationSystem(10);

        // Define some users
        User user1 = new User("Alice");
        User user2 = new User("Bob");
        User user3 = new User("Eve");

        try {
            // Simulate concurrent seat reservation
            Future<SeatReservation> future1 = 
                system.reserveSeat(user1, 5);
            Future<SeatReservation> future2 = 
                system.reserveSeat(user2, 5); 
                // Trying to reserve the same seat
            Future<SeatReservation> future3 = 
                system.reserveSeat(user3, 7);

            // Handle the results
            try {
                System.out
                    .println("Reservation Successful: " 
                        + future1.get());
            } catch (ExecutionException e) {
                System.out
                    .println("Reservation Failed for Alice: " 
                    + e.getCause().getMessage());
            }

            try {
                System.out
                    .println("Reservation Successful: " 
                        + future2.get());
            } catch (ExecutionException e) {
                System.out.println("Reservation Failed for Bob: " 
                    + e.getCause().getMessage());
            }

            try {
                System.out
                    .println("Reservation Successful: " 
                        + future3.get());
            } catch (ExecutionException e) {
                System.out.println("Reservation Failed for Eve: " 
                    + e.getCause().getMessage());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out
                .println("The reservation process was interrupted.");
        } finally {
            system.executor.shutdown();
        }
    }

}
```
</procedure>

## Locking Shared Memory and Granularity
<p>Throughout this chapter, examples have been laid out that showcase the way we should work 
with shared state, in some cases it was through methods marked as synchronous, others were 
through the synchronous word for methods, and others through the use of 
<code>newSingleThreadExecutor</code>. The thing that all of these have in common though, is, 
that <b>they all managed locks in memory, blocking other threads from accessing state as 
they accessed it themselves</b></p>
<p>The concept is nothing new if we think about it, in sequential programming we would not want 
to have two loops that modified the values of an array at the same time, unless their actions 
were completely detached and free of side effects. In the case of parallel and concurrent 
programming, we often do not want different threads accessing the same area of memory as this can 
lead to race conditions, and the famous <b><code>lost-operation error</code></b>, where part of 
our work goes out of the window as it is effectively ignored by other threads.
</p>
<p>Race conditions, as they are mostly known can arise from this shared memory access, as the 
timing of events can vary, unpredictably even, the way our program executes and the outputs it 
produces. Think as an example of the previous movie seat registry software. Were we to not write 
any methods as synchronous, effectively restricting memory access to these, we could have cases 
in which a thread overrides or reads erroneously a state that has changed on another, causing 
seat reallocation for users which could affect our app's usability.
</p>
<p>For this reason, there are some concepts that we must understand to a high level of affinity 
to work with threads, and memory safety. Additionally, at the end of this section a glossary of 
new terms will be presented, made out of those that I deem not worthy of mentioning in the main 
file (if any)
</p>

### Thread Synchronization — Protections Against Uncoordinated Access
<p>Keeping up with the notion of thread safety and keeping our states synchronized, we must 
discuss thread synchronization to a certain extent. The idea is that <b><code>when multiple 
threads access and modify an object's state, the results of said operations become 
indeterminate</code></b></p>
<p>Consider the case for the banking system presented a couple examples above, if the system 
were not to be synchronized, there could be a case in which, the withdrawal operation reads a 
balance that is much higher than the real one due to issues in synchronization and race 
conditions. Or in the opposite case, depositing might ignore previous withdrawal operations and 
increment our balance incorrect</p>
<p>These kinds of issues are often solved in concurrent programming by giving threads 
<i><b><code>exclusive access to coe that accesses the shared object</code></b>, through 
which, essentially, a thread can only modify this object at a given moment</i> blocking any 
other attempt from even getting through (keeping them waiting)</p>
<note>Thread synchronization is the process that <i><b><code>coordinates 
access to shared data by multiple concurrent threads, ensuring that accessing 
a shared object excludes all other threads from doing so simultaneously.</code> 
(mutual exclusion)</b></i></note>

#### Protections Against uncoordinated Access — Java's Monitor Locks
<p>In Java, a common way to perform synchronization is to use monitors. 
<b><code>Every object has a monitor lock (or intrinsic lock)</code></b>. A lock is a restriction 
that is applied to an object such that only one Thread or operation handles the resource at any 
given point of its execution. For example, if two operations have to access the same resources 
(the balance in our example), a lock can make it so that the withdrawal method in a thread 
acquires a lock and operates on the shared state without issues.
</p>
<deflist>
<def title="Acquiring a Lock">
<p>The process through which a thread acquires permissions to operate on a shared object 
whose state is mutable</p></def>
<def title="Holding a Lock"><p>The process through which, a thread with a Lock, holds onto 
it through the duration of their operation, enforcing mutual exclusion</p></def>
<def title="Releasing a Lock"><p>The process through which a thread releases an acquired 
lock after its operation is finished, prompting other threads to become active and seek 
said lock</p></def>
</deflist>
<p>To enforce these rules, Java provides two distinct methodologies, synchronized blocks and 
sychronized methods. Both of these, however, share the same basic principle of requiring a lock 
to work properly. The following block of code presents how to handle these in detail</p>
<procedure title="Synchronization Methodologies in Java">
<list type="none" columns="1">
<li><deflist collapsible="true">
<def title="Synchronous Blocks">
<p>Synchronous blocks define a variable that is to be held within a lock, and a series of 
lines of code that require a lock to work. The following are some characteristics of this 
block</p>
<list columns="2">
<li><strong>Fine-grained Control</strong>
Synchronized statements provide precise control over which specific object to lock and exactly 
what code needs synchronization. This granular approach allows for better performance by 
limiting the synchronized scope to only the critical sections that require thread safety, rather 
than locking an entire method.</li>
<li><strong>Resource Efficiency</strong>
By synchronizing only specific blocks of code rather than entire methods, threads spend less 
time waiting for locks to be released. This reduces thread contention and improves overall 
application performance, especially in scenarios where only small portions of a method require 
thread safety.</li>
<li><strong>Multiple Lock Support</strong>
Synchronized statements can manage multiple locks within the same method by using different 
objects as monitors. This flexibility enables complex synchronization patterns where different 
parts of the code can be protected by different locks, allowing for more sophisticated thread 
coordination.</li>
<li><strong>Dynamic Lock Selection</strong>
The object used for synchronization can be chosen at runtime, allowing for dynamic locking 
strategies based on program state or conditions. This flexibility enables more sophisticated 
concurrent designs where the synchronization target can change based on runtime requirements.</li>
<li><strong>Automatic Lock Release</strong>
The synchronized block automatically releases the lock when execution exits the block, even if 
an exception occurs. This built-in safety feature prevents deadlocks that might occur from 
forgotten lock releases and ensures proper resource cleanup, making the code more robust and 
reliable.</li>
</list>
<p>In addition to this listing, here is an example on how to define it</p>

```Java

// Example implementation demonstrating synchronization 
// blocks in a university course registration system.
// This system ensures thread safety during validation 
// and registration using synchronized blocks.

import java.util.HashMap;
import java.util.Map;

public class CourseRegistrationSystem {
    private final Map<String, 
        Integer> courses = new HashMap<>();

    // Constructor to initialize available courses with maximum seats
    public CourseRegistrationSystem() {
        courses.put("Mathematics", 3); // Max 3 seats
        courses.put("Physics", 2);    // Max 2 seats
        courses.put("Computer Science", 5); // Max 5 seats
    }

    // Method to validate and register a course 
    // using synchronized blocks
    public void registerCourse(String courseName,
                               String studentName) {
        // Synchronizing block on shared resource `courses`
        synchronized (courses) { 
            if (!courses.containsKey(courseName)) {
                System.out.println(
                    "Course `" + courseName + 
                    "` does not exist for " 
                    + studentName + ".");
                return;
            }

            // Check course availability
            int seatsAvailable = courses.get(courseName);
            if (seatsAvailable > 0) {
                System.out.println("Registering " 
                + studentName 
                + " for course: " + courseName);

                // Update seats within the synchronized 
                // block to prevent race conditions
                courses.put(courseName, seatsAvailable - 1);
                System.out
                    .println("Registration successful!" 
                    +" Remaining seats: " +
                    (seatsAvailable - 1));
            } else {
                System.out.println("No seats available for " 
                + courseName + " for student: " +
                    studentName + ".");
            }
        }
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        // Creating threads simulating 
        // multiple students registering concurrently
        Thread student1 = new Thread(
            () -> system.registerCourse("Mathematics", "Alice"));
        Thread student2 = new Thread(
            () -> system.registerCourse("Mathematics", "Bob"));
        Thread student3 = new Thread(
            () -> system.registerCourse("Physics", "Charlie"));
        Thread student4 = new Thread(
            () -> system.registerCourse("Mathematics", "Eve"));
        Thread student5 = new Thread(
            () -> system.registerCourse("Physics", "Dave"));
        Thread student6 = new Thread(
            () -> system.registerCourse("Physics", "Santiago"));
        //! This last line will cause Dave to not be able to register, 
        //! or any other physics register as there are only two cupos.

        // Start threads
        student1.start();
        student2.start();
        student3.start();
        student4.start();
        student5.start();

        // Wait for all threads to complete
        try {
            student1.join();
            student2.join();
            student3.join();
            student4.join();
            student5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread execution was interrupted.");
        }

        // Print the final course availability
        System.out.println("\nFinal course availability:");
        system.courses.forEach((course, seats) ->
            System.out.println(course + ": " 
                + seats + " seats remaining"));
    }
}
```
</def>
</deflist>
</li>
<li><deflist collapsible="true">
<def title="Synchronous Methods">
<p>Synchronous methods are a way for us to achieve <b><code>atomic operation, i.e., an operation 
that cannot be subdivided into smaller sub-operations
</code></b>. The idea in Java is to <i>simulate</i>this concepts by making sure that a 
Thread ca only access and carry out a single operation at a time. To do this, we use the 
aforedmentioned <b><code>synchronized</code> keyword</b>, which effectively makes these 
operations mutually exclusive and therefore localized to a single thread instead of being 
available to all at the same time.</p>
<p>As before, the following are some characteristics of this methodology</p>
<list type="none" columns="2">
<li><strong>Implicit Object Locking</strong>
Synchronized methods <i><code>automatically use the instance object (this) as the lock for instance 
methods, or the Class object for static methods</code></i>. This implicit locking mechanism simplifies the synchronization 
model by eliminating the need to explicitly specify the lock object, making the code cleaner and 
less prone to locking errors.</li>
<li><strong>Inheritance Considerations</strong>
<i><code>The synchronized modifier is not inherited by overriding methods in subclasses, requiring 
explicit synchronization in overridden methods</code></i> if thread safety is needed. This 
characteristic allows subclasses to make conscious 
decisions about synchronization requirements while maintaining the flexibility to change 
synchronization strategies.</li>
<li><strong>Reentrant Behavior</strong>
<i><code>Synchronized methods support reentrancy, allowing a thread that holds the lock to enter other 
synchronized methods of the same object without deadlocking</code></i>. This feature enables natural method 
composition and recursive calls while maintaining thread safety, as the same thread can acquire 
the same lock multiple times.</li>
<li><strong>Performance Implications</strong>
The synchronized keyword on methods can <i><code>impact performance due to the overhead of acquiring and 
releasing locks for the entire method execution</code></i>. This coarse-grained locking strategy 
may lead to unnecessary thread contention when only small portions of the method require 
synchronization, potentially reducing application throughput in high-concurrency scenarios.</li>

<li><strong>Memory Visibility Guarantees</strong>
Synchronized methods <i><code>establish happens-before relationships, ensuring that all threads see the 
most up-to-date values of variables modified within the synchronized method</code></i>. This automatic 
memory synchronization behavior helps prevent visibility problems common in concurrent 
programming, though it may come with some performance cost.</li>
</list>
<p>Additionally, we shall modify our class registration example with method synchronization, 
rather than blocks.</p>

```Java
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CourseRegistrationSystemV2 {
    private final Map<String, Integer> 
        courses = new HashMap<>();
    private final Map<String, LinkedList<String>> 
        courseStudentMapping = new ConcurrentHashMap<>();
    // Constructor to initialize 
    // available courses with maximum seats
    public CourseRegistrationSystemV2() {
        courses.put("Mathematics", 3);// Max 3 seats
        courseStudentMapping
            .put("Mathematics", new LinkedList<>());
        courses.put("Physics", 2);           // Max 2 seats
        courseStudentMapping
            .put("Physics", new LinkedList<>());
        courses.put("Computer Science", 5);  // Max 5 seats
        courseStudentMapping
            .put("Computer Science", new LinkedList<>());
    }

    // Synchronized method to validate course availability
    public synchronized boolean 
        validateCourseAvailability(String courseName, 
                                  String studentName) {
        if (!courses.containsKey(courseName)) {
            printMessage("Course `" 
            + courseName 
            + "` does not exist for " 
            + studentName + ".");
            return false;
        }
        if (courses.get(courseName) > 0) {
            return true; // Seats are available
        } else {
            printMessage("No seats available for course `"
             + courseName 
             + "` for student: " 
             + studentName + ".");
            return false;
        }
    }

    // Synchronized method for 
    // registering a student to a course
    public synchronized void 
        registerCourse(String courseName, 
            String studentName) {
        if (validateCourseAvailability(courseName, studentName)) {
            int seatsAvailable = courses.get(courseName);
            this
                .courses
                .put(courseName, seatsAvailable - 1);
            this
                .courseStudentMapping
                .get(courseName).add(studentName);
            printMessage(studentName + 
            " successfully registered for `" 
            + courseName + "`. Remaining seats: " 
            + (seatsAvailable - 1));
        }
    }

    // Synchronized method for dropping a course
    public synchronized void 
        dropCourse(String courseName, String studentName) {
        if (!courses.containsKey(courseName)) {
            printMessage("Course `" + courseName 
            + "` does not exist for " 
            + studentName + ".");
            return;
        }
        if (this.courseStudentMapping
            .get(courseName).contains(studentName)) {
            int seatsAvailable = courses.get(courseName);
            this.courses
                .put(courseName, seatsAvailable + 1);
            this.courseStudentMapping
                .get(courseName).remove(studentName);
            printMessage(studentName 
            + " has dropped the course `" 
            + courseName 
            + "`. Available seats: " 
            + (seatsAvailable + 1));
        }
        else {
            printMessage(studentName 
                + " is not registered for `" 
                + courseName + "`.");
        }
    }

    // Synchronized method for logging messages (Thread-safe)
    public synchronized void printMessage(String message) {
        System.out.println(message);
    }

    // Synchronized method to print the final course availability
    public synchronized void printFinalAvailability() {
        System.out.println("\nFinal course availability:");
        this.courses.forEach((course, seats) ->
                                System.out.println(course + ": " 
                                + seats + " seats remaining"));
    }

    public static void main(String[] args) {
        CourseRegistrationSystemV2 system = 
            new CourseRegistrationSystemV2();

        system.printFinalAvailability();
        // Creating threads simulating 
        // multiple student registrations and dropouts
        Thread student1 = new Thread(
            () -> system.registerCourse("Mathematics", "Alice"));
        Thread student2 = new Thread(
            () -> system.registerCourse("Mathematics", "Bob"));
        Thread student3 = new Thread(
            () -> system.registerCourse("Physics", "Charlie"));
        Thread student4 = new Thread(
            () -> system.registerCourse("Mathematics", "Eve"));
            // Alice drops out
        Thread student5 = new Thread(
            () -> system.dropCourse("Mathematics", "Alice")); 
            // Charlie drops out
        Thread student6 = new Thread(
            () -> system.dropCourse("Physics", "Charlie"));  
            // Dave registers
        Thread student7 = new Thread(
            () -> system.registerCourse("Physics", "Dave"));
            // Santiago registers 
        Thread student8 = new Thread(
            () -> system.registerCourse("Physics", "Santiago")); 

        // Start threads
        student1.start();
        student2.start();
        student3.start();
        student4.start();
        student5.start();
        student6.start();
        student7.start();
        student8.start();

        // Wait for all threads to complete
        try {
            student1.join();
            student2.join();
            student3.join();
            student4.join();
            student5.join();
            student6.join();
            student7.join();
            student8.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread execution was interrupted.");
        }

        // Print the final course availability
        system.printFinalAvailability();
    }
}
```
</def>
</deflist></li>
</list>
</procedure>
<p>This has concluded our session for the day, it is almost a complete analysis on how threads 
work in general, virtual and platform, Callable and Executors. For threads, I will most likely 
work on completing class four where this was presented, although the information to be put there 
will most likely be short.
</p>