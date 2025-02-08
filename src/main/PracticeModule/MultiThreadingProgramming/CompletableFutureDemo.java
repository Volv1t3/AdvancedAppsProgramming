package PracticeModule.MultiThreadingProgramming;


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
