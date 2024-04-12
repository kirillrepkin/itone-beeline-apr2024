package ru.itone.beeline;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;

public class FileProcessor implements Runnable {

    private final BlockingQueue<String> queue;
    private final String fName;

    public FileProcessor(BlockingQueue<String> queue, String fName) {
        this.queue = queue;
        this.fName = fName;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fName))) {
            String fLine;
            while ((fLine = reader.readLine()) != null) {
                queue.offer(fLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
