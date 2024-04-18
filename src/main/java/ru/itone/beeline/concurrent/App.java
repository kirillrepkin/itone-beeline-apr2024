package ru.itone.beeline.concurrent;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App
 */
public class App 
{
    
    private static final Logger log = LoggerFactory.getLogger(App.class);
    public static void main(String[] args)
    {
        log.info("Application starting...");

        if(args.length == 0 || !(new File(args[0]).isDirectory())) {
            log.error("The path is not a directory");
        } else {
            Integer minLength = (Integer) (args.length >= 2 ? Integer.parseInt(args[1]) : 10);
            Integer topNum = (Integer) (args.length >= 3 ? Integer.parseInt(args[2]): 10);
            ReadMultipleFiles readMultipleFiles = new ReadMultipleFiles(args[0], minLength, topNum);
            Thread rmfThread = new Thread(readMultipleFiles);
            rmfThread.start();
            try {
                rmfThread.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }

        log.info("Application finished.");
    }
}
