package PracticeModule.MultiThreadingProgramming;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CourseRegistrationSystemV2 {
    private final Map<String, Integer> courses = new HashMap<>();
    private final Map<String, LinkedList<String>> courseStudentMapping = new ConcurrentHashMap<>();
    // Constructor to initialize available courses with maximum seats
    public CourseRegistrationSystemV2() {
        courses.put("Mathematics", 3);// Max 3 seats
        courseStudentMapping.put("Mathematics", new LinkedList<>());
        courses.put("Physics", 2);           // Max 2 seats
        courseStudentMapping.put("Physics", new LinkedList<>());
        courses.put("Computer Science", 5);  // Max 5 seats
        courseStudentMapping.put("Computer Science", new LinkedList<>());
    }

    // Synchronized method to validate course availability
    public synchronized boolean validateCourseAvailability(String courseName, String studentName) {
        if (!courses.containsKey(courseName)) {
            printMessage("Course `" + courseName + "` does not exist for " + studentName + ".");
            return false;
        }
        if (courseStudentMapping.get(courseName).contains(studentName)){
            return false; //! Student already registered
        }
        if (courses.get(courseName) > 0) {
            return true; // Seats are available
        } else {
            printMessage("No seats available for course `" + courseName + "` for student: " + studentName + ".");
            return false;
        }
    }

    // Synchronized method for registering a student to a course
    public synchronized void registerCourse(String courseName, String studentName) {
        if (validateCourseAvailability(courseName, studentName)) {
            int seatsAvailable = courses.get(courseName);
            this.courses.put(courseName, seatsAvailable - 1);
            this.courseStudentMapping.get(courseName).add(studentName);
            printMessage(studentName + " successfully registered for `" + courseName + "`. Remaining seats: " + (seatsAvailable - 1));
        }
    }

    // Synchronized method for dropping a course
    public synchronized void dropCourse(String courseName, String studentName) {
        if (!courses.containsKey(courseName)) {
            printMessage("Course `" + courseName + "` does not exist for " + studentName + ".");
            return;
        }
        if (this.courseStudentMapping.get(courseName).contains(studentName)) {
            int seatsAvailable = courses.get(courseName);
            this.courses.put(courseName, seatsAvailable + 1);
            this.courseStudentMapping.get(courseName).remove(studentName);
            printMessage(studentName + " has dropped the course `" + courseName + "`. Available seats: " + (seatsAvailable + 1));
        }
        else {
            printMessage(studentName + " is not registered for `" + courseName + "`.");
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
                                System.out.println(course + ": " + seats + " seats remaining"));
    }

    public static void main(String[] args) {
        CourseRegistrationSystemV2 system = new CourseRegistrationSystemV2();

        system.printFinalAvailability();
        // Creating threads simulating multiple student registrations and dropouts
        Thread student1 = new Thread(() -> system.registerCourse("Mathematics", "Alice"));
        Thread student2 = new Thread(() -> system.registerCourse("Mathematics", "Bob"));
        Thread student3 = new Thread(() -> system.registerCourse("Physics", "Charlie"));
        Thread student4 = new Thread(() -> system.registerCourse("Mathematics", "Eve"));
        Thread student5 = new Thread(() -> system.dropCourse("Mathematics", "Alice")); // Alice drops out
        Thread student6 = new Thread(() -> system.dropCourse("Physics", "Charlie"));  // Charlie drops out
        Thread student7 = new Thread(() -> system.registerCourse("Physics", "Dave")); // Dave registers
        Thread student8 = new Thread(() -> system.registerCourse("Physics", "Santiago")); // Santiago registers
        Thread thread = new Thread(System.out::println);
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
