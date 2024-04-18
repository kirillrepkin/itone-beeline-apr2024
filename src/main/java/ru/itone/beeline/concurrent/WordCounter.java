package ru.itone.beeline.concurrent;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordCounter implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(WordCounter.class);

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
                    result.put(line, Integer.valueOf(result.get(line) + 1));
                } else {
                    result.put(line, Integer.valueOf(1));
                }
            } else {
                idle = true;
            }
        }
    }

    public boolean isIdle() {
        return idle;
    }

    public Map<String, Integer> getSortedResult() {
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(result.entrySet());
        list.sort(new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> v1, Entry<String, Integer> v2) {
                return (v1.getValue()).compareTo(v2.getValue());
            }
        });
        Collections.reverse(list);
        Map<String, Integer> output = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            output.put(entry.getKey(), entry.getValue());
        }
        return output;
    }

    public void printSorted(Integer topNum) {
        getSortedResult().entrySet().stream()
            .limit(topNum)
            .forEach(e -> log.info("RESULT: \"{}\" used {} times", e.getKey(), e.getValue()));
    }
}
