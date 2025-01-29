# Class #4 | Thursday Jan 23rd | Multitasking

> The following will detail to some extent the concepts of concurrent and parallel progrmaming. The idea is to first 
> define how these work, their uses, and why we have to learn about these now. 

## Multitask Programming | What is it?
<p>Multitask programming is often referred to as parallel or concurrent programming as these are two of the main 
areas where it is applied. The idea of multitask-programming is to make a program <code>more efficient, 
faster, and generally well behaved on multi-core systms</code>, using threads and task management. However, there are 
some cons that we ought to analyze before we use it: <i><b> it could generate <code>incoherent or 
non-deterministic results</code>, it could create issues when <code>concurrently accessing memeory</code>, and 
it could even result in <code>overly complex</code></b></i>
<br/><br/>
These two types of multitask programming can be defined as follows
</p>
<note><i><b>Concurrent multitask-programming </b> works by, on a single processing unit(core or thread) we 
switch from one task to another effectively doing one or more tasks at the same time but in bits. Switching 
control from thread to thread, to increment their execution. This happens when <code>the number of tasks is often 
higher than the number of resources
</code></i></note>
<note><i><b>Parallel multitask-programming </b> works by, on multicore systems, dividing the number of tasks 
that the program might have evenly over the threads and cores available. Effectively this happens when 
<code>the number of tasks is less than or equal to the number of processing units</code></i></note>

### Concurrency | How does it work?
<p>In general, concurrency works using the <i><b><code>round-robin algorithm </code></b></i>, which effectively is 
used to define a series of <code>jumps from task to task</code>, using a predefined allotted time per task called 
<code>time-slice</code>.
<br/><br/>
The process trough which two tasks change their priority in execution (i.e., one goes into execution and another 
exists), is known as <i><b><code>context change (overhead time)</code></b></i>, where our operating system defines a 
time for state 
transition, storing their state, memory layout, and information, and then allow space for another thread to work.
<br/><br/>
Sometimes, <i>if the number of tasks increases and the overhead time remains constant</i>, we can come into a 
situation where work does not advance quickly due to less time to work <i><b><code>(if the number of 
tasks increases often the time-slice is reduced for all tasks)</code></b></i>. The name for this situation is 
<i>thrashing</i>.
</p>

#### Concurrency | Lost Transaction (Lost Memory) issue
<p>The main definition for this task is, sometimes another task might delete what another did. This happens when we 
are sharing memory through processes (tasks) opened in a single thread or multiple threads.
</p>

## Multitask Programming in Java | Terminology and Base Classes
<p>In Java, a <code>Task is anything we want to do</code>, this is implemented through <code>Runnable or 
Callable</code>. Meanwhile, a thread is a unit that <code>provides the mechanism for running a task</code></p>