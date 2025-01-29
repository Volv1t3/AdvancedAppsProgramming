<p>If you are to leave the original code provided, with the comment on the priority and simple join methods, you would have an 
output that is similar to this</p>

```Text
At runtime, my computer has available this many processors available= 8
At the beginning of execution my context has this many threads opened = 2
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Print 10 Task?] : 10 From main!
```
```Java
//! Rest of the code above
@Override
public void run() {
    for(int i = 0; i < counterOfPrintedVals; i++){
        System.out.println("Message From : [ "
                                   + this.threadInfo
                                   + "] : "
                                   + this.getThingToPring());
        Thread.yield();
    }
}
```

<p>An output that indicates that these two are yielding to each other, allowing one to do a certain number of tasks before 
going to the other (effectively then, since the <code>Thread.yield()</code> method is within the for loop, it is called per 
iteration of it, causing this behavior.</p>
<p>if we were to chang the <code>Thread.yield()</code> method to be outside the for loop, we should see that each of them finishes
once it does and yields to the other when indicated</p>

```text
At runtime, my computer has available this many processors available= 8
At the beginning of execution my context has this many threads opened = 2
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Print 10 Task?] : 10 From main!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
Message From : [ Thread-0] : Hello, as defined in the main, this came from a task!
```
```Java
//! Rest of the code above
@Override
public void run() {
    for(int i = 0; i < counterOfPrintedVals; i++){
        System.out.println("Message From : [ "
                                   + this.threadInfo
                                   + "] : "
                                   + this.getThingToPring());
    }
    Thread.yield();
}

```

<p>Consider now, that while we might want these to do something like print messages, how about having them modify the salary
of a person. Lets create a similar inner class, have two employees each with their salaries, and lets have two threads, one 
add and one reduce their salaries.</p>
<p>if we were to start on 1000 bucks, and if we executed both of them at the same time, we would have a thread that subtracts 100
and another one that adds 100, in theory after 10 iterations we should have the same amount of money</p>