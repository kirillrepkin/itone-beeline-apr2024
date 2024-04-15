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

    private static Logger log = LoggerFactory.getLogger(FileProcessor.class);

    private final BlockingQueue<String> queue;
    private final String fName;

    private Pattern wordPtrn = Pattern.compile(
        "\\w{15,}+", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

    public FileProcessor(BlockingQueue<String> queue, String fName) {
        this.queue = queue;
        this.fName = fName;
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
                    substr = fLine.substring(matcher.start(), matcher.end());
                    log.info("Adding item: {}", substr);
                    queue.offer(substr);
                }
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
