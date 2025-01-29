package PracticeModule.MultiThreadingProgramming;


// Example implementation demonstrating synchronization blocks in a university course registration system.
// This system ensures thread safety during validation and registration using synchronized blocks.

import java.util.HashMap;
import java.util.Map;

public class CourseRegistrationSystem {
    private final Map<String, Integer> courses = new HashMap<>();

    // Constructor to initialize available courses with maximum seats
    public CourseRegistrationSystem() {
        courses.put("Mathematics", 3); // Max 3 seats
        courses.put("Physics", 2);    // Max 2 seats
        courses.put("Computer Science", 5); // Max 5 seats
    }

    // Method to validate and register a course using synchronized blocks
    public void registerCourse(String courseName, String studentName) {
        synchronized (courses) { // Synchronizing block on shared resource `courses`
            if (!courses.containsKey(courseName)) {
                System.out.println(
                        "Course `" + courseName + "` does not exist for " + studentName + ".");
                return;
            }

            // Check course availability
            int seatsAvailable = courses.get(courseName);
            if (seatsAvailable > 0) {
                System.out.println("Registering " + studentName + " for course: " + courseName);

                // Update seats within the synchronized block to prevent race conditions
                courses.put(courseName, seatsAvailable - 1);
                System.out.println("Registration successful! Remaining seats: " +
                                           (seatsAvailable - 1));
            } else {
                System.out.println("No seats available for " + courseName + " for student: " +
                                           studentName + ".");
            }
        }
    }


    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        // Creating threads simulating multiple students registering concurrently
        Thread student1 = new Thread(() -> system.registerCourse("Mathematics", "Alice"));
        Thread student2 = new Thread(() -> system.registerCourse("Mathematics", "Bob"));
        Thread student3 = new Thread(() -> system.registerCourse("Physics", "Charlie"));
        Thread student4 = new Thread(() -> system.registerCourse("Mathematics", "Eve"));
        Thread student5 = new Thread(() -> system.registerCourse("Physics", "Dave"));
        Thread student6 = new Thread(() -> system.registerCourse("Physics", "Santiago"));

        // Start threads
        student1.start();
        student2.start();
        student3.start();
        student4.start();
        student5.start();
        student6.start();

        // Wait for all threads to complete
        try {
            student1.join();
            student2.join();
            student3.join();
            student4.join();
            student5.join();
            student6.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread execution was interrupted.");
        }

        // Print the final course availability
        System.out.println("\nFinal course availability:");
        system.courses.forEach((course, seats) ->
                                       System.out.println(course + ": " + seats + " seats remaining"));
    }
}