package PracticeModule.MultiThreadingProgramming;

import java.util.ArrayList;
import java.util.List;


public class MusicDownloaderIssues {

    private List<String> musicLibrary = new ArrayList<>();
    private String currentlyPlaying;

    // Unsynchronized method to simulate downloading music
    public void downloadMusic(String url) {
        System.out.println(Thread.currentThread().getName() + " downloading: " + url);
        musicLibrary.add(url); // Shared resource accessed unsafely
    }

    // Unsynchronized method to simulate playing music
    public void playMusic() {
        if (!musicLibrary.isEmpty()) {
            currentlyPlaying = musicLibrary.remove(0); // Shared resource accessed unsafely
            System.out.println(Thread.currentThread().getName() + " playing: " + currentlyPlaying);
        } else {
            System.out.println(Thread.currentThread().getName() + " nothing to play!");
        }
    }

    public synchronized void printState(){
        System.out.println("musicLibrary = " + musicLibrary);
        System.out.println("Currently Playing: " + currentlyPlaying);
    }

    public static void main(String[] args) {
        MusicDownloaderIssues downloader = new MusicDownloaderIssues();

        // Thread 1 - Downloads music
        Thread downloadThread = new Thread(() -> {
            downloader.downloadMusic("http://example.com/song1.mp3");
            downloader.printState();
            downloader.downloadMusic("http://example.com/song2.mp3");
            downloader.printState();
        }, "DownloadThread");

        // Thread 2 - Plays music
        Thread playThread = new Thread(() -> {
            downloader.playMusic();
            downloader.printState();
            downloader.playMusic();
            downloader.printState();
            downloader.playMusic();
        }, "PlayThread");

        playThread.start();
        downloadThread.start();

        try {
            downloadThread.join();
            playThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        System.out.println("Simulation completed!");
    }
}

class MusicDownloader {

    private final List<String> musicLibrary = new ArrayList<>();
    private String currentlyPlaying;

    // Synchronized method to simulate downloading music
    public synchronized void downloadMusic(String url) {
        System.out.println(Thread.currentThread().getName() + " downloading: " + url);
        musicLibrary.add(url); // Shared resource accessed safely
    }

    // Synchronized method to simulate playing music
    public synchronized void playMusic() {
        if (!musicLibrary.isEmpty()) {
            currentlyPlaying = musicLibrary.remove(0); // Shared resource accessed safely
            System.out.println(Thread.currentThread().getName() + " playing: " + currentlyPlaying);
        } else {
            System.out.println(Thread.currentThread().getName() + " nothing to play!");
        }
    }

    public static void main(String[] args) {
        MusicDownloader downloader = new MusicDownloader();

        // Thread 1 - Downloads music
        Thread downloadThread = new Thread(() -> {
            downloader.downloadMusic("http://example.com/song1.mp3");
            downloader.downloadMusic("http://example.com/song2.mp3");
        }, "DownloadThread");

        // Thread 2 - Plays music
        Thread playThread = new Thread(() -> {
            downloader.playMusic();
            downloader.playMusic();
        }, "PlayThread");

        downloadThread.start();
        playThread.start();

        try {
            downloadThread.join();
            playThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        System.out.println("Simulation completed!");
    }
}
