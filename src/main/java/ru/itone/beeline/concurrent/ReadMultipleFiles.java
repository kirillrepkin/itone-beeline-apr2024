package ru.itone.beeline.concurrent;

import java.io.File;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadMultipleFiles implements Runnable {

    private static Logger log = LoggerFactory.getLogger(ReadMultipleFiles.class);
    private static BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final String baseDir;

    public ReadMultipleFiles(String baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public void run() {

        Set<Thread> procs = Stream.of(new File(baseDir).listFiles())
            .filter(file -> file.isFile() && !file.isHidden())
            .map(f -> {
                log.info("Thread for {}", f.getName());
                Thread t = new Thread(new FileProcessor(queue, f.getAbsolutePath()));
                t.start();
                return t;
            }).collect(Collectors.toSet());

        procs.stream().forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        });

        WordCounter counter = new WordCounter(queue);
        Thread thCounter = new Thread(counter);
        thCounter.start();

        while(procs.stream().collect(Collectors.summarizingInt(t -> t.isAlive() ? 1 : 0)).getSum() > 0 && !counter.isIdle()) {
            continue;
        }
        counter.print();
        thCounter.interrupt();

        log.info("Read complete");
    }

}
