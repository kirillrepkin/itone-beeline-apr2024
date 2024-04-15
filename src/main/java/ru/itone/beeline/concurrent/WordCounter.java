package ru.itone.beeline.concurrent;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordCounter implements Runnable {

    private static Logger log = LoggerFactory.getLogger(WordCounter.class);

    private final BlockingQueue<String> queue;

    private final Map<String, Integer> result = new ConcurrentHashMap<String, Integer>();

    private boolean idle = true;

    public WordCounter(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String line;

        while(true) {
            if ((line = queue.poll()) != null) {
                idle = false;
                if(result.get(line) != null) {
                    result.put(line, result.get(line)+1);
                } else {
                    result.put(line, 1);
                }
            } else {
                idle = true;
            }
        }
    }

    public Map<String, Integer> getResult() {
        return result;
    }

    public boolean isIdle() {
        return idle;
    }

    public void print() {
        result.entrySet().forEach(e -> log.info("RESULT: {} -> {}", e.getKey(), e.getValue()));
    }
}
