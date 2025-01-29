# Class #4 | Thursday Jan 23rd | Multitasking

> The following will detail to some extent the concepts of concurrent and parallel progrmaming. The idea is to first 
> define how these work, their uses, and why we have to learn about these now. 

## Multitask programming — MultiThreading or MultiTasking ? 
<p>The concept of multitasking and multi-threading programming has come a long way since the 
inception of the computer and single core CPUs. In the olden days of computation we talked about 
multitasking as this framework that allowed CPUs to have various tasks running under the hood, 
sharing resources and execution threads. While when we talk about multithreading, we talk about 
having a series of threads running concurrently on the same CPU and running a different set of 
tasks sharing the same pool of resources.
</p>
<p>In Java, these two can be used interchangeably (<b>hence why Fausto does it)</b> due to Java 
associating threads with processes and their execution. Even the core definition of a thread, as 
the lowest form of processes (or subprocesses) that the JVM and the program in and of itself can 
support. However, this does not always hold true, if these two were equal terms we would not 
have them in this section, we would not even be doubting their definitions and usage, yet here we 
should delve deep into what they are, uncover their inner details
</p>
<procedure type="choices" title="Multitasking and MultiThreading">
<list columns="2">
<li>
<deflist type="full">
<def title="Multitasking">
<p>Multitasking is a process-based feature where multiple programs run independently, each with 
its own memory space and resources. It enables running multiple applications like a web browser, 
music player, and text editor simultaneously. The CPU does this by defining to each task, 
thread, process or program a slice of CPU time, or a chunk of CPU Time known as timeslice to run 
its code
</p>
<note>Process-based multitasking is heavyweight because each process requires separate memory 
allocation and context switching between processes is computationally expensive.</note>
</def>
</deflist>
</li>
<li>
<deflist>
<def title="Multithreading">
<p>Multithreading is a lightweight execution model where multiple threads share the same process 
resources but execute independently. In Java, this is managed by the JVM through virtual threads, 
making efficient use of system resources.</p>
<note>Thread-based operations are more efficient than process-based ones since threads share the 
same memory space, making context switching and inter-thread communication much faster.</note>
</def>
</deflist>
</li>
</list>
</procedure>
<p>As can be noted in the previous definition, we have two models of operation here. The first 
one, multitasking, is an effective model for systems in which a CPU must handle resource 
allocation and usage for various programs. However, if we are to work on a single application, 
server application or even API, we need to use the concept of multithreading such that our 
application becomes a series of threads rather than instances of itself</p>
<p>Nevertheless, in Java we will soon see that both of these can be thought of as fundamentally 
the same, only with minor differences that affect their use case scenarios, rather than defining 
two whole different worlds. For this reason, here are some pros, cons, and use cases of both 
paradigms.
</p>
<procedure title="Multitasking and Multithreading in Java">
<list columns="1" type="none">
<li>
<deflist type="full">
<def title="Multitasking Programming">
<list>
<li> <b>Pros</b>:

<list>
<li>Complete isolation between processes</li>
<li>More secure as processes don't share memory</li>
<li>Crash in one process doesn't affect others</li>
<li>Better for CPU-intensive tasks</li>
</list>
</li>
<li><b>Cons</b>:
<list>
<li>Higher memory overhead per process</li>
<li>Slower context switching</li>
<li>More complex inter-process communication</li>
<li>Limited by system resources</li>
</list>
</li>
<li> <b>Use Cases</b>:
<list>
<li>Running multiple independent applications</li>
<li>Applications requiring strong isolation</li>
<li>System-level services and daemons</li>
<li>Heavy computational tasks requiring separate memory spaces</li>
</list>
</li>
</list>
</def>
</deflist>
</li>
<li>
<deflist type="full">
<def title="Multithreading Programming">
<list>
<li><b>Pros</b>:
<list>
<li>Shared memory space between threads</li>
<li>Faster context switching</li>
<li>Efficient resource utilization</li>
<li>Easy data sharing between threads</li>
</list>
</li>
<li><b>Pros</b>:
<list>
<li>Potential thread synchronization issues</li>
<li>Risk of deadlocks and race conditions</li>
<li>Debugging can be complex</li>
<li>Shared state can lead to bugs</li>
</list>
</li>
<li><b>Pros</b>:
<list>
<li>Web servers handling multiple client requests</li>
<li>GUI applications for responsive interfaces</li>
<li>Real-time data processing systems</li>
<li>Concurrent I/O operations</li>
</list>
</li>
</list>
</def>
</deflist>
</li>
</list>
</procedure>
<p>So then, what is the point of choosing between one and the other, it basically comes down to 
the process you are working in. In Java (even from my searches), I have not been able to find 
information on multitasking programming that does not reference me back to multithreading and 
threaded applications. As such rather than continue with the dvide, we are going to go back to 
the official Fausto-approved version which is <b><code>multitasking</code></b>, hence from now 
on if someone asks we are working on multitasking programming</p>

## Concurrency and Parallelism
<p>Concurrency and parallelism are two of the main children types of multitasking applications 
that we will develop in our course. These are applications of the ideas of multitasking 
programming in such a way that we efficiently reduce resource usage, and control multiple paths 
of execution at the same time</p>
<p>The idea of this course is to give us an introduction to both of them, however, to do so we 
are to see that, while concurrency is the main way to go with Java, there are little options to 
implement parallelism, aside from carefully managed thread pools or streams. Nevertheless these 
two next sections will introduce each of these topics in a somewhat approachable way</p>

### Concurrency — A closer Look
<p>The idea of concurrent programming is not new, in fact there are wonderful books written 
about it that have more than ten years of age. Concurrency at its core does not change very 
often in Java, and the classes that are used in most Java concurrent programming have been 
around for years, and only minor changes have been done to them.</p>
<p>One key benefit of this is that we are capable of studying this topic easily with in-depth 
resources that, despite their age, still hold information that is valuable to us as Java 
programmers.</p>
<note><p><b>Concurrent programming</b> is defined as a technique in computer programming 
where multiple sequences of operations are executed in overlapping periods of time, 
allowing programs to handle multiple tasks simultaneously, improving efficiency and 
performance</p></note>
<p>At its core, Java supports the concept of concurrent programming through 
<b><code>Threads, Executors, Locks, Concurrent Collections, etc.</code></b>, this is why this 
language is used the most to teach concurrency, as it has safety nets and features developed for 
us to understand the concept thoroughly. 
</p>
<p>To do this, concurrent programming uses Threads first, as the main class that allow us to run 
code somewhat asynchronously (in the sense that it does not follow sequential execution), but in 
a way that is still readable and controllable from a sequential point of view. Before we delve 
into examples of this, we shall take a look at its main characteristics, pros, cons, and use 
cases. Finally we will touch on concurrency in Java with a brief introduction to Threads.  
</p>

#### Concurrency ― Main Characteristics
<p>Concurrent programming has a set of characteristics that make it a powerful tool for 
programmers, from <b><code>task interleaving</code></b>, to <b><code>resource sharing</code></b>,
to <b><code>context switching</code></b>. All of these are to be defined further in the 
following block.
</p>
<deflist type="full" collapsible="true">
<def title="Task Interleaving in Concurrent Programming">
<p>Given that in concurrent programming, tasks (threads in the case of Java) share a 
single processor, we are inevitably forced to share a single processor. To do this the 
system does <b><code>task interleaving</code></b>, where a task <i><code>stops 
its execution mid running when its time-slice is done, and surrenders execution privileges to 
another task.</code></i> This is done to avoid a task from devouring all CPU time and resources 
(sequential programming), and to avoid having long running tasks boggle down execution from other tasks.
</p>
<p>Additionally, task interleaving allows for different priority threads (to be discussed later),
to execute and allows others to also do this. Moreover, this process ensures scalability by 
allowing multiple tasks (far more than what a normal single threaded program could do in tandem),
to execute side-by-side <i><b><code>giving the illusion of parallel execution to the 
user</code></b></i>
</p>
</def>

<def title="Resource Sharing in Concurrent Programming">
<p>In concurrent programming, multiple threads operate within the same process space, requiring 
careful management of shared resources. These resources include <b><code>memory, file handles, 
network connections, and other system resources</code></b>. Threads must coordinate their access 
to these shared resources to maintain <i><code>data consistency and prevent race 
conditions</code></i>.
</p>
<p>Java provides synchronization mechanisms like <b>monitors, locks, and semaphores</b> to ensure 
safe resource sharing. These mechanisms help prevent common concurrency issues such as deadlocks 
and resource starvation, while enabling <i><b><code>controlled access to critical 
sections</code></b></i> of the program.
</p>
</def>

<def title="Context Switching in Concurrent Programming">
<p>Context switching is the process where the CPU <b><code>saves the current state of a thread 
and loads the state of another thread</code></b>. This involves storing and loading register 
values, program counters, and memory maps. The process is <i><code>managed by the operating 
system's scheduler in coordination with the JVM</code></i>.
</p>
<p>While essential for concurrent execution, context switching introduces overhead as it requires 
<b>saving and restoring thread states</b>. The efficiency of context switching significantly 
impacts overall program performance, especially in systems with <i><b><code>high thread 
counts or frequent thread state changes</code></b></i>.
</p>
</def>

<def title="Thread Synchronization in Concurrent Programming">
<p>Thread synchronization ensures orderly execution of concurrent operations through 
<b><code>mutual exclusion and coordination mechanisms</code></b>. Java provides built-in 
synchronization tools like the <i><code>synchronized keyword, volatile variables, and the 
java.util.concurrent package</code></i> to manage thread interaction.
</p>
<p>Proper synchronization is crucial for maintaining data integrity but requires careful 
balance, as excessive synchronization can lead to <i><b><code>performance bottlenecks through 
thread contention and blocking</code></b></i>. Modern Java applications often use higher-level 
concurrency utilities to simplify synchronization while maintaining performance.
</p>
</def>

<def title="Thread States and Lifecycle in Multithreaded Programming">
<p>Threads in Java transition through various states during their lifecycle: 
<b><code>NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, and TERMINATED</code></b>. Each state 
represents different phases of thread execution and resource utilization. The JVM manages these 
transitions based on <i><code>thread operations, scheduling decisions, and resource 
availability</code></i>.
</p>
<p>Understanding thread states is crucial for debugging concurrency issues and optimizing 
application performance. Proper management of thread lifecycles helps prevent resource leaks 
and ensures <i><b><code>efficient utilization of system resources</code></b></i>.
</p>

<img alt="ThreadLifeCycle.png" src="ThreadLifeCycle.png"/>
</def>
</deflist>
<p>As can be seen, there are a series of main characteristics, most having to do with the CPU 
and the way it interacts with the JVM to obscure away the details of threads implementations and 
communication with the CPU. And another portion of these have to do with how we write our code, 
taking care of threads and their life cycles, synchronization, etc. 
</p>
<p>Additionally to these though, it is important for us to define a bit more about the pros and 
cons of concurrent programming, in general.</p>
<procedure collapsible="false">
<tabs>
<tab title="Pros">
<list>
<li><strong>Improved Performance</strong>
Enables better resource utilization through parallel execution of tasks. Multiple operations can 
progress simultaneously, reducing overall execution time. Efficient use of modern multi-core 
processors and system resources.</li>
<li><strong>Enhanced Responsiveness</strong>
Applications remain responsive even during long-running operations. User interface threads can 
continue processing while background tasks execute. Prevents application freezing and provides 
better user experience.</li>
<li><strong>Resource Sharing</strong>
Efficient sharing of resources between threads within the same process. Reduced memory overhead 
compared to multiple processes. Fast inter-thread communication through shared memory space.</li>
<li><strong>Scalability</strong>
Supports handling multiple tasks simultaneously. Can easily adapt to varying workloads by 
adjusting thread pool sizes. Enables processing of numerous concurrent operations efficiently.</li>
</list>
</tab>
<tab title="Cons">
<list>
<li><strong>Complexity Management</strong>
Increased program complexity due to synchronization requirements. Difficult to debug and test 
concurrent code effectively. Requires careful design to avoid race conditions and deadlocks.</li>
<li><strong>Resource Contention</strong>
Multiple threads competing for shared resources can cause bottlenecks. Synchronization overhead 
can impact performance. Risk of thread starvation in high-contention scenarios.</li>
<li><strong>Memory Consistency</strong>
Challenges in maintaining consistent view of shared data across threads. Need for proper 
synchronization mechanisms to ensure data integrity. Potential for subtle bugs due to memory 
visibility issues.</li>
<li><strong>Development Overhead</strong>
Higher development and maintenance costs due to complexity. Requires specialized knowledge and 
expertise in concurrent programming. More difficult to modify and refactor concurrent code 
safely.</li>
</list>
</tab>
</tabs>
</procedure>

#### Concurrency ― Common Pitfalls
<p>There were two common pitfalls discussed in class, these were the 
<i><b><code>lost transaction problem </code> (completely focused on synchronization), 
and <code>thrashing</code> (focused on CPU time-slice reduction due to an uneven increase of 
tasks with respect to CPU cores)
</b></i>. These two will be discussed in this section, not as deeply as I would like, but just 
in general such that we have a complete understanding of the issues of concurrent programming</p>

<procedure title="Common Pitfalls in Concurrent Programming" type="choices">
<deflist>
<def title="Thrashing">
<note>Thrashing is a condition or a situation when the system is spennding a mejor portion of 
its time <i><b><code>servicing context changes</code></b></i>, than what it should be on 
executing tasks and producing results
</note>
<p>Before this section, we defined that in some cases, when there is a context change in 
concurrent programming (i.e. when a thread has to stop its execution to give way to another 
whose timeslice just began), there is a process through which for one thread <b>the CPU must 
store all state involved (memory, registers, program counters, and any other useful information)
</b>. Well this happens the other way around too, if we are loading a thread (context change), 
and we are getting ready to execute the CPU must reload all the state before to match all 
preconditions and guarantee safe execution.</p>
<p>Under normal conditions, this is no issue for the CPU as it is capable of loading and 
unloading state quickly and <b><code>so long as there is a reasonable number of 
threads</code></b>, it is capable of serving their needs and executing their tasks. However, if 
the number of tasks to execute where to grow, without taking into consideration the nature of 
said tasks and resource availability, we can fall into <b><code>excessive content 
switching,which leads to decreased system performance</code></b></p>
<p>To remedy this issue, it is often recommended to <b><code>not submit too many 
tasks or too many long running tasks, as well as increase thread pool size</code></b></p>
</def>
<def title="Lost Transaction">
<note><p>The <b>Lost transaction phenomena</b> occurs when we are trying to define a set 
of threads that access unsynchronized memory, accessing it directly within all threads 
leading to concurrent modifications that might override or ignore previously written data, as it 
is <b>reading previous, unsafe, and illegal states</b>
</p></note>
<p>In concurrent programming there are a lot more lost transaction problems that we could talk 
about, but this is the most important one here, the lost update problem. The lost update 
problem <i><b><code>typically occurs when you are trying to modify a variable or 
state without proper synchronization</code></b></i>, this can lead to incorrect states being 
reported in different threads and overall lead to issues in execution.</p>
<p>To remedy this, we must use classes that are synchronized, like concurrent collections, or 
lock methods and or memory values either by defining them as finals (immutable) or using 
synchronized methods, forcing the program to acquire locks to access the data</p>
</def>
</deflist>
</procedure>

### Concurrency ― Implementation In Java
<p>To implement concurrency, we will follow the tutorial of the Java 8 platform (when this was 
introduced), and some of the examples will be provided through AI and the book, mostly to make 
them more complete and allow me to focus on explaining what is going on (I have too many classes 
to write long pages about threads!). 
</p>
<p>The main building block in Java concurrency is both the <code>java.concurrent</code> package, the
<code>java.lang.Thread</code> class and the <code>java.lang.Runnable</code> interface. For 
reasons that will become obvious as we progress through this chapter, there are certain 
additional methods and classes that we will use that make our work with threads much easier. For 
now, let us take a look at the Thread and Runnable implementations
</p>

#### Implementation ― Threads and Runnables
<p>A Runnable  is a <b><code>functional interface with a single method run() that 
returns void</code></b> (keep this in mind as in other chapters we will analyze Futures), that 
executes a task but returns no value. This means that a Runnable can be implemented as any 
method that has a signature of doing something and not returning anything (System.out.println as 
an example).  
</p>
<p>The Runnable interface is the basis of the Threads class as it is the core component that is 
required within the instantiation of Threads. <b>Threads are lightweight processes capable 
of executing a task, keeping their own program counter, stack and values</b>, but that at the 
same time require some way of execution as they are meant to be executed, not just defined.</p>
<p>In Java, the <b>contents of a Runnable are known as a task</b>, and a thread is used to run a 
task concurrently in the CPU. Now in most examples there are two ways that this can be done, 
either you define a singular thread object which encapsulates a Runnable, or you create a Thread 
subclass (this is possible) and override its Run method. Whichever path you choose, the method 
most needed when working directly with Threads is <code>.start()</code>, which tells the thread 
to begin its execution on the CPU in parallel. Lastly when we want to stop the threads we use 
the method <code>.join()</code>, or one of its overloads to define a wait period
</p>
<p>The following is an example of all of this</p>

```Java

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

// Simulated HTTP API class with GET and POST functionality
class SimpleHttpApi {
    private final Map<String, String> 
        database = new ConcurrentHashMap<>();

    // Simulated GET method
    public synchronized String get(String key) {
        return database.getOrDefault(key, "No data available");
    }

    // Simulated POST method
    public synchronized void post(String key, String value) {
        database.put(key, value);
    }
}

// Thread class for POST requests
class PostThread extends Thread {
    private final SimpleHttpApi api;
    private final String key;
    private final String value;

    public PostThread(SimpleHttpApi api, String key, String value) {
        this.api = api;
        this.key = key;
        this.value = value;
    }

    @Override
    public void run() {
        System.out
            .println("Processing POST request: Key = " 
            + key + ", Value = " + value);
        api.post(key, value);
        System.out.println("POST completed: Key = " + key);
    }
}

// Runnable class for GET requests
class GetRunnable implements Runnable {
    private final SimpleHttpApi api;
    private final String key;

    public GetRunnable(SimpleHttpApi api, String key) {
        this.api = api;
        this.key = key;
    }

    @Override
    public void run() {
        System.out.println("Processing GET request for Key = " + key);
        String result = api.get(key);
        System.out
            .println("GET result: Key = " 
            + key + ", Value = " + result);
    }
}

// Main class to run the program
public class CropApiSimulation {
    public static void main(String[] args) {
        SimpleHttpApi simulatedApi = new SimpleHttpApi();

        // Simulate POST requests
        Thread postCarrot = 
            new PostThread(simulatedApi, "carrot", "Genus: Daucus");
        Thread postOnion = 
            new PostThread(simulatedApi, "onion", "Genus: Allium");

        // Simulate GET requests
        Thread getCarrot = 
            new Thread(new GetRunnable(simulatedApi, "carrot"));
        Thread getOnion = 
            new Thread(new GetRunnable(simulatedApi, "onion"));
        // Non-existing key
        Thread getPotato = 
            new Thread(new GetRunnable(simulatedApi, "potato"));

        // Starting POST threads
        postCarrot.start();
        postOnion.start();

        try {
            // Wait for POST operations to finish
            postCarrot.join();
            postOnion.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        // Starting GET threads
        getCarrot.start();
        getOnion.start();
        getPotato.start();

        try {
            // Wait for GET operations to finish
            getCarrot.join();
            getOnion.join();
            getPotato.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        System.out.println("Simulation completed!");
    }
}
```

### Concurrency ― Issues with Synchronization
<p>The previous block of code showcased a) the ability of Java to handle multiple classes and 
types of classes within a file, b) the way to implement both a Runnable implementation class and 
a Thread extension class, and c) the way we use both <code>start() and join()</code> to handle 
execution and termination of Thread objects. Compared to this example, the next cahpter in this 
webpage will show more complex classes and futures but for now this is enough.
</p>
<p>What isn't enough is one part of the code, <code>the usage of the keywords 
synchronized and the collection ConcurrentHashMap</code>. These two declarations make one thing 
possible, reducing the possibility of a lost transaction problem. As you can recall form our 
explanation on the topic, an issue in synchronization, also known as race conditions or lost 
transaction, can lead to erroneous results, both from reading and writing operations onto 
unsynchronized objects, or through unsynchronized methods</p>
<p>One thing that the code before showed was the use of both locks (throuigh the use of 
syncrhonized methods) and the use of concurrent-modification-tolerable classes 
(ConcurrentHashMap). The idea of this is to effectively reduce all kinds of problems with 
synchronization, but what are these?</p>

#### Issues with Synchronization ― Thread Interference
<deflist type="full" collapsible="false">
<def title="Definition">
<p>Interference, is an issue with threads tht happens when <i><b>two operations, on 
different threads, but operating under the same MUTABLE data, interleave</b> (recall that 
interleaving or context switching is nothing more than changing the thread executing for 
another)</i>.This then translates into <code>overlapping operations with sequences of 
steps that might override each other</code></p>
</def>
</deflist>
<deflist>
<def title="How to identify Thread Interference?">

<p>Thread Interference can often be identified through unexpected or incorrect program outcomes 
that occur under concurrent thread execution. Common symptoms include <b><code>data 
inconsistencies</code></b>, where shared variables 
contain unexpected values due to overlapping 
thread operations, and <b><code>unreliable behavior</code></b>, such as different execution results for 
the same inputs when the program is run multiple times. Another indication is <b><code>debugging 
complexities</code></b>, where stepping 
through code or adding print statements seems to change the 
problem's behavior, often hiding or introducing the issue.</p>
</def>
</deflist>
<deflist>
<def title="How to Remedy Thread interference?">
<p>Thread interference can be remedied by implementing proper synchronization techniques, such 
as using <b><code>synchronized blocks or methods</code></b>, to ensure that only one thread 
accesses mutable shared data at a time. Another approach is leveraging thread-safe classes like 
<b><code>ConcurrentHashMap</code></b> or <b><code>CopyOnWriteArrayList</code></b>, which 
internally handle synchronization. By ensuring that 
critical sections of your code are isolated and coordinated across threads, you can prevent 
overlapping operations and resolve inconsistencies, thereby eliminating interference issues.</p>
</def>
</deflist>

#### Issues With Synchronization ― Memory Consistency Errors
<deflist>
<def title="Definition">
<p>Memory consistency errors occur when <i><b><code>different threads have 
inconsistent views of what should be the same data.</code></b></i>. This is often caused 
by not following the rule of <i><code>happens-before</code></i>, which states that we must 
guarantee that the memory written by one statement are visible to another statement (in the 
same or other thread)
</p>
<p>Effectively, the happens before is a complex Java specification behavior that 
determines in what cases does an action constitute as coming before (literaly) of another 
one. This in threads can be remedied by having safe and synchronized methods</p></def>
</deflist>

<deflist>
<def title="How to identify Memory Consistency Errors?">
<p>Memory consistency errors often manifest as <b><code>unexpected, inconsistent, or stale 
data</code></b> being accessed by threads, even if no direct interference between threads is observed. These errors 
typically arise due to threads working with local copies of shared data that are not properly synchronized with main 
memory. Symptoms include <b><code>stale or outdated values</code></b>, where threads appear to work with old versions of 
data despite updates made by other threads, and <b><code>non-deterministic program behavior</code></b>, where the same 
program produces different results across multiple runs. Proper implementation of the 
<b><code>happens-before</code></b> relationship, which establishes a clear order of memory operations (like write 
and read), is necessary to resolve these errors effectively.</p>
</def>
</deflist>

<deflist>
<def title="How to Remedy Memory Consistency Errors?">
<p>Memory consistency errors can be remedied by ensuring proper synchronization and establishing 
a <b><code>happens-before</code></b> relationship across threads. This can be achieved by using 
synchronization constructs such as <b><code>synchronized blocks</code></b>, 
<b><code>locks</code></b>, or higher-level concurrency tools like 
<b><code>ExecutorService</code></b>. Additionally, leveraging thread-safe classes, such as 
<b><code>Atomic variables</code></b>, guarantees atomic updates to shared data and prevents 
stale reads. Properly handling shared memory access is crucial to ensuring that changes made by 
one thread are visible to others in a predictable order, eliminating the state inconsistencies 
caused by memory consistency errors.</p>
</def>
</deflist>
<p>Having defined these, lets take a look at some examples of code that causes these issues and 
code that fixes them</p>

##### Thread Interference
<compare first-title="Code That Causes Thread Interference" second-title="Code That 
Fixes Thread Interferece" type="top-bottom">

```Java

import java.util.ArrayList;
import java.util.List;

public class MusicDownloader {

    private List<String> musicLibrary = new ArrayList<>();
    private String currentlyPlaying;

    // Unsynchronized method to simulate downloading music
    public void downloadMusic(String url) {
        System.out.println(Thread.currentThread().getName() + " downloading: " + url);
        musicLibrary.add(url); // Shared resource accessed unsafely
    }

    // Unsynchronized method to simulate playing music
    public void playMusic() {
        if (!musicLibrary.isEmpty()) {
            currentlyPlaying = musicLibrary.remove(0); // Shared resource accessed unsafely
            System.out.println(Thread.currentThread().getName() + " playing: " + currentlyPlaying);
        } else {
            System.out.println(Thread.currentThread().getName() + " nothing to play!");
        }
    }

    public static void main(String[] args) {
        MusicDownloader downloader = new MusicDownloader();

        // Thread 1 - Downloads music
        Thread downloadThread = new Thread(() -> {
            downloader.downloadMusic("http://example.com/song1.mp3");
            downloader.downloadMusic("http://example.com/song2.mp3");
        }, "DownloadThread");

        // Thread 2 - Plays music
        Thread playThread = new Thread(() -> {
            downloader.playMusic();
            downloader.playMusic();
        }, "PlayThread");

        downloadThread.start();
        playThread.start();

        try {
            downloadThread.join();
            playThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        System.out.println("Simulation completed!");
    }
}
```

```Java

import java.util.ArrayList;
import java.util.List;

public class MusicDownloader {

    private final List<String> musicLibrary = new ArrayList<>();
    private String currentlyPlaying;

    // Synchronized method to simulate downloading music
    public synchronized void downloadMusic(String url) {
        System.out.println(Thread.currentThread().getName() + " downloading: " + url);
        musicLibrary.add(url); // Shared resource accessed safely
    }

    // Synchronized method to simulate playing music
    public synchronized void playMusic() {
        if (!musicLibrary.isEmpty()) {
            currentlyPlaying = musicLibrary.remove(0); // Shared resource accessed safely
            System.out.println(Thread.currentThread().getName() + " playing: " + currentlyPlaying);
        } else {
            System.out.println(Thread.currentThread().getName() + " nothing to play!");
        }
    }

    public static void main(String[] args) {
        MusicDownloader downloader = new MusicDownloader();

        // Thread 1 - Downloads music
        Thread downloadThread = new Thread(() -> {
            downloader.downloadMusic("http://example.com/song1.mp3");
            downloader.downloadMusic("http://example.com/song2.mp3");
        }, "DownloadThread");

        // Thread 2 - Plays music
        Thread playThread = new Thread(() -> {
            downloader.playMusic();
            downloader.playMusic();
        }, "PlayThread");

        downloadThread.start();
        playThread.start();

        try {
            downloadThread.join();
            playThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        System.out.println("Simulation completed!");
    }
}
```
</compare>
<p>Execution of the previous code shows that the methods that are supposed to go after 
downloading, i.e., playThread threads methods were going first, exhausting their limited 
existances with playing nothing while the worker threads for the downloads were not even started.
</p>

```Markdown
PlayThread nothing to play!
musicLibrary = []
Currently Playing: null
PlayThread nothing to play!
musicLibrary = []
Currently Playing: null
PlayThread nothing to play!
DownloadThread downloading: http://example.com/song1.mp3
musicLibrary = [http://example.com/song1.mp3]
Currently Playing: null
DownloadThread downloading: http://example.com/song2.mp3
musicLibrary = [http://example.com/song1.mp3, http://example.com/song2.mp3]
Currently Playing: null
Simulation completed!

```
<p>Running the second code shows a correct execution</p>

```Markdown
DownloadThread downloading: http://example.com/song1.mp3
DownloadThread downloading: http://example.com/song2.mp3
PlayThread playing: http://example.com/song1.mp3
PlayThread playing: http://example.com/song2.mp3
Simulation completed!
```

##### Memory Consistency Errors
<compare first-title="Code That Causes Memory Consistency Errors" second-title="Code That Fixes 
memory Consistency Errors" type="top-bottom" >

```Java
import java.util.ArrayList;
import java.util.List;

public class FileDownloader {

    private List<String> downloadedFiles = new ArrayList<>();
    private boolean downloadComplete = false;

    public void downloadFile(String fileName) {
        System.out
            .println(Thread.currentThread().getName() 
            + " downloading: " + fileName);

        // Simulate download delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err
                .println("Download interrupted: " 
                + e.getMessage());
        }

        downloadedFiles.add(fileName);
        downloadComplete = true; // Memory consistency issue: 
                                 // No synchronization
    }

    public void verifyFiles() {
        if (downloadComplete) { // Memory consistency issue: 
                                // May read stale value
            System.out.println(Thread.currentThread().getName() 
                + " verifying files: " + downloadedFiles);
        } else {
            System.out.println(
                Thread.currentThread().getName() 
                + " no files to verify!");
        }
    }

    public static void main(String[] args) {
        FileDownloader fileDownloader = new FileDownloader();

        // Thread 1 - Downloads files
        Thread downloadThread = new Thread(() -> {
            fileDownloader.downloadFile("file1.txt");
            fileDownloader.downloadFile("file2.txt");
        }, "DownloadThread");

        // Thread 2 - Verifies files
        Thread verificationThread = new Thread(() -> {
            fileDownloader.verifyFiles();
        }, "VerificationThread");

        downloadThread.start();
        verificationThread.start();

        try {
            downloadThread.join();
            verificationThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " 
            + e.getMessage());
        }

        System.out.println("Simulation completed!");
    }
}
```

```Java
import java.util.ArrayList;
import java.util.List;

public class FileDownloader {

    private List<String> downloadedFiles = new ArrayList<>();
    private boolean downloadComplete = false;

    public synchronized void downloadFile(String fileName) {
        System.out
                .println(Thread.currentThread().getName()
                                 + " downloading: " + fileName);

        // Simulate download delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err
                    .println("Download interrupted: "
                                     + e.getMessage());
        }

        downloadedFiles.add(fileName);
        downloadComplete = true; // Memory consistency issue:
        // No synchronization
    }

    public void verifyFiles() {
        synchronized (this){
            if (downloadComplete) { // Memory consistency issue:
                // May read stale value
                System.out.println(Thread.currentThread().getName()
                                           + " verifying files: " + downloadedFiles);
            } else {
                System.out.println(
                        Thread.currentThread().getName()
                                + " no files to verify!");
            }
        }
    }

    public static void main(String[] args) {
        FileDownloader fileDownloader = new FileDownloader();

        // Thread 1 - Downloads files
        Thread downloadThread = new Thread(() -> {
            fileDownloader.downloadFile("file1.txt");
            fileDownloader.downloadFile("file2.txt");
        }, "DownloadThread");

        // Thread 2 - Verifies files
        Thread verificationThread = new Thread(() -> {
            fileDownloader.verifyFiles();
        }, "VerificationThread");

        downloadThread.start();
        verificationThread.start();

        try {
            downloadThread.join();
            verificationThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: "
                                       + e.getMessage());
        }

        System.out.println("Simulation completed!");
    }
}
```
</compare>


### Concurrency ― Issues of Liveliness
<p>Having checked these definitions, it is time for us to move to two more interesting concepts, 
that will be the last concepts covered in this topic, Deadlocks and Thread Starvation
</p>

#### Issues of Liveliness ― Deadlocks
<p>Deadlocks are common occurances when we are working with Threads that might have to access 
synchronized memory. The idea is that this issue happens when <b><code>two threads 
are stuck waiting for each other to finish</code></b>, often resulting from circular access 
patterns to shared resources that are kept under locks for synchronous management.</p>
<p>For a deadlock to occur, four conditions must be met simultaneously: <i><code>mutual 
exclusion (resources cannot be shared), hold and wait (threads hold resources while waiting 
for others), no preemption (resources cannot be forcibly taken away), and circular wait 
(each thread holds resources needed by another thread in a circular chain)</code></i>. 
Breaking any of these conditions can prevent deadlocks from occurring.</p>

#### Issues of Liveliness ― Starvation
<p>Starvation occurs in concurrent systems when <b><code>a thread is perpetually denied access 
to necessary resources to complete its execution</code></b>, often resulting from higher 
priority threads consistently taking precedence or from poorly designed resource allocation 
strategies that unfairly favor certain threads over others.</p>

<p>Unlike deadlocks, starvation doesn't involve mutual blocking, but rather unfair resource 
distribution. The key factors that contribute to starvation include: <i><code>thread priority 
mismanagement (high-priority threads constantly preempting lower ones), greedy threads 
(threads holding resources for extended periods), poor scheduling algorithms, and inefficient 
synchronization mechanisms that favor certain threads</code></i>. Preventing starvation 
typically involves implementing fair locking policies and ensuring balanced resource 
allocation.</p>


### Parallelism — A Closer Look
<p>Parallelism is another component of the multithreading programming toolbox. The idea now is 
not just to have a process that manages, within a single execution environment (commonly a CPU 
core), multiple threads, rather the idea is to spread the load <b><code>over multiple cores
</code></b></p>
<p>From this concept then we can extrapolate how it works, and generally we can understand that 
it is simply a multicore version of multithreading in which you define various threads whose 
work wil go to all available CPUs for processing, rather than switching rapidly on one to create 
the illusion of parallelism.
</p>
<p>While this method of multithreading is not supported directly in Java, often it is 
implemented through <b><code>fork/join frameworks stemming from Executor services</code></b>, 
they are more often seen in Streams with methods like <code>parallelStream()</code></p>

