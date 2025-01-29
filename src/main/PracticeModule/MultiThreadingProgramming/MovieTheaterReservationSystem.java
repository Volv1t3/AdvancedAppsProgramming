package PracticeModule.MultiThreadingProgramming;

import java.util.concurrent.*;
        import java.util.*;

// Record to represent a seat reservation
record SeatReservation(String userName, long reservationTime, int seatNumber) {}

// Class representing a Seat
class Seat {
    private final int seatNumber;
    private boolean reserved;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.reserved = false;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public synchronized boolean reserve() {
        if (!reserved) {
            reserved = true;
            return true;
        }
        return false;
    }

}

// Class representing a User
class User {
    private final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

// Callable task to reserve a seat
class SeatReservationTask implements Callable<SeatReservation> {
    private final User user;
    private final int seatNumber;
    private final Map<Integer, Seat> seatMap;

    public SeatReservationTask(User user, int seatNumber, Map<Integer, Seat> seatMap) {
        this.user = user;
        this.seatNumber = seatNumber;
        this.seatMap = seatMap;
    }

    @Override
    public SeatReservation call() {
        Seat seat = seatMap.get(seatNumber);
        if (seat != null && seat.reserve()) {
            return new SeatReservation(user.getName(), System.currentTimeMillis(), seat.getSeatNumber());
        } else {
            throw new IllegalStateException("Seat " + seatNumber + " is already reserved or does not exist.");
        }
    }

}

// Main class to simulate the movie theater seat reservation system
public class MovieTheaterReservationSystem {
    private final Map<Integer, Seat> seatMap = new HashMap<>();
    private final Map<String, SeatReservation> reservationMap = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public MovieTheaterReservationSystem(int totalSeats) {
        for (int i = 1; i <= totalSeats; i++) {
            seatMap.put(i, new Seat(i));
        }
    }

    public Future<SeatReservation> reserveSeat(User user, int seatNumber) {
        SeatReservationTask task = new SeatReservationTask(user, seatNumber, seatMap);
        return executor.submit(task);
    }

    public static void main(String[] args) {
        MovieTheaterReservationSystem system = new MovieTheaterReservationSystem(10);

        // Define some users
        User user1 = new User("Alice");
        User user2 = new User("Bob");
        User user3 = new User("Eve");

        try {
            // Simulate concurrent seat reservation
            Future<SeatReservation> future1 = system.reserveSeat(user1, 5);
            Future<SeatReservation> future2 = system.reserveSeat(user2, 5); // Trying to reserve the same seat
            Future<SeatReservation> future3 = system.reserveSeat(user3, 7);

            // Handle the results
            try {
                System.out.println("Reservation Successful: " + future1.get());
            } catch (ExecutionException e) {
                System.out.println("Reservation Failed for Alice: " + e.getCause().getMessage());
            }

            try {
                System.out.println("Reservation Successful: " + future2.get());
            } catch (ExecutionException e) {
                System.out.println("Reservation Failed for Bob: " + e.getCause().getMessage());
            }

            try {
                System.out.println("Reservation Successful: " + future3.get());
            } catch (ExecutionException e) {
                System.out.println("Reservation Failed for Eve: " + e.getCause().getMessage());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("The reservation process was interrupted.");
        } finally {
            system.executor.shutdown();
        }
    }

}