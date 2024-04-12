package ru.itone.beeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * App
 */
public class App 
{
    private static BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private static Logger log = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        log.info("Application starting...");

        Thread wCounter = new Thread(new WordCounter(queue));
        wCounter.start();

        Thread fProc = new Thread(new FileProcessor(queue, null));
        fProc.start();

        try {
            wCounter.join();
            fProc.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
