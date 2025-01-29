package PracticeModule.MultiThreadingProgramming;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

public class ThreadingModifications {


    public static void main(String[] args) {
        employee instanceOne = new employee("Santiago",
                                            BigInteger.valueOf(1000));

        System.out.println("instanceOne salary before declaring all threads = " + instanceOne._salary);
        //! Lets create to threads, both with a method that effectively increases or decreases an
        //! external object
        AtomicInteger integer = new AtomicInteger(0);
        AtomicInteger integer1 = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);
        employeeModificationRunnable increaseRunnable = new employeeModificationRunnable(() -> {
            instanceOne.increaseSalary();
            System.out.println("instanceOne increasedRunnable number  " +  integer.getAndIncrement() + "= " +  instanceOne._salary);
            latch.countDown();
        });
        employeeModificationRunnable decreaseRunnable = new employeeModificationRunnable(() -> {
            instanceOne.decreaseSalary();
            System.out.println("instanceOne within a decreasedRunnable  number" + integer1.getAndIncrement() + "= " + instanceOne._salary);
            latch.countDown();
        });

        executor.execute(increaseRunnable);
        executor.execute(decreaseRunnable);
        executor.close();
        System.out.println("instanceOne exit salary = " + instanceOne._salary);
    }

     static class employee{

        //! Fields
        private String _name;
        private BigInteger _salary;

        public employee(String name, BigInteger salary){
                this._name = name;
                this._salary = salary;
        }

        //! Method to change salary
        public final  synchronized void  increaseSalary(){this._salary =
                this._salary.add(BigInteger.valueOf(100));}
        public final synchronized void decreaseSalary(){this._salary =
                this._salary.subtract(BigInteger.valueOf(100));}

    }

    static class employeeModificationRunnable implements Runnable{
        private int currIter = 0;
        private final Runnable instance;

        public employeeModificationRunnable(Runnable running){
            this.instance = running ;
        }


        @Override
        public void run() {
            //! Fields
            int maxIter = 10;
            while (currIter < maxIter){
                this.instance.run();
                currIter++;
            }
            Thread.yield();
        }
    }
}
