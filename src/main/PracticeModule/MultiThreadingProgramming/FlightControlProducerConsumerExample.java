package PracticeModule.MultiThreadingProgramming;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FlightControlProducerConsumerExample {

    public static class Airplane{
        private int remainingFuelInMinutes;
        private String airplanePilotName;
        private String airplaneAirline;

        public Airplane(int remainingFuelInMinutes, String airplanePilotName, String airplaneAirline) {
            this.remainingFuelInMinutes = remainingFuelInMinutes;
            this.airplanePilotName = airplanePilotName;
            this.airplaneAirline = airplaneAirline;
        }
        public int getRemainingFuelInMinutes() {
            return remainingFuelInMinutes;
        }
        public void setRemainingFuelInMinutes(int remainingFuelInMinutes) {
            this.remainingFuelInMinutes = remainingFuelInMinutes;
        }
        public String getAirplanePilotName() {
            return airplanePilotName;
        }
        public void setAirplanePilotName(String airplanePilotName) {
            this.airplanePilotName = airplanePilotName;
        }
        public String getAirplaneAirline() {
            return airplaneAirline;
        }
        public void setAirplaneAirline(String airplaneAirline) {
            this.airplaneAirline = airplaneAirline;
        }

        @Override
        public String toString() {
            return "Airplane{" +
                    "remainingFuelInMinutes=" + remainingFuelInMinutes +
                    ", airplanePilotName='" + airplanePilotName + '\'' +
                    ", airplaneAirline='" + airplaneAirline + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !obj.getClass().equals(this.getClass())){return false;}
            else if (obj == this) {return true;}
            else {
                Airplane another = (Airplane) obj;
                return this.getAirplaneAirline().equals(another.getAirplaneAirline());
            }
        }
    }

    public static class Runway{

        private final ArrayDeque<Airplane> runwayPlanesThatLanded =
                new ArrayDeque<>(2);
        private final AtomicInteger runwayTotalPlanesLanded = new AtomicInteger(0);
        private final Lock lockingMechanismForRunway = new ReentrantLock(true);
        private final Condition conditionForRunwaySize = lockingMechanismForRunway.newCondition();
        private final Condition conditionForRunwayEmpty = lockingMechanismForRunway.newCondition();


        public void landAirplaneOnRunway(Airplane planeReadyToLand){
            //! Acquire lock
            lockingMechanismForRunway.lock();
            try{
                //! Add plane into the runway if there is space for him
                while (runwayPlanesThatLanded.size() == 2){
                    displayMessage("Runway is full, waiting for runway to be empty");
                    conditionForRunwaySize.await();
                }

                //! If the condition is over then we can land a plane
                runwayPlanesThatLanded.add(planeReadyToLand);
                displayMessage("Plane " + planeReadyToLand.getAirplaneAirline() + " landed on " +
                                       "runway");
                runwayTotalPlanesLanded.incrementAndGet();
                //! Notify all waiting threads because it was empty that they can come and
                //! register a plane
                conditionForRunwayEmpty.signalAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lockingMechanismForRunway.unlock();
            }
        }

        public void registerAnAirplane(){
            //! Acquire lock
            lockingMechanismForRunway.lock();
            try{
                //! Wait until there is a plane on the runway
                while (runwayPlanesThatLanded.isEmpty()){
                    displayMessage("Runway is empty, waiting for runway to be full");
                    conditionForRunwayEmpty.await();
                }

                //! If the condition is over then we can register a plane
                Airplane planeToRegister = runwayPlanesThatLanded.pollFirst();
                displayMessage("Plane " + planeToRegister.getAirplaneAirline() + " registered");
                runwayTotalPlanesLanded.decrementAndGet();
                //! Notify all waiting threads because it was full that they can come and
                //! land a plane
                conditionForRunwaySize.signalAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lockingMechanismForRunway.unlock();
            }
        }


        public synchronized void displayMessage(String msg){
            System.out.println(msg);
        }
    }

    public static class ProducerConsumerImplementations{

        private final List<Airplane> planesToLandHolder;
        private final Runway runway = new Runway();
        private volatile boolean fuelReductionActive =true;
        private Thread fuelReductionThread;

        public ProducerConsumerImplementations(ArrayList<Airplane> planesToLand) {
            //! Fill up internal data based on a filtering operation over planesToLand
            this.planesToLandHolder = Collections.synchronizedList(planesToLand);
        }

        public void startFuelReduction() {
            fuelReductionThread = new Thread(this::drippingFuel, "FuelReductionThread");
            fuelReductionThread.start();

        }

        public void stopFuelReduction() {
            fuelReductionActive = false;
            if (fuelReductionThread != null) {
                fuelReductionThread.interrupt();
            }
        }

        //! With the data separated, lets categorize them in the consumer based on a roundrobin
        //! style. While I am not familiar with the correct implementation, I am going to
        // prioritize removing elements from criticalTime rather than the rest.

        public class PlaneRunwayProducer implements Runnable {

            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    Airplane planeToLand = null;

                    synchronized (planesToLandHolder) {
                        if (planesToLandHolder.isEmpty()) {
                            break;
                        }
                        // Priority: Critical planes first
                        for (Iterator<Airplane> iterator = planesToLandHolder.iterator();
                             iterator.hasNext(); ) {
                            Airplane plane = iterator.next();
                            if (plane.getRemainingFuelInMinutes() <= 5) { // CRITICAL_THRESHOLD
                                planeToLand = plane;
                                iterator.remove();
                                break;
                            }
                        }

                        // If no critical planes, take from risk queue
                        if (planeToLand == null) {
                            for (Iterator<Airplane> iterator = planesToLandHolder.iterator();
                                 iterator.hasNext(); ) {
                                Airplane plane = iterator.next();
                                if (plane.getRemainingFuelInMinutes() <= 15) { // RISK_THRESHOLD
                                    planeToLand = plane;
                                    iterator.remove();
                                    break;
                                }
                            }
                        }
                    }

                    if (planeToLand != null) {
                        runway.landAirplaneOnRunway(planeToLand);
                        runway.displayMessage("Plane landed: " + planeToLand);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // Optional: Sleep or wait based on runway availability
                    planesToLandHolder.remove(planeToLand);
                }
            }
        }

        public class PlaneRunwayConsumer implements Runnable{

            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        runway.registerAnAirplane();
                        Thread.sleep(2000);

                    }
                } catch (Exception e) {
                    runway.displayMessage("Consumer interrupted.");
                }
            }
        }


        /**
         * Continuously reduces the remaining fuel of each airplane by one every second.
         */
        public void drippingFuel() {
            while (fuelReductionActive && !Thread.currentThread().isInterrupted()) {
                try {
                    synchronized (planesToLandHolder) {
                        if (planesToLandHolder.isEmpty()){
                            fuelReductionActive = false;
                        }
                        for (Airplane airplane : planesToLandHolder) {
                            if (airplane.getRemainingFuelInMinutes() > 0) {
                                airplane.setRemainingFuelInMinutes(airplane.getRemainingFuelInMinutes() - 1);
                                // Optionally, log or handle planes that reach zero fuel
                                if (airplane.getRemainingFuelInMinutes() == 0) {
                                    runway.displayMessage("Plane out of fuel: " + airplane);
                                    runway.landAirplaneOnRunway(airplane);
                                }
                            }
                        }
                    }
                    // Wait for 1 second before next iteration
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    runway.displayMessage("Fuel reduction thread interrupted.");
                }
            }
        }
    }

    public static void main(String[] args) {
        // Initialize Runway
        FlightControlProducerConsumerExample.Runway runway = new FlightControlProducerConsumerExample.Runway();

        // Initialize planes
        ArrayList<FlightControlProducerConsumerExample.Airplane> initialPlanes = new ArrayList<>();
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(10, "John Doe", "AirlineA"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(20, "Jane Smith", "AirlineB"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(5, "Mike Johnson", "AirlineC"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(15, "Alice Brown", "AirlineD"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(25, "Bob Martin", "AirlineE"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(3, "Charlie White", "AirlineF"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(8, "David Black", "AirlineG"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(18, "Ella Green", "AirlineH"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(12, "Frank Yellow", "AirlineI"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(28, "Grace Purple", "AirlineJ"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(4, "Hank Blue", "AirlineK"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(22, "Ivy Orange", "AirlineL"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(6, "Jack Red", "AirlineM"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(16, "Karen Gray", "AirlineN"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(30, "Leo Pink", "AirlineO"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(2, "Mia Violet", "AirlineP"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(9, "Noah Brown", "AirlineQ"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(14, "Olivia Cyan", "AirlineR"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(23, "Paul Gold", "AirlineS"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(7, "Quincy White", "AirlineT"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(19, "Rachel Amber", "AirlineU"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(27, "Sam Silver", "AirlineV"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(1, "Tom Cash", "AirlineW"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(13, "Uma Dawn", "AirlineX"));
        initialPlanes.add(new FlightControlProducerConsumerExample.Airplane(11, "Victor Gray", "AirlineY"));
        // Add more planes as needed

        FlightControlProducerConsumerExample.ProducerConsumerImplementations pci =
                new FlightControlProducerConsumerExample.ProducerConsumerImplementations(initialPlanes);

        // Start the fuel reduction thread
        pci.startFuelReduction();

        ExecutorService service = Executors.newFixedThreadPool(2);
        try {
            // Submit tasks and get futures
            Future<?> producerFuture = service.submit(pci.new PlaneRunwayProducer());
            Future<?> consumerFuture = service.submit(pci.new PlaneRunwayConsumer());

            // Wait for producer to complete
            try {
                producerFuture.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            // Once producer is done, cancel consumer
            consumerFuture.cancel(true);

            // Shutdown the executor service
            service.shutdown();
            if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        } finally {
            pci.stopFuelReduction();
        }
    }

}
