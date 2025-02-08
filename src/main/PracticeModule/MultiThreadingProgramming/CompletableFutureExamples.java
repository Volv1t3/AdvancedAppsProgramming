package PracticeModule.MultiThreadingProgramming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * @author : Santiago Arellano
 * @date : February 6th 2024
 * @description : The following file will contain internal classes with their own main methods
 * designed to showcase a way of working with Completable Futures.
 */
public class CompletableFutureExamples {

    /*
     * Our first Example will take a look at an Employee manager class, lets say we want to
     * asynchronosly modify a list of a 1000 employees, setting their salary to a value and
     * sleeping the thread for 1 second.
     */
    public static class AsyncEmployeeModification {
        private final List<Employee> employees =
                Collections.synchronizedList(
                        IntStream.rangeClosed(1, 1000).mapToObj(new IntFunction<Employee>() {
                                                                    @Override
                                                                    public Employee apply(int value) {
                                                                        return new Employee();
                                                                    }
                                                                }
                                                               ).toList());

        public static void main(String[] args) {
            /*
             * Let us say now that our boss asked us to modify each of these employees, having it
             * run asynchronously while we are also working on the main thread counting and
             * priting a couter.
             */
            AsyncEmployeeModification modification = new AsyncEmployeeModification();
            CompletableFuture<Void> future =
                    CompletableFuture.runAsync(new EmployeeModifier(modification.employees));
            for (int i = 0; i < 1000; i++) {
                System.out.println("On Thread=" + Thread.currentThread().getName() + "value= " + i);
            }
            modification.employees.forEach(System.out::println);

        }


        private static final class EmployeeModifier implements Runnable {

            List<Employee> employees;

            public EmployeeModifier(List<Employee> modification){
                this.employees = modification;
            }

            @Override
            public void run() {
                employees.forEach(employee -> {
                    employee.setSalary(2000);
                    System.out.println("On thread = " + Thread.currentThread().getName()
                                               + ", employee = " + employee);
                });
            }
        }

    }

    /*
     * Lets work with something else, we are going to modify a concurrent List
     */
}

class Employee {
    private String name;
    private double salary;

    public Employee(){}

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString(){
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
