package PracticeModule.MultiThreadingProgramming;

import java.util.ArrayList;
import java.util.List;

public class FileDownloaderIssues {

    private List<String> downloadedFiles = new ArrayList<>();
    private boolean downloadComplete = false;

    public synchronized void downloadFile(String fileName) {
        System.out
                .println(Thread.currentThread().getName()
                                 + " downloading: " + fileName);

        // Simulate download delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err
                    .println("Download interrupted: "
                                     + e.getMessage());
        }

        downloadedFiles.add(fileName);
        downloadComplete = true; // Memory consistency issue:
        // No synchronization
    }

    public void verifyFiles() {
        synchronized (this){
            if (downloadComplete) { // Memory consistency issue:
                // May read stale value
                System.out.println(Thread.currentThread().getName()
                                           + " verifying files: " + downloadedFiles);
            } else {
                System.out.println(
                        Thread.currentThread().getName()
                                + " no files to verify!");
            }
        }
    }

    public static void main(String[] args) {
        FileDownloaderIssues fileDownloader = new FileDownloaderIssues();

        // Thread 1 - Downloads files
        Thread downloadThread = new Thread(() -> {
            fileDownloader.downloadFile("file1.txt");
            fileDownloader.downloadFile("file2.txt");
        }, "DownloadThread");

        // Thread 2 - Verifies files
        Thread verificationThread = new Thread(() -> {
            fileDownloader.verifyFiles();
        }, "VerificationThread");

        downloadThread.start();
        verificationThread.start();

        try {
            downloadThread.join();
            verificationThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: "
                                       + e.getMessage());
        }

        System.out.println("Simulation completed!");
    }
}
