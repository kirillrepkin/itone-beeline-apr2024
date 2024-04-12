package ru.itone.beeline;

import java.util.concurrent.BlockingQueue;

public class WordCounter implements Runnable {

    private final BlockingQueue<String> queue;

    public WordCounter(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}
