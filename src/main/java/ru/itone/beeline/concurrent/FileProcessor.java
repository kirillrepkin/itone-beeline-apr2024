package ru.itone.beeline.concurrent;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileProcessor implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(FileProcessor.class);

    private final BlockingQueue<String> queue;
    private final String fName;

    private final Pattern wordPtrn;

    public FileProcessor(BlockingQueue<String> queue, String fName, Integer minLength) {
        this.queue = queue;
        this.fName = fName;
        this.wordPtrn = Pattern.compile(
            "\\w{" + minLength + ",}+", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fName))) {
            String fLine;
            String substr;
            Matcher matcher;
            while ((fLine = reader.readLine()) != null) {
                matcher = wordPtrn.matcher(fLine);
                while(matcher.find()) {
                    substr = fLine.substring(matcher.start(), matcher.end()).toLowerCase();
                    log.debug("Adding item: {}", substr);
                    queue.offer(substr);
                }
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
