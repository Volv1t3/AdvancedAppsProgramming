package PracticeModule.MultiThreadingProgramming;

import javax.management.openmbean.CompositeData;
import java.lang.management.ThreadInfo;

/**
 * @author : Santiago Arellano
 * @date : Sunday, Jan 26, 2025
 * @description : The following file contains information about multi-threading basics, it will
 * contain most classes that will be used to said implementation and it will define some helper
 * methods and classes to get information from thread amounts, etc.
 */
public class ThreadingBasics {
    public static void main(String[] args) {
        //! My first thread instance, my first task instance
        PrintNTimesRunnable printingTask = new PrintNTimesRunnable("Hello, as defined in the main" +
                                                                           ", this came from a " +
                                                                           "task!");
        /*
        ? This is the normal way of declaring a Thread in java, it takes a runnable task as it will
        ? call its .run() method within the thread;
        */
        Thread printingThread = new Thread(printingTask);
        printingTask.setThreadInformation(printingThread.getName());
        PrintNTimesRunnable printingIntegerTask = new PrintNTimesRunnable("10 From main!");
        Thread printingThreadTwo = new Thread(printingIntegerTask, "Print 10 Task?");
        printingIntegerTask.setThreadInformation(printingThreadTwo.getName());

        /*
         ? To start these threads, I would like to use one of Fausto's classes to get the number
         ? of threads in main before running them.
         */
        System.out.println("At runtime, my computer has available this many processors available=" +
                                   " " + Runtime.getRuntime().availableProcessors());
        System.out.println("At the beginning of execution my context has this many threads opened" +
                                   " = " + Thread.activeCount());

        /*
         ? Now I would like to, effectively, start these threads
         */
        // printingThread.setPriority(10); // * This method increases the priority of this thread to
                                        //* its max of 10
        printingThread.start(); //! These methods start the threads, a thing that is different from
        printingThreadTwo.start(); //! joining the threads.

        try{
            printingThread.join();
            printingThreadTwo.join();
        } catch (InterruptedException e) {
            //! Interesting new exception here
            System.out.println("e.getLocalizedMessage() = " + e.getLocalizedMessage());
            System.out.println("This means that one of our threads was interrupted");
            System.out.println(e.getMessage());
        }

    }
}

class PrintNTimesRunnable implements Runnable{

    //! Internal params
    private final int counterOfPrintedVals = 10;
    private String thingToPring;
    private String threadInfo;
    public PrintNTimesRunnable(String thingToPring){
        this.setThingToPring(thingToPring);
    }

    public void setThingToPring(String thingToPring){
        if (!thingToPring.isBlank()){
            this.thingToPring = thingToPring;
        }
    }

    public String getThingToPring(){return this.thingToPring;}
    public int getCounterOfPrintedVals() {return this.counterOfPrintedVals;}
    public final void setThreadInformation(String externalThreadInformation){
        if (externalThreadInformation != null){
            this.threadInfo = externalThreadInformation;
        }
    }

    @Override
    public void run() {

        for(int i = 0; i < counterOfPrintedVals; i++){
            System.out.println("Message From : [ "
                                       + this.threadInfo
                                       + "] : "
                                       + this.getThingToPring());
        }

    }
}
