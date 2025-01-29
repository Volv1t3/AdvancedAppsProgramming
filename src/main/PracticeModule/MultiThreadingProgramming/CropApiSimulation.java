package PracticeModule.MultiThreadingProgramming;


import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

// Simulated HTTP API class with GET and POST functionality
class SimpleHttpApi {
    private final Map<String, String>
            database = new ConcurrentHashMap<>();

    // Simulated GET method
    public synchronized String get(String key) {
        return database.getOrDefault(key, "No data available");
    }

    // Simulated POST method
    public synchronized void post(String key, String value) {
        database.put(key, value);
    }
}

// Thread class for POST requests
class PostThread extends Thread {
    private final SimpleHttpApi api;
    private final String key;
    private final String value;

    public PostThread(SimpleHttpApi api, String key, String value) {
        this.api = api;
        this.key = key;
        this.value = value;
    }

    @Override
    public void run() {
        System.out
                .println("Processing POST request: Key = "
                                 + key + ", Value = " + value);
        api.post(key, value);
        System.out.println("POST completed: Key = " + key);
    }
}

// Runnable class for GET requests
class GetRunnable implements Runnable {
    private final SimpleHttpApi api;
    private final String key;

    public GetRunnable(SimpleHttpApi api, String key) {
        this.api = api;
        this.key = key;
    }

    @Override
    public void run() {
        System.out.println("Processing GET request for Key = " + key);
        String result = api.get(key);
        System.out
                .println("GET result: Key = "
                                 + key + ", Value = " + result);
    }
}

// Main class to run the program
public class CropApiSimulation {
    public static void main(String[] args) {
        SimpleHttpApi simulatedApi = new SimpleHttpApi();

        // Simulate POST requests
        Thread postCarrot =
                new PostThread(simulatedApi, "carrot", "Genus: Daucus");
        Thread postOnion =
                new PostThread(simulatedApi, "onion", "Genus: Allium");

        // Simulate GET requests
        Thread getCarrot =
                new Thread(new GetRunnable(simulatedApi, "carrot"));
        Thread getOnion =
                new Thread(new GetRunnable(simulatedApi, "onion"));
        // Non-existing key
        Thread getPotato =
                new Thread(new GetRunnable(simulatedApi, "potato"));

        // Starting POST threads
        postCarrot.start();
        postOnion.start();

        try {
            // Wait for POST operations to finish
            postCarrot.join();
            postOnion.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        // Starting GET threads
        getCarrot.start();
        getOnion.start();
        getPotato.start();

        try {
            // Wait for GET operations to finish
            getCarrot.join();
            getOnion.join();
            getPotato.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        System.out.println("Simulation completed!");
    }
}
