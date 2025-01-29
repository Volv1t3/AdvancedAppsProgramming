package PracticeModule.MultiThreadingProgramming;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadTypes {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello Wold!");
            }
        });

        Thread virtualThread = Thread.ofVirtual().unstarted(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World! from Virtual Thread");
            }
        });

        virtualThread.start();
        thread.start();
        try {
            virtualThread.join();
            thread.join();
            virtualThread.interrupt();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //! Lets create two platform threads and two virtual threads, which are going to help us
        //! count from 1 to 100 and 2 to 200 in steps of 1
        int beginNum = 1;
        int endNum = 100_000;

        Thread platformThreadOne =
                Thread.ofPlatform().unstarted(new CountNumberTask(beginNum, endNum, beginNum));
        platformThreadOne.setName("platofrmThreadOne");
        Thread platformThreadTwo =
                Thread.ofPlatform().unstarted(new CountNumberTask(beginNum + 1, endNum*2,
                                                                  beginNum));
        platformThreadTwo.setName("platformThreadTwo");

        // Print the number of active threads before starting the platform threads
        System.out.println("Number of active threads before starting: " + Thread.activeCount());

        // Record the start time
        long startTime = System.currentTimeMillis();

        // Start both platform threads
        platformThreadOne.start();
        platformThreadTwo.start();
        System.out.println(Thread.activeCount());
        try {
            // Join both platform threads
            platformThreadOne.join();
            platformThreadTwo.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Calculate and print the execution time
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time for platform threads: " + (endTime - startTime) + " ms");

        Thread virtualThreadOne = Thread.ofVirtual().unstarted(new CountNumberTask(beginNum, endNum, beginNum));
        virtualThreadOne.setName("virtualThreadOne");
        Thread virtualThreadTwo = Thread.ofVirtual().unstarted(new CountNumberTask(beginNum + 1,
                                                                                   endNum*2,
                                                                                   beginNum));
        virtualThreadTwo.setName("virtualThreadTwo");

        // Print the number of active threads before starting the platform threads
        System.out.println("Number of active threads before starting: " + Thread.activeCount());

        // Record the start time
        startTime = System.nanoTime();

        // Start both platform threads
        virtualThreadOne.start();
        virtualThreadTwo.start();

        System.out.println(Thread.activeCount());
        try {
            // Join both platform threads
            virtualThreadOne.join();
            virtualThreadTwo.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Thread.activeCount());
        // Calculate and print the execution time
        endTime = System.nanoTime();
        System.out.println("Execution time for virtual threads: " + (endTime - startTime) + " ns");
        
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

class BankingApplication {

    // Simulates a banking transaction task
    static class TransactionTask implements Runnable {
        private final String transactionName;

        public TransactionTask(String transactionName) {
            this.transactionName = transactionName;
        }

        public static <T extends Runnable> Thread getAsThread( T transactionTask){
            return Thread.ofPlatform().unstarted(transactionTask);
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


        try(ExecutorService fixedThreadPool =
                    Executors.newFixedThreadPool(4, new TransactionTaskFactory());){
            // Submit multiple tasks
            // (transactions) to the thread pool
            for (int i = 1; i <= 8; i++) {
                fixedThreadPool
                        .submit(new TransactionTask("Transaction-" + i));
            }
            fixedThreadPool.shutdown();
        }
    }


    public static class TransactionTaskFactory implements ThreadFactory{


        @Override
        public Thread newThread(Runnable r) {
            return TransactionTask.getAsThread(r);
        }
    }
}

class BankAccountExample {

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
                    account::checkBalance);
            cachedThreadPool.submit(
                    () -> account.withdraw(1200.0));
            // Should trigger insufficient funds
            cachedThreadPool.submit(
                    () -> account.deposit(500.0));
            cachedThreadPool.submit(
                    account::checkBalance);
        } finally {
            // Shutdown the thread pool
            // after all tasks are submitted
            cachedThreadPool.shutdown();
        }
    }
}

class POSExample {

    // Simulates a Point-of-Sale system with synchronized operations
    static class POS {
        private final HashMap<String, Integer> inventory = new HashMap<>();
        // Synchronized method to add an item
        public synchronized void addItem(String item, int amount) {
            inventory.put(item, inventory.getOrDefault(item, 0) + amount);
            System.out.println(Thread.currentThread().getName() +
                                       " added: " + amount + " units of " + item +
                                       " | Current inventory: " + inventory);
        }

        // Synchronized method to review an item
        public synchronized void reviewItem(String item) {
            int stock = inventory.getOrDefault(item, 0);
            System.out.println(Thread.currentThread().getName() +
                                       " reviewed: " + item + " | Stock: " + stock);
        }

        // Synchronized method to remove an item
        public synchronized void removeItem(String item, int amount) {
            if (inventory.containsKey(item) && inventory.get(item) >= amount) {
                inventory.put(item, inventory.get(item) - amount);
                if (inventory.get(item) == 0) {
                    inventory.remove(item); // Remove the item completely if stock is 0
                }
                System.out.println(Thread.currentThread().getName() +
                                           " removed: " + amount + " units of " + item +
                                           " | Current inventory: " + inventory);
            } else {
                System.out.println(Thread.currentThread().getName() +
                                           " tried to remove: " + amount + " units of " + item +
                                           " | Not enough stock or item does not exist!");
            }
        }
    }

    public static void main(String[] args) {
        // Create a single-threaded executor
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        // Create a POS instance
        POS posSystem = new POS();

        try {
            // Submit tasks to the executor to demonstrate sequential execution
            singleThreadExecutor.submit(() -> posSystem.addItem("Apples", 50));
            singleThreadExecutor.submit(() -> posSystem.addItem("Bananas", 30));
            singleThreadExecutor.submit(() -> posSystem.reviewItem("Apples"));
            singleThreadExecutor.submit(() -> posSystem.removeItem("Apples", 20));
            singleThreadExecutor.submit(() -> posSystem.reviewItem("Apples"));
            singleThreadExecutor.submit(() -> posSystem.removeItem("Bananas", 40)); // Should fail
            singleThreadExecutor.submit(() -> posSystem.reviewItem("Bananas"));
        } finally {
            // Shutdown the executor after tasks are completed
            singleThreadExecutor.shutdown();
        }
    }
}