package ru.itone.beeline.database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.itone.beeline.database.entity.UserWebVisit;

public class InitDataGenerator {

    private static final String DEFAULT_FILE_PATH = "data/sql/init.sql";

    private static final int DEFAULT_ROWS_COUNT = 100;

    private static final String SQL_TABLE_DDL = """
    create table if not exists `public`.`user_web_visit` (
        `login` varchar(255),
        `location` varchar(255),
        `date_time_ts` timestamp default current_timestamp
    );
    """;
    
    private static final String SQL_INSERT_PREFIX = """
    insert into `public`.`user_web_visit` (`login`, `location`, `date_time_ts`) values
    """;
    
    private static final String NEW_LINE = "\r\n";

    private static Logger log = LoggerFactory.getLogger(InitDataGenerator.class);

    public static void main(String[] args) {
        long count = args.length >= 1 ? Long.parseLong(args[0]) : DEFAULT_ROWS_COUNT;
        String output = args.length >= 2 ? args[1] : DEFAULT_FILE_PATH;
        log.info("Generating {} with {} rows", output, count);
        generateSqlFile(count, output);
    }

    private static void generateSqlFile(long count, String output) {
        File file = new File(output);
        if(file.exists()) {
            file.delete();
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(SQL_TABLE_DDL);
            bw.write(NEW_LINE);
            bw.write(SQL_INSERT_PREFIX);
            String line;
            for(int i=0; i<count; i++) {
                UserWebVisit row = UserWebVisit.random();
                line = "\t('"+row.getLogin()+"', '"+row.getLocation()+"', '"
                    +row.getDateTime().minusMinutes(Long.valueOf(count/60).intValue())+"')";
                bw.write(line);
                bw.write(i==count-1 ? ";" : ","+NEW_LINE);
            }
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
